package de.dis;

import java.util.ArrayList;

import de.dis.data.EstateAgent;

/**
 * main class and starting point
 */
public class EstateAgentManagement {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}
	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int MENU_ESTATE = 1;
		final int MENU_CONTRACT = 2;
		final int QUIT = -1;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("estate agent management", MENU_MAKLER);
		mainMenu.addEntry("estate management", MENU_ESTATE);
		mainMenu.addEntry("contract management", MENU_CONTRACT);
		mainMenu.addEntry("quit", QUIT);
		
		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_MAKLER:
					showMaklerMenu();
					break;
				case MENU_ESTATE:
					EstateManagement.showEstateManagementMenu();
					break;
				case MENU_CONTRACT:
					ContractManagement.showContractManagementMenu();
					break;
				case QUIT:
					return;
			}
		}
	}
	
	/**
	 * Zeigt die Maklerverwaltung
	 */
	public static void showMaklerMenu() {
		String password = "agent007";
		String entered_password = FormUtil.readString("password");
		if(!password.equals(entered_password)) {
			System.out.println("wrong password!\n");
			return;
		}
		
		//Menüoptionen
		final int NEW_MAKLER = 0;
		final int EDIT_MAKLER = 1;
		final int DELETE_MAKLER = 2;
		final int BACK = -1;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("estate agent management");
		maklerMenu.addEntry("create estate agent", NEW_MAKLER);
		maklerMenu.addEntry("edit estate agent", EDIT_MAKLER);
		maklerMenu.addEntry("delete estate agent", DELETE_MAKLER);
		maklerMenu.addEntry("back to main menu", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_MAKLER:
					newMakler();
					break;
				case EDIT_MAKLER:
					editMakler();
					break;
				case DELETE_MAKLER:
					deleteMakler();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		EstateAgent m = new EstateAgent();
		
		m.setName(FormUtil.readString("name"));
		m.setAddress(FormUtil.readString("address"));
		m.setLogin(FormUtil.readString("login"));
		m.setPassword(FormUtil.readString("password"));
		m.save();
		
		System.out.println("successfully created estate agent with id "+m.getId()+" .\n");
	}
	
	/*
	 * 
	 * @param Returns id of selected estate agent
	 */
	public static EstateAgent selectEstateAgent(String title) {
		ArrayList<EstateAgent> estateAgentList = EstateAgent.loadAll();
		Menu editEstateAgentMenu = new Menu(title);
		for (EstateAgent estateAgent : estateAgentList) {
			editEstateAgentMenu.addEntry(estateAgent.getName() + " (" + estateAgent.getLogin() + ")", estateAgent.getId());
		}

		editEstateAgentMenu.addEntry("quit", -1);
		
		while(true) {
			int id = editEstateAgentMenu.show();

			if(id == -1) return null;
			
			return EstateAgent.load(id);
		}
	}
	
	public static void editMakler() {
		EstateAgent selectedMakler = selectEstateAgent("which estate agent you want to change?");
		if(selectedMakler == null) return;
		
		Menu selectEditMaklerMenu = new Menu("which estate agent property you want to change?");
		int selectedValue;
		while(true) {
			selectEditMaklerMenu.addEntry("name", 0);
			selectEditMaklerMenu.addEntry("address", 1);
			selectEditMaklerMenu.addEntry("login", 2);
			selectEditMaklerMenu.addEntry("password", 3);
			selectEditMaklerMenu.addEntry("quit", -1);
			
			selectedValue = selectEditMaklerMenu.show();

			if(selectedValue == -1) return;
			else break;
		}
		
		String response = FormUtil.readString("new value");
		
		switch(selectedValue) {
			case 1:
				selectedMakler.setName(response);
				break;
			case 2:
				selectedMakler.setAddress(response);
				break;
			case 3:
				selectedMakler.setLogin(response);
				break;
			case 4:
				selectedMakler.setPassword(response);
				break;
			default:
			
		}
		if(selectedValue == -1) return;
		
		selectedMakler.save();
		System.out.println("estate agent successfully updated!\n");
	}
	
	public static void deleteMakler() {
		EstateAgent selectedEstateAgent = selectEstateAgent("delete estate agent");
		if(selectedEstateAgent == null) return;
		
		
		EstateAgent.delete(selectedEstateAgent);
		System.out.println("estate agent deleted\n");
	}
}
