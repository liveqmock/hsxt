define([comm.domainList["baiduApi"]],function(){	
	//加载地图API
	var showMap = {
		initMap : function(landMark,num){
			//这里因为id有冲突，加一个标记加以区分
			var myMap = null;
			if(num==1){
				myMap = new BMap.Map("dituContent");
			}else{
				myMap = new BMap.Map("contentDitu");
			}
			//获取经纬度
			//var landMark = $("#shopLandMark").attr("value");
			var point = null;
			if(landMark != null && landMark != ''){
				var landMarkArr = landMark.split(",");
				point = new BMap.Point(landMarkArr[1], landMarkArr[0]);
			}else{
				point = new BMap.Point(116.39564504,39.92998578);
			}
			myMap.centerAndZoom(point, 17);//创建地图坐标及显示级别
			myMap.addControl(new BMap.NavigationControl());
			myMap.enableScrollWheelZoom();                 //启用滚轮放大缩小
			//创建标注
			zsIcon = new BMap.Icon("resources/img/mappoint.png", //图片地址
				new BMap.Size(40, 64), // 标注显示大小
				{
					offset: new BMap.Size(20, 64), // 标注底部小尖尖的偏移量
					imageOffset: new BMap.Size(0, 0) // 这里相当于CSS sprites
				});
			var marker = new BMap.Marker(point, {icon: zsIcon}); // 自定义标注
			myMap.addOverlay(marker);
		}
	}
	return showMap;
});