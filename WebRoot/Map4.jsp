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
		function loadmap(chinaJson){
			echarts.registerMap('china', chinaJson);
			var map = echarts.init(document.getElementById('map'));
			var option = {
				geo:{
					map: 'china',
					roam: true
				},
				series:[
					{
			        type: 'pie',
			        radius: '60%',
			        startAngle:'45',
			        label: {
			            normal: {
			            	//position:'inside',
			                show: false
			            },
			            emphasis: {
			                show: false,
			                textStyle:{
			                  color: '#000000',
			                  fontWeight:'bold',
			                  fontSize:12
			                }
			            }
			        },
			        lableLine: {
			          normal: {
			              show: false
			          },
			          emphasis: {
			              show: false
			          }
			        },
			        data:[{"name":"a", "value":1}, {"name":"b", "value":2}]
					}
				]
			};
			map.setOption(option);
		}
		
		function load(table){
			var req = new XMLHttpRequest();
			var url = "data/getGeoJson.jsp?" + "table=" + table;
			req.open("GET", url, true);
			req.send(null);
			req.onreadystatechange = function f(){
				if(req.readyState == 4)
				{
					var datas = req.responseText;  //返回文本数据
					alert(datas);
					datas = eval("(" + datas + ")");
					loadmap(datas);
				}
			};  
		}
	</script> 
	<script type="text/javascript">
		function addThematicData(option, table){				
			var req = new XMLHttpRequest();
			var url = "pic/getThematicPics.jsp?" + "table=" + table + 
				"&option=" + option;
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
                    	addMapCharts(option, datas, 100, 100);  
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
			xSize = xSize + (zoom - 1) * 50;
			ySize = ySize + (zoom - 1) * 50;
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
  	<button onclick="addThematicData('pie','province_thematic')">饼图</button>
  	<button onclick="addThematicData('line','province_thematic')">折线图</button>
  	<button onclick="addThematicData('bar','province_thematic')">柱状图</button>
  </body>
</html>
