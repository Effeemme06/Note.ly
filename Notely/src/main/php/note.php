<?php

$conn = new mysqli("localhost", "root", "", "notely");

function isTokenUsed($token){

    global $conn;

    $sql = "SELECT COUNT(*) AS num FROM utente WHERE utente.token = ?";
    $stmt = $conn->prepare($sql);
    
    $stmt->bind_param("s", $token);
    $stmt->execute();
    $result = $stmt->get_result();
    $row = $result->fetch_assoc();

    if (isset($row['num']) && $row['num']==0) {
        return false;
    }else if($row['num']>0){
        return true;
    }else{
        return false;
    }

}

function generateToken($length = 16) {
    
    do{
        $token = substr(bin2hex(random_bytes($length)), 0, $length);
    }while(isTokenUsed($token)==true);

    return $token;
}

if ($_SERVER['REQUEST_METHOD'] == "GET") {
    if (isset($_GET["auth"])) {

            if(isset($_GET["token"]) && isset($_GET["mod"])){
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

                if($_GET["mod"]=="xml"){
                    header('Content-Type: application/xml');
                    echo $xml->asXML();
                }else if($_GET["mod"]=="json"){
                    header('Content-Type: application/json');
                    echo json_encode($xml);
                }

            }else{
                http_response_code(400);
            }
    
        }else if (isset($_GET["checkUsername"])) {

            if(isset($_GET["username"]) && isset($_GET["mod"])){
                $xml = new SimpleXMLElement('<AuthenticationResults/>');
                
                $sql = "SELECT COUNT(*) AS num FROM utente WHERE utente.login = ?";
                $stmt = $conn->prepare($sql);
                
                $stmt->bind_param("s", $_GET["username"]);
                $stmt->execute();
                $result = $stmt->get_result();
                $row = $result->fetch_assoc();
                
                if (isset($row['num']) && $row['num']==0) {
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

                if($_GET["mod"]=="xml"){
                    header('Content-Type: application/xml');
                    echo $xml->asXML();
                }else if($_GET["mod"]=="json"){
                    header('Content-Type: application/json');
                    echo json_encode($xml);
                }

            }else{
                http_response_code(400);
            }
    
        }
        else{
            http_response_code(400);
        }

    }



if ($_SERVER['REQUEST_METHOD'] == "POST") {

    if (isset($_POST["addUser"])) {

        if(isset($_POST["username"]) && isset($_POST["name"]) && isset($_POST["surname"]) && isset($_POST["mod"])){
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
                $xml->addChild('message', 'Utente inserito con successo (token: "'.$token.'")');
                $xml->addChild('error', '');
                http_response_code(200);
            } else {
                $xml->addChild('success', 'false');
                $xml->addChild('userID', "-1");
                $xml->addChild('message', 'Utente non inserito');
                $xml->addChild('error', 'Erro during the insertion');
                http_response_code(401);
            }
            $stmt->close();

            if($_POST["mod"]=="xml"){
                header('Content-Type: application/xml');
                echo $xml->asXML();
            }else if($_POST["mod"]=="json"){
                header('Content-Type: application/json');
                echo json_encode($xml);
            }

        }else{
            http_response_code(400);
        }
    }else{
        http_response_code(400);
    }
}
    $conn->close();
?>