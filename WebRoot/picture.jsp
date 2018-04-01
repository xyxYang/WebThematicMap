<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DrawThematicData.*"%>
<%@ page import="org.jfree.chart.*"%>
<%@ page import="org.jfree.chart.servlet.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
		
List<String> keys = Arrays.asList("1月", "2月", "3月","4月","5月");
List<Double> values = Arrays.asList(21.0D, 50.0D, 152.0D, 184.0D, 299.0D);
List<String> rows = Arrays.asList("Series 1");
ChartInfo info = ChartInfo.createLineInfo("标题", "x", "y");
		
JFreeChart chart = ChartBuilder.build(keys, values, rows, info);

ServletUtilities.setTempFilePrefix("public-jfreechart-");
//保存图片 返回图片文件名
String filename = ServletUtilities.saveChartAsPNG(chart, 600, 400, null, session); 
//获取图片路径（内存中）
String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
%>

<html>
	<body>
		<img src="<%=graphURL %>" width="800" height="600"> 
	</body>
</html>