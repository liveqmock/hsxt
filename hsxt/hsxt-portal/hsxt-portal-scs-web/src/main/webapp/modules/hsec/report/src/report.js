define(["hsec_complaintDat/report"
        ,"text!hsec_complaintTpl/report/reportHead.html"
        ,"text!hsec_complaintTpl/report/reportList.html"
        ,"text!hsec_complaintTpl/report/reportDetail.html"
        ,"hsec_tablePointSrc/tablePoint"
        ],function(ajax,reportHead,reportList,reportDetail,tablePoint){
	var queryList = {};
	var gridObj;
	var report ={
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
				queryList["type"] = 2;
				queryList["status"] = $("#status").attr("optionvalue");
				queryList["itemName"] = $("#itemName").val();
			},
			bindData : function(){
				$("#busibox").html(reportHead);
				report.selectList();
				report.queryParam(1);
				report.bindTable(queryList);
				report.selectCocombox();
			},
			bindHeadClick : function(){
				$('#btnQuery').bind('click',function(){
					report.queryParam(1);
					report.bindTable(queryList);
				});
			},
			bindTable : function(queryList){
				gridObj = $.fn.bsgrid.init('report_grid',{
					url:{url:comm.UrlList['getComplainByParam'],domain:'scs'},
					pageSize:20,
					pageSizeSelect:true,
					otherParames:$.param(queryList),
					displayBlankRows:false,
					lineWrap:true,
					operate:{
						add : function(record, rowIndex, colIndex, options){
							if(colIndex==0){return _.template(reportList,record)}
							if(colIndex==4){
								if(record.status==1){
					         		return '处理中'
					         	}else if(record.status==2){
					        		return '已处理'
					         	}
							}
							if(colIndex==5){
								sTpl = ""
								 if(record.status == 1){
							        sTpl += '<button name="btnProcess" type="button" class="btn-search ml10" value="'+record.id+'" itemid="'+record.itemIDStr+'" tagid="'+record.virtualShopId+'">处理</button>'
						         }
						         	sTpl += '<button name="btnDetail" type="button" class="btn-search ml10" value="'+record.id+'">查看详情</button>'
						         if(record.spstatus!=1){
						         	sTpl += '<button name="btnDown" type="button" class="btn-search ml10" value="'+record.id+'" itemid="'+record.itemIDStr+'" tagid="'+record.virtualShopId+'">商品下架</button>'
							     }
							     return sTpl;
							}
						}
					},
					complete:function(o, e, c){
						//获取后台数据转换开始
						var other = eval("("+o.responseText+")").orther;
						var https = other.url;
						var itemInfoList = other.itemInfoList;
						var itemVisitUrl = other.itemVisitUrl;
						itemHttpUrl = "";
						$(".imgSrc").each(function(){
							var srcImg = $(this).attr("data-src");
							$(this).attr("src",https+srcImg);
						});
						$(".itemName").each(function(){
							var name= $(this).attr("data-name");
							var itemID = $(this).attr("data-id");
							$.each(itemInfoList,function(k,v){
					           if(v.id == itemID){
					            	itemHttpUrl = v.itemPreviewUrl;
					            	return false;
					           } }) 
					        if(itemHttpUrl != null && itemHttpUrl != ""){
					         	html = '<a href="'+itemVisitUrl+itemHttpUrl+'" target="_blank" >'+name+'</a>'
					        }else{
					         	html = name
					        }
					        $(this).append(html);
						});
						//获取后台数据转换结束
						report.bindHeadClick();
						report.processSetp();
						report.processDown();
						report.showDetail();
					}
				});
			},
			showDetail : function(){
				$("button[name='btnDetail']").unbind().on('click',function(){
					var id = $(this).attr("value");
					ajax.getComplainDetailById(id,function(response){
						var html = _.template(reportDetail,response);
						$("#porpDetail").html(html);
						$("#porpDetail").dialog({
							title : "客户举报详情",
							show : true,
							modal : true,
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
			//【事件注册】商品下架(弹出框)
			processDown : function(){
				$("button[name='btnDown']").unbind().on('click',function(){
					var idVal = $(this).attr("value");
					var strVirtualShopId =  $(this).attr("tagid");
					var itemID = $(this).attr("itemid");
					var updateParam = {
							"id" : idVal,
							"itemID" : itemID,
							"spstatus":1,
							"virtualShopId" : strVirtualShopId
						};
					
					//弹出框
					$("#processDown").dialog({
						show : true,
						modal : true,
						title:"商品下架",
						width:"370",
						buttons : {
							'下架':function(){
								ajax.processVoilate(updateParam,function(response){
									if(response.retCode == 200){
										$('#porpDivMsg').dialog({
											title : '系统提示',
											modal : true,
											show : true,
											open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>下架成功!</center>"); },
											buttons : {
											 '确定':function(){
											 	report.queryParam(1);
												report.bindTable(queryList);
												$(this).dialog("destroy");
												$("#processDown").dialog("destroy");
											 }
											}
										});
									}else{
										$('#porpDivMsg').dialog({
											title : '系统提示',
											modal : true,
											show : true,
											open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>对不起,下架失败!</center>"); },
											buttons : {
											 '确定':function(){
											 	report.queryParam(1);
												report.bindTable(queryList);
											 	$(this).dialog("destroy");
												$("#processDown").dialog("destroy");
											 }
											}
										});
									}
								});
							},
							'取消':function(){
								$(this).dialog('destroy');
							}
						}
					});
					
				});
			},
			processSetp : function(){
				$("button[name='btnProcess']").unbind().on('click',function(){
					var idVal = $(this).attr("value");
					var strVirtualShopId =  $(this).attr("tagid");
					var itemID = $(this).attr("itemid");
					$("#porpDiv").dialog({
						title : '举报处理',
						modal : true,
						show : true,
						open : function () {
							$(".ui-dialog-titlebar-close").click(function(){
								$("#txtProcessType").val("");
							　　$("#txtProcessType").find("option:selected").text();
								$("#content").val("");
								$("#porpDiv").dialog("destroy");
							});
						},
						buttons : {
						'处理':function(){
							var checkType = $("#txtProcessType").val();
							var txtCheckType = $("#txtProcessType").find("option:selected").text();
							var content = $("#content").val();
							if(checkType == ''){
								tablePoint.tablePoint("请选择处理类型！");
							}
							if(content == ''){
								tablePoint.tablePoint("请填写处理描述! ");
							}
							if(checkType != '' && content != ''){
								var updateParam = {
									"id" : idVal,
									"itemID" : itemID,
									"virtualShopId" : strVirtualShopId,
									"status":2,
									"type" : 2,
									"processType" : txtCheckType,//文本值
									"remark" : content
								};
								ajax.processComplaints(updateParam,function(response){
									if(response.retCode == 200){
										$('#porpDivMsg').dialog({
											title : '系统提示',
											modal : true,
											show : true,
											open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>处理成功!</center>"); },
											buttons : {
											 '确定':function(){
												$("#txtProcessType").val("");
											　　$("#txtProcessType").find("option:selected").text();
												$("#content").val("");
												$(this).dialog("destroy");
												$("#porpDiv").dialog("destroy");
											 	report.queryParam(1);
												report.bindTable(queryList);
											 }
											}
										});
										}else if(response.retCode == 201){
											$('#porpDivMsg').dialog({
												title : '系统提示',
												modal : true,
												show : true,
												open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>对不起,请求失败!</center>"); },
												buttons : {
												 '确定':function(){
													$("#txtProcessType").val("");
												　　$("#txtProcessType").find("option:selected").text();
													$("#content").val("");
												 	$(this).dialog("destroy");
													$("#porpDiv").dialog("destroy");
												 }
											}
										});
										}else if(response.retCode == 212){
											$('#porpDivMsg').dialog({
												title : '系统提示',
												modal : true,
												show : true,
												open : function() { $("#porpDivMsg").html("<center style='margin-top:20px;color:blue;font-size:14px;'>登录已过期,请重新登录,谢谢!</center>"); },
												buttons : {
												 '确定':function(){
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
			},
			selectCocombox : function(){
				ajax.getDictionarysByType(function(response){
					var html="<option value=''>-请选择-</option>";
					$.each(response.data,function(i,value){
						html+="<option value='"+value.value+"'>"+value.name+"</option>"
					})
					$("#txtProcessType").html(html);
				});
			}
	}
	return report;
});