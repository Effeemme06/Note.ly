package RF.Notely.app.model;

import java.util.Scanner;

public class NotepadHandler {
	
	private static String MENU_NOTEPAD = "1.\tView Note\n2.\tCreate Note\n3.\tDelete Note\n4.\tRename Notepad\n0.\tback\n>>";
    private Scanner user_input;
	
	public void GenerateMenu(int id_notepad) {
		
		
		boolean exit = false;
		int selection = 0;
		
		while(exit == false) {
			
			try {
				
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
		
	}
	
}
