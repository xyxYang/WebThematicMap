package com.DataStruct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;

import com.DBManage.DBConBuilder;
import com.DBManage.ResultType;

public class ThematicTable {
	private String name;
	private List<String> fields = new ArrayList<String>();
	private List<ThematicData> datas = new ArrayList<ThematicData>();
	private static final String selectSQL = "select * from %s";
	
	public ThematicTable(String tableName) {
		this.name = tableName;
	}

	public ResultType initial() {
		Connection con = DBConBuilder.instance.build();
		if (con == null) {
			return ResultType.ConnectFailed;
		}

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(String.format(selectSQL, name));
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnName(i);
				if(!(columnName.equals("id") || columnName.equals("name"))){
					fields.add(columnName);
				}
			}
			while (rs.next()) {
				ThematicData data = new ThematicData();
				data.id = rs.getInt("gid");
				//data.name = rs.getString("name");
				for(String fieldName:fields){
					//System.out.print(fieldName);
					data.datas.add(rs.getDouble(fieldName));
				}
				datas.add(data);
			}
			if (datas.size() != 0) {
				return ResultType.Success;
			} else {
				return ResultType.NoTable;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResultType.ConnectFailed;
		}
	}
	
	public int setGeoData(GeoTable geoTable){
		Map<Integer, GeoData> geoDataIndexs = getIndexs(geoTable);
		int count = 0;
		for(ThematicData data : datas){
			GeoData geoData = geoDataIndexs.get(data.id);
			if(geoData == null){
				continue;
			}
			data.lon = geoData.midLon;
			data.lat = geoData.midLat;
			data.name = geoData.name;
			++ count;
		}
		return count;
	}
	
	private Map<Integer, GeoData> getIndexs(GeoTable geoTable){
		List<GeoData> geoDatas = geoTable.getDatas();
		Map<Integer, GeoData> ret = new HashMap<Integer, GeoData>();
		for(GeoData data : geoDatas){
			int id = data.id;
			ret.put(id, data);
		}
		return ret;
	}
	
	public int getFieldIndex(String fieldName){
		return fields.indexOf(fieldName);
	}
	
	public List<String> getFields(){
		return new ArrayList<String>(fields);
	}
	
	public List<ThematicData> getDatas(){
		return new ArrayList<ThematicData>(datas);
	}
	
	public List<JSONObject> getJsonDatas(){
		List<JSONObject> ret = new ArrayList<JSONObject>();
		for(ThematicData data:datas){
			JSONObject json = data.toJson(fields);
			ret.add(json);
		}
		return ret;
	}
}
