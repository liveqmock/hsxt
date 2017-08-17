define([
"text!yufuTpl/jfyfkzhgl/jfyfkzhgl.html",
"text!yufuTpl/jfyfkzhgl/jfyfkzhyjsz.html",
"text!yufuTpl/jfyfkzhgl/jfyfkzhyjsz_xz.html",
"jqueryalert"
],function(jfyfkzhglTpl,jfyfkzhyjszTpl,jfyfkzhyjsz_xzTpl){
 
	var jfyfkzhgl = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(jfyfkzhglTpl).append(jfyfkzhyjszTpl).append(jfyfkzhyjsz_xzTpl);	
			
			//下拉选择框
			$("#yStatues").selectbox({width:90});
			
			
			
			$(".yjsz").click(function(){
				$("#yjgl").addClass("none");
				$("#yjsz_xz").addClass("none");
				
				$("#yjsz").removeClass("none");	
				$("#yStatues1").selectbox({width:90});
				 $( ".a_jfyfkzhsz_del" ).click(function() {
					alert("删除成功！");  
					});

				$( ".a_jfyfkzhsz_ck_1" ).click(function() {
				  $( "#jfyfzhsz_ck_1" ).dialog({
					title:"预警设置详情",
					width:"800",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						}
					}
				  });
				 
				});
				
				$( ".a_jfyfkzhsz_ck_2" ).click(function() {
				  $( "#jfyfzhsz_ck_2" ).dialog({
					title:"预警设置详情",
					width:"800",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "close" );
						}
					}
				  });
				 
				});
			});
			//弹出框事件
			function tips() {
				  $( "#jfyfzhgl_tips" ).dialog({
					title:"提示信息",
					width:"400",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						}
					}
				  });
			};
			$( ".a_jfyfkzhgl_ck" ).click(function() {
				  $( "#jfyfzhgl_ck" ).dialog({
					title:"预警管理详情",
					width:"800",
					modal:true,
					buttons:{ 
						"立即发送":function(){
							$( this ).dialog( "close" );
							tips();
						},
						"取消":function(){
							 $( this ).dialog( "close" );
						}
					}
			
				  });
			});
			
			$(".yjgl").click(function(){
				$("#yjsz").addClass("none");
				$("#yjsz_xz").addClass("none");
				
				$("#yjgl").removeClass("none");	
			});
			$(".a_yjsz_xz").click(function(){
				$("#yjsz").addClass("none");
				$("#yjgl").addClass("none");
				
				$("#yjsz_xz").removeClass("none");
				$("#template_use").selectbox({width:280,height:260});
					
				$("#dingshifasong").click(function(){
					if($(this).is(':checked')){
						$("#dsfsbox").css("display","");
					}else{
						$("#dsfsbox").css("display","none");	
					}
				});
				$("#mrfs_i").click(function(){
					if($(this).is(':checked')){
						$("#mrfs").css("display","");
						$("#mzfs").css("display","none");
						$("#myfs").css("display","none");
						$("#dsfs").css("display","none");
					}
				});
				$("#mzfs_i").click(function(){
					if($(this).is(':checked')){
						$("#mzfs").css("display","");
						$("#mrfs").css("display","none");
						$("#myfs").css("display","none");
						$("#dsfs").css("display","none");
					}
				});
				$("#myfs_i").click(function(){
					if($(this).is(':checked')){
						$("#myfs").css("display","");
						$("#mzfs").css("display","none");
						$("#mrfs").css("display","none");
						$("#dsfs").css("display","none");
					}
				});
				$("#dsfs_i").click(function(){
					if($(this).is(':checked')){
						$("#dsfs").css("display","");
						$("#mzfs").css("display","none");
						$("#myfs").css("display","none");
						$("#mrfs").css("display","none");
					}
				});
				$("#a_jfyfkzhsz_xz_back").click(function(){
					$("#yjgl").addClass("none");
					$("#yjsz_xz").addClass("none");
					
					$("#yjsz").removeClass("none");	
			    });
				$("#a_jfyfkzhsz_xz_tj").click(function(){
					alert("操作成功！");	
			    });
				
				//下拉选择框
				//$("#yHour").selectbox({width:120,height:100}).change(function(){
					//console.log("Hour change");
				//});
				//$("#yMinute").selectbox({width:120,height:100}).change(function(){
					//console.log("Minute change");
				//});
				//
				
				
			});
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return jfyfkzhgl;
	 

});