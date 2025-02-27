package RF.Notely.app.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import RF.Notely.app.errors.WebServiceException;
import RF.Notely.app.util.WebServiceClient;

public class Authenticator {
	
	private WebServiceClient WSC;
	private Scanner input;
	private String TOKEN;
	private int selection;

	public Authenticator() {
		input = new Scanner(System.in);
	}

	public void authenticate() {
		AuthenticationResult result;
		do {
			ShowOutMenu();
			result = auth();
			System.out.println("\n===========================================\n");
		} while (!result.success);
	}

	private AuthenticationResult auth() {
		try {
			return WSC.authenticateClient(TOKEN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void getCredentials() {
		System.out.println("\n============== Authenticator ==============\n");
		System.out.print("\n\tInsert Authentication Token:\n>> ");
		this.TOKEN = input.nextLine();
		
	}
	
	private void ShowOutMenu() {
		
			System.out.println("\n============== First Access ==============\n");
			System.out.print("\n\tChoose an option:\n1. Insert token\n2. Register\n\n>>");
			this.selection = input.nextInt();
			switch (selection) {
			case 1:
				input.nextLine();
				getCredentials();
				break;
			case 2:
				input.nextLine();
				RegisterNewUser();
				break;
			default:
				System.out.println("Insert a valid option!");
				break;
			}
	}
	
	private void RegisterNewUser() {
		System.out.println("\n============== Registration ==============\n");
		
		boolean status = false;
		String user = "";
		
		while(status == false) {
			System.out.println("\nInsert the username: ");
			user = input.nextLine();
			try {
				status = WSC.checkUsername(user);
			} catch (JAXBException | WebServiceException | IOException | InterruptedException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(status == true) {
				System.out.println("\nUsername available!");
			}else {
				System.out.println("\nUsername already in use!");
			}
			
		}
		
		System.out.println("\nInsert your name: ");
		String nome = input.nextLine();
		
		System.out.println("\nInsert your surname: ");
		String cognome = input.nextLine();
		
		try {
			AuthenticationResult AR = WSC.registerUser(user, nome, cognome);
			System.out.println("\nSei stato registrato nel sistema! Questo e' il tuo token, non condividerlo con nessuno:\n\tTOKEN:\t" + AR.token);
			this.TOKEN = AR.token;
		} catch (JAXBException | WebServiceException | IOException | InterruptedException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void setWebServiceClient(WebServiceClient WSC) {
		this.WSC = WSC;
		WSC.setAuthenticator(this);
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return this.TOKEN;
	}

	
}
