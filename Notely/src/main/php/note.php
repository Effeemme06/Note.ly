<?php

$conn = new mysqli("localhost", "root", "", "notely");

function isTokenUsed($token)
{

    global $conn;

    $sql = "SELECT COUNT(*) AS num FROM utente WHERE utente.token = ?";
    $stmt = $conn->prepare($sql);

    $stmt->bind_param("s", $token);
    $stmt->execute();
    $result = $stmt->get_result();
    $row = $result->fetch_assoc();

    if (isset($row['num']) && $row['num'] == 0) {
        return false;
    } else if ($row['num'] > 0) {
        return true;
    } else {
        return false;
    }
}

function generateToken($length = 16)
{

    do {
        $token = substr(bin2hex(random_bytes($length)), 0, $length);
    } while (isTokenUsed($token) == true);

    return $token;
}

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    if (isset($_GET["auth"])) {

        if (isset($_GET["token"]) && isset($_GET["mod"])) {
            $xml = new SimpleXMLElement('<AuthenticationResults/>');

            $sql = "SELECT id FROM utente WHERE token = ?";
            $stmt = $conn->prepare($sql);

            $stmt->bind_param("s", $_GET["token"]);
            $stmt->execute();
            $result = $stmt->get_result();
            $row = $result->fetch_assoc();

            if (isset($row['id'])) {
                $id = $row['id'];
                $xml->addChild('success', 'true');
                $xml->addChild('userID', $id);
                $xml->addChild('message', 'Autenticazione riuscita. Benvenuto!');
                $xml->addChild('error', '');
                http_response_code(200);
            } else {
                $xml->addChild('success', 'false');
                $xml->addChild('userID', "-1");
                $xml->addChild('message', 'Autenticazione fallita. Username o password errati');
                $xml->addChild('error', 'Wrong username or password');
                http_response_code(401);
            }
            $stmt->close();

            if ($_GET["mod"] == "xml") {
                header('Content-Type: application/xml');
                echo $xml->asXML();
            } else if ($_GET["mod"] == "json") {
                header('Content-Type: application/json');
                echo json_encode($xml);
            }
        } else {
            http_response_code(400);
        }
    } else if (isset($_GET["checkUsername"])) {

        if (isset($_GET["username"]) && isset($_GET["mod"])) {
            $xml = new SimpleXMLElement('<AuthenticationResults/>');

            $sql = "SELECT COUNT(*) AS num FROM utente WHERE utente.login = ?";
            $stmt = $conn->prepare($sql);

            $stmt->bind_param("s", $_GET["username"]);
            $stmt->execute();
            $result = $stmt->get_result();
            $row = $result->fetch_assoc();

            if (isset($row['num']) && $row['num'] == 0) {
                $xml->addChild('success', 'true');
                $xml->addChild('message', 'Username disponibile!');
                $xml->addChild('error', '');
                http_response_code(200);
            } else {
                $xml->addChild('success', 'false');
                $xml->addChild('userID', "-1");
                $xml->addChild('message', 'Username in uso');
                $xml->addChild('error', 'Usarname already in use');
                http_response_code(401);
            }
            $stmt->close();

            if ($_GET["mod"] == "xml") {
                header('Content-Type: application/xml');
                echo $xml->asXML();
            } else if ($_GET["mod"] == "json") {
                header('Content-Type: application/json');
                echo json_encode($xml);
            }
        } else {
            http_response_code(400);
        }
    } else if (isset($_GET["fetchNotepads"])) {

        if (isset($_GET['token'], $_GET['mod'])) {
            $xml = new SimpleXMLElement('<Store/>'); // Radice <Store>
    
            $sql = "SELECT id, titolo, descrizione FROM blocco b WHERE id_utente = (SELECT id FROM utente WHERE token = ?)";
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("s", $_GET["token"]);
            $stmt->execute();
            $result = $stmt->get_result();
            
            if ($result->num_rows > 0) {
                while ($notepad_row = $result->fetch_assoc()) {
                    
                    // Per ogni Notepad, crea un nodo <Notepad> dentro <Store>
                    $notepad = $xml->addChild('NotePad');
                    $notepad->addAttribute('id', $notepad_row['id']);
                    $notepad->addChild('title', $notepad_row['titolo']);
                    $notepad->addChild('description', $notepad_row['descrizione']);

                    // Query per ottenere le Note associate al Notepad
                    $sql_notes = "SELECT id, titolo, corpo FROM nota WHERE id_blocco = ?";
                    $stmt_notes = $conn->prepare($sql_notes);
                    $stmt_notes->bind_param("i", $notepad_row['id']);
                    $stmt_notes->execute();
                    $notes_result = $stmt_notes->get_result();
    
                    while ($note_row = $notes_result->fetch_assoc()) {
                        $note = $notepad->addChild('Note');
                        $note->addAttribute('id', $note_row['id']);  // Attributo id
                        $note->addChild('title', htmlspecialchars($note_row['titolo']));
                        $note->addChild('body', htmlspecialchars($note_row['corpo']));
                    }
    
                    $stmt_notes->close();
                }
    
                http_response_code(200);
            } else {
                http_response_code(404); // Nessun Notepad trovato
            }
    
            $stmt->close();
    
            // Output in base al formato richiesto
            if ($_GET["mod"] == "xml") {
                header('Content-Type: application/xml');
                echo $xml->asXML();
            } else if ($_GET["mod"] == "json") {
                header('Content-Type: application/json');
                echo json_encode($xml);
            }
        }
    } else {
        http_response_code(400);
    }
}



