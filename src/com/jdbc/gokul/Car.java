package com.jdbc.gokul;

import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * 
 * In this project I taken as the Car Data to our DataBase on mysql workbench and 
 * I will Successfully completed as three process on our data the Three operations are 
 * 1.) Retrieve the record from Data Base
 * 2.) Update the record from backend and stored into our CarDataBase
 * 3.) Finally Delete the particular record from our database.
 */
 
class Car {

	public static void main(String[] args) {
		/*
		 * Here we taken as one input from user taken as user entered 1 Retrieve the data and 2 for update
		 * the data and 3 for delete the data.
		 */
		Scanner s = new Scanner(System.in);
		System.out.println("Enter 1 for Retrieve the Record");
		System.out.println("Enter 2 for Update the Record");
		System.out.println("Enter 3 for Delete the Record");
		int val =s.nextInt();
		switch (val) {       //The entered value can be checked by do the process by switch case process.
		case 1:
			Car.selectRecords();
			break;

		case 2 :
			Car.update();
			break;

		case 3 :
			Car.delete();
			break;
		}
		s.close();
	}
	public  static void selectRecords(){   //In this method to retrieve the record.
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the Car ID :");
		int cid = s.nextInt();
		s.close();

		Properties prop = new Properties(); 
		
		/*
		 * I am stored the url,password,username on my properties because when i change the Database means I
		 * no need to changes on my Java code I can change it on my properties file.
		 * 
		 */
		String qure = ("select * from jdbc.car where CID= "+cid);  //QUERY to retrieve the record.
 
		try {
			FileReader read = new FileReader("config/configCar.properties"); //To read the properties file.
			prop.load(read); //Load the Properties file here.
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);  //JDBC Step 1 process "load the class".
			Connection con = DriverManager.getConnection(url,username,pwd);
			Statement smt =con.createStatement();
			ResultSet rs =smt.executeQuery(qure);
			while(rs.next()) {
				int id = rs.getInt("CID");
				String name = rs.getString("CNAME");
				double cost = rs.getDouble("COST");
				String var = rs.getString("VARIENT");

				System.out.println("ID :"+id+" Name :"+name+" Cost :"+cost+" Varient :"+var);
			}
			rs.close();  smt.close();
			con.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void update() {
		Properties prop = new Properties();
		String dynamicQuery = "insert into jdbc.Car values(?,?,?,?)";
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Update Process");
		System.out.println("Enter Car ID ");
		int cid = s.nextInt();
		System.out.println("Enter Car Name ");
		String cname = s.next();
		cname=cname.toUpperCase();
		System.out.println("Enter Car Cost ");
		double cost = s.nextDouble();
		System.out.println("Enter Car Varient likewise Disel or Petrol or Electric  ");
		String var = s.next();
		var=var.toUpperCase();

		s.close();

		try {
			FileReader read = new FileReader("config/configCar.properties");
			prop.load(read);
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,pwd);
			PreparedStatement pstmt = con.prepareStatement(dynamicQuery);

			pstmt.setDouble(3, cost);
			pstmt.setInt(1, cid);
			pstmt.setString (2, cname);

			pstmt.setString(4,var);

			int rs =pstmt.executeUpdate();
			if(rs>0)
				System.out.println("Data Update Sucessfully ....");
			else
				System.err.println("Data Not Updated...");
			pstmt.close(); con.close(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void delete() {
		Properties prop = new Properties();

		Scanner s = new Scanner(System.in);
		System.out.println("Enter the CID for Delete");
		int cid = s.nextInt();
		String query = "delete from jdbc.Car where cid="+cid;
		s.close();
		try {
			FileReader read = new FileReader("config/configCar.properties");
			prop.load(read);
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,pwd);
			Statement stmt = con.createStatement();
			int rs = stmt.executeUpdate(query);
			if(rs>0)
				System.out.println("Record delete sucess");
			else
				System.err.println("Invalid Id Please Check the ID once Correctly...");
			stmt.close(); con.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
