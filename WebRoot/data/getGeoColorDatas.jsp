<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="org.json.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String thematicTableName = new String(request.getParameter("table").getBytes("ISO-8859-1"),"UTF-8");
String thematicTableName = "province_thematic";
String mapTableName = "province";
GeoTable geoTable = DataUtils.getGeoTable(mapTableName);
List<GeoData> geoDatas = geoTable.getDatas();
List<JSONObject> ret = new ArrayList<JSONObject>();
for(GeoData geoData : geoDatas){
	JSONObject geoObject = new JSONObject(geoData.toJson());
	JSONObject jo = new JSONObject();
	jo.put("geometry", geoObject);
	jo.put("color", "#ff0000");
	ret.add(jo);
}
out.print(DataUtils.toJson(ret));
%>
