package RF.Notely.app.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONStringer;

import RF.Notely.app.errors.WebServiceException;
import RF.Notely.app.model.AuthenticationResult;
import RF.Notely.app.model.Authenticator;
import RF.Notely.app.model.NotePad;
import RF.Notely.app.model.Query;
import RF.Notely.app.model.QueryHandler;
import RF.Notely.app.model.RequestMethod;
import RF.Notely.app.model.ResponseMethod;
import RF.Notely.app.model.ResponseMethod.Mode;
import RF.Notely.app.model.Store;
import RF.Notely.app.util.XmlUtils;
import jakarta.xml.bind.JAXBException;

public class WebServiceClient {
	private String baseUrl;
	private HttpClient client;
	private Authenticator AUTH;
	private ResponseMethod RSP_MTHD;

	public WebServiceClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = HttpClient.newHttpClient();
		this.RSP_MTHD = new ResponseMethod(Mode.XML_REQUEST_METHOD);
	}

	public AuthenticationResult authenticateClient(String token) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = QueryHandler.AUTHENTICATE_USER
				.newQuery(token)
				.setRequestMethod(RequestMethod.GET)
				.addResponseMethod(RSP_MTHD)
				.build();
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		
		switch (res.statusCode()) {
		case 401:
			System.err.println("Il Token inserito e' errato/inesistente");
			break;
		case 200:
		case 201:
		case 202:
		case 203:
			System.out.println("Sei stato autorizzato.");
			break;
		default:
			throw new WebServiceException("HTTP status code: " + res.statusCode());
		}

		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(AuthenticationResult.class, body);
		
	}
	
	public boolean checkUsername(String login) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = QueryHandler.CHECK_USERNAME
				.newQuery(login)
				.setRequestMethod(RequestMethod.GET)
				.addResponseMethod(RSP_MTHD)
				.build();
		System.out.println(query);
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() != 200) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public AuthenticationResult registerUser(String login, String nome, String cognome) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String query = QueryHandler.ADD_USER
				.newQuery(login, nome, cognome)
				.setRequestMethod(RequestMethod.POST)
				.addResponseMethod(RSP_MTHD)
				.build();
		System.out.println(query);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(query)).build();
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
		this.RSP_MTHD.swap();
	}

	public NotePad getNotes() throws Exception {
		String query = QueryHandler.GET_NOTES.newQuery()
				.setRequestMethod(RequestMethod.GET)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		switch (res.statusCode()) {
		case 404:
			System.err.println("Nessuna nota trovata");
			break;
		case 200:
		case 201:
		case 202:
		case 203:
			break;
		default:
			throw new WebServiceException("HTTP status code: " + res.statusCode());
		}
		
		String body = (String) res.body();
		// System.out.println("received: " + body);
		return XmlUtils.unmarshal(NotePad.class, body);
	}

	public Store getNotepads() throws Exception {
		String query = QueryHandler.GET_NOTEPADS
				.newQuery()
				.setRequestMethod(RequestMethod.GET)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		URI uri = new URI(this.baseUrl + query);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).GET().build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		switch (res.statusCode()) {
		case 404:
			System.err.println("Nessun Blocco note trovato");
			break;
		case 200:
		case 201:
		case 202:
		case 203:
			break;
		default:
			throw new WebServiceException("HTTP status code: " + res.statusCode());
		}
		
		String body = (String) res.body();
//		System.out.println("XML ricevuto:\n" + body);

		return XmlUtils.unmarshal(Store.class, body);
	}

	public boolean createNotepad(String title) throws Exception {
	    String query = QueryHandler.CREATE_NOTEPAD
	    		.newQuery(title)
	    		.setRequestMethod(RequestMethod.POST)
	    		.addResponseMethod(RSP_MTHD)
	    		.addToken(AUTH)
	    		.build();
	    URI uri = new URI(this.baseUrl);

	    HttpRequest req = HttpRequest.newBuilder()
	        .uri(uri)
	        .POST(HttpRequest.BodyPublishers.noBody()) // Richiesta POST senza corpo
	        .build();

	    HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

	    if (res.statusCode() == 201) { // 201 = Created
	        return true;
	    } else {
	        System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
	        return false;
	    }
	}

	

}
