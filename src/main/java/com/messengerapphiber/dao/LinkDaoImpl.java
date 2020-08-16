package com.messengerapphiber.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.messengerapphiber.model.Link;

public class LinkDaoImpl implements LinkDao {

	
	static Connection con;
	//connection with mysql
	static void getConnectionMySql()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}  
		try {
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/messdb","root","root");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}  
	}
	
	@Override
	public int save(Link link) {
		getConnectionMySql();
		//Date dgh = new Date(0);
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Date today;
		
			//today = dateFormat.parse(dateFormat.format(new Date()));
			//java.sql.Date sqlDate = new java.sql.Date(today.getTime());
			String query="insert into link values (?,?,?,?,)";
			try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, link.getLinkId());
			ps.setString(2, link.getLinks());
			
				ps.setString(3, link.getRel());
				ps.setInt(4,link.getMessLinkFk());
				ps.executeUpdate();
				System.out.println("link created");
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return 0;
	}

}
