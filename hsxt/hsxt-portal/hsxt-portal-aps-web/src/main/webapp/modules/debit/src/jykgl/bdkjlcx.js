define(['text!debitTpl/jykgl/bdkjlcx.html',
		'text!debitTpl/jykgl/bdkjlcx_ck.html',
		'text!debitTpl/jykgl/jykzlz.html',
        'debitDat/debit',
        'debitSrc/debitComm',
		'debitLan'], function(bdkjlcxTpl, bdkjlcx_ckTpl,bdkjlcx_zlzTpl,dataModoule,debitComm){
	return bdkjlcx = {
		showPage : function(){			
			$('#busibox').html(_.template(bdkjlcxTpl));
			
			//查询不动款
			bdkjlcx.findNotMoveSum();
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			$("#queryNomove").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startTime:$("#search_startDate").val(),
						search_endTime:$("#search_endDate").val(),
						search_payEntCustName:$("#payEntCustName").val(),
						search_useEntCustName:$("#useEntCustName").val(),
						search_debitStatus:$("#debitStatus").val()
				};
				dataModoule.listDebit("searchTable",params,bdkjlcx.detail,bdkjlcx.edit,null);
				
				//查询不动款余额查询
				bdkjlcx.findNotMoveSum();
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 4){
				//付款用途
				var cUse=debitComm.collectUse(record.payUse);
				return '<span title='+cUse+'>'+cUse+'</span>' ;
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.unlinkAmount);
			}else if(colIndex == 7){
				var link1 = $('<a>查看</a>').click(function(e) {
					bdkjlcx.chaKan(record);					
				}) ;			
				return link1;				
			}
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){				
				var link2 = $('<a>转临账</a>').click(function(e) {
					bdkjlcx.linZhang(record);				
				});			
			    return link2;
		    }
	    },
	    /**
	     * 查看
	     */
		chaKan : function(obj){
			//付款用途
			obj.payUseString=debitComm.collectUse(obj.payUse);
			
			$('#bdkjlcxTpl').addClass("none");
			$('#bdkjlcx_operation_tpl').removeClass("none");
			$('#bdkjlcx_operation_tpl').html(_.template(bdkjlcx_ckTpl, obj));
			comm.liActive_add($('#ck'));
			
			//操作人真实姓名
			comm.groupOperInfo(obj.updatedBy,function(val){$("#tdOperation").text(val);});
			
			//返回
			$('#ck_back').click(function(){
				$('#bdkjlcxTpl').removeClass("none");
				$('#bdkjlcx_operation_tpl').addClass("none");
				$("#bdkjlcx_operation_tpl").html("");
				
				comm.liActive_add($('#bdkjlcx'));	
				$("#ck").addClass('tabNone');
			});						
		},
		/**
		 * 转临帐
		 */
		linZhang : function(obj){
			//付款用途
			obj.payUseString=debitComm.collectUse(obj.payUse);
			$('#dialogBox').html(_.template(bdkjlcx_zlzTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"余额不动款转临账内容",
				width:"640",
				height:'430',
				modal:true,
				buttons:{ 
					"确定":function(){
						var dself=this;
						
						//转临战表单验证
						if(!bdkjlcx.validateDataTrans()){
							return;
						}	
						//双签验证
						comm.verifyDoublePwd($("#txtReviewUser").val(),$("#txtReviewPwd").val(),$("#verificationMode").attr("optionvalue"),function(rsp){
							//保存双签客户号
							$("#dblOptCustId").val(rsp.data);
							
							//转临帐
							var params=$("#bdkzlz_form"). serializeJson();
							dataModoule.turnNotMove(params,function(res){
								 comm.yes_alert("不动款转临账完成",400);
								 $(dself).dialog("destroy");
								 $("#queryNomove").click();
							});	
						});
							
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			//双签验证方式
			comm.initSelect("#verificationMode", comm.lang("common").verificationMode, 180).change(function(e){
				var val = $(this).attr('optionValue');
				$("#txtReviewUser,#txtReviewPwd").val("");
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;
				}
			});
			/*end*/
		},
		//不动款总额 
		findNotMoveSum : function(){
			dataModoule.notMoveSum(null,function(res){
				var data = res.data;
				$("#totalAmout").text(comm.formatMoneyNumberAps(data));
			});
		},
		/**
		 * 转临账-表单校验
		 */
		validateDataTrans : function(){
			return comm.valid({
				formID : '#bdkzlz_form',
				rules : {
					debitId : {
						required : true
					},
					balanceRecord : {
						required : true
					},
					txtReviewUser : {
						required : function(){
							var $verificationMode = $("#verificationMode").attr("optionvalue");	//双签验证类型
							if($verificationMode!="1"){return false;}	//非用户密码验证时，不验证帐号
							return true;
						}
					},
					txtReviewPwd : {
						required : function(){
							var $verificationMode = $("#verificationMode").attr("optionvalue");	//双签验证类型
							if($verificationMode!="1"){return false;}	//非用户密码验证时，不验证密码
							return true;
						}
					},
					debitStatus : {
						required : true
					},
					verificationMode : {
						required : true
					}
				},
				messages : {
					debitId : {
						required : comm.lang("debit")[35020]
					},
					balanceRecord : {
						required : comm.lang("debit")[35032]
					},
					txtReviewUser : {
						required :  comm.lang("debit")[35034]
					},
					txtReviewPwd : {
						required :  comm.lang("debit")[35035]
					},
					debitStatus : {
						required :  comm.lang("debit")[35022]
					},
					verificationMode : {
						required :  comm.lang("debit")[35036]
					},
				}
			});
		}
	}	
});