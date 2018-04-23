<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.DBManage.ResultType"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="com.DataStruct.GeoData" %>
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <meta charset='utf-8'></meta>
    <title>My JSP 'Map.jsp' starting page</title>
    <script type='text/javascript' src='openlayers/lib/OpenLayers.js'></script>  <!--src最好指向自己机器上对应的js库 -->
	<script src='lib/echarts.js'></script>
	<script type="text/javascript">
		var map;
		function load(){
			var bounds= new OpenLayers.Bounds(73.44696044921875,3.408477306365967,135.08583068847656,53.557926177978516);  //设置坐标范围对象
			var options = {				
				projection: "EPSG:4326",		//地图投影方式
				maxExtent:bounds,				     //坐标范围
				uints:'degrees'	,        //单位
				center: new OpenLayers.LonLat(116.5, 39.5)   //图形中心坐标
			};
			map = new OpenLayers.Map('map',options);     //构建一个地图对象，并指向后面页面中的div对象，这里是'map'
			
			var wms = new OpenLayers.Layer.WMS(    //构建地图服务WMS对象，
			  	"Map Of China",         //图层名称，最好用中文，由于页面编码原因，写中文可能乱码，可以到网上搜索解决方法			
				"http://gisserver.tianditu.com/TDTService/region/wms", 		 	//geoserver所在服务器地址及对应的地图服务		
				{                                           //以下是具体访问参数
					layers: "030100",  //图层名称，对应与我们自己创建的服务layers层名
					style:'',            //样式
					format:'image/png',   //图片格式
					TRANSPARENT:"true",   //是否透明
				},
				  {isBaseLayer: true}   //是否基础层，必须设置
				);
			map.addLayer(wms);	//增加这个wms图层到map对象

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
	<script type="text/javascript">
		function addThematicData(option, table){				
			var req = new XMLHttpRequest();
			var url = "pic/getThematicPics.jsp?" + "table=" + table;
			req.open("GET", url, true);
			req.send(null);
			req.onreadystatechange = function f(){
				if(req.readyState == 4)
				{
					var datas = req.responseText;  //返回文本数据
					datas = eval("(" + datas + ")");
					removeAllPopups();
					addMapCharts(option, datas, 50, 50);
					map.events.register("zoomend", map, function(){
						removeAllPopups();
                    	addMapCharts(option, datas, 150, 150);  
            		});
				}
			};  
		}
		
		function br2mm(lonlat, xSize, ySize){
			var pixel = map.getViewPortPxFromLonLat(lonlat);
			var newX = pixel.x - xSize/2;
			var newY = pixel.y - ySize/2;
			var newPixel = new OpenLayers.Pixel(newX, newY);
			return map.getLonLatFromViewPortPx(newPixel);
		}
		
		function addMapCharts(option, datas, xSize, ySize){
			var zoom = map.getZoom();
			//xSize = xSize + (zoom - 1) * 40;
			//ySize = ySize + (zoom - 1) * 40;
			for(var i=0; i<datas.length; ++i){
				var data = datas[i];
				var chartID = "chart" + data.gid;
				var content = "<img id='" + chartID + "' src='" +
					data.graphURL + "'>";
				var lonlat = new OpenLayers.LonLat(data.lon, data.lat);
				lonlat = br2mm(lonlat, xSize, ySize);
				var popup = new OpenLayers.Popup(chartID,
					lonlat,
					new OpenLayers.Size(xSize, ySize),
					content,
					false);
				popup.setBackgroundColor("transparent");  
                popup.setBorder("0px #0066ff solid");  
                popup.keepInMap = false;  
                map.addPopup(popup,false);
			}
		}

		function removeAllPopups(){
			while(map.popups.length > 0){
				map.removePopup(map.popups[0]);
			}
		}
	</script> 
  </head>  

  <body onload="load()">
  	<div id='map' style='width:100%;height:90%;'></div>
  	<button onclick="addThematicData('pie')">饼图</button>
  	<button onclick="addThematicData('line')">折线图</button>
  	<button onclick="addThematicData('bar')">柱状图</button>
  </body>
</html>
