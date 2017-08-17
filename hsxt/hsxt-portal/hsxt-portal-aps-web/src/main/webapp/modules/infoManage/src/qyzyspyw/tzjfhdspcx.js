define(['text!infoManageTpl/qyzyspyw/tzjfhdspcx.html',
		'text!infoManageTpl/qyzyspyw/tzjfhdspcx_ck_dialog.html',
		'infoManageDat/infoManage'], 
		function(tzjfhdspcxTpl, tzjfhdspcx_ck_dialogTpl,infoManage){
	var self= {
		//self : null,
		showPage : function(){
			
			//self = this;
			
			$('#busibox').html(_.template(tzjfhdspcxTpl));
			
			/*下拉列表*/
			comm.initSelect("#status",comm.lang("infoManage").appStatusEnum);

	
			var gridObj;
			
			$("#searchBtn").click(function(){
				
				var params = {
						search_applyType : '0',			//申请类别 0为停止积分活动申请， 1为参与积分活动申请
						search_entResNo : $("#entResNo").val(),			//企业互生号
						search_entName : $("#entName").val(),			//企业名称
						search_linkman : $("#linkman").val(), 			//联系人
						search_status : $("#status").attr("optionvalue")//状态
				};
				gridObj = comm.getCommBsGrid("searchTable","activityapply_list",params,comm.lang("infoManage"),self.detail);
				
			});	
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5)
			{
				return comm.lang("infoManage").applyBusinessStatueEnum[record.status];
			}
			if(colIndex == 7)
			{
				var link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
						self.chaKan(record);
						
					} ) ;
	
				return link1;
			}
		},
		chaKan : function(obj){ 	
			
			infoManage.serchPointActivityDetail({applyId:obj.applyId},function(res){
				
				$('#dialogBox > div').html(_.template(tzjfhdspcx_ck_dialogTpl, res.data));
				
				//申请书附件文件id
				var fileId = res.data.POINT_ACTIVITY.bizApplyFile;
				
				//申请书在文件系统中的url访问地址
				var href = comm.getFsServerUrl(fileId);
				
				$("#ywblsqs").attr("href",href);
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:comm.lang("infoManage").tzjfhdcxxq,//"停止积分活动审批查询详情",
					width:"600",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				
			});
			
		}
	}	;
	
	return self;
});