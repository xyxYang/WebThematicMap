<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.DBManage.ResultType"%>
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
    
    <meta charset='utf-8'></meta>
    <title>My JSP 'Map.jsp' starting page</title>
    <script type='text/javascript' src='openlayers/lib/OpenLayers.js'></script>  <!--src最好指向自己机器上对应的js库 -->
	<script src='lib/echarts.js'></script>
	<script type="text/javascript">
		var map;
		var thematicDatas;
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
			
			var wms = new OpenLayers.Layer.WMS(    //构建地图服务WMS对象，
			  	"Map Of China",         //图层名称，最好用中文，由于页面编码原因，写中文可能乱码，可以到网上搜索解决方法			
				"http://gisserver.tianditu.com/TDTService/region/wms", 		 	//geoserver所在服务器地址及对应的地图服务		
				{                                           //以下是具体访问参数
					layers: "030100",  //图层名称，对应与我们自己创建的服务layers层名
					style:'',            //样式
					format:'image/png',   //图片格式
					TRANSPARENT:"true",   //是否透明
				},
				  {isBaseLayer: false}   //是否基础层，必须设置
				);
			//添加wms图层
			//map.addLayer(wms);	//增加这个wms图层到map对象
			
			var osm = new OpenLayers.Layer.OSM();
			map.addLayer(osm);
			
			var style = new OpenLayers.Style({ 
				fillColor: "#008710", 
				fillOpacity: 0.2
            });
			var vector = new OpenLayers.Layer.Vector("province", {
				styleMap: new OpenLayers.StyleMap(style)
			});
			var geojson_Format = new OpenLayers.Format.GeoJSON({
				'internalProjection': toProjection,
				'externalProjection': fromProjection
			});
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
       		//map.addControl(new OpenLayers.Control.NavToolbar());     //添加拉框放大工具
       		map.addControl(new OpenLayers.Control.Scale);            //添加比例尺
       		
       		var selectFeature = new OpenLayers.Control.SelectFeature(vector,  //指定工具针对的图层
				{
					hover:false,
					onSelect:onFeatureSelect,    //指定选择消息函数
					//onUnselect:onFeatureUnselect, //制定取消选择消息函数
				});
			map.addControl(selectFeature);    //添加要素选取工具
			selectFeature.activate();
			/*
       		map.events.register('click', null, function onclick(event){
				//添加时间响应函数
				var vector = map.getLayerByName("province");
				var features = vector.features;
				var pixel = new OpenLayers.Pixel(event.xy.x, event.xy.y);
				var lonlat = map.getLonLatFromViewPortPx(pixel);
				for(var i=0; i<features.length; ++i){
					var feature = features[i]
					var geometry = feature.geometry;
				}
			});
			*/

       		map.zoomToExtent(bounds);		//缩放到全图显示
       		//getCharts();
		}
		
		function onFeatureSelect(feature){
			alert("hi");
			var gid = feature.attributes["gid"];
			for(var i=0; i<thematicDatas.length; ++i){
				var data = thematicDatas[i];
				if(data.id == gid){
					setChart("pie", "showChart", data);
					break;
				}
			}
		}
	</script> 
	<script type="text/javascript">
		function addThematicData(option, table){				
			var req = new XMLHttpRequest();
			var url = "data/getThematicDatas.jsp?" + "table=" + table;
			req.open("GET", url, true);
			req.send(null);
			req.onreadystatechange = function f(){
				if(req.readyState == 4)
				{
					var datas = req.responseText;  //返回文本数据
					thematicDatas = eval("(" + datas + ")");
					removeAllPopups();
					addMapCharts(option, thematicDatas, 10, 10);
					map.events.register("zoomend", map, function(){
						removeAllPopups();
                    	addMapCharts(option, thematicDatas, 10, 10);  
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
			xSize = xSize + (zoom - 1) * 20;
			ySize = ySize + (zoom - 1) * 20;
			for(var i=0; i<datas.length; ++i){
				var data = datas[i];
				var chartID = "chart" + data.id;
				var content = "<div id='" + chartID + "'></div>";
				var lonlat = new OpenLayers.LonLat(data.lon, data.lat).transform(fromProjection, toProjection);
				lonlat = br2mm(lonlat, xSize, ySize);
				var popup = new OpenLayers.Popup(chartID,
					lonlat,
					new OpenLayers.Size(xSize, ySize),
					content,
					false);
				popup.setBackgroundColor("transparent");  
                //popup.setBorder("0px #0066ff solid");  
                popup.keepInMap = false;  
                map.addPopup(popup,false);
                setChart(option, chartID, data);
			}
		}
		
		function setChart(option, chartID, data){
			if(option == "pie"){
				setPieChart(chartID, data);
			}else if(option == "line"){
				setLineChart(chartID, data);
			}else if(option == "bar"){
				setBarChart(chartID, data);
			}
		}
		
		function getNames(datas){
			var ret = new Array();
			for(var i=0; i<datas.length; ++i){
				d = datas[i];
				ret.push(d.name);
			}
			return ret;
		}
		
		function setPieChart(chartID, data){
			var zoom = map.getZoom();
			var option = {
				title: {
					text: data.name,
					textStyle: {
						fontSize:10 + zoom
					}
				},
			    tooltip: {
			      trigger: 'item',
			      formatter: "{b} : {c} ({d}%)",
			      fontSize:8,
			      position:['20%', '20%']
			    },
			    toolbox:{
			      show:true,
			      feature : {
			          mark : {show: true},
			          magicType : {
			              show: true,
			              type: ['pie', 'funnel']
			          },
			      }
			    },
			    calculable: true,
			    series: [{
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
			        data:data.data
			    }]
			};
			var chart = echarts.init(document.getElementById(chartID));
			chart.setOption(option);
		}
		
		function setLineChart(chartID, data){
			var zoom = map.getZoom();
			var option = {
				title: {
					text: data.name,
					textStyle: {
						fontSize:10 + zoom
					}
				},
				tooltip: {
			      	trigger: 'axis',
			      	formatter: "{b} : {c}",
			      	fontSize:8,
			      	position:['50%', '50%']
			    },
				calculable : true,    
				xAxis:{
            		type : 'category',
            		boundaryGap : false,
            		data: getNames(data.data)
            	},
    			yAxis:{
            		type:'value'
            	},
				series:{
					type:'line',
					data:data.data
				}
			};
			var chart = echarts.init(document.getElementById(chartID));
			chart.setOption(option);
		}
		
		function setBarChart(chartID, data){
			var zoom = map.getZoom();
			var option = {
				title: {
					text: data.name,
					textStyle: {
						fontSize:10 + zoom
					}
				},
				tooltip: {
			      	trigger: 'axis',
			      	formatter: "{b} : {c}",
			      	fontSize:8,
			      	position:['20%', '20%']
			    },
				calculable : true,    
				xAxis:{
            		type : 'category',
            		boundaryGap : false,
            		data: getNames(data.data)
            	},
    			yAxis:{
            		type:'value'
            	},
				series:{
					type:'bar',
					data:data.data,
					itemStyle: {
	                    normal: {
							color: function(params) {
		                            // build a color map as your need.
		                            var colorList = [
		                              '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
		                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
		                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
		                            ];
		                            return colorList[params.dataIndex];
		                    	}
		                 }
		             }
				}
			};
			var chart = echarts.init(document.getElementById(chartID));
			chart.setOption(option);
		}
		
		function removeAllPopups(){
			while(map.popups.length > 0){
				map.removePopup(map.popups[0]);
			}
		}
	</script> 
  </head>  

  <body onload="load()">
  	<div style='width:100%;height:90%;'>
  		<div id='map' style='float:left;width:80%;height:100%;'></div>
  		<div id='showChart' style='float:left;width:20%;height:100%'></div>
  	</div>
  	<button onclick="addThematicData('pie', 'province_thematic')">饼图</button>
  	<button onclick="addThematicData('line', 'province_thematic')">折线图</button>
  	<button onclick="addThematicData('bar', 'province_thematic')">柱状图</button>
  	<button onclick="removeAllPopups()">清除</button>
  </body>
</html>
