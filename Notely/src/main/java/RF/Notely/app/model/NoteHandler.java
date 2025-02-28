package RF.Notely.app.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import RF.Notely.app.errors.WebServiceException;
import RF.Notely.app.util.WebServiceClient;
import jakarta.xml.bind.JAXBException;

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
						System.out.print("Insert the new title of the note> ");
						String title = user_input.nextLine();
						WSC.editNoteTitle(title, selected.id);
						exit = true;
						break;
					case 3:
						
						//TODO
						//Modifica il body della nota
						System.out.print("Insert the new body of the note> ");
						String body = user_input.nextLine();
						WSC.editNoteBody(body, selected.id);
						exit = true;
						break;
					case 2:
						//elimina
						boolean success = WSC.deleteNote(id_note);
						exit = true;
						break;
					case 1:
						//condividi
						
						boolean status = true;
						String username = "";
						
						while(status == true) {
							System.out.print("Insert the person username> ");
							username = user_input.nextLine();
							try {
								status = WSC.checkUsername(username);
							} catch (JAXBException | WebServiceException | IOException | InterruptedException | URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(status == true) {
								System.out.println("\nUsername doesn't exist!");
							}else {
								System.out.println("\nExisting username!");
							}
							
						}
						
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
