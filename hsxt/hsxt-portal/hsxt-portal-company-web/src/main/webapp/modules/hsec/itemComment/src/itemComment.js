define(["hsec_itemCommentDat/itemComment"
        ,"text!hsec_itemCommentTpl/itemComment.html"
        ,"text!hsec_itemCommentTpl/lsDisplay.html"
        ,"hsec_tablePointSrc/tablePoint"
        ,"hsec_itemCommentSrc/evaluate"
        ,"hsec_tablePointSrc/jquery.charcount"
        ,"hsec_tablePointSrc/function"
        ],function(ajax,icTpl,lsTpl,tablePoint,evaluateAjax){
	var queryParam = {"currentPageIndex":1};
	var shopType = 0;
	var shop ={
			//查询条件
			queryData : function(currentPageIndex) {
				  var orderId = $.trim($("#orderId").val());
				  var itemName = $.trim($("#itemName").val());
				  var shopId = $.trim($("#shopId").val());
				  var userName = $.trim($("#userName").val());
				  var itemScore = $("#itemScore").attr("optionvalue");
				  var beginPrice = $.trim($("#beginPrice").val());
				  var endPrice = $.trim($("#endPrice").val());
				  var beginTime = $.trim($("#beginTime").val());
				  var endTime = $.trim($("#endTime").val());
				queryParam = {};
				queryParam["orderId"] = orderId;
				queryParam["itemName"] = itemName;
				if(itemScore != '' && itemScore != null){
					queryParam["itemScore"] = itemScore;
				}
				queryParam["shopId"] = shopId;
				queryParam["userName"] = userName;
				
				if(beginPrice != '' && beginPrice != null){
					queryParam["beginPrice"] = beginPrice;
				}
				if(endPrice != '' && endPrice != null){
					queryParam["endPrice"] = endPrice;
				}
				if(beginTime != '' && beginTime != null){
					queryParam["beginTime"] = new Date(beginTime);
				}
				if(endTime != '' && endTime != null){
					var d = new Date(endTime);
					d.setDate(d.getDate()+1); 
					queryParam["endTime"] = d;
				}
				queryParam["eachPageSize"] = 20;
			},
			//初始化
		selectList : function(){
			var data = [{"id":"5","name":"*****"},{"id":"4","name":"****"},{"id":"3","name":"***"},{"id":"2","name":"**"},{"id":"1","name":"*"}];
			var opts = new Array();
			opts[0] = {name:"全部",value:""};
			$.each(data,function(k,v){
				opts[k+1] = {name:v.name,value:v.id}
			});
			$('#itemScore').selectList({
				options:opts,
				width:115
			});
		},
		bindData : function(){
			ajax.queryStartSalerShop(null, function(response){
				var html = _.template(icTpl, response.data);
				$("#busibox").html(html);
				//loadDatePicker("#beginTime","#endTime");
				/**日期控件**/
				$("#beginTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$( "#endTime" ).datepicker( "option", "minDate", selectedDate );
					}
				});
				$("#endTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$( "#beginTime" ).datepicker( "option", "maxDate", selectedDate );
					}
				});
				/**日期控件**/
				shop.selectList();
				shop.bindItemList();
				shop.bindHtmlClick();
			})
		   },
		   //数据页面
		   bindItemList : function(){
		   		shop.queryData(1);
		   		shop.loadGridData(queryParam);
		   },
		   loadGridData : function(queryParam){
		   		var gridObj = $.fn.bsgrid.init('itemCommentGridMain',{
		   			url : {url:comm.UrlList['getCommentsByShopGrid'],domain:"sportal"},//comm.domainList['sportal'] + comm.UrlList['getCommentsByShopGrid'],
		   			otherParames:$.param(queryParam),
		   			pageSize : 20,
		   			displayBlankRows:false,
		   			stripeRows:true,
		   			//渲染单元格数据执行方法, 不论此单元格是否为空单元格(即无数据渲染)
		   			additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
		   				/*data = gridObj.getRecord(rowIndex);
		   				sTpl = '<%try{%><div class="pllb_content myTable">'
						sTpl += '   <ul>'
						sTpl += '   <li class="pllb_contentli" style="width: 10%">'
						sTpl += '   <%for(var i = obj.score;i>0;i--){ %>'
						sTpl += '   	*'
						sTpl += '   <%}%>'
						sTpl += '   </li>'
						sTpl += '   <li class="pllb_contentli" style="width: 15%;word-break: break-all"><%=obj.tag%></li>'
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
						sTpl += '   <li class="pllb_contentli" style="width: 25%">'
						sTpl += '   <ul>'
						sTpl += '   <li class="pllb_spxxli">商品名称：<span style="width: 150px;word-wrap: break-word;display: inline-block;vertical-align: top;"><%=obj.itemName%><span></li>'
						sTpl += '   <li class="pllb_spxxli tcr_red"><img src="resources/img/price.png" width="20" style="vertical-align:middle;">价格：<%=comm.formatMoneyNumber(obj.price)%></li>'
						sTpl += '   <li class="pllb_spxxli">订单编号：<%=obj.orderId%></li>'
						sTpl += '   <li class="pllb_spxxli">订单日期：<%=obj.buyTime%></li>'
						sTpl += '   <li class="pllb_spxxli"><%if(obj.userName != null && obj.userName != ""){%>昵称：<%=obj.userName%><%}%></li>'
						sTpl += '   <li class="pllb_spxxli red"><%if(obj.dobjStatus == 1){%>评价已删除<%}%></li>'
						sTpl += '   </ul>'
						sTpl += '   </li>'
						sTpl += '   <%if(obj.reply==null){%>'
						sTpl += '    <li class="pllb_contentli mt4">'
						sTpl += '     <input id="" type="button" value="回复" class="btn_reply btn_bg submit">'
						sTpl += '    </li>'
						sTpl += '    <%}%>'
						sTpl += '   </ul>'
						sTpl += '</div><%}catch(e){}%>';
						trObj.find('td').attr('colspan', '5').html(_.template(sTpl,data));*/
						trObj.find('td').attr('colspan', '5').html(_.template(lsTpl,record));
		   			},
		   			complete : function(e,o){
		   				//当没有数据时合并自定义单元格
		   				if(gridObj.getAllRecords()){
							$("#gridMain").find("tbody tr td").attr('colspan', '5');
						}
		   				var other = eval("("+o.responseText+")").orther;
		   			
		   				if (other){
		   					shopType =  other.shopType;
		   				}  
		   				shop.bindInitClick();
		   			}
		   		});
		   },
		   //初始化功能
		   bindInitClick : function(){
				$('.pllb_textarea').charcount({
					maxLength: 200,
					preventOverage: false,
					position : 'after'
				});
				//编号输入验证
				$("#orderId").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				//下拉列表框，关联实体店
				$("#shopId").unbind().on("change",function(){
			    	$(this).attr("title",$(this).find("option:selected").text());
				})

			$(".btn_reply").unbind().on('click',function(){
				var warp=$(this).parents(".pllb_content > ul");
				if(warp.find(".pllb_textareabox").length==0){
					warp.find(".reply_cont").append($(".pllb_textareabox"));
					}
				$(".pllb_textareabox").hide()
				var ulHeight=warp.height();
				$(this).parents("div .pllb_content").find(".pllb_textareabox").show(500);
				});
			
			  	//回复评价
			 	$(".submitYes").unbind().on('click',function(){
			 	   var parent = $(this).parents("div .pllb_textareabox");
				   var conmentId = parent.find(".conmentID").val();
	    		   var replyValue = parent.find(".pllb_textarea").val();
	    		   if(replyValue.length > 200){
	    			   tablePoint.tablePoint("回复内容长度不超过200个字符");
	    			   return false;
	    		   }
	    		   if($.trim(replyValue)!='' && replyValue!=null){
	    			   var param = {};
	    			   param["conmentID"] = conmentId;
	    			   param["replys"] = replyValue;
		    		   ajax.replyBuyerComment($.param(param),function(response){
		    			   if(response.retCode == 200){
		    				   $(".pllb_textareabox").hide(500);
		    				   shop.bindItemList($("#divitem").scrollTop(),1);
		    			   }else if(response.retCode == "206"){
								tablePoint.tablePoint("评价信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再回复！");
		    			   }else{
		    				   tablePoint.tablePoint("回复失败,请重试！");
		    			   }
					   })  
	    		   }else{
	    			   $(".pllb_textareabox").hide(500);
	    		   }
			 });
		  },
		  bindHtmlClick : function(){
			  $("#query").unbind().on('click',function(){
				  shop.queryData(1);
				  shop.bindItemList();
			  }) 
		  }
       }
	return shop;
});