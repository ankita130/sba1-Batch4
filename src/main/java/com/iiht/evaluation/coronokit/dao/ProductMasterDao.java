package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.model.ProductMaster;



public class ProductMasterDao {

	private String jdbcDriverName;
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ProductMasterDao(String jdbcDriver,String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcDriverName=jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
	
	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public List<ProductMaster> getAvailableItems() throws ClassNotFoundException, SQLException {
		String query = "select * from ProductMaster";
		this.connect();
		
		PreparedStatement pstatement = this.jdbcConnection.prepareStatement(query);
		ResultSet rs = pstatement.executeQuery();
		List<ProductMaster> items = new ArrayList<ProductMaster>();
		while(rs.next()) {
			ProductMaster item = new ProductMaster(rs.getInt("id"),rs.getString("productName"), rs.getDouble("price"), rs.getString("productDescription"));
			items.add(item);
		}
		
		rs.close();
		pstatement.close();
		this.disconnect();
		
		return items;
	}
	
	public Integer addItem(String itemName,String itemPrice,String itemDescription) throws ClassNotFoundException, SQLException {
		String query = "insert into ProductMaster(productName,price,productDescription) value(?,?,?)";
		int numberOfRowAdded;
		this.connect();
		
		PreparedStatement pstatement = this.jdbcConnection.prepareStatement(query);
		pstatement.setString(1,itemName);
		pstatement.setDouble(2,Double.parseDouble(itemPrice));
		pstatement.setString(3,itemDescription);
		
		numberOfRowAdded=pstatement.executeUpdate();
		if(numberOfRowAdded>0)
		{
		pstatement.close();
		this.disconnect();
		}
		return numberOfRowAdded;
	}
	
	public ProductMaster fetchItemUsingId(String itemId) throws ClassNotFoundException, SQLException {
		String query = "select * from ProductMaster where id=?";
		this.connect();
		
		PreparedStatement pstatement = this.jdbcConnection.prepareStatement(query);
		pstatement.setString(1,itemId);
		
		ResultSet rs = pstatement.executeQuery();
		List<ProductMaster> items = new ArrayList<ProductMaster>();
		while(rs.next()) {
			ProductMaster item = new ProductMaster(rs.getInt("id"),rs.getString("productName"), rs.getDouble("price"), rs.getString("productDescription"));
			items.add(item);
		}
		
		return items.get(0);
	}
	
	public boolean updateItem(int itemId,String itemName,String itemPrice,String itemStock) throws ClassNotFoundException, SQLException {
		String query = "UPDATE ProductMaster SET productName = ?, price =?,productDescription=? where id=?";
		int numberOfRowUpdated;
		this.connect();
		
		PreparedStatement pstatement = this.jdbcConnection.prepareStatement(query);
		pstatement.setString(1,itemName);
		pstatement.setDouble(2,Double.parseDouble(itemPrice));
		pstatement.setString(3,itemStock);
		pstatement.setInt(4,itemId);
		
		numberOfRowUpdated=pstatement.executeUpdate();
		if(numberOfRowUpdated>0)
		{
		pstatement.close();
		this.disconnect();
		}
		return numberOfRowUpdated>0;
	}
	
	public boolean deleteItem(int itemId) throws ClassNotFoundException, SQLException {
		String query = "delete from ProductMaster where id=?";
		int numberOfRowUpdated;
		this.connect();
		
		PreparedStatement pstatement = this.jdbcConnection.prepareStatement(query);
		
		pstatement.setInt(1,itemId);
		
		numberOfRowUpdated=pstatement.executeUpdate();
		if(numberOfRowUpdated>0)
		{
		pstatement.close();
		this.disconnect();
		}
		return numberOfRowUpdated>0;
	}


	
}