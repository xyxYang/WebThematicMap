package com.DataStruct;

import java.awt.Color;
import java.util.List;

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
			System.out.print("���ݿ����Ӵ���");
			return null;
		}
		else if(type == ResultType.NoTable){
			System.out.print("ר�����ݱ�����");
			return null;
		}
		type = geoTable.initial();
		if(type == ResultType.ConnectFailed){
			System.out.print("���ݿ����Ӵ���");
			return null;
		}
		else if(type == ResultType.NoTable){
			System.out.print("�������ݱ�����");
			return null;
		}
		thematicTable.setGeoData(geoTable);
		return thematicTable;
	}
	
	public static GeoTable getGeoTable(String name){
		GeoTable geoTable = new GeoTable(name);
		ResultType result = geoTable.initial();
		if(result == ResultType.NoTable){
			System.out.print("������");
			return null; 
		}
		else if(result == ResultType.ConnectFailed){
			System.out.print("���ݿ����Ӵ���");
			return null;
		}
		return geoTable;
	}
	
	public static String toJson(List<JSONObject> jsons){
		JSONArray ja = new JSONArray(jsons);
		return ja.toString();
	}
	
	public static String colorToString(Color color){
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		StringBuilder sb = new StringBuilder();
		sb.append('#');
		sb.append(Integer.toHexString(r));
		sb.append(Integer.toHexString(g));
		sb.append(Integer.toHexString(b));
		return sb.toString();
	}
}
