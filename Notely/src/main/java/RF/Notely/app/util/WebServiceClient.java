package RF.Notely.app.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import RF.Notely.app.errors.WebServiceException;
import RF.Notely.app.model.AuthenticationResult;
import RF.Notely.app.model.Authenticator;
import RF.Notely.app.model.REQUESTS;
import RF.Notely.app.model.RequestMode;
import RF.Notely.app.model.RequestMode.Mode;
import RF.Notely.app.util.XmlUtils;
import jakarta.xml.bind.JAXBException;

public class WebServiceClient {
	private String baseUrl;
	private HttpClient client;
	private Authenticator AUTH;
	private RequestMode RQST_MTHD;

	public WebServiceClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
		this.RQST_MTHD = new RequestMode(Mode.XML_REQUEST_METHOD);
	}

	public AuthenticationResult authenticateClient(String token) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = REQUESTS.AUTHENTICATE_USER.buildQuery(token);
		URI uri = new URI(this.baseUrl + query + RQST_MTHD.getMode());
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WebServiceException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(AuthenticationResult.class, body);
		
	}
	
	public boolean checkUsername(String login) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = REQUESTS.CHECK_USERNAME.buildQuery(login);
		URI uri = new URI(this.baseUrl + query + RQST_MTHD.getMode());
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public AuthenticationResult registerUser(String login, String nome, String cognome) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = REQUESTS.ADD_USER.buildQuery(login, nome, cognome).substring(1)+RQST_MTHD.getMode();
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(query)).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WebServiceException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(AuthenticationResult.class, body);
		
	}

	public void setAuthenticator(Authenticator authenticator) {
		// TODO Auto-generated method stub
		this.AUTH = authenticator;
	}

	public void swapXML_JSON() {
		this.RQST_MTHD.swap();
	}
	
	

}
