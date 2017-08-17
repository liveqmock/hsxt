define(["text!shutdownsystemTpl/gbqyxt/gbqyxt.html",
"text!shutdownsystemTpl/gbqyxt/gbxt.html",
'shutdownsystemDat/shutdownsystem',
'shutdownsystemLan'
],function(gbqyxtTpl,gbxtTpl,shutdownsystem){
	var self = {
		//self: null,
		showPage : function(){
			//self=this;
			$('#busibox').html(_.template(gbqyxtTpl));	
			
			/*下拉列表*/
			comm.initSelect('#company_type_1', comm.lang("shutdownsystem").custType);
			$("#gbqyxt_qry").click(function(){
				var params={
						search_companyResNo:$("#companyResNo").val(),
						search_companyName:$("#companyName").val(),
						search_linkman:$("#linkman").val(),
						search_custType:$("#company_type_1").attr("optionvalue"),
						search_status:$("#status").val()
				};
				shutdownsystem.getAllEnt("searchTable",params,self.detail);
			});
			//$("#gbqyxt_qry").click();
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				return comm.lang('shutdownsystem').custType[record.entCustType];
			}else if(colIndex==6){
				var link1 = $('<a>关闭系统</a>').click(function(e) {				
					self.gbxt(record);				
				});
				return  link1;
			}
			
		},
		gbxt : function(obj){
			obj.entCustTypeName=comm.lang('shutdownsystem').custType[obj.entCustType];
			$('#busibox').html(_.template(gbxtTpl,obj));	
			comm.liActive_add($('#gbxt'));
			
			$('#cancel_gbqyxt').triggerWith('#gbqyxt');	
			
			$("#gbxt_submit").click(function(){
				if (!self.validateData()) {
					return;
				}
				var params = $('#gbxt_form').serializeJson();
				shutdownsystem.close(params,function(res){
					comm.yes_alert("关闭系统申请成功",400);
					$('#cancel_gbqyxt').click();
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#gbxt_form',
				rules : {
					reqRemark:{
						required : true,
						rangelength:[1,300]
					}
				},
				messages : {
					reqRemark:{
						required : comm.lang("shutdownsystem").reqRemarkReired,
						rangelength:comm.lang("shutdownsystem").remarkLength
					}
				}
			});
		}
	};
	return self;

});