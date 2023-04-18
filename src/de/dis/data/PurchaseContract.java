package de.dis.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseContract extends Contract {
	private int id = -1;
	private String no_of_installments;
	private Double intrest_rate;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNumberOfInstallments() {
		return no_of_installments;
	}
	
	public void setNumberOfInstallments(String no_of_installments) {
		this.no_of_installments = no_of_installments;
	}
	
	public Double getIntrestRate() {
		return intrest_rate;
	}
	
	public void setIntrestRate(Double intrest_rate) {
		this.intrest_rate = intrest_rate;
	}
	
	@Override
	public void save() {
		Connection con = DbConnectionManager.getInstance().getConnection();

		try {
			if (getId() == -1) {
				String insertSQL = "INSERT INTO purchase_contract(no_of_installments, intrest_rate)"
						+ "VALUES (?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, getNumberOfInstallments());
				pstmt.setDouble(2, getIntrestRate());
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
