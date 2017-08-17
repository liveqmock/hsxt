define(['text!safeSetTpl/szmbwt/szmbwt.html',"safeSetDat/safeSet","safeSetLan"],function(tpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(tpl));
			
			var questionList=[{name:"请选择",value:"",selected:true}];//密码列表
			//获取密保问题列表
			safeSet.getmblb({},function(res){
				//遍历循环下拉列表参数
				$.each(res.data,function(index,item){
					questionList.push({name:item.question,value:item.question});
				});
				$("#szmbwt_mbwt").selectList({
					width: 190,
					optionWidth: 190,
					options:questionList
				})
			});
			
			//下拉框列表
			$("#szmbwt_mbwt").change(function(e){
				var v = $(this).attr('optionValue');
			});
			
			/**
			 * 提交事件
			 */
			$('#btn_szmbwt_qr').click(function(){
				if (!self.validateData()) {
					return false;
				}	
				var $answer=$("#txtAnswer").val();//答案
				var $question=$("#szmbwt_mbwt").attr("optionvalue");//密保问题
				var setParam={"answer":CryptoJS.MD5($answer).toString(),"question":$question};
				//设置密保答案
				safeSet.setmbwt(setParam,function(res){
					comm.alert({
						content: comm.lang("safeSet").set_question_answer_success
					});
					$("#xtaq_szmbwt").click();
				});
			});
		},
		validateData : function(){
			//选着密保问题
			if($("#szmbwt_mbwt").attr("optionvalue")==""){
				comm.alert({imgClass: 'tips_i' ,content:comm.lang("safeSet").select_pwd_question});
				return false;
			}
			
			return comm.valid({
				formID : '#szmbwt_form',
				rules : {
					szmbwt_mbwt : {
						required : true
					},
					txtAnswer : {
						required : true
					}
				},
				messages : {
					szmbwt_mbwt : {
						required : comm.lang("safeSet").select_pwd_question
					},
					txtAnswer : {
						required : comm.lang("safeSet").question_answer_null,
						maxlength:comm.lang("safeSet").maxlength
					}
				}
			});
		}
	}
});