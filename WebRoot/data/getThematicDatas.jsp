<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.DataStruct.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String thematicTableName = new String(request.getParameter("table").getBytes("ISO-8859-1"),"UTF-8");
String mapTableName = "province";
ThematicTable thematicTable = new ThematicTable(thematicTableName);
GeoTable geoTable = new GeoTable(mapTableName);

%>
