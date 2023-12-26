package com.jdbc.gokul;

import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * 
 * In this project I taken as the Car Data to our DataBase on mysql workbench and 
 * I will Successfully completed as three process on our data that Three operations are 
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
		if(val>3 )
			System.err.println("Invalid Value Please enter the Value at 1 To 3");
		switch (val) {                      //The entered value can be checked by do the process by switch case process.
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
		/*
		 * I am stored the url,password,username on my properties because when i change the Database means I
		 * no need to changes on my Java code I can change it on my properties file.
		 * 
		 */
		String qure = ("select * from jdbc.car where CID= "+cid);  //QUERY to retrieve the record. And this also JDBC Step 4 "Execute the SQL Statement".
 
		try {
			
			Connection con = DBSingleton.getConnectionObject(); //JDBC Step 2 "Establish the connection"
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

		String dynamicQuery = "insert into jdbc.Car values(?,?,?,?)";   /*hear ?-> is used to what the column name on our data base order
																		and this query is used to insert the value
																		*/
		
		Scanner s = new Scanner(System.in);								
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
			Connection con = DBSingleton.getConnectionObject();
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
//		Properties prop = new Properties();

		Scanner s = new Scanner(System.in);
		System.out.println("Enter the CID for Delete");
		int cid = s.nextInt();
		String query = "delete from jdbc.Car where cid="+cid;  //QUERY to delete the record from table.
		s.close();
		try {
			Connection con = DBSingleton.getConnectionObject();
			Statement stmt = con.createStatement();
			int rs = stmt.executeUpdate(query);   	//executeUpdate () method is used to execute the Query and update
			if(rs>0)
				System.out.println("Record delete sucess"); //Data data deleted successfully print this message
			else
				System.err.println("Invalid Id Please Check the ID once Correctly...");  //if not deleted means print this error message.
			stmt.close(); con.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * In this DBSingleton class we taken first two process of JDBC will be created this class and we can call this method to 
 * all the place because of this will reduce our code.
 * 
 * why I am taken properties file here means once you can change your DATABASE mens you no need to change in java source 
 * code I can change in my Properties file so it will not affect the java Source code..
 * 
 */
final class DBSingleton{
	private static Connection con;
	static {
		try {
			Properties prop = new Properties();


			FileReader read = new FileReader("config/configCar.properties");
			prop.load(read);
			String username = prop.getProperty("UserName");
			String pwd = prop.getProperty("Password");
			String url = prop.getProperty("url");
			String driver = prop.getProperty("Driver");

			Class.forName(driver);
			 con = DriverManager.getConnection(url,username,pwd);  
			 /**
			  * Important process here I no need to close the connection because i can call this method to our processed method. 
			  * If I can close the connection by hear mens we can face the Exception.
		      */
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private DBSingleton() {

	}  
	public static Connection getConnectionObject() {
		return con;
	}
}

