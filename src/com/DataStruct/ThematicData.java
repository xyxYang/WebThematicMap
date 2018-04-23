package com.DataStruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThematicData {
	public int id;
	public String name;
	public double lon;
	public double lat;
	public List<Double> datas = new ArrayList<Double>();
	
	public JSONObject toJson(List<String> fields){
		List<JSONObject> dataJsons = listToJsons(fields, datas);
		JSONArray dataJsonArray = new JSONArray(dataJsons);
		JSONObject retJsonObject = new JSONObject();
		retJsonObject.put("id", id);
		retJsonObject.put("name", name);
		retJsonObject.put("lon", lon);
		retJsonObject.put("lat", lat);
		retJsonObject.put("data", dataJsonArray);
		return retJsonObject;
	}
	
	private List<JSONObject> listToJsons(List<String> fields, List<Double> datas){
		List<JSONObject> ret = new ArrayList<JSONObject>();
		int len = Math.min(fields.size(), datas.size());
		for(int i=0; i<len; ++i){
			String name = fields.get(i);
			double value = datas.get(i);
			JSONObject jo = new JSONObject();
			jo.put("name", name);
			jo.put("value", value);
			ret.add(jo);
		}
		return ret;
	}
}
