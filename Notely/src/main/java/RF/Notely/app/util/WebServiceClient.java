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
import RF.Notely.app.util.XmlUtils;
import jakarta.xml.bind.JAXBException;

public class WebServiceClient {
	private String baseUrl;
	private HttpClient client;
	private Authenticator AUTH;

	public WebServiceClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
	}

	public AuthenticationResult authenticateClient(String username, String password) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = REQUESTS.AUTHENTICATE_USER.buildQuery(username, password);
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
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
	
	

}
