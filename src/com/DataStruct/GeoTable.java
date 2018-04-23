package com.DataStruct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.DBManage.DBConBuilder;
import com.DBManage.ResultType;

public class GeoTable {
	private String name;
	private List<GeoData> datas = new ArrayList<GeoData>();
	private static final String selectSQL = "select id, name, midLon, midLat, ST_AsGeoJson(geom) as geojson from %s";
	
	public GeoTable(String tableName){
		name = tableName;
	}
	
	public ResultType initial(){
		Connection con = DBConBuilder.instance.build();
		if(con == null){
			return ResultType.ConnectFailed;
		}
		
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(String.format(selectSQL, name));
			while(rs.next()){
				GeoData geoData = new GeoData();
				geoData.id = rs.getInt(1);
				geoData.name = rs.getString(2);
				
				geoData.midLon = rs.getDouble(3);
				geoData.midLat = rs.getDouble(4);
				geoData.geojson = rs.getString(5);
				datas.add(geoData);
			}
			if(datas.size() != 0){
				return ResultType.Success;
			}
			else{
				return ResultType.NoTable;
			}
		}
		catch(Exception e){
			System.out.print(e.getMessage());
			return ResultType.ConnectFailed;
		}
	}
	
	public GeoPoint getMidPoint(String name){
		for(GeoData geoData:datas){
			if(geoData.name.equals(name)){
				return new GeoPoint(geoData.midLon, geoData.midLat);
			}
		}
		return null;
	}
	
	public GeoPoint getMidPoint(int id){
		for(GeoData geoData:datas){
			if(geoData.id == id){
				return new GeoPoint(geoData.midLon, geoData.midLat);
			}
		}
		return null;
	}
	
	public String getGeoJson(String name){
		for(GeoData geoData:datas){
			if(geoData.name == name){
				return geoData.geojson;
			}
		}
		return null;
	}
	
	public String getGeoJson(int id){
		for(GeoData geoData:datas){
			if(geoData.id == id){
				return geoData.geojson;
			}
		}
		return null;
	}
	
	public String getName(){
		return new String(name);
	}
	
	public List<GeoData> getDatas(){
		return new ArrayList<GeoData>(this.datas);
	}
	/*
	public double getMinX(){
		
	}
	
	public double getMinY(){
		
	}
	
	public double getMaxX(){
		
	}
	
	public double getMaxY(){
		
	}
	*/
}
