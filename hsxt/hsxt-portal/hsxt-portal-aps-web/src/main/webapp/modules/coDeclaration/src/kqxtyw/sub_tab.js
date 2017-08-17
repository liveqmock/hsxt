define(['text!coDeclarationTpl/kqxtyw/sub_tab.html',
		'coDeclarationSrc/kqxtyw/sbxx',
		'coDeclarationSrc/kqxtyw/qyxtzcxx',
		'coDeclarationSrc/kqxtyw/qygsdjxx',
		'coDeclarationSrc/kqxtyw/qylxxx',
		'coDeclarationSrc/kqxtyw/qyyhzhxx',
		'coDeclarationSrc/kqxtyw/qysczl',
		'coDeclarationSrc/kqxtyw/blztxx',
		'coDeclarationDat/kqxtyw/sub_tab'
		],function(tab, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx, dataModoule){
	return {	 
		showPage : function(){		
			$('#busibox').html(_.template(tab)) ;
			
			var self = this;
			
			var num =$('.tabList > li > a[class="active"]').attr('data-num') ;
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
				$("input[name='isPass']").attr('checked', false);
				$('#kqxt_content').dialog({
					title:'开启系统确认',
					modal:true,
					width:'380',
					height:'325',
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
								var pass = $("input[name='isPass']:checked").val();
								if(!pass){
									comm.warn_alert('请选择审核结果!');
									return;
								}
								params.isPass = (pass == '1');
								params.apprRemark = $("#apprRemark").val();//审核意见
								dataModoule.openSystem(params, function(res){
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
			comm.initSelect("#subVerificationMode", comm.lang("coDeclaration").verificationMode, null, '1').change(function(e){
				var val = $(this).attr('optionValue');
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
					viewMark : {
						rangelength : [0, 300]
					},
					subDoubleUserName : {
						required : true
					},
					subDoublePassword : {
						required : true
					},
					apprRemark : {
						rangelength : [0, 300]
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[36006]
					},
					subDoubleUserName : {
						required : comm.lang("coDeclaration")[36047]
					},
					subDoublePassword : {
						required : comm.lang("coDeclaration")[36048]
					},
					apprRemark : {
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
		}
	}
}); 

