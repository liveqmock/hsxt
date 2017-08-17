define([
"text!coDeclarationTpl/htgl/htgl.html",
"text!coDeclarationTpl/htgl/htmbwh.html",
"text!coDeclarationTpl/htgl/htgz.html",
"text!coDeclarationTpl/htgl/modifyModules.html",
"text!coDeclarationTpl/htgl/htmbfh.html",
"text!coDeclarationTpl/htgl/addModules.html",
"text!coDeclarationTpl/htgl/fhaddModules.html",
"text!coDeclarationTpl/htgl/htmbfh_fh.html",
"text!coDeclarationTpl/htgl/htff.html",
"text!coDeclarationTpl/htgl/ffht.html",
"text!coDeclarationTpl/htgl/htffls.html",
"text!coDeclarationTpl/htgl/cxscht.html",
'xheditor_cn'
],function(htglTpl,htmbwhTpl,htgzTpl,modifyModulesTpl,htmbfhTpl,addModulesTpl,fhaddModulesTpl,htmbfh_fhTpl,htffTpl,ffhtTpl,htfflsTpl,cxschtTpl){
 
	var htgl = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(htglTpl).append(htmbwhTpl).append(htgzTpl).append(modifyModulesTpl).append(htmbfhTpl).append(addModulesTpl).append(fhaddModulesTpl).append(htmbfh_fhTpl).append(htffTpl).append(ffhtTpl).append(htfflsTpl).append(cxschtTpl);	
			
			
			$(".back_htmbwh").click(function(){
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htmbwh").removeClass("none");	
				
				$("#enterprise_type").selectList({
					options:[
						{name:'xxx',value:'100'},
						{name:'xxx',value:'101'}
					]
				});
				
				$("#state").selectList({
					options:[
						{name:'已启用',value:'100'},
						{name:'待启用',value:'101'},
						{name:'待复核',value:'101'},
						{name:'复核驳回',value:'101'}
					]
				});
				
				$(".enable_btn").click(function(){
					alert("你确认启用此模板？");
				});
				
				$(".disable_btn").click(function(){
					alert("你确认停用此模板？");
				});
				
				$(".whDetialView_btn").click(function(){
				  $( "#whDetailContent" ).dialog({
					title:"查看模版",
					modal:true,
					width:"900",
					closeIcon:true
				  });
				});
			});
			
			$(".back_htcx").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htgl").removeClass("none");	
			});
			
			$(".back_htgz").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htgz").removeClass("none");	
				
				//下拉选择框
				$("#gz_state").selectList({
					width: 130,
					options:[
						{name:'已盖章',value:'100'},
						{name:'未盖章',value:'101'},
						{name:'须重新盖章',value:'102'}
					]
				});
				
			});

			$(".modifyModules_btn").click(function() {
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#modifyModules").removeClass("none");
				
				$("#enterprise_type_4").selectList({
					width:180,
					options:[
						{name:'服务公司',value:'100'},
						{name:'托管企业',value:'101'},
						{name:'成员企业',value:'101'}
					]
				});
				
				$("#resources_state_2").selectList({
					width:155,
					options:[
						{name:'创业资源',value:'100'},
						{name:'首段资源',value:'101'},
						{name:'全部资源',value:'101'}
					]
				});
				
				$('#template_content_2').xheditor({
					upLinkUrl:"upload.php",
					upLinkExt:"zip,rar,txt",
					upImgUrl:"upload.php",
					upImgExt:"jpg,jpeg,gif,png",
					upFlashUrl:"upload.php",
					upFlashExt:"swf",
					upMediaUrl:"upload.php",
					upMediaExt:"wmv,avi,wma,mp3,mid",
                    width:678,
                    height:150
				}); 
                //$(".xheIframeArea").css("height","170px");初始化编辑框的高度
			});
			
			$(".back_htmbfh").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htmbfh").removeClass("none");
				
				$("#enterprise_type_2").selectList({
					options:[
						{name:'xxx',value:'100'},
						{name:'xxx',value:'101'}
					]
				});
				
				
				$("#state_2").selectList({
					options:[
						{name:'已启用',value:'100'},
						{name:'待启用',value:'101'},
						{name:'待复核',value:'101'},
						{name:'复核驳回',value:'101'}
					]
				});
				
				$(".fhdetialView_btn").click(function(){
				$( "#fhDetailContent" ).dialog({
					title:"查看模版",
					modal:true,
					width:"900",
					closeIcon:true
			
				  	});
				});
			});

			$("#addModules_btn").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#addModules").removeClass("none");
				
				$("#enterprise_type_3").selectList({
					width:180,
					options:[
						{name:'服务公司',value:'100'},
						{name:'托管企业',value:'101'},
						{name:'成员企业',value:'101'}
					]
				});
				
				$("#resources_state").selectList({
					width:155,
					options:[
						{name:'创业资源',value:'100'},
						{name:'首段资源',value:'101'},
						{name:'全部资源',value:'101'}
					]
				});
				
				$('#template_content').xheditor({
					upLinkUrl:"upload.php",
					upLinkExt:"zip,rar,txt",
					upImgUrl:"upload.php",
					upImgExt:"jpg,jpeg,gif,png",
					upFlashUrl:"upload.php",
					upFlashExt:"swf",
					upMediaUrl:"upload.php",
					upMediaExt:"wmv,avi,wma,mp3,mid",
                    width:678,
                    height:150
				}); 
                //$(".xheIframeArea").css("height","170px");初始化编辑框的高度
			});
			
			$("#fh_addModules_btn").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#fhaddModules").removeClass("none");	
				
				$("#fhenterprise_type").selectList({
					width:180,
					options:[
						{name:'服务公司',value:'100'},
						{name:'托管企业',value:'101'},
						{name:'成员企业',value:'101'}
					]
				});
				
				$("#fhresources_tpye").selectList({
					width:155,
					options:[
						{name:'创业资源',value:'100'},
						{name:'首段资源',value:'101'},
						{name:'全部资源',value:'101'}
					]
				});
				
				$('#template_content_3').xheditor({
					upLinkUrl:"upload.php",
					upLinkExt:"zip,rar,txt",
					upImgUrl:"upload.php",
					upImgExt:"jpg,jpeg,gif,png",
					upFlashUrl:"upload.php",
					upFlashExt:"swf",
					upMediaUrl:"upload.php",
					upMediaExt:"wmv,avi,wma,mp3,mid",
                    width:678,
                    height:150
				}); 
				
			});
			
			$(".fh_btn").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htmbfh_fh").removeClass("none");
				
				$( "#fhtj_btn" ).click(function() {
				  $( "#fhtjContent" ).dialog({
					title:"复核确认",
					modal:true,
					width:"400",
					height:"220",
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						},
						"取消":function(){
							 $( this ).dialog( "close" );
						}
					}
			
				  });
				});	
			});
			
			$(".back_htff").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htff").removeClass("none");
				
				$("#ff_state").selectList({
					width: 130,
					options:[
						{name:'已发放',value:'100'},
						{name:'未发放',value:'101'}
					]
				});
				
			});
			
			$(".ffht_btn").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#htffls").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#ffht").removeClass("none");
			});
			
			$(".htffls_btn").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#cxscht").addClass("none");
				
				$("#htffls").removeClass("none");
			});
			
			$(".back_cxscht").click(function(){
				$("#htmbwh").addClass("none");
				$("#htgl").addClass("none");
				$("#htgz").addClass("none");
				$("#modifyModules").addClass("none");
				$("#htmbfh").addClass("none");
				$("#addModules").addClass("none");
				$("#fhaddModules").addClass("none");
				$("#htmbfh_fh").addClass("none");
				$("#htff").addClass("none");
				$("#ffht").addClass("none");
				$("#htffls").addClass("none");
				
				$("#cxscht").removeClass("none");	
			});
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return htgl;
	 

});