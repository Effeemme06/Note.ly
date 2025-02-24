package RF.Notely.app.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import RF.Notely.app.errors.WebServiceException;
import RF.Notely.app.model.REQUESTS;
import RF.Notely.app.util.XmlUtils;

public class WebServiceClient {
	private String baseUrl;
	private HttpClient client;

	public WebServiceClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
	}

	public void authenticateClient(String username, String password) {
		String query = REQUESTS.AUTHENTICATE_USER.buildQuery(username, password);
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200)
			throw new WebServiceException("HTTP status code: " + res.statusCode());

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(Voti.class, body);
		
	}
	
	

}
