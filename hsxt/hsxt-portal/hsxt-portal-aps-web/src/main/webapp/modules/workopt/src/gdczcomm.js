define(["workoptDat/workopt","commSrc/cacheUtil"], function(workflow){
	return {
		/** 加载验证元素 */
		loadCheckWay:function(){
			/*验证方式*/
			comm.initSelect("#verificationMode",comm.lang("workopt").checkTypeEnum);
			$("#verificationMode").change(function(e){
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
					case '':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').addClass('none');
				}
			});
		},
		/** 表单提交验证 */
		fromVerification:function(){
			return comm.valid({
				formID : '#fTermination',
				rules : {
					txtReason : {
						required : true,
						maxlength :  300
					},
					verificationMode : {
						required : true,
					},
					txtCheckAccount : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					},
					txtCheckPwd : {
						required : function(){
							if($("#verificationMode").attr("optionvalue")=="1"){
								return true;
							}
							return false;
						}
					}
				},
				messages : {
					txtReason : {
						required :  comm.lang("workopt")[36048],
						maxlength : comm.lang("workopt").inp_len_err
					},
					verificationMode : {
						required : comm.lang("workopt").sel_chk_tp_err
					},
					txtCheckAccount : {
						required :  comm.lang("workopt")[34025]
					},
					txtCheckPwd : {
						required : comm.lang("workopt")[34026]
					}
				}
			});
		} 
	}	
});
