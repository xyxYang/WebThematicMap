<%@page import="com.DBManage.ResultType"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="com.DataStruct.GeoData" %>
<%@ page import="java.util.*" %>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
//String tableName = new String(request.getParameter("tableName").getBytes("ISO-8859-1"),"UTF-8");
String tableName = "province";
GeoTable geoTable = new GeoTable(tableName);
ResultType result = geoTable.initial();
if(result == ResultType.NoTable){
	out.print("表不存在");
	return; 
}
else if(result == ResultType.ConnectFailed){
	out.print("数据库连接错误");
	return;
}

List<GeoData> geoDatas = geoTable.getDatas();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'Map.jsp' starting page</title>
    <script type='text/javascript' src='openlayers/lib/OpenLayers.js'></script>  <!--src最好指向自己机器上对应的js库 -->
	<script type="text/javascript">
		function load(){
			var bounds= new OpenLayers.Bounds(73.44696044921875,3.408477306365967,135.08583068847656,53.557926177978516);  //设置坐标范围对象
			var options = {				
				projection: "EPSG:4326",		//地图投影方式
				maxExtent:bounds,				     //坐标范围
				uints:'degrees'	,        //单位
				center: new OpenLayers.LonLat(116.5, 39.5)   //图形中心坐标
			};
			var map = new OpenLayers.Map('map',options);     //构建一个地图对象，并指向后面页面中的div对象，这里是'map'
			
			var wms = new OpenLayers.Layer.WMS(    //构建地图服务WMS对象，
			  	"Map Of China",         //图层名称，最好用中文，由于页面编码原因，写中文可能乱码，可以到网上搜索解决方法			
				"http://180.76.245.169:8080/geoserver/wms/openlayers", 		 	//geoserver所在服务器地址及对应的地图服务		
				{                                           //以下是具体访问参数
					layers: "BoundaryChn2_4l",  //图层名称，对应与我们自己创建的服务layers层名
					style:'',            //样式
					format:'image/png',   //图片格式
					TRANSPARENT:"true",   //是否透明
				},
				  {isBaseLayer: true}   //是否基础层，必须设置
				);
			//添加wms图层
			map.addLayer(wms);	//增加这个wms图层到map对象
			
			var vector = new OpenLayers.Layer.Vector("Point");
			var geojson_Format = new OpenLayers.Format.GeoJSON();
			<%
			for(GeoData geoData:geoDatas){
				 String json = geoData.toJson();%>
				 var geojson=<%=json%>;
				 vector.addFeatures(geojson_Format.read(geojson));
			<%
			}%>
			map.addLayer(vector);
			map.addControl(new OpenLayers.Control.LayerSwitcher());  //增加图层控制
       		map.addControl(new OpenLayers.Control.MousePosition());  //增加鼠标移动显示坐标      
       		var overviewMap =  new OpenLayers.Control.OverviewMap();
       		overviewMap.maximized = true;    //设置为初始可见
       		//overviewMap.autoPan = true;     //视图自动移动
       		overviewMap.minRectSize = 1;   //鹰眼视图框的最小值
       		map.addControl(overviewMap);    //添加鹰眼控件
       		var zoomBox = new OpenLayers.Control.ZoomBox();
       		zoomBox.keyMask = OpenLayers.Handler.MOD_CTRL;
       		map.addControl(zoomBox);                                 //添加放大方法控件
       		map.addControl(new OpenLayers.Control.NavToolbar());     //添加拉框放大工具
       		map.addControl(new OpenLayers.Control.Scale);            //添加比例尺
       		
       		map.zoomToExtent(bounds);		//缩放到全图显示
		}
	</script>  
  </head>  

  <body onload="load()">
  	<div id='map' style='width:1300px;height:500px;'></div>
  	<button onclick="load()">点击</button>
    This is my JSP page. <br>
  </body>
</html>
