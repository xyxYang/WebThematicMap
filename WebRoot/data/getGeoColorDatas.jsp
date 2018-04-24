<%@page import="com.DataCalculate.KindCalculator"%>
<%@page import="com.DataCalculate.KindInfo"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.RET"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="org.json.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String thematicTableName = new String(request.getParameter("table").getBytes("ISO-8859-1"),"UTF-8");
//String thematicTableName = "province_thematic";
String mapTableName = "province";
GeoTable geoTable = DataUtils.getGeoTable(mapTableName);
ThematicTable thematicTable = DataUtils.getThematicTable(mapTableName, thematicTableName);
Map<Integer, Double> gid2data = thematicTable.getList("gdp08");

List<JSONObject> geolist = DataUtils.getGeometryDatas(geoTable, gid2data);
JSONArray geoJa = new JSONArray(geolist);

List<KindInfo> kindList = KindCalculator.EqualDistanceClassification(gid2data.values(), 5);
List<JSONObject> kindjsonList = new ArrayList<JSONObject>();
for(KindInfo info : kindList){
	kindjsonList.add(new JSONObject(info.toJson()));
}
JSONArray kindJa = new JSONArray(kindjsonList);

JSONObject ret = new JSONObject();
ret.put("features", geoJa);
ret.put("kindInfos", kindJa);

out.print(ret.toString());
%>
