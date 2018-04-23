package com.DataCalculate;

import java.awt.Color;

import org.json.JSONObject;

import com.DataStruct.DataUtils;

public class KindInfo {
	public double lower;
	public double upper;
	public Color color;
	
	public String toJson(){
		if(lower > upper){
			double tmp = lower;
			lower = upper;
			upper = tmp;
		}
		
		JSONObject jo = new JSONObject();
		jo.put("lower", lower);
		jo.put("upper", upper);
		jo.put("color", DataUtils.colorToString(color));
		return jo.toString();
	}
	
	
}
