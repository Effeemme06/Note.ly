package RF.Notely.app.model;

import java.util.Scanner;

import RF.Notely.app.util.WebServiceClient;

public class NotepadHandler {
	
	private static String MENU_NOTEPAD = "\n1.\tSelect Note\n2.\tCreate Note\n3.\tDelete Note\n4.\tRename Notepad\n0.\tback\n>>";
    private Scanner user_input;
    private Store store;
    private WebServiceClient WSC;
	
	public void GenerateMenu(int id_notepad, Store ntp) {
		
		this.store = ntp;
		WSC = Notely.getInstance().getWSC();
		
		user_input = new Scanner(System.in);
		
		boolean exit = true;
		NotePad selected = null;

		for (NotePad np : store.getNotePads()) {
			if(np.getID() == id_notepad) {
				exit = false;
				selected = np;
				break;
			}
		}

		
		
		int selection = 0;
		
		while(exit == false) {
			
			try {
				updateStore();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (NotePad np : store.getNotePads()) {
				if(np.getID() == id_notepad) {
					selected = np;
					break;
				}
			}
			
			System.out.println("\n============ " + selected.getTitle() + " ============\n");
			try {
				System.out.println("Note:");
				for (Note n : selected.getNotes()) {
					System.out.println("\t-" + n.getTitle() + " (" + n.getId() + ")");
				}
				System.out.println(MENU_NOTEPAD);
	    		selection = user_input.nextInt();
	    		user_input.nextLine();
	    		switch (selection) {
					case 0:
						exit = true;
						break;
					case 4:
						//rinomina blocco
						System.out.print("Insert the new title of the notepad> ");
						String title = user_input.nextLine();
						
						// TODO
						//rinomina blocco
						updateStore();
						break;
					case 3:
						//lista note per eliminare
						System.out.println("Note:");
						for (Note n : selected.getNotes()) {
							System.out.println("\t-" + n.getTitle() + " (" + n.getId() + ")");
						}
						System.out.println("\nDelete note:\n>>");
						selection = user_input.nextInt();
						boolean success = WSC.deleteNote(selection);
						updateStore();
						break;
					case 2:
						//crea nota
						System.out.print("Insert the title of the new note> ");
						String title_note = user_input.nextLine();
						System.out.print("Write the note > ");
						String body = user_input.nextLine();
						WSC.createNote(title_note, body, id_notepad);
						updateStore();
						break;
					case 1:
						System.out.println("Note:");
						for (Note n : selected.getNotes()) {
							System.out.println("\t-" + n.getTitle() + " (" + n.getId() + ")");
						}
						System.out.println("\nSelect note:\n>>");
						selection = user_input.nextInt();
						Notely.getInstance().getNtHandler().GenerateMenu(selection, selected);
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

	private void updateStore() throws Exception {
		this.store = WSC.getNotepads();
	}
	
}
