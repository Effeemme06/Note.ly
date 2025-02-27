package RF.Notely.app.model;

import java.util.Scanner;

public class NotepadHandler {
	
	private static String MENU_NOTEPAD = "\n1.\tSelect Note\n2.\tCreate Note\n3.\tDelete Note\n4.\tRename Notepad\n0.\tback\n>>";
    private Scanner user_input;
	
	public void GenerateMenu(int id_notepad, Store ntp) {
		
		user_input = new Scanner(System.in);
		
		boolean exit = true;
		NotePad selected = null;

		for (NotePad np : ntp.getNotePads()) {
			if(np.getID() == id_notepad) {
				exit = false;
				selected = np;
				System.out.println("\n============ " + selected.getTitle() + " ============\n");
				break;
			}
		}
		
		
		
		int selection = 0;
		
		while(exit == false) {
			
			try {
				for (Note n : selected.getNotes()) {
					System.out.println("Note:\n\t-" + n.getTitle() + " (" + n.getId() + ")");
				}
				System.out.println(MENU_NOTEPAD);
	    		selection = user_input.nextInt();
	    		switch (selection) {
					case 0:
						exit = true;
						break;
					case 4:
						//rinomina blocco
						break;
					case 3:
							//lista note per eliminare
						break;
					case 2:
							//crea nota
						break;
					case 1:
							//lista note selezionabili--> classe per visualizzare note / rinominare
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
