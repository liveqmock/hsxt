define(["text!callCenterTpl/tableSetting.html","text!callCenterTpl/tableStatusControl.html","text!callCenterTpl/dutyList.html","text!callCenterTpl/schedule.html","text!callCenterTpl/rankArrange.html","text!callCenterTpl/rankArrangeModif.html","text!callCenterTpl/seatsList.html","text!callCenterTpl/agentConfigList.html","callCenterDat/callCenter/callCenter","callCenterSrc/callCenter/showSelected", "callCenterDat/callCenter/tableSetting","callCenterSrc/callCenter/rankArrange","jquerytreeview"],function(tableSettingTpl,tableStatusControlTpl,dutyListTpl,scheduleTpl,rankArrangeTpl,rankArrangeModifTpl,seatsListTpl,agentConfigTpl,ajaxObj){
	var workingList_DataTable=null;
	var searchSchedule_DataTable =null;
	var searchDutyList_DataTable=null;
	var deptName="";
	var tableSetting = {
		tableSettingShow:function(){
			//坐席状态监控 
			$('.operationsArea').html(tableSettingTpl);		
			qryTakeMonitoring("10,20,21,22,23,30","tableSetting_li");
			//根据状态查询坐席
			$("#tableSettingDiv a").off("click").click(function(e){		
				var target = $(e.currentTarget);
				var dataId= target.attr('data-id');
				qryTakeMonitoring(dataId,"tableSetting_li");
			});
		},
		tableStatusControlShow:function(){
			//坐席设置
			$('.operationsArea').html(tableStatusControlTpl);		
			qryTakeMonitoring("10,20,21,22,23,30","settingUL");
		  $("#settingDiv a").off("click").click(function(e){	
				var target = $(e.currentTarget);
				var dataId= target.attr('data-id');
				qryTakeMonitoring(dataId,"settingUL");
			});
			//刷新查询坐席 编辑
	     $("#updateRefre").off("click").click(function(e){
				var target = $(e.currentTarget);
				var dataId= target.attr('data-id');
				qryTakeMonitoring(dataId,"settingUL");
			});

		},
		addDutyShow:function(){
			//工作组设置
			$('.operationsArea').html(dutyListTpl);	
			//加载岗位列表
			getDutyListShow();
			
			//弹框添加岗位
			$("#addDutyBut").off("click").click(function(e){
				var data = $(e.currentTarget).attr('data');
						  $( "#insertDuty" ).dialog({
							title:"新增岗位",
							modal:true,
							width:"500",
							height:"270",
							buttons:{ 
								"确定":function(){

									var dutyValue=$("#dutyValue").val();
									var description=$("#description").val();
									var dutyName=$("#dutyName").val();
									var dutyCode=$("#dutyCode").val();
									var deptSel= $("#duty_Sel2 option:selected");
									var sysDuty={
											"dutyType":"1",
										"dutyValue":dutyValue,
										"description":description,
										"dutyName":dutyName,
										"dutyCode":dutyCode
									};
									$.ajax({
										url :  comm.domainList['server']+comm.UrlList["saveDutyInfo"],
										type : 'POST',
										cache : false,
										data:sysDuty,
										success : function(responseData) {
											if(responseData.code<0){
												alert("失败!"+responseData.message);
											}else{
												alert("成功!");
											}
										}}); 
									searchDutyList_DataTable.draw();
									$( this ).dialog( "destroy" );
								
								},
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
					
						});
			});
			$("#duty_Sel1").dblclick(function(e){		
				 var duty_Sel1= $("#duty_Sel1 option:selected");
				   var duty_Sel2= $("#duty_Sel2 ");
				   moveOption(duty_Sel1,duty_Sel2);
			});
			//添加but
			$("#addDuty").off("click").click(function(){
				var duty_Sel1= $("#duty_Sel1 option:selected");
				   var duty_Sel2= $("#duty_Sel2 ");
				   moveOption(duty_Sel1,duty_Sel2);
			});
			//删除but
			$("#delDuty").off("click").click(function(){
				var duty_Sel1= $("#duty_Sel2 option:selected");
				   var duty_Sel2= $("#duty_Sel1");
				   moveOption(duty_Sel1,duty_Sel2);
			});
			//删除but
			$("#duty_Sel2").dblclick(function(){
				var duty_Sel1= $("#duty_Sel2 option:selected");
				   var duty_Sel2= $("#duty_Sel1");
				   moveOption(duty_Sel1,duty_Sel2);
			});
			},
		scheduleShow:function(){
			//日程安排 
			$('.operationsArea').html(scheduleTpl);	
			//显示树
			showTreeview();
		},
		rankArrangeShow:function(){
			//排班管理
			$('.operationsArea').html(rankArrangeTpl);	
			//显示树
			showManagerDeptTreeview();
			//事件绑定
			addClickRankArrange();
		},
		rankArrangeModiShow:function(){
			//排班修改
			$('.operationsArea').html(rankArrangeModifTpl);	
			//显示树
			showRankArrangeModiTreeview();
			//事件绑定
			addClickRankArrangeModif();
			//下拉框样式
		    $("#modifEmployee").selectbox({width:150,height:200});
		},
		seatsListShow:function(){
			//我的日程
			$('.operationsArea').html(seatsListTpl);
			 var mydate = new Date();
			  var t=mydate.toLocaleString();
			//加载
			seatsList(ajaxObj.formatDate(mydate,"yyyy-MM-dd HH:mm:ss"));
		},
		agentConfigShow:function(){
			$('.operationsArea').html(agentConfigTpl);
			//加载列表
			getAgentConfigs();
		}
			
	};	
	
	//显示树
	function showTreeview(){
		$.ajax({
			url :  comm.domainList['server']+comm.UrlList["qryTreeview"],
			type : 'POST',
			cache : false,
			data:{"companyId":'00000000156'},
			success : function(responseData) {
				var treeStr="";
				$.each(responseData.aaData,function(index,item){
					treeStr = assemblyTree(item);
				});
				$("#depttreeview").remove();
				$("#treeview_div").empty();
				$("#treeview_div").html("<ul id=\"depttreeview\" class=\"filetree\">"+treeStr+"</ul>");
				//清空递归 全局变量
				str="";
				$("#depttreeview").treeview({
					  persist: "location",
					  collapsed: true,
					  unique: true
				});
				//树形菜单 单击事件
				$("#depttreeview span").off("click").click(function(e){
					var currentDiv = $(e.currentTarget);
					var deptNo=currentDiv.attr("deptNo");
					deptName=currentDiv.attr("deptName");
					deptShow(deptNo);
				});
			}}); 
		
	};

	
	//切换Tab标签
	$(document).on('click','li' ,function(e){	
		var target = $(e.currentTarget);
		var dataId= target.attr('data-id');
		switch (dataId){
			case "5":
				tableSetting.tableSettingShow();
				break;
			case "6" : 				
				tableSetting.tableStatusControlShow();
				break;
			case "7" : 				
				tableSetting.addDutyShow();
				break;
			case "8" : 				
				tableSetting.scheduleShow();
				break;
			case "9" : 				
				tableSetting.rankArrangeShow();
				break;
			case "10" : 				
				tableSetting.rankArrangeModiShow();
				break;
			case "11" :			
				tableSetting.seatsListShow();
				break;
			case "12" :			
				tableSetting.agentConfigShow();
				break;
				
		}
	});
	//显示部门排班视图
	function deptShow(deptNo){
		var employeeOption="";//岗位列表
		$.ajax({
			url :  comm.domainList['server']+comm.UrlList["qrygetDutyByDeptNo"],
			type : 'POST',
			cache : false,
			data:{"deptNo":deptNo},
			success : function(responseData) {
				employeeOption+="<select id='deptNoSel'>";
				$.each(responseData.aaData,function(index,items){
					employeeOption+="<option value='"+items.dutyId+"'>"+items.dutyName+"</option>";
			
				});
				employeeOption+="</select>";
				$.ajax({
					url :  comm.domainList['server']+comm.UrlList["qrygetEmployeeByDeptCode"],
					type : 'POST',
					cache : false,
					data:{"deptNo":deptNo},
					success : function(responseData) {
						var tbody="";
						$.each(responseData.aaData,function(index,items){
							tbody+="<tr><td data='"+items.userId+"'>"+items.userName+"</td><td data='"+items.dutyId+"'>"+items.dutyName+"</td><td>"+employeeOption+"</td><td><a class='dept_but'  title=\"确定\">确定</a></td></tr>";
						});
						$("#tbody_dept").html(tbody);
						
						//保存岗位分配
						$(".dept_but").off("click").click(function(e){
							var currentDiv = $(e.currentTarget);
							var userId=currentDiv.parent("td").parent("tr").find("td").eq(0).attr("data");
						    var dutyId=currentDiv.parent("td").parent("tr").find("td").eq(2).children("select").val();
							$.ajax({
								url :  comm.domainList['server']+comm.UrlList["changeUserDuty"],
								type : 'POST',
								cache : false,
								data:{"userId":userId,"dutyId":dutyId,"deptName":deptName},
								success : function(responseData) {
									if(responseData.code<0){
										alert("失败!"+responseData.message);
									}else{
										var txt=currentDiv.parent("td").parent("tr").find("td").eq(2).children("select").find("option:selected").text();
										currentDiv.parent("td").parent("tr").find("td").eq(1).text(txt);
										alert("成功!");
									}
								}});
						});
					}}); 
			}}); 
	};
	//树节点拼装
	var b = true;
	var str="";	
	function assemblyTree(item){
		if(item.hasChild){
			if(b)
		    {
			 str+="<li><div class=\"hitarea expandable-hitarea\"></div><span class=\"folder\" deptName=\""+item.deptName+"\" deptNo=\""+item.deptNo+"\">"+item.deptName+"</span>";
			}
			$.each(item.child,function(index,items){
				if(index==0){
					str+="<ul class=\"filetree treeview\">";
				}
		        str+="<li><span class=\"file\" deptNo=\""+items.deptNo+"\">"+items.deptName+"</span>";
		    	if(items.hasChild)
		    	{
		    		b = false;
		    		assemblyTree(items);
		    		b = true;
		    	}
		    	str+="</li>";
			});
			str+="</ul></li>";
		}else{
			str+="<li><span class=\"folder\" deptNo=\""+item.deptNo+"\">"+item.deptName+"</span></li>";
		}
		return str;
	};
	

	//下拉框左右移动
 function moveOption(e1, e2) {
				for ( var i = 0; i < e1.length; i++) {
					e2.append(e1[i]);
				}
				$.ajax({
					url :  comm.domainList['server']+comm.UrlList["linkDutyToDept"]+"/"+getvalue($("#duty_Sel2 option")),
					type : 'POST',
					cache : false,
					data:{"dutyId":dutyId_},
					success : function(responseData) {}});
			}
function getvalue(geto) {
				var allvalue = "";
				for ( var i = 0; i < geto.length; i++) {
					allvalue += geto[i].value + ",";
				}
				return allvalue;
			}


	//坐席状态监控查询 状态status 显示在那 ulId
	function qryTakeMonitoring(status,ulId){
		$.ajax({
			url :  comm.domainList['server']+comm.UrlList["qryTakeMonitoring"],
			type : 'POST',
			cache : false,
			data:{"status":status},
			success : function(responseData) {
			var str="";
			//21, ''空闲'' \r\n            22, ''置忙'' \r\n            23, ''静音''\r\n 
			$.each(responseData.aaData,function(index,item){
				switch (item.workStatus){
				case 20:
					str+="<li  class=\"state_bt_green pr\"><div class=\"seatContentDiv\" parent-id='20'>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='22'>置忙</a><br></div></li>";
					break;
				case 22 : 				
					str+="<li class=\"state_bt_red pr\"><div class=\"seatContentDiv\" parent-id='22'>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='21'>示闲</a></div></li>";
					break;
				case 23 : 				
					str+="<li class=\"state_bt_yellow pr\"><div class=\"seatContentDiv\" parent-id='23'>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='23'>静音</a><br><a data='22'>置忙</a><br><a data='21'>示闲</a></div></li>";
					break;
				case 21 : 				
					str+="<li class=\"state_bt_blue pr\"><div class=\"seatContentDiv\" parent-id='21'>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='22'>置忙</a></div></li>";
					break;
				case 10 : 				
					str+="<li class=\"state_bt_gray pr\"><div class=\"seatContentDiv\" parent-id='10'>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='23'>静音</a><br><a data='22'>置忙</a><br><a data='21'>示闲</a></div></li>";
					break;
				default:
					str+="<li><div class=\"seatContentDiv pr\" parent-id=''>"+item.agentNum+"</div><div class=\"seatPop-up_menu pa none\"><a data='23'>静音</a><br><a data='22'>置忙</a><br><a data='21'>示闲</a></div></li>";
				break;
				}
			});
			$("#"+ulId).html(str);
		if($("#tableSetting").hasClass("active")){//只有坐席设置的li 才添加事件
			// 操作li 右边div
			$(".seatContentDiv").off("click").click(function(e){
				var currentDiv = $(e.currentTarget);
				//只有 签入 至忙 空闲 才有点击事件 
				if(currentDiv.attr("parent-id")=='20'||currentDiv.attr("parent-id")=='21'||currentDiv.attr("parent-id")=='22'){
					if(currentDiv.siblings(".seatPop-up_menu").hasClass("none")){
						currentDiv.siblings(".seatPop-up_menu").removeClass("none");
						currentDiv.parent("li").siblings().children(".seatPop-up_menu").addClass("none");
					}else{
						//隐藏右边div
						currentDiv.siblings(".seatPop-up_menu").addClass("none");
						//currentDiv.parent("li").siblings().children(".seatPop-up_menu").removeClass("none");
					}
				
				currentDiv.parent("li").children(".seatPop-up_menu").children("a").off("click").click(function(e){
					var currentA = $(e.currentTarget);
					currentA.parent("div").addClass("none");
					var ret;
					switch (currentA.attr("data")) {
					case '23':
						// ret = Test.JS_SetUserFree(currentDiv.html());
						break;
					case '22':
						  ret = Test.JS_SetUserBusy(currentDiv.html());
						  if(ret==1){
							  alert("置忙成功!");
							  qryTakeMonitoring('22',"settingUL");
						  }
						break;
					default:
						 ret = Test.JS_SetUserFree(currentDiv.html());
					 if(ret==1){
						  alert("示闲成功!");
						  qryTakeMonitoring('21',"settingUL");
					  }
						break;
					}
					});
				}
				});
			}
			}});
	};
//岗位查询
	function getDutyListShow(){
		//列表显示
		if (searchDutyList_DataTable != null) {
			$("#DutyList_DateTbl").DataTable().destroy();
		}
		searchDutyList_DataTable = $("#DutyList_DateTbl").DataTable(
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
						"url" : comm.domainList['server']+comm.UrlList["qrygetDutyList"],
						"type" : "POST"
					},
					"aoColumns" : [ /*{
						"data" : "dutyId"//,"bVisible": false 
					},*/ {
						"data" : "description"
					},{ 
						"data" : "dutyCode"
					}, {
						"data" : "dutyName"
					}],
					"columnDefs" : [	{
						"targets" : [ 0 ],
						"render" : function(data, type, full) {
							  return "<span  data='"+full.dutyId+"' >"+data+"</span>";
						}
					},{
						"targets" : [ 3 ],
						"render" : function(data, type, full) {
							var str="<a  data='"+full.dutyId+"' onclick=showSelected('"+full.dutyId+"')  class=\"DutyList_A\"  title=\"分配\">分配</a>";
							  return str;
						}
					}]
				});
	
	};

	//日程安排查询
	function searchSchedule(){
		//查询条件 
		var groupSchedule=$("#groupSchedule").val();
		var scheduleUserName=$("#scheduleUserName").val();
		var workData=$("#workData").val();
				if (groupSchedule == "--请选择--") {
					groupSchedule = "";
				}
				if (scheduleUserName == "输入工作人员名称") {
					scheduleUserName = "";
				}
				
				
				//列表显示
				if (searchSchedule_DataTable != null) {
					$("#searchScheduleDataTable").DataTable().destroy();
				}
				searchSchedule_DataTable = $("#searchScheduleDataTable").DataTable(
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
								"url" : comm.domainList['server']+comm.UrlList["qrySchedule"],
								"type" : "POST",		
								"data":{"groupSchedule":groupSchedule,"scheduleUserName":scheduleUserName,"workData":workData}
							},
							"columns" : [ {
								"data" : "id"
							}, {
								"data" : "callingNum"
							}, {
								"data" : "agentNum"
							}, {
								"data" : "userName"
							}, {
								"data" : "createTime"
							}, {
								"data" : "duration"
							} ],
							"columnDefs" : [
											{
												"targets" : [ 0 ],
												"render" : function(data, type, full) {
													return "<div class='checker'><span ><input  id=\"callList"
															+ data
															+ "\" type=\"checkbox\" value=\""
															+ data + "\"/></span></div>";
												}
											} ,	
										{
										"targets" : [4],
										"render" : function(data, type, full) {
											return ajax.formatDate(data,"yyyy-MM-dd HH:mm:ss");
										}
									}

							]
						});

	
	};
	return tableSetting;
});
