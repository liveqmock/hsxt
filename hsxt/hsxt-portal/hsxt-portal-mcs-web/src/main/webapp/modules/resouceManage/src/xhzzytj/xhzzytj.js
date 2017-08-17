define(['text!resouceManageTpl/xhzzytj/xhzzytj.html',"resouceManageDat/xhzzytj/xhzzytj"
        ],function(xhzzytjTpl,xhzzytj){
	return {
		showPage : function(){
			var self=this;
			//消费者资源统计
			xhzzytj.personResDetail(function(rsp){
				$('#busibox').html(_.template(xhzzytjTpl,rsp.data));	
				var title; 
				//平台所在区域
				switch (rsp.data.countryNo) {
				case "156":
					title="中国大陆"; 
					break;
				case "344":
					title="中国香港";   
					break;
				case "446":
					title="中国澳门";
					break;
				}
				$("#hPlatName").html(title+"地区系统资源使用数");
				
				//条件查询
				$("#btnQuery").click(function(){
					self.pageQuery();
				});
			});
			
		
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {"search_queryResNo":$("#txtEntResNo").val(),"search_queryResNoName":$("#txtEntName").val()};
			xhzzytj.entNextPersonStatisticsPage(param);
		}
	}
}); 

 