define(["text!shutdownsystemTpl/kgxtshcx/kgxtshcx.html",
"text!shutdownsystemTpl/kgxtshcx/ck.html",
'shutdownsystemDat/shutdownsystem',
'shutdownsystemLan'],function(kgxtfhcxTpl,ckTpl,shutdownsystem){
	var self= {
        //self : null,
		showPage : function(){
			$('#busibox').html(_.template(kgxtfhcxTpl));	
			//self=this;
			/*下拉列表*/
			comm.initSelect('#company_type_1', comm.lang("shutdownsystem").custType);
			comm.initSelect('#fh_type', comm.lang("shutdownsystem").applyType);
			$("#kgxtsh_qry").click(function(){
				var params={
						search_companyResNo:$("#companyResNo").val(),
						search_companyName:$("#companyName").val(),
						search_linkman:$("#linkman").val(),
						search_custType:$("#company_type_1").attr("optionvalue"),
						search_applyType:$("#fh_type").attr("optionvalue")
				};
				shutdownsystem.queryCloseOpenEnt("searchTable",params,self.detail);
			});
			//$("#kgxtsh_qry").click();
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==4){
				return comm.lang('shutdownsystem').custType[record.custType];
			}else if(colIndex==5){
				return comm.lang('shutdownsystem').applyType[record.applyType];
			}else if(colIndex==7){
				var link1 = $('<a>查看</a>').click(function(e) {
					shutdownsystem.getDetail({applyId:record.applyId},function(res){
						self.caikan(res.data);
					});				
				});
				return  link1 ;
			}
		},
		caikan : function(obj){
			obj.custTypeName=comm.lang('shutdownsystem').custType[obj.custType];
			if(obj.status==2 || obj.status==3){
				obj.statusName=comm.lang('shutdownsystem').applystatus[obj.status];
			}
			$('#kgxtshcx_ck_dialog').html( _.template(ckTpl,obj) );
			$('#kgxtshcx_ck_dialog').dialog({
				width:800,
				buttons:{
					'确定':function(){ 
						$(this).dialog('destroy');
						
					} 
				}
			});
			 
			
			$('#cancel_kgxtfh').triggerWith('#kgxtfh');
		}
	};
	return self;
});