package de.dis;

import de.dis.data.EstateAgent;

import java.util.ArrayList;

import de.dis.data.Estate;

/**
 * Immobilienverwaltung für Markler 
 */
public class EstateManagement {
	
	public static void showEstateManagementMenu() {
		String _name = FormUtil.readString("login");
		String _password = FormUtil.readString("password");
		
		// authenticate
		EstateAgent makler = EstateAgent.load(_name, _password);
		if(makler == null) {
			System.out.println("wrong credentials!\n");
			return;
		}
		
		//Menüoptionen
		final int NEW_ESTATE = 0;
		final int EDIT_ESTATE = 1;
		final int DELETE_ESTATE = 2;
		final int BACK = -1;
		
		//Maklerverwaltungsmenü
		Menu estateMenu = new Menu("estate management");
		estateMenu.addEntry("create estate", NEW_ESTATE);
		estateMenu.addEntry("edit estate", EDIT_ESTATE);
		estateMenu.addEntry("delete estate", DELETE_ESTATE);
		estateMenu.addEntry("back to main menu", BACK);

		//Verarbeite Eingabe
		while(true) {
			int response = estateMenu.show();
			
			switch(response) {
				case NEW_ESTATE:
					newEstate();
					break;
				case EDIT_ESTATE:
					editEstate();
					break;
				case DELETE_ESTATE:
					deleteEstate();
					break;
				case BACK:
					return;
			}
		}
	}
	
	public static void newEstate() {
		Estate e = new Estate();
		
		e.setCity(FormUtil.readString("city"));
		e.setPostalCode(FormUtil.readString("postal Code"));
		e.setStreet(FormUtil.readString("street"));
		e.setStreetNumber(FormUtil.readString("street Number"));
		e.setSquareArea(FormUtil.readString("square Area"));
		e.save();
		
		System.out.println("successfully created estate with id "+e.getId()+" .\n");
	}
	
	/*
	 * 
	 * @param Returns id of selected estate agent
	 */
	public static Estate selectEstate(String title) {
		ArrayList<Estate> estateList = Estate.loadAll();
		Menu editEstateMenu = new Menu(title);
		for (Estate estate : estateList) {
			editEstateMenu.addEntry(estate.getCity(), estate.getId());
		}
		
		editEstateMenu.addEntry("quit", -1);
				
		while(true) {
			int id = editEstateMenu.show();
			
			if(id == -1) return null;
			
			return Estate.load(id);
		}
	}
	
	private static void editEstate() {
		Estate selectedEstate = selectEstate("which estate you want to change?");

		if(selectedEstate == null) return;
		
		Menu selectEditEstateMenu = new Menu("which estate property you want to change?");
		int selectedValue;
		while(true) {
			selectEditEstateMenu.addEntry("city", 0);
			selectEditEstateMenu.addEntry("postal code", 1);
			selectEditEstateMenu.addEntry("street", 2);
			selectEditEstateMenu.addEntry("street number", 3);
			selectEditEstateMenu.addEntry("square area", 4);
			selectEditEstateMenu.addEntry("quit", -1);
			
			selectedValue = selectEditEstateMenu.show();

			if(selectedValue == -1) return;
			else break;
		}
		String response = FormUtil.readString("new value");

		switch(selectedValue) {
			case 0:
				selectedEstate.setCity(response);
				break;
			case 1:
				selectedEstate.setPostalCode(response);
				break;
			case 2:
				selectedEstate.setStreet(response);
				break;
			case 3:
				selectedEstate.setStreetNumber(response);
				break;
			case 4:
				selectedEstate.setSquareArea(response);
				break;
			case -1:
				return;
			default:
			
		}
		
		selectedEstate.save();
		System.out.println("estate successfully updated!\n");

	}
	
	private static void deleteEstate() {
		Estate selectedEstate = selectEstate("delete estate");
		if(selectedEstate == null) return;
		
		
		Estate.delete(selectedEstate);
		System.out.println("estate deleted\n");
		
	}
}
