package com.mahendra;

import java.sql.*;

public class Main {

	public static void main(String[] args) {
		
		Connection con = null;
		
		//Phase 1: Load JDBC Driver (JDK 11/ OPtional)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC Driver loaded");
		}catch(ClassNotFoundException  ex) {
			ex.printStackTrace();
			return;
		}
		
		//Phase 2: Establish connection with database
		
		try {
		con = DriverManager.getConnection
					("jdbc:mysql://mydata.cqre1azyhyqk.ap-south-1.rds.amazonaws.com:3306",
							"admin","Pass$12345");
		System.out.println("Connected to database");
		}catch(SQLException ex) {
			ex.printStackTrace();
			return;
		}
		
		//Phase 3: Create a statement to create table and add few records
		try {
		Statement st = con.createStatement();
		st.execute("CREATE DATABASE IF NOT EXISTS sales");
		st.execute("use sales");
		boolean result = st.execute("create table products ( pid int primary key, pname varchar(20))");
		System.out.println("Table created : "+result);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}

		
		//END 
		try {
		con.close();
		System.out.println("SQL Connection released ...");
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
