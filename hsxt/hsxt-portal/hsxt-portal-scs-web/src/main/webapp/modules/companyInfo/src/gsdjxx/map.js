define([comm.domainList['baiduApi']], function () {
	return {
		map : null,
		map_self : null,
		showPage : function(){
			map_self = this;
			var entAllInfo = comm.getCache("companyInfo", "entAllInfo");
			var lng = (entAllInfo.longitude)?entAllInfo.longitude:116.404;
			var lat = (entAllInfo.latitude)?entAllInfo.latitude:39.915;
			map = new BMap.Map("allmap");
			map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
			if(entAllInfo.longitude && entAllInfo.latitude){
				map.centerAndZoom(new BMap.Point(lng, lat), 12);
				map_self.addMarker(lng, lat);
			}else{//如果未填写经纬度
				var entRegAddr = $("#entRegAddr").val();
				if(entRegAddr == ""){
					//如果未填写企业地址，直接定位到该企业所在城市
					cacheUtil.syncGetRegionByCode(null, comm.getCustPlace()[1], comm.getCustPlace()[2], "-", function(res){
						var array = res.split("-");
						$("#cityName").val(array[1]);
						map_self.theLocation();
					});
				}else{
					// 创建地址解析器实例
					var myGeo = new BMap.Geocoder();
					// 将地址解析结果显示在地图上,并调整地图视野
					myGeo.getPoint(entRegAddr, function(point){
						if (point) {
							map.centerAndZoom(point, 16);
							map_self.addMarker(point.lng, point.lat);
						}else{
							alert("您选择地址没有解析到结果!");
							//如果地址解析错误，直接定位到该企业所在城市
							cacheUtil.syncGetRegionByCode(null, comm.getCustPlace()[1], comm.getCustPlace()[2], "-", function(res){
								var array = res.split("-");
								$("#cityName").val(array[1]);
								map_self.theLocation();
							});
						}
					}, "北京市");
				}
				
			}
			map.addEventListener("click", function(e){
				map_self.addMarker(e.point.lng, e.point.lat);
			});
			map_self.add_control();
			$("#search_city_btn").click(function(){
				map_self.theLocation();
			});
			map.addEventListener("tilesloaded", function(){
				
			});
		},
		/**
		 * 添加控件和比例尺
		 */
		add_control : function(){
			map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));   
			map.addControl(new BMap.NavigationControl());   
			//map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));    
		},
		/**
		 * 按城市名称定位
		 */
		theLocation : function(){
			var city = $("#cityName").val();
			if(city != ""){
				map.centerAndZoom(city, 11);// 用城市名设置地图中心点
			}else{
				map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
			}
		},
		/**
		 * 标记地图
		 */
		addMarker : function (lng, lat){
			$("#latitude").val(lat);
			$("#longitude").val(lng);
			var href = window.location.href;
			var imgUrl = href.substring(0, href.lastIndexOf('/') + 1)+"resources/img/d920880a.png";
			var myIcon = new BMap.Icon(imgUrl, new BMap.Size(24,24));
			var marker = new BMap.Marker(new BMap.Point(lng, lat),{icon:myIcon}); // 创建点
			map.clearOverlays();
			map.addOverlay(marker);//增加点
		}
	};
});