define(["hsec_tablePointSrc/tablePoint",comm.domainList["baiduApi"] ],function(tablePoint){		
    
	//标注点数组
	   var markerArr = [];
	var shopMap = {
	    
		//创建和初始化地图函数:
			initMap:function(shopName,contactWay,x,y,callback){
				shopMap.createMap(x,y);//创建地图
				shopMap.setMapEvent();//设置地图事件
				shopMap.addMapControl();//向地图添加控件
				var param= {};
				var iconParam={w:23,h:25,l:46,t:21,x:9,lb:12};
				if($.trim(x) != null && $.trim(x) !="" && $.trim(y) != null && $.trim(y) != ""){
					if(shopName != null && shopName !="" && contactWay != null && contactWay != ""){
						param["title"]="地址:"+shopName;
				    	param["content"]="服务热线:"+contactWay;
					}else{
						param["title"]="";
				    	param["content"]="";
					}
				    param["point"]=x+"|"+y;
				    param["isOpen"]=1;
				    param["icon"]=iconParam;
					markerArr[0]=param;
					shopMap.addMarker(markerArr[0]);//向地图中添加marker
				}else{
					    param["point"]=""+"|"+"";
					    param["isOpen"]=1;
					    param["icon"]=iconParam;
						markerArr[0]=param;
						shopMap.addMarker(markerArr[0]);//向地图中添加marker
						if(callback != null && callback != ""){
			        		callback(true);
			        	}
				}
			},
			xyzuobiao : function(callback){
					var map = window.map;
					var diqu = $("#zuobiaodiqu").val();
					var quyu = $("#quyu").find('option:selected').text();
			    	var localSearch = new BMap.LocalSearch(map);
					map.clearOverlays();//清空原来的标注
					localSearch.setSearchCompleteCallback(function (searchResult) {
						var poi = searchResult.getPoi(0);
						var x = poi.point.lng;
						var y = poi.point.lat;
						if(callback != null && callback != ""){
			        		callback(x,y);
			        	}
					})
					var zongdizhi;
					if(diqu != null && diqu != ""){
						zongdizhi = diqu;
						if(quyu != null && quyu != "" &&  quyu != "选择区域"){
							zongdizhi += quyu;
						}
					}else{
						zongdizhi = "中国";
					}
					localSearch.search(zongdizhi);
			},
			
		  //创建地图函数：
	       createMap:function(x,y){
	    	   var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
		       var point = new BMap.Point(x,y);//定义一个中心点坐标
		       map.centerAndZoom(point,18);//设定地图的中心点和坐标并将地图显示在地图容器中
		       window.map = map;//将map变量存储在全局
	       },
	       //地图事件设置函数：
	       setMapEvent:function(){
	    	   map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
		       map.enableScrollWheelZoom();//启用地图滚轮放大缩小
		       map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
		       map.enableKeyboard();//启用键盘上下左右键移动地图
	       },
	     //地图控件添加函数：
	       addMapControl:function(){
	           //向地图中添加缩放控件
	    		var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	    		map.addControl(ctrl_nav);
	    		       //向地图中添加缩略图控件
	    		var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	    		map.addControl(ctrl_ove);
	    		       //向地图中添加比例尺控件
	    		var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	    		map.addControl(ctrl_sca);
	       },
	       
	     //创建marker
	       addMarker:function(markerArr){
	    	   		   var geoc = new BMap.Geocoder();
			           var json = markerArr;
			           var p0 = json.point.split("|")[0];
			           var p1 = json.point.split("|")[1];
			           var point = new BMap.Point(p0,p1);
			  var iconImg = shopMap.createIcon(json.icon);
			           var marker = new BMap.Marker( point,{icon:iconImg,enableDragging: true,raiseOnDrag: true});
			  var iw = shopMap.createInfoWindow(markerArr);
			  var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			  marker.setLabel(label);
			           map.addOverlay(marker);
			           label.setStyle({
			                       borderColor:"#808080",
			                       color:"#333",
			                       cursor:"pointer"
			           });
			  
			  (function(){
			   var index = 0;
			   var _iw = shopMap.createInfoWindow(markerArr);
			   var _marker = marker;
			   _marker.addEventListener('dragend', function(e){
				   if($("#xyMap").length > 0){
					   $("#xyMap").val(e.point.lat+","+e.point.lng)
				       $("#baiduXY").html("当前位置:"+e.point.lat+"|"+e.point.lng);
				       $("#xyZhou").html("");
					   
//					    var pt = e.point;
//						geoc.getLocation(pt, function(rs){
//							var addComp = rs.addressComponents;
//							var city = addComp.city;
//							var thisCity = $("#xyMap").attr("yw-city");
//							if(thisCity.indexOf(city) > -1){
//								   $("#xyMap").val(e.point.lat+","+e.point.lng)
//							       $("#baiduXY").html("当前位置:"+e.point.lat+"|"+e.point.lng);
//							       $("#xyZhou").html("");
//							}else{
//								   tablePoint.tablePoint("选择区域超出("+thisCity+")范围，请重新选择地点");
//								   $("#xyMap").val("");
//							}
//						}); 
				   }
			   })
			   _marker.addEventListener("click",function(){
			       this.openInfoWindow(_iw);
			      });
			      _iw.addEventListener("open",function(){
			       _marker.getLabel().hide();
			      })
			      _iw.addEventListener("close",function(){
			       _marker.getLabel().show();
			      })
			   label.addEventListener("click",function(){
			       _marker.openInfoWindow(_iw);
			      })
			   if(!!json.isOpen){
			    label.hide();
			    _marker.openInfoWindow(_iw);
			   }
			  })()
	       },
	       //创建InfoWindow
	       createInfoWindow: function(markerArr){
	    	   var json = markerArr;
	    	   var str;
	    	   if(json.title != null && json.title != "" && json.content != null && json.content != ""){
	    		  str = "<br/><b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>";
	    	   }else{
	    		  str = "<div id='baiduXY' style='margin-left:45px;margin-top:25px'>当前位置:"+json.point.split("|")[1]+"|"+json.point.split("|")[0]+"</div>";
	    	   }
		       var iw = new BMap.InfoWindow(str,{enableMessage:false});
		       return iw;
	       },
	     //创建一个Icon
	       createIcon : function(json){
	    	   var icon = new BMap.Icon("https://map.baidu.com/image/us_cursor.gif", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
		       return icon;
	       }
	}
	return shopMap;
})