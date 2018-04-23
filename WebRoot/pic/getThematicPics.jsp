<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DrawThematicData.*"%>
<%@ page import="org.jfree.chart.*"%>
<%@ page import="org.jfree.chart.servlet.*" %>
<%@ page import="com.DataStruct.*" %>
<%@ page import="org.json.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
String thematicTableName = "province_thematic";
String mapTableName = "province";
ThematicTable thematicTable = DataUtils.getThematicTable(mapTableName, thematicTableName);
List<String> keys = thematicTable.getFields();
List<ThematicData> thematicDatas = thematicTable.getDatas();

JSONArray ja = new JSONArray();
for(ThematicData thematicData : thematicDatas){
	JSONObject jo = new JSONObject();
	List<Double> values = thematicData.datas;
	List<String> rows = Arrays.asList("Series 1");
	ChartInfo info = ChartInfo.createLineInfo("标题", "x", "y");
	JFreeChart chart = ChartBuilder.build(keys, values, rows, info);

	//ServletUtilities.setTempFilePrefix("public-jfreechart-");
	//保存图片 返回图片文件名
	String filename = ServletUtilities.saveChartAsPNG(chart, 300, 300, null, session); 
	//获取图片路径（内存中）
	String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
	
	jo.put("lon", thematicData.lon);
	jo.put("lat", thematicData.lat);
	jo.put("gid", thematicData.id);
	jo.put("name", thematicData.name);
	jo.put("graphURL", graphURL);
	
	ja.put(jo);
}
out.print(ja.toString());
%>

