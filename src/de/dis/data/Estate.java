package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Estate {
	private int id = -1;
	private String city;
	private int postalCode;
	private String street;
	private int streetNumber;
	private double squareArea;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEstateId() {
		return id;
	}
	
	public void setEstateId(int estateId) {
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = Integer.parseInt(postalCode);
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = Integer.parseInt(streetNumber);
	}
	
	public double getSquareArea() {
		return squareArea;
	}
	
	public void setSquareArea(String squareArea) {
		this.squareArea = Double.parseDouble(squareArea);
	}
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Estate load(int id) {
		try {
			// Hole Verbindung
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM estate WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Estate e = new Estate();
				e.setId(id);
				e.setCity(rs.getString("city"));
				e.setPostalCode(rs.getString("postal_code"));
				e.setStreet(rs.getString("street"));
				e.setStreetNumber(rs.getString("street_number"));
				e.setSquareArea(rs.getString("square_area"));

				rs.close();
				pstmt.close();
				return e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
//	public static Estate load(String login, String password) {
//		try {
//			// Hole Verbindung
//			Connection con = DbConnectionManager.getInstance().getConnection();
//
//			// Erzeuge Anfrage
//			String selectSQL = "SELECT * FROM estate_agent WHERE login = ? AND password = crypt(?, password)";
//			PreparedStatement pstmt = con.prepareStatement(selectSQL);
//			pstmt.setString(1, login);
//			pstmt.setString(2, password);
//
//			// Führe Anfrage aus
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				Estate ts = new Estate();
//				ts.setId(rs.getInt("agent_id"));
//				ts.setName(rs.getString("name"));
//				ts.setAddress(rs.getString("address"));
//				ts.setLogin(rs.getString("login"));
//				ts.setPassword(rs.getString("password"));
//
//				rs.close();
//				pstmt.close();
//				return ts;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von der DB geholt und dem Model übergeben.
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO estate(city, postal_code, street, street_number, square_area)"
						+ "VALUES (?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostalCode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetNumber());
				pstmt.setDouble(5, getSquareArea());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE estate SET city = ?, postal_code = ?, street = ?, street_number = ?, square_area = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostalCode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetNumber());
				pstmt.setDouble(5, getSquareArea());
				pstmt.setInt(6, getId());
				pstmt.executeUpdate();

				pstmt.close();
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
	public static int delete(Estate estate) {
		try {			
			// Hole Verbindung
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "DELETE FROM estate WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, estate.getId());
			
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
	public static ArrayList<Estate> loadAll() {
		try {
			// Hole Verbindung
			Connection con = DbConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM estate";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Estate> estateList = new ArrayList<Estate>();
			while(rs.next()) {
				Estate estate = new Estate();
				estate.setId(rs.getInt("id"));
				estate.setCity(rs.getString("city"));
				estate.setPostalCode(rs.getString("postal_code"));
				estate.setStreet(rs.getString("street"));
				estate.setStreetNumber(rs.getString("street_number"));
				estate.setSquareArea(rs.getString("square_area"));
				estateList.add(estate);
			}
			rs.close();
			pstmt.close();
			
			return estateList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
