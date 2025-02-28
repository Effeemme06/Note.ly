package RF.Notely.app.model;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class Notely {
	
	private static String MENU = "\n\n================MENU'================\n\n"
			+ "1.\tSelect Notepads\n2.\tCreate Notepad\n3.\tDelete Notepad\n4.\tViews shared notes\n5.\tSwap XML/JSON\n0.\tLogout\n>>";
	private static Notely instance;
    private Authenticator AUTH;
    private WebServiceClient WSC;
    private Scanner user_input;
    private NotepadHandler ntpHandler;
    private NoteHandler ntHandler;
    
    private Notely() {
    	WSC = new WebServiceClient("http://localhost/Notely/Notely/src/main/php/note.php");
    	AUTH = new Authenticator();
    	user_input = new Scanner(System.in);
    	ntpHandler = new NotepadHandler();
    	ntHandler = new NoteHandler();
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
					case 5:
						WSC.swapXML_JSON();
						break;
					case 4:
							//note condivise
							Store notepad_s = WSC.getNotepads();
							ntpHandler.GenerateMenu(-2, notepad_s);
						break;
					case 3:
							//lista blocco appunti per eliminare
							Store notepads_1 = WSC.getNotepads();
							System.out.println("\n==========ELENCO BLOCCHI NOTE==========\n");
							for (NotePad np : notepads_1.getNotePads()) {
								if(np.getID()>0) {									
									System.out.println("\t- "+ np.getTitle()+" (" + np.getID() + ")");
								}
							}
							System.out.println("\nDelete notepad:\n>>");
							selection = user_input.nextInt();
							WSC.deleteNotepad(selection);
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
								if(np.getID()>0) {
									System.out.println("\t- "+ np.getTitle()+" (" + np.getID() + ")");
								}
							}
							System.out.println("\nSelect notepad:\n>>");
							selection = user_input.nextInt();
							ntpHandler.GenerateMenu(selection, notepads);
							
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

	public WebServiceClient getWSC() {
		return WSC;
	}

	public void setWSC(WebServiceClient wSC) {
		WSC = wSC;
	}

	public NotepadHandler getNtpHandler() {
		return ntpHandler;
	}

	public void setNtpHandler(NotepadHandler ntpHandler) {
		this.ntpHandler = ntpHandler;
	}

	public NoteHandler getNtHandler() {
		return ntHandler;
	}

	public void setNtHandler(NoteHandler ntHandler) {
		this.ntHandler = ntHandler;
	}
    
    
    
}