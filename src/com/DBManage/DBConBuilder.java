package com.DBManage;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConBuilder {
	private static final String url = "jdbc:postgresql://localhost:5432/WebThematicMap_DB";
	private static final String user = "postgres";
	private static final String password = "Jack1203";
	public static DBConBuilder instance = new DBConBuilder();
	
	private DBConBuilder(){
		
	}
	
	public Connection build(){
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return con;
	}
	
	public DBConn buildDBConn(){
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		if(con != null){
			return new DBConn(con);
		}
		else{
			return null;
		}
	}
}
