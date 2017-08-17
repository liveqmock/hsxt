define(['text!coDeclarationTpl/kqxtqksh/sub_tab.html',
		'coDeclarationSrc/kqxtqksh/sbxx',
		'coDeclarationSrc/kqxtqksh/qyxtzcxx',
		'coDeclarationSrc/kqxtqksh/qygsdjxx',
		'coDeclarationSrc/kqxtqksh/qylxxx',
		'coDeclarationSrc/kqxtqksh/qyyhzhxx',
		'coDeclarationSrc/kqxtqksh/qysczl',
		'coDeclarationSrc/kqxtqksh/blztxx',
		'coDeclarationDat/kqxtqksh/sub_tab'
		],function(tab, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx, dataModoule){
	return {	 
		showPage : function(){
			var self = this;
			
			$('#busibox').html(_.template(tab));
			
			var num =$('.tabList > li > a[class="active"]').attr('data-num');
			//console.log(num);

			/*申报信息*/
			$('#sbxx').click(function(e) {
                sbxx.showPage(num);
				comm.liActive($('#sbxx'));
            }.bind(this)).click(); 
			
			/*企业系统注册信息*/
			$('#qyxtzcxx').click(function(e) {
                qyxtzcxx.showPage(num);
				comm.liActive($('#qyxtzcxx'));
            }.bind(this));
			
			/*企业工商登记信息*/
			$('#qygsdjxx').click(function(e) {
                qygsdjxx.showPage(num);
				comm.liActive($('#qygsdjxx'));
            }.bind(this)); 
			
			/*企业联系信息*/
			$('#qylxxx').click(function(e) {
                qylxxx.showPage(num);
				comm.liActive($('#qylxxx'));
            }.bind(this)); 
			
			/*企业银行账户信息*/
			$('#qyyhzhxx').click(function(e) {
                qyyhzhxx.showPage(num);
				comm.liActive($('#qyyhzhxx'));
            }.bind(this)); 
			
			/*企业上传资料*/
			$('#qysczl').click(function(e) {
                qysczl.showPage(num);
				comm.liActive($('#qysczl'));
            }.bind(this)); 
			
			/*办理状态信息*/
			$('#blztxx').click(function(e) {
                blztxx.showPage(num);
				comm.liActive($('#blztxx'));
            }.bind(this));  
			
			/*审核提交弹出框*/
			$('#kqxt_btn').click(function(){
				$('#kqxt_content').dialog({
					title:'开系统欠款审核确认',
					modal:true,
					width:'600',
					height:'320',
					buttons:{ 
						"确定":function(){
							if(!self.validateViewMarkData().form()){
								return;
							}
							//验证双签
							comm.verifyDoublePwd($("#subDoubleUserName").val(), $("#subDoublePassword").val(), 1, function(verifyRes){
								var params = {};
								params.applyId = $("#applyId").val();//申请编号
								params.dblOptCustId = verifyRes.data;//双签用户ID
								params.feeFlag = $('input[name="feeFlag"]:checked').val();//开启系统欠费审核
								params.optRemark = $("#optRemark_kq").val();//审核意见
								dataModoule.apprDebtOpenSys(params, function(res){
									comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
										$('#kqxt_content').dialog('destroy');
										$('#'+$("#menuName").val()).click();
									}});
								});
							});
						},
						"取消":function(){
							 $('#kqxt_content').dialog('destroy');
						}
					}
				  });
			});
			
			this.initVerificationMode();
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationMode : function(){
			comm.initSelect("#verificationMode_kq", comm.lang("coDeclaration").verificationMode, null, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_userName_qk, #fhy_password_qk').removeClass('none');
						$('#verificationMode_prompt_qk').addClass('none');
						break;	
					case '2':
						$('#fhy_userName_qk, #fhy_password_qk').addClass('none');
						$('#verificationMode_prompt_qk').removeClass('none');
						break;
					case '3':
						$('#fhy_userName_qk, #fhy_password_qk').addClass('none');
						$('#verificationMode_prompt_qk').removeClass('none');
						break;
				}
			});
			
			window.setTimeout(function(){
				$("#subDoubleUserName").attr("value","");
				$("#subDoublePassword").val("");	
			},100);
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#sub_confirm_dialog_form").validate({
				rules : {
					feeFlag : {
						required : true,
						rangelength : [0, 300]
					},
					subDoubleUserName : {
						required : true
					},
					subDoublePassword : {
						required : true
					},
					optRemark_kq : {
						rangelength : [0, 300]
					},
				},
				messages : {
					feeFlag : {
						required : comm.lang("coDeclaration").apply_type_error,
						rangelength : comm.lang("coDeclaration")[36073]
					},
					subDoubleUserName : {
						required : comm.lang("coDeclaration")[36047]
					},
					subDoublePassword : {
						required : comm.lang("coDeclaration")[36048]
					},
					optRemark_kq : {
						rangelength : comm.lang("coDeclaration")[36065]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
	}
}); 

