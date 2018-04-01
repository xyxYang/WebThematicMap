package com.DBManage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConn {
	private Connection con;
	private Statement st;
	
	public DBConn(Connection con){
		this.con = con;
		this.st = null;
	}
	
	public Object res(String sql){
		try{
			if(st != null){
				st.close();
			}
			this.st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next())
				return null;
			return rs.getObject(1);
		}
		catch (Exception ex) {
			// TODO: handle exception
			System.out.print(ex.getMessage());
			return null;
		}
	}
	
	public List<Object> list(String sql){
		try{
			if(st != null){
				st.close();
			}
			List<Object> ret = new ArrayList<Object>();
			this.st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				ret.add(rs.getObject(1));
			}
			return ret;
		}
		catch (Exception ex) {
			// TODO: handle exception
			System.out.print(ex.getMessage());
			return null;
		}
	}
	
	public List<Object> row(String sql){
		try{
			if(st != null){
				st.close();
			}
			List<Object> ret = new ArrayList<Object>(); 
			this.st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(!rs.first())
				return null;
			for(int i=1; i<=rs.getMetaData().getColumnCount(); ++i){
				ret.add(rs.getObject(i));
			}
			return ret;
		}
		catch (Exception ex) {
			// TODO: handle exception
			System.out.print(ex.getMessage());
			return null;
		}
	}
	
	public ResultSet resultset(String sql){
		try{
			if(st != null){
				st.close();
			}
			this.st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			return rs;
		}catch(Exception ex){
			System.out.print(ex.getMessage());
			return null;
		}
	}
	
	public int update(String sql){
		try{
			this.st = con.createStatement();
			int ret = st.executeUpdate(sql);
			return ret;
		}catch(Exception ex){
			System.out.print(ex.getMessage());
			return -1;
		}
	}
	
	public void close(){
		try{
			this.st.close();
			this.con.close();
		}catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	}
}
