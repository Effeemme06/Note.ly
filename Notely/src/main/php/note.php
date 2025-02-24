<?php
    header('Content-Type: application/xml');

    $conn = new mysqli("localhost", "root", "", "notely");

    if ($_SERVER['REQUEST_METHOD'] == "GET") {
        if (isset($_GET["auth"])) {
            $xml = new SimpleXMLElement('<AuthenticationResults/>');
            
            $sql = "SELECT id FROM utente WHERE login = ? AND password = ?";
            $stmt = $conn->prepare($sql);
            
            $stmt->bind_param("ss", $_GET["username"], $_GET["password"]);
            $stmt->execute();
            $result = $stmt->get_result();
            $row = $result->fetch_assoc();
            $stmt->fetch(); // Devi eseguire il fetch per ottenere il valore
            
            if (isset($row['id'])) {
                $id = $row['id'];
                $xml->addChild('success', 'true');
                $xml->addChild('userID', $id);
                $xml->addChild('message', 'Autenticazione riuscita. Benvenuto!');
                $xml->addChild('error', '');
            } else {
                $xml->addChild('success', 'false');
                $xml->addChild('userID', "-1");
                $xml->addChild('message', 'Autenticazione fallita. Username o password errati');
                $xml->addChild('error', 'Wrong username or password');
            }
    
            $stmt->close();
            echo $xml->asXML();
        }
    }

    $conn->close();
?>