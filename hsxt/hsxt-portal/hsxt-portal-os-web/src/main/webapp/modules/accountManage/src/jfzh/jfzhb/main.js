define(['text!accountManageTpl/jfzh/jfzhb/jfzhb.html' ,'accountManageLan'],function(jfzhbTpl ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(jfzhbTpl)) ;
		    
		    
		    //校验		 
				var valid=$("#jfzhb_form").validate({ //"corpform" 表单中form的id
					rules : {
						thename : { //form表单中包含的 name为corpName 的表单元素
							required : true,//非空验证
						},
						theage:{
			                required : true,//非空验证
							digits:true
			            }			
					},
					messages : {
						thename : { //校验失败时处理方式
							required :  comm.lang('accountManage').enterTheName //   "请输入姓名", //非空提示为"必填" (手动填写文案，不建议这么做)			                
						},
			            theage:{
							required : '请输入年龄' ,  // comm.lang(‘mobileNoRequired’) , 
							digits :  '必须输入数'                //comm.lang(‘mobileNoErr’)   //(把文案写模块的lang.js中，国际化文案最好的做法，详见“国际化语言文案”)
			 
			            }
					},
					errorPlacement: function(error, element) {
								$(element).attr("title",$(error).text()).tooltip({
									position:{
										my:"left+100 top+5",
										at:"left top"	
									}
								}).tooltip("open");
								$(".ui-tooltip").css("max-width","230px");       
						},
						success:function(element){
						    	$(element).tooltip();
						    	$(element).tooltip("destroy");
					}
					 
				});
 
		 
				//提交
				$('#post').click(function(){
		    			if (valid.form()){
		    				comm.alert('成功了')	
		    			}
		    	});
		
		
	} 
	
	
	}
	
	
}); 

 