package RF.Notely.app.model;

import java.util.Scanner;

public class NoteHandler {
	
	private static String MENU_NOTE = "\n1.\tShare Note\n2.\tEliminate Note\n3.\tModify Note\n4.\tRename Note\n0.\tback\n>>";
    private Scanner user_input;
	
	public void GenerateMenu(int id_note, NotePad ntp) {
		
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
	    		switch (selection) {
					case 0:
						exit = true;
						break;
					case 4:
						//rinomina titolo nota
						break;
					case 3:
						//Modifica il body della nota
						break;
					case 2:
						//elimina
						break;
					case 1:
						//condividi
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
