var deptNo_ = "";
var deptName_ = "1";
// 显示树
function showManagerDeptTreeview() {
	$.ajax({
		url : comm.domainList['server'] + comm.UrlList["qryManagerDeptTreeview"],
		type : 'POST',
		cache : false,
		data : {
			"userId" : '00000000156_dengblsq'
		},
		success : function(responseData) {
			var treeStrDept = "";
			$.each(responseData.aaData, function(index, item) {
				treeStrDept = ManagerDeptTree(item);
			});
			$("#ManagerDeptview_div").empty();
			$("#ManagerDeptview_div").html(
					"<ul id=\"treeManagerDeptview\" class=\"filetree\">"
							+ treeStrDept + "</ul>");
			// 清空递归 全局变量
			str = "";
			$("#treeManagerDeptview").treeview({
				persist : "location",
				collapsed : true,
				unique : true
			});
			// 树形菜单 单击事件
			$("#treeManagerDeptview span").off("click").click(function(e) {
				var currentDiv = $(e.currentTarget);
				deptShow(currentDiv.attr("deptNo"),"employee1");
			});
		}
	});

};

//排班修改树
function showRankArrangeModiTreeview(){
	$.ajax({
		url : comm.domainList['server'] + comm.UrlList["qryManagerDeptTreeview"],
		type : 'POST',
		cache : false,
		data : {
			"userId" : '00000000156_dengblsq'
		},
		success : function(responseData) {
			var treeStrDept = "";
			$.each(responseData.aaData, function(index, item) {
				treeStrDept = ManagerDeptTree(item);
			});
			$("#rankArrangeModifTree").empty();
			$("#rankArrangeModifTree").html(
					"<ul id=\"rankArrangeModifTreeView\" class=\"filetree\">"
							+ treeStrDept + "</ul>");
			// 清空递归 全局变量
			str = "";
			$("#rankArrangeModifTreeView").treeview({
				persist : "location",
				collapsed : true,
				unique : true
			});
			// 树形菜单 单击事件
			$("#rankArrangeModifTreeView span").off("click").click(function(e) {
				var currentDiv = $(e.currentTarget);
				deptShow(currentDiv.attr("deptNo"),"modifEmployee");
				/*$("#modifEmployee").selectbox({width:100}).change(function(){
				});*/
			});
		}
	});


}
// 树节点拼装
var b = true;
var str = "";
function ManagerDeptTree(item) {
	if (item.hasChild) {
		if (b) {
			str += "<li><div class=\"hitarea expandable-hitarea\"></div><span class=\"folder\" deptName=\""
					+ item.deptName
					+ "\" deptNo=\""
					+ item.deptNo
					+ "\">"
					+ item.deptName + "</span>";
		}
		$.each(item.child, function(index, items) {
			if (index == 0) {
				str += "<ul class=\"filetree treeview\">";
			}
			str += "<li><span class=\"file\" deptNo=\"" + items.deptNo + "\">"
					+ items.deptName + "</span>";
			if (items.hasChild) {
				b = false;
				ManagerDeptTree(items);
				b = true;
			}
			str += "</li>";
		});
		str += "</ul></li>";
	} else {
		str += "<li><span class=\"folder\" deptNo=\"" + item.deptNo + "\">"
				+ item.deptName + "</span></li>";
	}
	return str;
};

// 显示部门排班视图deptNo 部门id   optionId下拉框id
function deptShow(deptNo,optionId) {
	deptNo_ = deptNo;
	$.ajax({
		url : comm.domainList['server']
				+ comm.UrlList["qrygetEmployeeByDeptCode"],
		type : 'POST',
		cache : false,
		data : {
			"deptNo" : deptNo
		},
		success : function(responseData) {
			$("#"+optionId).find("option").remove();
			$.each(responseData.aaData, function(index, items) {
				$("#"+optionId).append(
						"<option value='" + items.userId + "'>"
								+ items.userName + "</option>");

			});	
			if(optionId!="employee1"){
				$("#"+optionId).selectbox({width:300,height:200});
				$('#'+optionId).selectRefresh();
			}
		}
	});

	

};
// 班次列表
function dictionarySel(dictionarySelId) {
	$.ajax({
		url : comm.domainList['server'] + comm.UrlList["qrygetDictionaryByName"],
		type : 'POST',
		cache : false,
		data : {
			"ruleName" : "SCHEDUAL"
		},
		success : function(responseData) {
			$("#"+dictionarySelId).find("option").remove();
			$.each(responseData.date, function(index, items) {
				$("#"+dictionarySelId).append(
						"<option value='" + items.srId + "'>" + items.srValue
								+ "</option>");

			});
			$("#"+dictionarySelId).selectbox({width:150}).change(function(){}); 

		}
	});
};

