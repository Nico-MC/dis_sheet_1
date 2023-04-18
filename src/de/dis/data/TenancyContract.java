package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TenancyContract extends Contract {
	private int id = -1;
	private String start_date;
	private String duration;
	private Double additional_costs;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStartDate() {
		return start_date;
	}
	
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Double getAdditionalCosts() {
		return additional_costs;
	}
	
	public void setAdditionalCosts(Double additional_costs) {
		this.additional_costs = additional_costs;
	}
	
	@Override
	public void save() {
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			if (getId() == -1) {
				String insertSQL = "INSERT INTO tenancy_contract(start_date, duration, additional_costs)"
						+ "VALUES (?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, getStartDate());
				pstmt.setString(2, getDuration());
				pstmt.setDouble(3, getAdditionalCosts());
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
}
