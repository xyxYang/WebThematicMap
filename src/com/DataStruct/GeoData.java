package com.DataStruct;

public class GeoData {
	public int id;
	public String name;
	public double midLon;
	public double midLat;
	public String geojson;
	
	public String toJson(){
		String jsonFormat = "{\"type\":\"Feature\",\"geometry\":%s,\"properties\":%s}";
		String propertiesFormat = "{\"name\":\"%s\",\"gid\":%d,\"midLon\":%f,\"midLat\":%f}";
		String properties = String.format(propertiesFormat, name, id, midLon, midLat);
		String json = String.format(jsonFormat, geojson, properties);
		//String point = String.format("{\"type\":\"Point\",\"coordinates\":[%f,%f]}", midLon, midLat);
		//String json = String.format(jsonFormat, point, properties);
		return json;
	}
}
