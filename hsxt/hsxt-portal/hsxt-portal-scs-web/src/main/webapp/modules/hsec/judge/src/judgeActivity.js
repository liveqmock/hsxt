define(["text!hsec_judgeTpl/lingshouevaluateShop.html",
		"text!hsec_judgeTpl/lsDisplay.html",
        "hsec_judgeSrc/evaluate",
        "hsec_judgeDat/judgeActivity",
        "hsec_tablePointSrc/tablePoint",
        "hsec_tablePointSrc/jquery.charcount",
        "hsec_tablePointSrc/function"],
        function(tpl,sTpl,evaluateJs,ajaxRequest,tablePoint){
	var queryParam = {};
	var shopType = 0;
	var gridObj;
	var judge ={
			//查询条件
			queryData : function(currentPageIndex) {
				  var itemName = $("#itemName").val();
				  var judge = $("#select").val();
				  var beginTime = $("#dateStart").val();
				  var endTime = $("#dateEnd").val();
				queryParam = {};
				queryParam["itemName"] = itemName;
				if(judge != '' && judge != null){
					queryParam["judge"] = judge;
				}
				if(beginTime != '' && beginTime != null){
					/*queryParam["dateStart"] = new Date(beginTime);*/
					queryParam["dateStart"] = beginTime; /*modify by kuangrb 2016-05-17*/
				}
				if(endTime != '' && endTime != null){
					/*var d = new Date(endTime);
					d.setDate(d.getDate()+1); */
					queryParam["dateEnd"] = endTime;/*modify by kuangrb 2016-05-17*/
				}
				queryParam["eachPageSize"] = 20;
			},
			bindData : function(){
			ajaxRequest.findSalerShops(null, function(response){
				$("#busibox").html(_.template(tpl, response.data));
				//loadDatePicker("#dateStart","#dateEnd");
				/**日期控件**/
				$("#dateStart").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$("#dateEnd").datepicker( "option", "minDate", selectedDate);
					}
				});
				$("#dateEnd").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$("#dateStart").datepicker( "option", "maxDate", selectedDate);
					}
				});
				/**日期控件**/
				judge.bindItemList();
			})
		   },
		   //数据页面
		   bindItemList : function(){
		   		judge.queryData(1);
		   		judge.loadGridData(queryParam);
		   },
		   loadGridData : function(queryParam){
		   		gridObj = $.fn.bsgrid.init('judge_ls',{
		   			url : {url:comm.UrlList['findJudges'],domain:"scs"},
		   			otherParames:$.param(queryParam),
		   			pageSize : 20,
		   			displayBlankRows:false,
		   			stripeRows:true,
		   			lineWrap:true,
		   			//渲染单元格数据执行方法, 不论此单元格是否为空单元格(即无数据渲染)
		   			additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
		   				/*data = gridObj.getRecord(rowIndex);
		   				sTpl = '<%try{%><div class="pllb_content myTable">'
						sTpl += '   <ul>'
						sTpl += '   <li class="pllb_contentli" style="width: 9%">'
						sTpl += '   <%for(var i = obj.score;i>0;i--){ %>'
						sTpl += '   	*'
						sTpl += '   <%}%>'
						sTpl += '   </li>'
						sTpl += '   <li class="pllb_contentli" style="width: 14%;word-break: break-all"><%=obj.tag%></li>'
						sTpl += '   <li class="pllb_contentli clearfix reply_cont" style="width: 30%">   '
						sTpl += '   <ul class="">'
						sTpl += '     <li class="pllb_liboeder"><div style="float: left;">【评价】</div><%=obj.content!=""?obj.content:"评价方未及时作出评价时，系统默认好评！"%><br />【<%=obj.commentTime%>】</li>'
						sTpl += '     <%if(obj.reply!=null && obj.reply!=""){%>'
						sTpl += '     <li class="pllb_liboeder tcr_red"><div style="float: left;">【回复】</div><%=obj.reply.reply%><br />【<%=obj.reply.replyTime%>】</li>'
						sTpl += '     <%}%>'
						sTpl += '      <%if(obj.appendComment!=null && obj.appendComment!=""){%>'
						sTpl += '     <li class="pllb_liboeder last"><div style="float: left;">【追评】</div><%=obj.appendComment%><br />【<%=obj.appendCommentTime%>】</li>'
						sTpl += '  	  <%}%>'
						sTpl += '   </ul>'
						sTpl += '   <%if(obj.reply==null){%>'
						sTpl += '   <form class="form1">'
						sTpl += '    <div class="pllb_textareabox" >'
						sTpl += '       <div class="reply_arrow">▲</div>'
						sTpl += '       <input type="hidden" name="conmentID" class="conmentID" value="<%=obj.id%>"/>'
						sTpl += '      <textarea name="" cols="" rows="" name="reply" class="pllb_textarea" placeholder="请输入……"></textarea>'
						sTpl += '      <div class="reply" style="float: right;position: robjative;"><input type="button" name="button"  value="发送回复" class="btn_bg submit submitYes" /></div>'
						sTpl += '    </div>'
						sTpl += '   </form>'
						sTpl += '   <%}%>'
						sTpl += '   </li>'
						sTpl += '   <li class="pllb_contentli" style="width: 24%">'
						sTpl += '   <ul>'
						sTpl += '   <li class="pllb_spxxli">商品名称：<%=obj.itemName%></li>'
						sTpl += '   <li class="pllb_spxxli tcr_red"><img src="resources/img/price.png" width="20" style="vertical-align:middle;">价格：<%=comm.formatMoneyNumber(obj.price)%></li>'
						sTpl += '   <li class="pllb_spxxli">订单编号：<%=obj.orderId%></li>'
						sTpl += '   <li class="pllb_spxxli">订单日期：<%=obj.buyTime%></li>'
						sTpl += '   <li class="pllb_spxxli"><%if(obj.userName != null && obj.userName != ""){%>昵称：<%=obj.userName%><%}%></li>'
						sTpl += '   <li class="pllb_spxxli">营业点：<%=obj.shopName%></li>'
						sTpl += '   </ul>'
						sTpl += '   </li>'
						sTpl += '    <li class="pllb_contentli mt4" style="width: 15%">'
						sTpl += '   <%if(obj.delStatus == 0){%>'
						sTpl += '     <input name="judgeDel" delId="<%=obj.id%>" type="button" value="删除" class=" btn_bg submit btn_reply"/>'
						sTpl += '    <%}else{%>'
						sTpl += '	 <button type="button" class="btn-search ml10 red"><b>已删除</b></button>'
						sTpl += '	 <%}%>'
						sTpl += '    </li>'
						sTpl += '   </ul>'
						sTpl += '</div><%}catch(e){}%>';*/
						trObj.find('td').attr('colspan', '5').html(_.template(sTpl,record));
		   			},
		   			complete : function(e,o){
		   				//当没有数据时合并自定义单元格
		   				if(gridObj.getAllRecords()){
							$("#gridMain").find("tbody tr td").attr('colspan', '5');
						}
						judge.judgeDel();
						judge.appendCommentDel();
		   				judge.bindHtmlClick();
		   				//评分查询
						$("#select").change(function(){
							$("button[name='judgeQuery']").click();
						});
		   			}
		   		});
		   },
		   judgeDel : function(){
				$("input[type='button'][name='judgeDel']").click(function(){
					//获取评价ID
					var judgeId = $( this ).attr("delId");
					$("#dialog-confirm").dialog({
						show: true,
						modal: true,
						title:"提示信息",
						width:"400",
						buttons: {
							确定: function() {
								//删除
								ajaxRequest.deleteJudge({"judgeId":judgeId}, function(response){
									if(response["retCode"] == 200){
										tablePoint.tablePoint("评价删除成功!");
										$( "#dialog-confirm" ).dialog("destroy");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}else{
										tablePoint.tablePoint("评价删除失败(" + response["msg"] + ")!")
									}
									//开启完成,刷新列表
									$("button[name='judgeQuery']").click();
								});
							},
							取消: function() {
								$( "#dialog-confirm" ).dialog("destroy");
								$("button[name='judgeQuery']").click();
							}		
						}
					});
				});
			},
			appendCommentDel : function(){
				$("input[type='button'][name='appendCommentDel']").click(function(){
					//获取评价ID
					var judgeId = $( this ).attr("delId");
					$("#dialog-confirm").dialog({
						show: true,
						modal: true,
						title:"提示信息",
						width:"400",
						buttons: {
							确定: function() {
								//删除
								ajaxRequest.deleteAppendComment({"judgeId":judgeId}, function(response){
									if(response["retCode"] == 200){
										tablePoint.tablePoint("追评删除成功!");
										$( "#dialog-confirm" ).dialog("destroy");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}else{
										tablePoint.tablePoint("追评删除失败(" + response["msg"] + ")!")
									}
									//开启完成,刷新列表
									$("button[name='judgeQuery']").click();
								});
							},
							取消: function() {
								$( "#dialog-confirm" ).dialog("destroy");
								$("button[name='judgeQuery']").click();
							}		
						}
					});
				});
			},
		  bindHtmlClick : function(){
			  $("#judgeQuery").unbind().on('click',function(){
				  judge.queryData(1);
				  judge.loadGridData(queryParam);
			  }) 
		  }
	};
	
	return judge;
});

