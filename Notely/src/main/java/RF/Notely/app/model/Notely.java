package RF.Notely.app.model;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class Notely {
	
	private static String MENU = "\n\n================MENU'================\n\n"
			+ "1.\tSelect Notepads\n2.\tCreate Notepad\n3.\tDelete Notepad\n4.\tSwap XML/JSON\n0.\tLogout\n>>";
	private static Notely instance;
    private Authenticator AUTH;
    private WebServiceClient WSC;
    private Scanner user_input;
    
    private Notely() {
    	WSC = new WebServiceClient("http://localhost/Notely/Notely/src/main/php/note.php");
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
    	
    	Integer selection = null;
    	
    	AUTH.setWebServiceClient(WSC);
    	
//    	Authentication Loop ----------------
    	AUTH.authenticate();
//    	End Authentication Loop ------------
    	
//    	APP Loop ---------------------------
    	do {
    		
    		try {
	    		System.out.print(MENU);    
	    		selection = user_input.nextInt();
	    		user_input.nextLine();
	    		switch (selection) {
					case 4:
						WSC.swapXML_JSON();
						break;
					case 3:
							//lista blocco appunti per eliminare
						break;
					case 2:
							//classe che crea il blocco appunti
							System.out.print("Inserisci il titolo del nuovo Blocco note > ");
							String title = user_input.nextLine();
							System.out.print("Inserisci la descrizione del nuovo Blocco note > ");
							String description = user_input.nextLine();
						
							boolean success = WSC.createNotepad(title, description);
						break;
					case 1:
							//collegamento alla classe che fa la lista dei blocchi appunti --> selezione blocco --> classe Gestion_notepad
							Store notepads = WSC.getNotepads();
							System.out.println("\n==========ELENCO BLOCCHI NOTE==========\n");
//							System.out.println(notepads);
							for (NotePad np : notepads.getNotePads()) {
								System.out.println("- "+ np.getTitle()+" (" + np.getID() + ")");
							}
							System.out.println("\nSelect notepad:\n>>");
							NotepadHandler nphHandler = new NotepadHandler();
							selection = user_input.nextInt();
							nphHandler.GenerateMenu(selection, notepads);
							
						break;
					default:
						break;
				}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}while(selection != 0);
//    	End APP Loop -----------------------
    	
    }
}