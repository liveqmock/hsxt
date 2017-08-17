//显示下拉框
var dutyId_="";
var agentConfig_DateTbl=null;
	function showSelected(dutyId){
		dutyId_=dutyId;
		$.ajax({
			url :  comm.domainList['server']+comm.UrlList["qrygetDeptListByDutyId"],
			type : 'POST',
			cache : false,
			data:{"dutyId":dutyId},
			success : function(responseData) {
				$("#duty_Sel1").empty();
				$.each(responseData.date.unlink,function(index,item){
					$("#duty_Sel1").append( "<option value=\""+item.deptNo+"\">"+item.deptName+"</option>" );
				});
				
				$("#duty_Sel2").empty();
				$.each(responseData.date.link,function(index,item){
					$("#duty_Sel2").append( "<option value=\""+item.deptNo+"\">"+item.deptName+"</option>" );
				});	
				//先显示数据 在显示页面
				$( "#link_div" ).dialog({
				title:"部门分配",
				modal:true,
				width:"600",
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
		
			});

			}}); 
	};
	
//查找坐席设置列表
 function getAgentConfigs(){
	 //新增事件绑定
	 $("#addAgentConfigBut").off("click").click(function(e){
		 $("#agentConfigFrom")[0].reset(); 
			$( "#agentConfig_div" ).dialog({
				title:"坐席配置新增",
				modal:true,
				width:"400",
				heigth:"260",
				buttons:{ 
					"确定":function(){
						 var hardwareCode_= $("#hardwareCode").val();
						 var agentNum_= $("#agentNum").val();
						 var remark_= $("#remark").val();
						$.ajax({
							url :  comm.domainList['server']+comm.UrlList["addAgentConfig"],
							type : 'POST',
							cache : false,
							data:{"hardwareCode":hardwareCode_,"agentNum":agentNum_,"remark":remark_},
							success : function(responseData){
								if(responseData.code<0){
									alert("失败!"+responseData.message);
								}else{
									alert("成功!");
								}
								agentConfig_DateTbl.draw();
								
							
							}});
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}

			});
		 
	 });
	 
	 //查询列表

		//列表显示
		if (agentConfig_DateTbl != null) {
			$("#agentConfig_DateTbl").DataTable().destroy();
		}
		agentConfig_DateTbl = $("#agentConfig_DateTbl").DataTable(
				{
					"bJQueryUI" : true,
					"bFilter" : false,
					"sPaginationType" : "full_numbers",
					"sDom" : '<""l>t<"F"fp>',
					"processing" : true,
					"serverSide" : true,
					"bAutoWidth" : false, // 自适应宽度
					"bLengthChange" : false,
					"bSort":false,
					//'bStateSave' : true,
					"oLanguage" : {
						// "sLengthMenu" : "每页显示 _MENU_条",
						"sZeroRecords" : "没有找到符合条件的数据",
						"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
						"sInfoEmpty" : "没有记录",
						"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
						"sSearch" : "搜索：",
						"oPaginate" : {
							"sFirst" : "首页",
							"sPrevious" : "上一页",
							"sNext" : "下一页",
							"sLast" : "尾页"
						}
					},
					"ajax" : {
						"url" : comm.domainList['server']+comm.UrlList["qrygetAgentConfigs"],
						"type" : "POST"
					},
					"aoColumns" : [ {
						"data" : "hardwareCode"
					},{ 
						"data" : "agentNum"
					}, {
						"data" : "remark"
					}],
					"columnDefs" : [	{
						"targets" : [ 3 ],
						"render" : function(data, type, full) {
							var str="<a onclick=updateAgentConfig('"+full.gcId+"','"+full.hardwareCode+"','"+full.agentNum+"','"+full.remark+"')  title=\"修改\">修改</a>";
							  return str;
						}
					}]
				});
	
	
 };
 
 //修改坐席配置 hardwareCode 唯一标识
 function updateAgentConfig(gcId,hardwareCode,agentNum,remark){
	 //显示修改内容
	 $("#gcId").val(gcId);
	 $("#hardwareCode").val(hardwareCode);
	 $("#agentNum").val(agentNum);
	 $("#remark").val(remark);
		//先显示数据 在显示页面
		$( "#agentConfig_div" ).dialog({
		title:"坐席配置修改",
		modal:true,
		width:"400",
		height:"260",
		buttons:{ 
			"确定":function(){
				 var gcId_ = $("#gcId").val();
				 var hardwareCode_= $("#hardwareCode").val();
				 var agentNum_= $("#agentNum").val();
				 var remark_= $("#remark").val();
				$.ajax({
					url :  comm.domainList['server']+comm.UrlList["changeAgentConfig"],
					type : 'POST',
					cache : false,
					data:{"gcId":gcId_,"hardwareCode":hardwareCode_,"agentNum":agentNum_,"remark":remark_},
					success : function(responseData){
						if(responseData.code<0){
							alert("失败!"+responseData.message);
						}else{
							alert("成功!");
						}
						agentConfig_DateTbl.draw();
						
					
					}});
				$( this ).dialog( "destroy" );
			},
			"取消":function(){
				 $( this ).dialog( "destroy" );
			}
		}

	});
	
		
		
 }
