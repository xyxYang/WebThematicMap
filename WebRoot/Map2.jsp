<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.DBManage.ResultType"%>
<%@ page import="com.DataStruct.*" %>
<%@ page import="com.DataStruct.GeoData" %>
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'Map.jsp' starting page</title>
    <script type='text/javascript' src='openlayers/lib/OpenLayers.js'></script>  <!--src最好指向自己机器上对应的js库 -->
	<script src='lib/echarts.js'></script>
	<script type="text/javascript">
		var map;
		var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
        var toProjection   = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
		function load(){
			var bounds= new OpenLayers.Bounds(73.44696044921875,3.408477306365967,135.08583068847656,53.557926177978516);  //设置坐标范围对象
			var options = {				
				projection: "EPSG:900913",   //地图投影方式
				maxExtent:bounds.transform( fromProjection, toProjection),				     //坐标范围
				uints:'degrees'	,        //单位
				center: new OpenLayers.LonLat(116.5, 39.5).transform( fromProjection, toProjection)   //图形中心坐标
			};
			map = new OpenLayers.Map('map',options);     //构建一个地图对象，并指向后面页面中的div对象，这里是'map'

			var osm = new OpenLayers.Layer.OSM();
			map.addLayer(osm);
			
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
		
		function addThematicData(table){				
			var req = new XMLHttpRequest();
			var method = document.getElementById("method").value;
			var num = document.getElementById("num").value;
			var url = "data/getGeoColorDatas.jsp?" + "table=" + table
				 + "&method=" + method
				 + "&num=" + num;
			req.open("GET", url, true);
			req.send(null);
			req.onreadystatechange = function f(){
				if(req.readyState == 4)
				{
					var resp = req.responseText;  //返回文本数据
					resp = eval("(" + resp + ")");
					var geoDatas = resp.features;
					var kindInfos = resp.kindInfos;
					var rules = getRules(kindInfos);
					addVector(geoDatas, rules);
				}
			};  
		}
		
		function getRules(kindInfos){
			var rules = new Array();
			for(var i=0; i<kindInfos.length; ++i){
				var kindInfo = kindInfos[i];
				var rule = new OpenLayers.Rule({
					filter: new OpenLayers.Filter.Comparison({
						type: OpenLayers.Filter.Comparison.BETWEEN,
						property: "data",
						lowerBoundary: kindInfo.lower,  
                    	upperBoundary: kindInfo.upper
                    }), 
                    symbolizer:{  
                        fillColor: kindInfo.color  
                    }
				});
				rules.push(rule);
			}
			rules.push(new OpenLayers.Rule({  	
                elseFilter: true,  
                symbolizer: {  
                    fillColor: "#000000"  
            	}  
            }));
			return rules;
		}
		
		function addVector(datas, rules){
			var style = new OpenLayers.Style({  
                strokeColor: "#ffffff",  
                fillOpacity: 0.4,
                strokeWidth: 1,    
                labelAlign: "center",  
                labelXOffset: "0",  
                labelYOffset: "-0",  
                fontColor: "#000000",  
                fontFamily: "微软雅黑",  
                fontSize:13  
            }
            ,{  
                rules:rules
            });  			
			var vector = new OpenLayers.Layer.Vector("ThematicData",{
				styleMap: new OpenLayers.StyleMap(style)
			});
			var geojson_Format = new OpenLayers.Format.GeoJSON({
				'internalProjection': new OpenLayers.Projection("EPSG:900913"),
				'externalProjection': new OpenLayers.Projection("EPSG:4326")
			});
			for(var i=0; i<datas.length; ++i){
				var data = datas[i];
				var feature = geojson_Format.read(data);
				vector.addFeatures(feature);
			}
			map.addLayer(vector);
		}
		
	</script> 

  </head>  

  <body onload="load()">
  	<div id='map' style='width:100%;height:90%;'></div>
  	<table>
  		<tr>
  			<td>方法：<input type="text" name="method" id="method"><td>
  			<td>层数：<input type="text" name="num" id="num"><td>
  		<tr>
  	</table>
  	<button onclick="addThematicData('province_thematic')">分层设色</button>
  </body>
</html>
