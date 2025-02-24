package RF.Notely.app.model;

import java.io.InputStreamReader;
import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class Notely {
	
	private static String MENU = "1.\tView Notes\n2.\tCreate Note\n3.\tDelete Note\n4.\tView Notepads\n5.\tCreate Notepad\n6.\tDelete Notepad\n7.\tManage Permissions\n8.\tSwap XML/JSON\n0.\tLogout\n>>";
    private static Notely instance;
    private Authenticator AUTH;
    private WebServiceClient WSC;
    private Scanner user_input;
    
    private Notely() {
    	WSC = new WebServiceClient("http://localhost/Note.ly/Notely/src/main/php/note.php");
    	AUTH = new Authenticator();
    	user_input = new Scanner(System.in);
    }

    public static synchronized Notely getInstance() {
        if (instance == null) {
            instance = new Notely();
        }
        return instance;
    }
    
    public void start() {
    	
    	Integer selection;
    	
    	AUTH.setWebServiceClient(WSC);
    	
//    	Authentication Loop ----------------
    	AUTH.authenticate();
//    	End Authentication Loop ------------
    	
//    	APP Loop ---------------------------
    	do {
    		System.out.print(MENU);    
    		selection = user_input.nextInt();
    		switch (selection) {
			case 8:
				WSC.swapXML_JSON();
				break;

			default:
				break;
			}
    	}while(selection != 0);
//    	End APP Loop -----------------------
    	
    }
}