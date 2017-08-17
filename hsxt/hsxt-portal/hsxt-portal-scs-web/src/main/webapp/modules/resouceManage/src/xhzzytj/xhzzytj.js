define(['text!resouceManageTpl/xhzzytj/xhzzytj.html',"resouceManageDat/xhzzytj/xhzzytj"
        ],function( xhzzytjTpl,xhzzytj ){
	return {	 	
		showPage : function(){
			

			var self=this;
			//消费者资源统计
			xhzzytj.personResDetail(function(rsp){
				$('#contentWidth_2').html(_.template(xhzzytjTpl,rsp.data));	
				var title; 
               $("#hPlatName").html("系统资源使用数");
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
