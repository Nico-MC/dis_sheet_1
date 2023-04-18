package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Person {
	private int id = -1;
	private String first_name;
	private String name;
	private String address;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void save() {
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			if (getId() == -1) {
				String insertSQL = "INSERT INTO person(first_name, name, address)"
						+ "VALUES (?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, getFirstName());
				pstmt.setString(2, getName());
				pstmt.setString(3, getAddress());
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
	
	public static Person load(int id) {
		try {
			Connection con = DbConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM person WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Person ts = new Person();
				ts.setId(id);
				ts.setName(rs.getString("first_name"));
				ts.setName(rs.getString("name"));
				ts.setAddress(rs.getString("address"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Person> loadAll() {
		try {
			Connection con = DbConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM person";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Person> personList = new ArrayList<Person>();
			while(rs.next()) {
				Person person = new Person();
				person.setId(rs.getInt("id"));
				person.setFirstName(rs.getString("first_name"));
				person.setName(rs.getString("name"));
				person.setAddress(rs.getString("address"));
				personList.add(person);
			}
			rs.close();
			pstmt.close();
			
			return personList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
