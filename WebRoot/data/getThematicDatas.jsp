<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.DataStruct.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String thematicTableName = new String(request.getParameter("table").getBytes("ISO-8859-1"),"UTF-8");
String thematicTableName = "province_thematic";
String mapTableName = "province";
ThematicTable thematicTable = DataUtils.getThematicTable(mapTableName, thematicTableName);
out.print(DataUtils.toJson(thematicTable.getJsonDatas()));
%>
