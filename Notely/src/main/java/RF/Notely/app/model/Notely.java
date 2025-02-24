package RF.Notely.app.model;

import RF.Notely.app.util.WebServiceClient;

public class Notely {
	
    private static Notely instance;
    private Authenticator AUTH;
    private WebServiceClient WSC;
    
    private Notely() {
    	WSC = new WebServiceClient("http://localhost/Note.ly/Notely/src/main/php/note.php");
    	AUTH = new Authenticator();
    }

    public static synchronized Notely getInstance() {
        if (instance == null) {
            instance = new Notely();
        }
        return instance;
    }
    
    public void start() {
    	AUTH.setWebServiceClient(WSC);
    	AUTH.authenticate();
    }
}