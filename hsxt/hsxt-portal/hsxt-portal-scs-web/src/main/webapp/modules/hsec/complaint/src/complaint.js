define(["hsec_complaintDat/complaint"
        ,"text!hsec_complaintTpl/complaint/complaintHead.html"
        ,"text!hsec_complaintTpl/complaint/complaintDetail.html"
        ,"hsec_tablePointSrc/tablePoint"
        ],function(ajax,complaintHead,complaintDetail,tablePoint){
	var queryList = {};
	var gridObj;
	var complaint ={
		//初始化ComboxList
		selectList : function(){
				$("#status").selectList({
					options:[
	                    {name:'全部',value:''},
						{name:'处理中',value:'1'},
						{name:'已处理',value:'2'}
					]
				});
			},
			queryParam : function(num){
				queryList = {};
				queryList["type"] = 1;
				queryList["status"] = $("#status").attr("optionvalue");
				queryList["vshopName"] = $("#shopName").val();
				queryList["shopResourceNo"] = $("#resourceNo").val();
				queryList["userName"] = $("#userName").val();
				queryList["complainType"] = $("#complaintType").val();
			},
			bindData : function(){
				$("#busibox").html(complaintHead);
				complaint.selectList();
				complaint.queryParam(1);
				complaint.bindTable(queryList);
			},
			bindHeadClick : function(){
				//编号输入验证
				$("#resourceNo").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$('#btnQueryComplain').bind('click',function(){
					complaint.queryParam(1);
					complaint.bindTable(queryList);
				});
			},
			bindTable : function(queryList){
				gridObj = $.fn.bsgrid.init('complaint_grid',{
					url:{url:comm.UrlList['getComplainByParam'],domain:'scs'},
					pageSize:20,
					pageSizeSelect:false,
					otherParames:$.param(queryList),
					displayBlankRows:false,
					lineWrap:true,
					operate:{
						add : function(record, rowIndex, colIndex, options){
							if(colIndex==7){
								if(record.status == 1){
					            	return '处理中'
					            } else {
					            	return '已处理'
					            }
							}
							if(colIndex==8){
								sTpl = "";
								if(record.status == 1){
						            sTpl += '<button name="btnProcess" type="button" class="btn-search ml10" value="'+record.id+'">处理</button>'
					            }
					            sTpl += '<button name="btnDetail" type="button" class="btn-search ml10" value="'+record.id+'">查看详情</button>'
					            
					            return sTpl;
							}
						}
					},
					complete:function(o, e, c){
						complaint.bindHeadClick();
						complaint.processSetp();
						complaint.showDetail();
					}
				});
			},
			//查看详情
			showDetail : function(){
				$("button[name='btnDetail']").click(function(){
					var id = $(this).attr("value");
					ajax.getComplainDetailById(id,function(response){
						var html = _.template(complaintDetail,response);
						$("#porpDetail").html(html);
						$("#porpDetail").dialog({
							title : '客户投诉详情',
							show : true,
							model : true,
							width : 750,
							open : function(){
								$(".ui-dialog-titlebar-close").click(function(){
									$(this).dialog("destroy");
									$("#porpDetail").dialog("destroy");
								});
							},
							buttons : {
								"关闭" : function(){
									$(this).dialog("destroy");
									$("#porpDetail").dialog("destroy");
								}
							}
						});
					});
				});
			},
			processSetp : function(){
				$("button[name='btnProcess']").click(function(){
					var idVal = $(this).attr("value");
					$("#porpDiv").dialog({
						title : '投诉处理',
						modal : true,
						show : true,
						open : function(){
							//$(".ui-dialog-titlebar-close", $(this).parent()).hide();//隐藏close
							ajax.getDictionarysByType(function(response){
								var html="<option value=''>-请选择-</option>";
								$.each(response.data,function(i,value){
									html+="<option value='"+value.value+"'>"+value.name+"</option>"
								})
								$("#txtProcessType").html(html);
							});
							$(".ui-dialog-titlebar-close").click(function(){
								$("#txtProcessType").val("");
							　　$("#txtProcessType").find("option:selected").text();
								$("#content").val("");
								$(this).dialog("destroy");
								$("#porpDiv").dialog("destroy");
							});
						},
						buttons : {
						'处理':function(){
							var checkType = $("#txtProcessType").val();
						　　var txtCheckType = $("#txtProcessType").find("option:selected").text();
							var content = $("#content").val();
							if(checkType == ''){
								tablePoint.tablePoint("请选择处理类型!");
							}
							if(content == ''){
								tablePoint.tablePoint("请填写处理描述!");
							}
							if(checkType != '' && content != ''){
								var updateParam = {
									"id":idVal,
									"status":2,
									"processType" : txtCheckType,
									"remark":content
								};
								ajax.processComplaints(updateParam,function(response){
									if(response.retCode == 200){
										$("#porpDivMsg").dialog({
											title : "系统提示",
											modal : true,
											show : true,
											open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>处理成功!</center>"); },
											buttons : {
												'确定' : function(){
													complaint.queryParam(1);
													complaint.bindTable(queryList);
													$("#txtProcessType").val("");
												　　$("#txtProcessType").find("option:selected").text();
													$("#content").val("");
													$(this).dialog("destroy");
													$("#porpDiv").dialog("destroy");
												}
											}
										});
										}else if(response.retCode == 201){
											$("#porpDivMsg").dialog({
												title : "系统提示",
												modal : true,
												show : true,
												open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>对不起,请求失败!</center>"); },
												buttons : {
													'确定' : function(){
														$("#txtProcessType").val("");
													　　$("#txtProcessType").find("option:selected").text();
														$("#content").val("");
														$(this).dialog("destroy");
														$("#porpDiv").dialog("destroy");
													}
												}
											});
										}else if(response.retCode == 212){
											$("#porpDivMsg").dialog({
												title : "系统提示",
												modal : true,
												show : true,
												open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>登录已过期,请重新登录,谢谢!</center>"); },
												buttons : {
													'确定' : function(){
														$("#txtProcessType").val("");
													　　$("#txtProcessType").find("option:selected").text();
														$("#content").val("");
														$(this).dialog("destroy");
														$("#porpDiv").dialog("destroy");
													}
												}
											});
										}
								});
							}
						},
						'取消':function(){
							$("#txtProcessType").val("");
						　　$("#txtProcessType").find("option:selected").text();
							$("#content").val("");
							$(this).dialog('destroy');
						}
						}
					});
				});
			}
	}
	return complaint;
});