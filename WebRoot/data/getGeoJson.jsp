<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="org.json.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mapTableName = "province";
GeoTable geoTable = DataUtils.getGeoTable(mapTableName);
JSONArray ja = new JSONArray(geoTable.getJsonDatas());
JSONObject jo = new JSONObject();
jo.put("type", "FeatureCollection");
jo.put("features", ja);
out.print(jo.toString());
%>