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

	public Authenticator() {
		input = new Scanner(System.in);
	}

	public void authenticate() {
		AuthenticationResult result;
		do {
			getCredentials();
			result = auth();
			if(!result.success) {
				System.err.println("\n" + result.error);
			}else {
				System.out.println("\n" + result.message);
			}
			System.out.println("===========================================");
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
		System.out.println("============== Authenticator ==============");
		System.out.print("\tInsert Authentication Token: ");
		this.TOKEN = input.nextLine();
		
	}

	public void setWebServiceClient(WebServiceClient WSC) {
		this.WSC = WSC;
		WSC.setAuthenticator(this);
	}

	
}