// RankArrangeModif 事件绑定
function addClickRankArrangeModif(){
	//加载班次列表
	dictionarySel("modifDictionarySel");
	//渲染上班时间插件js
	$("#dutyDate").datepicker(comm.dateParament());
	// 提交 but
	$("#rankArrangeModif_But").off("click").click(function() {
		var userId = $("#modifEmployee").val();// 工作人员
		var srId = $("#modifDictionarySel").val();// 班次名称id
		var srValue = $("#modifDictionarySel option:selected").text();//班次名字
		var workingDay = $("#dutyDate").val();// 上班时间
 		var rankArrangeModifRadio = $("input[name=rankArrangeModifRadio]:checked").val();//是否正常上班  true : 正常    false :否
	var data_={
			"userId":userId,
            "srId":srId,
            "srValue":srValue,
            "workingDay":workingDay
	};
		$.ajax({
			url : comm.domainList['server'] + comm.UrlList["changeSchedual"]+"/"+rankArrangeModifRadio,
			type : 'POST',
			cache : false,
			data : data_,
			success : function(responseData) {
				if(responseData.code<0){
					alert("失败!"+responseData.message);
				}else{
					alert("成功!");
				}
			}
		});

	
	});
}
// RankArrange 事件绑定
function addClickRankArrange() {
	// 加载排班名称
	dictionarySel("dictionarySel");

	// 渲染时间插件
	$('#bDate_Rank').datepicker(comm.dateParament());
	$("#eDate_Rank").datepicker(comm.dateParament());
	$("#employee1").dblclick(function(e) {
		var employee1 = $("#employee1 option:selected");
		var employee2 = $("#employee2");
		moveOption(employee1, employee2);
	});
	// 添加but
	$("#addEmployee").off("click").click(function() {
		var employee1 = $("#employee1 option:selected");
		var employee2 = $("#employee2");
		moveOption(employee1, employee2);
	});
	// 删除but
	$("#delEmployee").off("click").click(function() {
		var employee1 = $("#employee2 option:selected");
		var employee2 = $("#employee1");
		moveOption(employee1, employee2);
	});
	$("#employee2").dblclick(function() {
		var employee1 = $("#employee2 option:selected");
		var employee2 = $("#employee1");
		moveOption(employee1, employee2);
	});

	// 确定保存
	$("#rankArrangeBut").off("click").click(function(e) {
		var dictionarySel = $("#dictionarySel").val();// 班次名称
		var bDate_Rank = $("#bDate_Rank").val();// 开始时间
		var eDate_Rank = $("#eDate_Rank").val();// 结束时间
		var Employee = getvalue($("#employee2 option"));// 选中的人员
		var data = {
			"startDate" : bDate_Rank,
			"endDate" : eDate_Rank,
			"deptNo" : deptNo_,
			"dictionary" : dictionarySel,
			"userIds" : Employee
		};
		//判断开始时间是否大于结束时间
 if(verifyDate(bDate_Rank, eDate_Rank)){
		$.ajax({
			url : comm.domainList['server'] + comm.UrlList["addSchedual"],
			type : 'POST',
			cache : false,
			data : data,
			success : function(responseData) {
				if(responseData.code<0){
					alert("失败!"+responseData.message);
				}else{
					alert("成功!");
				}
			}
		});
 }
	});
};

