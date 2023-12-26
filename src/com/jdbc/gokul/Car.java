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
		String qure = ("select * from jdbc.car where CID= "+cid);  //QUERY to retrieve the record. And this also JDBC Step 4 "Execute the SQL Statement".
 
		try {
			FileReader read = new FileReader("config/configCar.properties"); //To read the properties file.
			prop.load(read); //Load the Properties file here.
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);  //JDBC Step 1 process "load the class".
			Connection con = DriverManager.getConnection(url,username,pwd); //JDBC Step 2 "Establish the connection"
			Statement smt =con.createStatement();   //JDBC Step 3 "Create the Statement."
			ResultSet rs =smt.executeQuery(qure);   //JDBC Step 5 "ResultSet".
			while(rs.next()) {  // next() method is used to retrieve the record from one by one. 
				int id = rs.getInt("CID");
				String name = rs.getString("CNAME");
				double cost = rs.getDouble("COST");
				String var = rs.getString("VARIENT");

				System.out.println("ID :"+id+" Name :"+name+" Cost :"+cost+" Varient :"+var);//Print all the retrieve element.
			}
			rs.close();  smt.close();//This is IMPORTANT process to close all the open source because safety purpose.
			con.close();
		} 
		catch (Exception e) {  //Catch the exception.
			e.printStackTrace();
		}
	}
	public static void update() { //AGAIN the Same process to update the data.
		Properties prop = new Properties();
		String dynamicQuery = "insert into jdbc.Car values(?,?,?,?)";   //hear ?-> is used to what the column name on our data base order
		Scanner s = new Scanner(System.in);								//and this query is used to insert the value
		System.out.println("Enter Update Process");
		System.out.println("Enter Car ID ");
		int cid = s.nextInt();
		System.out.println("Enter Car Name ");
		String cname = s.next();
		cname=cname.toUpperCase();          //Convert the user Entered value to UpperCase to Store the Data.
		System.out.println("Enter Car Cost ");
		double cost = s.nextDouble();
		System.out.println("Enter Car Varient likewise Disel or Petrol or Electric  ");
		String var = s.next();
		var=var.toUpperCase();				//Convert the user Entered value to UpperCase to Store the Data.

		s.close();

		try {
			/**
			 * Same JDBC process to to read and all JDBC process work. 
			 * 
			 */
			FileReader read = new FileReader("config/configCar.properties");
			prop.load(read);
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,pwd);
			PreparedStatement pstmt = con.prepareStatement(dynamicQuery);

			pstmt.setDouble(3, cost);  //This is the 3rd-->? on the Query.
			pstmt.setInt(1, cid);	   //This is the 1st-->? on the Query.
			pstmt.setString (2, cname);//This is the 2nd-->? on the Query.

			pstmt.setString(4,var);    //This is the 4th-->? on the Query.

			int rs =pstmt.executeUpdate();  //executeUpdate () method is used to execute the Query and update the value.
			if(rs>0)
				System.out.println("Data Update Sucessfully ....");  //Data can be updated means print this success message
			else
				System.err.println("Data Not Updated...");  //or else print this error message.
			pstmt.close(); con.close(); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void delete() {
		/**
		 * Same all the Process to delete the Query also.
		 */
		Properties prop = new Properties();

		Scanner s = new Scanner(System.in);
		System.out.println("Enter the CID for Delete");
		int cid = s.nextInt();
		String query = "delete from jdbc.Car where cid="+cid;  //QUERY to delete the record from table.
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
			int rs = stmt.executeUpdate(query);   	//executeUpdate () method is used to execute the Query and update
			if(rs>0)
				System.out.println("Record delete sucess"); //Data data deleted sucessfully print this message
			else
				System.err.println("Invalid Id Please Check the ID once Correctly...");  //if not deleted means print this error message.
			stmt.close(); con.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
