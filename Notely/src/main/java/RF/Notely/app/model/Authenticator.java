package RF.Notely.app.model;

import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class Authenticator {
	
	private WebServiceClient WSC;
	private Scanner input;
	private String username;
	private String password;

	public Authenticator() {
		input = new Scanner(System.in);
	}

	public void authenticate() {
		AuthenticationResults result;
		do {
			getCredentials();
			result = auth();
			if(!result.success) {
				System.err.println(result.error);
			}
		} while (!result.success);
	}

	private AuthenticationResults auth() {
		WSC.authenticateClient(username, password);
	}

	private void getCredentials() {
		System.out.println("============== Authenticator ==============");
		System.out.println("\tUsername:");
		this.username = input.nextLine();
		System.out.println("\tPassword:");
		this.username = input.nextLine();
		
	}

	public void setWebServiceClient(WebServiceClient WSC) {
		this.WSC = WSC;
		
	}
	
	private class AuthenticationResults {
		
		public Boolean success;
		public String error;
		
		public AuthenticationResults(Boolean success, String error) {
			this.success = success;
			this.error = error;
		}
		
	}

	
}
