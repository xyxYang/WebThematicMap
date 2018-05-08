package com.DataStruct;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DBManage.ResultType;

public class DataUtils {
	public static ThematicTable getThematicTable(String geoTableName, String thematicTableName){
		ThematicTable thematicTable = new ThematicTable(thematicTableName);
		GeoTable geoTable = new GeoTable(geoTableName);
		ResultType type;
		type = thematicTable.initial();
		if(type == ResultType.ConnectFailed){
			System.out.print("数据库连接错误");
			return null;
		}
		else if(type == ResultType.NoTable){
			System.out.print("专题数据表不存在");
			return null;
		}
		type = geoTable.initial();
		if(type == ResultType.ConnectFailed){
			System.out.print("数据库连接错误");
			return null;
		}
		else if(type == ResultType.NoTable){
			System.out.print("几何数据表不存在");
			return null;
		}
		thematicTable.setGeoData(geoTable);
		return thematicTable;
	}
	
	public static GeoTable getGeoTable(String name){
		GeoTable geoTable = new GeoTable(name);
		ResultType result = geoTable.initial();
		if(result == ResultType.NoTable){
			System.out.print("表不存在");
			return null; 
		}
		else if(result == ResultType.ConnectFailed){
			System.out.print("数据库连接错误");
			return null;
		}
		return geoTable;
	}
	
	public static String toJson(List<JSONObject> jsons){
		JSONArray ja = new JSONArray(jsons);
		return ja.toString();
	}
	
	public static String colorToString(Color color){
		String r,g,b;  
        StringBuilder su = new StringBuilder();  
        r = Integer.toHexString(color.getRed());  
        g = Integer.toHexString(color.getGreen());  
        b = Integer.toHexString(color.getBlue());  
        r = r.length() == 1 ? "0" + r : r;  
        g = g.length() ==1 ? "0" +g : g;  
        b = b.length() == 1 ? "0" + b : b;  
        su.append("#");  
        su.append(r);  
        su.append(g);  
        su.append(b);  
        //#0000FF  
        return su.toString();  
	}
	
	public static List<JSONObject> getGeometryDatas(GeoTable geoTable, Map<Integer, Double> gid2data){
		List<JSONObject> ret = new ArrayList<JSONObject>();
		for(GeoData geoData : geoTable.getDatas()){
			int gid = geoData.id;
			String geoJson = geoData.geojson;
			Double data = gid2data.get(gid);
			ret.add(toGeoJson(geoJson, data));
		}
		return ret;
	}
	
	private static JSONObject toGeoJson(String geoJson, Double data){
		JSONObject properties = new JSONObject();
		if(data != null){
			properties.put("data", data);
		}
		
		JSONObject ret = new JSONObject();
		ret.put("type", "Feature");
		ret.put("geometry", new JSONObject(geoJson));
		ret.put("properties", properties);
		return ret;
	}
}