if ($_SERVER['REQUEST_METHOD'] == "POST") {

    if (isset($_POST["addUser"])) {

        if (isset($_POST["username"]) && isset($_POST["name"]) && isset($_POST["surname"]) && isset($_POST["mod"])) {
            $xml = new SimpleXMLElement('<AuthenticationResults/>');

            $token = generateToken();

            $sql = "INSERT INTO utente (login, token, nome, cognome) VALUES (?, ?, ?, ?);";
            $stmt = $conn->prepare($sql);

            $stmt->bind_param("ssss", $_POST["username"], $token, $_POST["name"], $_POST["surname"]);

            if ($stmt->execute()) {

                $sql2 = "SELECT id FROM utente WHERE utente.login = ?";
                $stmt2 = $conn->prepare($sql2);

                $stmt2->bind_param("s", $_POST["username"]);
                $stmt2->execute();
                $result2 = $stmt2->get_result();
                $row2 = $result2->fetch_assoc();

                $id = $row2['id'];
                $xml->addChild('success', 'true');
                $xml->addChild('userID', $id);
                $xml->addChild('token', $token);
                $xml->addChild('message', 'Utente inserito con successo (token: "' . $token . '")');
                $xml->addChild('error', '');
                http_response_code(200);
            } else {
                $xml->addChild('success', 'false');
                $xml->addChild('userID', "-1");
                $xml->addChild('token', '');
                $xml->addChild('message', 'Utente non inserito');
                $xml->addChild('error', 'Erro during the insertion');
                http_response_code(401);
            }
            $stmt->close();

            if ($_POST["mod"] == "xml") {
                header('Content-Type: application/xml');
                echo $xml->asXML();
            } else if ($_POST["mod"] == "json") {
                header('Content-Type: application/json');
                echo json_encode($xml);
            }
        } else {
            http_response_code(400);
        }
    } else {
        http_response_code(400);
    }
} else if ($_SERVER['REQUEST_METHOD'] == "POST") {

    // Recupero i dati dal php://input
    $data = json_decode(file_get_contents('php://input'), true);

    if(isset($data['createNotepad'], $data['title'] , $data['description'], $data['token'])){
        //Aggiugo il blocco note nel database
        $sql = "INSERT INTO blocco (titolo, descrizione, id_utente) VALUES (?, ?, (SELECT id FROM utente WHERE token = ?))";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sss", $data['title'], $data['description'], $data['token']);
        if($stmt->execute()){
            http_response_code(200);
        }
        
    }

}
$conn->close();
