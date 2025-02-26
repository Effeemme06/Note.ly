<?php

$conn = new mysqli("localhost", "root", "", "notely");

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

    else if (isset($_GET["addUser"])) {

        if(isset($_GET["username"]) && isset($_GET["name"]) && isset($_GET["surname"]) && isset($_GET["mod"])){
            $xml = new SimpleXMLElement('<AuthenticationResults/>');
            
            $sql = "INSERT INTO utente (login, token, nome, cognome) VALUES (?, ?, ?);";
            $stmt = $conn->prepare($sql);
            
            $stmt->bind_param("sss", $_GET["username"]);
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
    }else{
        http_response_code(400);
    }
}
    $conn->close();
?>