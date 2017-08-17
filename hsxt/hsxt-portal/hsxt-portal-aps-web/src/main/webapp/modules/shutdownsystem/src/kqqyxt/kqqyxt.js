define(["text!shutdownsystemTpl/kqqyxt/kqqyxt.html",
"text!shutdownsystemTpl/kqqyxt/kqxt.html",
'shutdownsystemDat/shutdownsystem',
'shutdownsystemLan'
],function(kqqyxtTpl,kqxtTpl,shutdownsystem){
	var self= {
		//self: null,
		showPage : function(){
			//self=this;
			$('#busibox').html(_.template(kqqyxtTpl));	
			
			comm.initSelect('#company_type_1', comm.lang("shutdownsystem").custType);
			
			$("#kqqyxt_qry").click(function(){
				var params={
						search_companyResNo:$("#companyResNo").val(),
						search_companyName:$("#companyName").val(),
						search_linkman:$("#linkman").val(),
						search_custType:$("#company_type_1").attr("optionvalue"),
						search_status:$("#status").val()
				};
				shutdownsystem.getAllEnt("searchTable",params,self.detail);
			});
			//$("#kqqyxt_qry").click();
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				return comm.lang('shutdownsystem').custType[record.entCustType];
			}else if(colIndex==6){
				var link1 = $('<a>开启系统</a>').click(function(e) {	
					var obj=record;
					shutdownsystem.queryLastCloseOpenEntInfo({companyCustId:record.entCustId,applyType:1},function(res){
						if(res.data!=null){
							obj.applyOptCustId=res.data.applyOptCustId;
							obj.applyOptCustName=res.data.applyOptCustName;
							obj.applyDate=res.data.applyDate;
							obj.applyRemark=res.data.applyRemark;
						}
						self.kqxt(obj);	
					})
								
				});
				return  link1;
			}
		},
		kqxt : function(obj){
			obj.entCustTypeName=comm.lang('shutdownsystem').custType[obj.entCustType];
			$('#busibox').html(_.template(kqxtTpl,obj));	
			comm.liActive_add($('#kqxt'));
			
			$('#cancel_kqqyxt').triggerWith('#kqqyxt');	
			$("#kqxt_submit").click(function(){
				if (!self.validateData()) {
					return;
				}
				var params = $('#kqxt_form').serializeJson();
				shutdownsystem.open(params,function(res){
					comm.yes_alert("开启系统申请成功",400);
					$('#cancel_kqqyxt').click();
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#kqxt_form',
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