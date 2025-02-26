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
    
        }
        else{
            http_response_code(400);
        }

    }

    $conn->close();
?>