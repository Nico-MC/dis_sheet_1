package de.dis;

import java.util.ArrayList;

import de.dis.data.Contract;
import de.dis.data.Person;
import de.dis.data.PurchaseContract;
import de.dis.data.TenancyContract;

/**
 * Hauptklasse
 */
public class ContractManagement {
	private static Contract baseContract;
	private static Contract subContract;
	
	public static void showContractManagementMenu() {
		final int INSERT_PERSON = 0;
		final int CREATE_CONTRACT = 1;
		final int LIST_CONTRACTS = 2;
		final int QUIT = -1;
		
		Menu mainMenu = new Menu("contract management");
		mainMenu.addEntry("insert person", INSERT_PERSON);
		mainMenu.addEntry("create contract", CREATE_CONTRACT);
		mainMenu.addEntry("show contracts", LIST_CONTRACTS);
		mainMenu.addEntry("quit", QUIT);
		
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case INSERT_PERSON:
					showInsertPersonMenu();
					break;
				case CREATE_CONTRACT:
					showCreateContractMenu();
					break;
				case LIST_CONTRACTS:
					showListContractMenu();
					break;
				case QUIT:
					return;
			}
		}
	}

	public static void showInsertPersonMenu() {
		Person p = new Person();
		
		p.setFirstName(FormUtil.readString("first name"));
		p.setName(FormUtil.readString("name"));
		p.setAddress(FormUtil.readString("address"));
		p.save();
		
		System.out.println("successfully created person with id "+p.getId()+" .\n");
	}

	public static void showCreateContractMenu() {
		Menu mainMenu = new Menu("contract type");
		mainMenu.addEntry("purchase contract", 0);
		mainMenu.addEntry("tenancy contract", 1);
		mainMenu.addEntry("quit", -1);
		
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case 0:
					showPurchaseContractMenu();
					break;
				case 1:
					showTenancyContractMenu();
					break;
				case -1:
					return;
			}
		}
	}
	
	public static void showPurchaseContractMenu() {
		PurchaseContract purchaseContract = new PurchaseContract();
		
		purchaseContract.setNumberOfInstallments(FormUtil.readString("number of installments"));
		purchaseContract.setIntrestRate(Double.parseDouble(FormUtil.readString("intrest rate")));
		subContract = purchaseContract;
		
		showContractMenu();
	}
	
	public static void showTenancyContractMenu() {
		TenancyContract tenancyContract = new TenancyContract();
		
		tenancyContract.setStartDate(FormUtil.readString("start date"));
		tenancyContract.setDuration(FormUtil.readString("duration"));
		tenancyContract.setAdditionalCosts(Double.parseDouble(FormUtil.readString("additional costs")));
		subContract = tenancyContract;
		
		showContractMenu();
	}
	
	public static void showContractMenu() {
		Contract contract = new Contract();
		
		contract.setDate(FormUtil.readString("date"));
		contract.setPlace(FormUtil.readString("place"));
		baseContract = contract;
		
		showSignContractMenu();
	}
	
	public static void showSignContractMenu() {
		Person person = selectPerson("Select the person you want to sign as");
		if(person != null) {
			subContract.save();
			baseContract.save();
			
			System.out.println("successfully created contract with id "+baseContract.getId()+"\n");

		}
	}
	
	public static Person selectPerson(String title) {
		ArrayList<Person> personList = Person.loadAll();
		Menu selectPersonMenu = new Menu(title);
		for (Person person : personList) {
			selectPersonMenu.addEntry(person.getFirstName() + " " + person.getName() + " (" + person.getAddress() + ")", person.getId());
		}

		selectPersonMenu.addEntry("quit", -1);
		
		while(true) {
			int id = selectPersonMenu.show();

			if(id == -1) return null;
			
			return Person.load(id);
		}
	}
	
	public static void showListContractMenu() {
		ArrayList<Contract> contractList = Contract.loadAll();
		System.out.println("#################");
		int i = 0;
		for (Contract contract : contractList) {
			System.out.println("Contract " + contract.getContractNo());
			System.out.println("date: " + contract.getDate());
			System.out.println("place: " + contract.getPlace());
			i++;
			if(i != contractList.size()) System.out.println(",");

		}
		System.out.println("#################\n");
	}
}