//我的日程  ===>>  个人
function seatsList(dateStr){
	$.ajax({
		url :  comm.domainList['server']+comm.UrlList["getMySchedualByMonth"],
		type : 'POST',
		cache : false,
		data:{"userId":'00000000156_yangzq',"dateStr":dateStr},
		success : function(responseData) {
		var strLi="";
		$.each(responseData.date,function(index,item){
			if(item.userSchedual.length<1){
				strLi+="<li style=\"width: 90px;height: 90px;\">无</li>";
			}
			$.each(item.userSchedual,function(index,items){
				if(items.srValue!=null)
					strLi+="<li style=\"width: 120px;height: 85px;line-height:30px;\" class=\"state_bt_yellow\">"+items.srValue+"<br>"+item.workingDay+"</li>";
			});
			
		});
		$("#MySchedual_li").html(strLi);
		$("#MySchedual_A").off("click").click(function(){
			var date=AddMonths(new Date(), 1);
			seatsList(date);
		});
		$("#MySchedual_Month").off("click").click(function(){
			 var mydate = new Date();
			  var t=mydate.toLocaleString();
			//加载
			seatsList(comm.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		});
		$("#MySchedual_boos").off("click").click(function(){
			//加载
			seatsList_boos(comm.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		});
		}});
}


var map={};
//我的日程  ===>>  Boos
function seatsList_boos(dateStr){

	$.ajax({
		url :  comm.domainList['server']+comm.UrlList["getSchedualByDeptNo"],
		type : 'POST',
		cache : false,
		data:{"DeptNo":'D00000000156002',"dateStr":dateStr},
		success : function(responseData) {
		var strLi="";
		$.each(responseData.date,function(index,item){
			map[item.workingDay]=item.userSchedual;
			strLi+="<li style=\"width: 90px;height: 90px;line-height:40px;\" class=\"state_bt_yellow\"><a href=\"javascript:toDayShow('"+item.workingDay+"')\">"+item.workingDay+"<br>"+item.userSchedual.length+"</a></li>";
		});
		$("#MySchedual_li").html(strLi);
		
		}});
}

//点击日期 显示本田 上班人员情况
function toDayShow(day){
	var boosLi="";
	//显示
	$.each(map[day],function(index,item){
		boosLi+=" <tr><td>"+item.deptName+"</td><td>"+item.userName+"</td><td>"+item.srValue+"</td></tr>"
	});
	  $("#MySchedual_boos_li").html(boosLi);
		  $("#boos_li_div").dialog({
			title:day+"  日程",
			modal:true,
			width:"500",
			buttons:{ 
				"确定":function(){
					$( this ).dialog( "destroy" );
				},
				"取消":function(){
					 $( this ).dialog( "destroy" );
				}
			}
		  });
}

//月份减1
function AddMonths(date, value) {
	 var currentYear=date.getFullYear();
     var currentMonth=date.getMonth();
     var prevCurrentYear=0,prevCurrentMonth=0;
     if(currentMonth==0){
         prevCurrentYear=currentYear-1;
         prevCurrentMonth=12;
     }else{
         prevCurrentYear=currentYear;
         prevCurrentMonth=currentMonth-value;
     }
     var prevmonthLastday=(new Date(currentYear,currentMonth,1)).getTime()-86400000;
          
     var prevmonthStartDate= new Date(prevCurrentYear,prevCurrentMonth,1).toLocaleString();    
      
     var prevmonthEndDate=new Date(prevmonthLastday).toLocaleString();
     return comm.formatDate(prevmonthLastday,"yyyy-MM-dd HH:mm:ss");
}
// 下拉框左右移动
function moveOption(e1, e2) {
	for ( var i = 0; i < e1.length; i++) {
		e2.append(e1[i]);
	}
}
function getvalue(geto) {
	var allvalue = "";
	for ( var i = 0; i < geto.length; i++) {
		allvalue += geto[i].value + ",";
	}
	return allvalue;
}
// 判断开始时间是否大于结束时间
function verifyDate(beginTime, endTime) {
	var bool = true;
	if (beginTime.length > 0 && endTime.length > 0) {
		if (Date.parse(TimeFormatToSQL(beginTime).replace("-", "/")) > Date
				.parse(TimeFormatToSQL(endTime).replace("-", "/"))) {
			alert("开始时间不能大于结束时间!");
			bool = false;
		}
	}
	return bool;
}

// /<summary>/// 将传递的时间值转换为SQL识别的时间格式
// /<param name="strTime">时间(正常的页面显示时间格式)</param>
// /</summary>
function TimeFormatToSQL(strTime) {
	var strResult = "";
	var strTemp = "";
	for ( var i = 0; i < strTime.length; i++) {
		strTemp = strTime.substr(i, 1);
		if (strTemp == "年" || strTemp == "月")
			strResult += "-";
		else if (strTemp == "日" || strTemp == "秒") {
			if (strTemp == "日")
				strResult += "|";
			else
				strResult += "";
		} else if (strTemp == "时" || strTemp == "分")
			strResult += ":";
		else
			strResult += strTemp;
	}
	var strArgDateTime = strResult.split('|'); // 此时的时间格式可能为2010-11-11 11:
												// 或2010-11-11 11等格式
	if (strArgDateTime.length == 1) {
		// 日期部分进行处理
		var strArgDate = strArgDateTime[0].split('-'); // 此时对时间部分进行处理,可能为11: 11
														// 或11:00等格式
		if (strArgDate.length == 2) {
			if (strArgDate[1].length < 1)
				strResult = strArgDate[0];
			else
				strResult = strArgDateTime[0] + "-01";
		} else if (strArgDate.length == 3) {
			if (strArgDate[2].length < 1)
				strResult = strArgDate[0] + "-" + strArgDate[1] + "-01";
		}
	} else if (strArgDateTime.length == 2) {
		// 时间部分进行处理
		var strArgTime = strArgDateTime[1].split(':'); // 此时对时间部分进行处理,可能为11: 11
														// 或11:00等格式
		if (strArgTime.length == 1) {
			strResult = strArgDateTime[0] + " " + strArgDateTime[1] + ":00:00"
		} else if (strArgTime.length == 2) {
			if (strArgTime[1].length < 1)
				strResult = strArgDateTime[0] + " " + strArgDateTime[1] + "00"
			else
				strResult = strArgDateTime[0] + " " + strArgDateTime[1] + ":00"
		} else if (strArgTime.length == 3) {
			if (strArgTime[2].length < 1)
				strResult = strArgDateTime[0] + " " + strArgDateTime[1] + "00"
		}
	}
	return strResult;
}