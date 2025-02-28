package RF.Notely.app.model;

import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class NoteHandler {
	
	private static String MENU_NOTE = "\n1.\tShare Note\n2.\tEliminate Note\n3.\tModify Note\n4.\tRename Note\n0.\tback\n>>";
    private Scanner user_input;
    private WebServiceClient WSC;
	
	public void GenerateMenu(int id_note, NotePad ntp) {
		

		WSC = Notely.getInstance().getWSC();
		user_input = new Scanner(System.in);
		
		boolean exit = true;
		Note selected = null;

		for (Note np : ntp.getNotes()) {
			if(np.getId() == id_note) {
				exit = false;
				selected = np;
				break;
			}
		}
		
		
		
		int selection = 0;
		
		while(exit == false) {
			
			try {
				System.out.println("\n============ " + selected.getTitle() + " ============\n");				
				System.out.println("\n"+ selected.getBody()+"\n");
				System.out.println(MENU_NOTE);
	    		selection = user_input.nextInt();
	    		user_input.nextLine();
	    		switch (selection) {
					case 0:
						exit = true;
						break;
					case 4:
						//TODO
						//rinomina titolo nota
						break;
					case 3:
						
						//TODO
						//Modifica il body della nota
						
						break;
					case 2:
						//elimina
						boolean success = WSC.deleteNote(id_note);
						exit = true;
						break;
					case 1:
						//condividi
						
						System.out.print("Insert the person username> ");
						String username = user_input.nextLine();
						int level = 0;
						do {
							System.out.print("Insert the permission \n1. modify\n2. only view\n>> ");
							level = user_input.nextInt();
							user_input.nextLine();
						}while(level<1 || level>2);
						WSC.shareNote(id_note, username, level);
						break;
					default:
						break;
				}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			
		}
		
		System.out.println("\nExiting...\n");

	}
	
}
