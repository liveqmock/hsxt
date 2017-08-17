

define(function() {
	var shippingDelete ={
			dlg1Click :function(ajaxRequest,thisObj,callback){
				 //var id=$(thisObj).parent("td").parent("tr").find("td:first").find("input").val();
				//var vShopId =$(thisObj).parent("td").parent("tr").find("td:first").find("input").attr("vShopId"); 
				var id=$(thisObj).attr("shippid");
				var vShopId=$(thisObj).attr("vshopid");
				 $( "#dlg1" ).dialog({
				 	  title:"提示信息",
				      modal: true,
					  close: function() { 
					        $(this).dialog('destroy');
					  },
				      buttons: {
				        确定: function() {
				        	//删除
				        	ajaxRequest.deleteShipping("id="+id+"&vShopId="+vShopId,function(response){
				        			callback(response.retCode);
				        	});
				            $( this ).dialog( "destroy" );
				            $("#wsscgl_yfsz").click();//Add by zhanghh @date:2015-12-16:tips:刷新页面;
				        },
						'取消' : function() {
							$(this).dialog("destroy");
						}
				      }
				  });
			}
	}
	
	return shippingDelete;
});	

