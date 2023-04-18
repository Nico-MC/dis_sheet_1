package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Contract {
	private int id = -1;
	private String contract_no;
	private String date;
	private String place;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getContractNo() {
		return contract_no;
	}
	
	public void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}	
	
	public void save() {
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			if (getId() == -1) {
				String insertSQL = "INSERT INTO contract(date, place)"
						+ "VALUES (?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, getDate());
				pstmt.setString(2, getPlace());
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Löscht einen Makler aus der Datenbank
	 * @param id ID des zu löschenden Maklers
	 * @return 1 wenn erfolgreich, 0 wenn nichts passiert ist
	 */
	public static int delete(Contract makler) {
		try {			
			// Hole Verbindung
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "DELETE FROM estate_agent WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, makler.getId());
			
			// Führe Anfrage aus
			pstmt.executeUpdate();
			pstmt.close();
			
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Lädt alle Makler aus der Datenbank
	 * @return Gibt alle Makler-Instanzen als Liste zurück
	 */
	public static ArrayList<Contract> loadAll() {
		try {
			// Hole Verbindung
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM contract";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Contract> contractList = new ArrayList<Contract>();
			while(rs.next()) {
				Contract contract = new Contract();
				contract.setId(rs.getInt("id"));
				contract.setContractNo(rs.getString("contract_no"));
				contract.setDate(rs.getString("date"));
				contract.setPlace(rs.getString("place"));
				contractList.add(contract);
			}
			rs.close();
			pstmt.close();
			
			return contractList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
