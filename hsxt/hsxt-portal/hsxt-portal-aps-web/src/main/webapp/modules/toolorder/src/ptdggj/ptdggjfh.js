define(['text!toolorderTpl/ptdggj/ptdggjfh.html',
		'text!toolorderTpl/ptdggj/ptdggjfh_ck_dialog.html',
		'text!toolorderTpl/ptdggj/ptdggjfh_fh_dialog.html',
		'toolorderDat/toolorder'
		], function(ptdggjfhTpl, ptdggjfh_ck_dialogTpl, ptdggjfh_fh_dialogTpl,toolorder){
	return {
		self : null,
		showPage : function(){
			$('#busibox').html(ptdggjfhTpl);
			
			self = this;
			/*下拉列表*/
			comm.initSelect("#shzt",comm.lang("toolorder").apprstatus);
	
			var gridObj;
			
			$("#serchBtn").click(function(){
				var params = {
						search_entResNo : $("#ptdggjfhEntResNo").val().trim(),	//互生号
						search_status : $("#shzt").attr("optionvalue")	//订单状态
				};
				gridObj = comm.getCommBsGrid("searchTable","query_proxyorder_auditing_list",params,comm.lang("toolorder"),self.detail,self.edit);
			});
	
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4)
			{
				return comm.lang("toolorder").apprstatus[record.status];
			}
			else if(colIndex == 6)
			{
				var link1 = $('<a>'+comm.lang("toolorder").chakan+'</a>').click(function(e) {
					
					self.chaKan(record);
					
				}) ;
				return link1;
			}
		},
		edit : function(record, rowIndex, colIndex, options){
			
			if(record.status == 0 && colIndex == 6)
			{
				var link2 = $('<a>'+comm.lang("toolorder").fuhe+'</a>').click(function(e){
					
					self.fuHe(record);
					
				}) ;
			}
			
			
			return link2;
		},
		chaKan : function(obj){
			
			toolorder.queryProxyOrder({proxyOrderNo : obj.proxyOrderNo},function(res){
				
				var transData = res.data;
				transData.statusName = comm.lang("toolorder").apprstatus[res.data.status];
				
				$('#dialogBox_ck').html(_.template(ptdggjfh_ck_dialogTpl, transData));	
				
				/*弹出框*/
				$( "#dialogBox_ck" ).dialog({
					title:"平台代购工具订单详情",
					width:"800",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
			
		},
		fuHe : function(obj){
			
				toolorder.queryProxyOrder({proxyOrderNo : obj.proxyOrderNo},function(res){
				
				var transData = res.data;
				transData.statusName = comm.lang("toolorder").apprstatus[res.data.status];
				
				$('#dialogBox_fh').html(_.template(ptdggjfh_fh_dialogTpl, transData));
				
				/*弹出框*/
				$( "#dialogBox_fh" ).dialog({
					title:"平台代购工具订单复核",
					width:"800",
					modal:true,
					buttons:{ 
						"确定":function(){
							var postData = {
									proxyOrderNo : obj.proxyOrderNo,				//代购订单编号
									status : $('input[name="status"]:checked').val(),//审批状态
									apprRemark : $("#apprRemark").val(),				//审批意见
									exeCustId : $.cookie('custId'),						//经办人客户号
									apprOperator : $.cookie('custId')					//操作人
							};
							var open = $(this);
							toolorder.audingProxyOrder(postData,function(res){
								
								open.dialog( "destroy" );
							});
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
			});
		}
	}	
});