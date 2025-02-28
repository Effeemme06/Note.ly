package RF.Notely.app.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;
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

	public AuthenticationResult authenticateClient(String token) throws Exception {
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
		return Deserializer.deserialize(AuthenticationResult.class, body, RSP_MTHD);
//		return XmlUtils.unmarshal(AuthenticationResult.class, body);
		
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
	
	public AuthenticationResult registerUser(String login, String nome, String cognome) throws Exception {
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
		return Deserializer.deserialize(AuthenticationResult.class, body, RSP_MTHD);
//		return XmlUtils.unmarshal(AuthenticationResult.class, body);
		
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
		return Deserializer.deserialize(NotePad.class, body, RSP_MTHD);
//		return XmlUtils.unmarshal(NotePad.class, body);
	}

	public Store getNotepads() throws Exception {
		String query = QueryHandler.GET_NOTEPADS
				.newQuery()
				.setRequestMethod(RequestMethod.GET)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		//System.out.println(query);
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

		return Deserializer.deserialize(Store.class, body, RSP_MTHD);
	}

	public boolean createNotepad(String title, String description) throws Exception {
	    String json = QueryHandler.CREATE_NOTEPAD
	    		.newQuery(title, description)
	    		.setRequestMethod(RequestMethod.PUT)
	    		.addResponseMethod(RSP_MTHD)
	    		.addToken(AUTH)
	    		.build();
	  //  System.out.println(json);
	    URI uri = new URI(this.baseUrl);

	    HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

	    if (res.statusCode() == 201) { // 201 = Created
	        return true;
	    } else {
	        System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
	        return false;
	    }
	}
	
	public boolean createNote(String title, String body, Integer notepad_id) throws Exception {
		String json = QueryHandler.INSERT_NEW_NOTE
				.newQuery(title, body, notepad_id)
				.setRequestMethod(RequestMethod.PUT)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		//  System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());
		
		if (res.statusCode() == 201) { // 201 = Created
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
	}
	
	public boolean deleteNotepad(Integer id) throws Exception {
		String json = QueryHandler.DELETE_NOTEPAD
				.newQuery(id)
				.setRequestMethod(RequestMethod.DELETE)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		//  System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json)).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());
		
		if (res.statusCode() == 200) { // 200 = Deleted
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
	}
	
	public boolean deleteNote(Integer id) throws Exception {
		String json = QueryHandler.DELETE_NOTE
				.newQuery(id)
				.setRequestMethod(RequestMethod.DELETE)
				.addResponseMethod(RSP_MTHD)
				.addToken(AUTH)
				.build();
		//  System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json)).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());
		
		if (res.statusCode() == 200) { // 200 = Deleted
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
	}

	public boolean shareNote(Integer noteID, String usernameToShareWith, Integer permissionLevel) throws JAXBException, WebServiceException, IOException, InterruptedException, URISyntaxException {
		String json = QueryHandler.SHARE_NOTE
				.newQuery(noteID, usernameToShareWith, permissionLevel)
				.setRequestMethod(RequestMethod.PUT)
				.addResponseMethod(RSP_MTHD)
				.build();
//		System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() == 201) { // 201 = Created
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
		
	}

	public boolean editNoteTitle(String newTitle, Integer noteID) throws Exception {
		String json = QueryHandler.EDIT_NOTE_TITLE
				.newQuery(newTitle, noteID)
				.setRequestMethod(RequestMethod.PUT)
				.addResponseMethod(RSP_MTHD)
				.build();
//		System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() == 201) { // 201 = Created
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
		
	}
	
	public boolean editNoteBody(String newBody, Integer noteID) throws Exception {
		String json = QueryHandler.EDIT_NOTE_BODY
				.newQuery(newBody, noteID)
				.setRequestMethod(RequestMethod.PUT)
				.addResponseMethod(RSP_MTHD)
				.build();
//		System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() == 201) { // 201 = Created
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
		
	}

	public boolean editNotepadTitle(String newTitle, Integer notepadID) throws Exception {
		String json = QueryHandler.EDIT_NOTEPAD_TITLE
				.newQuery(newTitle, notepadID)
				.setRequestMethod(RequestMethod.PUT)
				.addResponseMethod(RSP_MTHD)
				.build();
//		System.out.println(json);
		URI uri = new URI(this.baseUrl);
		
		HttpRequest req = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString())).build();
		HttpResponse<String> res = this.client.send(req, BodyHandlers.ofString());

		if (res.statusCode() == 201) { // 201 = Created
			return true;
		} else {
			System.err.println("Errore HTTP: " + res.statusCode() + " - " + res.body());
			return false;
		}
	}

}
