define(["hsec_shareOrdersDat/shareOrder"
        ,"text!hsec_shareOrdersTpl/gridMain.html"
        ,"text!hsec_shareOrdersTpl/shareOrderList.html"
        ,"text!hsec_shareOrdersTpl/shareOrderSearch.html"
        ,"hsec_tablePointSrc/tablePoint"
        ,"hsec_orderFoodSrc/function"
        ],function(ajaxRequest,gridMainTpl,shareOrderListTpl,shareOrderSearchTpl,tablePoint){
	var selectParam={"currentPageIndex":1,"eachPageSize":20};
	var https = "http://192.168.229.31:9099/v1/tfs/";
	var gridObj;
	var shop ={
		queryParam : function(currentPageIndex){
			selectParam={};
			selectParam["orderId"]= $.trim($("#orderId").val());
			selectParam["itemName"]=$.trim($("#itemName").val());
			if(''!=$.trim($("#beginPrice").val())&& null!=$.trim($("#beginPrice").val())){
				selectParam["beginPrice"]=$("#beginPrice").val();
			}
			if(''!=$.trim($("#endPrice").val())&& null!=$.trim($("#endPrice").val())){
				selectParam["endPrice"]=$("#endPrice").val();
			}
			selectParam["shopId"]=$.trim($("#shopId").val())!=''?$.trim($("#shopId").val()):'';
			selectParam["userName"]=$.trim($("#userName").val());
			selectParam["cnt"]=$.trim($("#cnt").val());
			var beginTime = $.trim($("#beginTime").val());
			var endTime = $.trim($("#endTime").val());
			if(beginTime != '' && beginTime != null){
				selectParam["beginTime"]=new Date(beginTime);
			}
			if(endTime != '' && endTime != null){
				var d = new Date(endTime);
				d.setDate(d.getDate()+1); 
				selectParam["endTime"] = d;
			}
			return selectParam;
		},
		//入口
		bindData : function(){
			selectParam={};
			$("#busibox").html(_.template(gridMainTpl,selectParam));
			//loadDatePicker("#beginTime","#endTime");
			/**日期控件**/
			$("#beginTime").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate(),
				onClose : function(selectedDate){
					$("#endTime").datepicker( "option", "minDate", selectedDate );
				}
			});
			$("#endTime").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate(),
				onClose : function(selectedDate){
					$("#beginTime").datepicker( "option", "maxDate", selectedDate);
				}
			});
			/**日期控件**/
			shop.loadBsGridData(shop.queryParam(1));
		},
		loadBsGridData : function(queryParam){
			$(function(){
				gridObj = $.fn.bsgrid.init('shareGridMain',{
					url:{url:comm.UrlList["listShareOrderByParam"],domain:"sportal"},// comm.domainList["sportal"]+comm.UrlList["listShareOrderByParam"],
					pageSizeSelect: true,
	                pageSize: 20,
	                otherParames:$.param(queryParam),
	                stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					operate: {
						add: function(record, rowIndex, colIndex, options){
							switch (colIndex){
								case 0 :
								var sTpl = '<dl><h6><%=obj.bt%></h6><div class="tit_img tr_img" style="margin-left:-5px;"><div class="td_img"><div class="inner_img clearfix">'
							        sTpl += '	<%$.each(obj.picUrls.split(","),function(k,v){%>'
							        sTpl += '	<img  src="'+https+'<%=v%>" width="68" height="68"/>'
							        sTpl += '  <%})%>'
									sTpl += '  	</div></div><div class="shfw_arrow fl shfw_arrow_left"  style="left:-0px;top:0px;">&lt;</div><div class="shfw_arrow fr shfw_arrow_right"  style="left: 230px;top:0px;" >&gt;</div></div></dl>'
									break;
								case 3 :
									var sTpl = '<dl><dd><span class="tit">商品名称：</span><span style="width: 176px;vertical-align: top;display: inline-block;word-wrap: break-word;"><%=obj.spmc%><span></dd><dd><span class="tit"><img src="resources/img/price.png" width="20" style="vertical-align:middle;">价格：</span><%=obj.jg%></dd><dd><span class="tit">订单编号：</span><%=obj.ddbh%></dd><dd><span class="tit">订单日期：</span><%=obj.ddrq%></dd><dd><span class="tit">昵称：</span><%=obj.nc%></dd></dl>';
									break;
								default :
									break;
							}
							return _.template(sTpl, {
								bt: gridObj.getRecordIndexValue(record, 'title'),//标题
								picUrls: gridObj.getRecordIndexValue(record, 'picUrls'),//图片
								spmc: gridObj.getRecordIndexValue(record, 'itemName'),//商品名称：123
								jg: gridObj.getRecordIndexValue(record, 'price'),//价格：123.00
								ddbh: gridObj.getRecordIndexValue(record, 'orderId'),//订单编号：12609299404145664
								ddrq: gridObj.getRecordIndexValue(record, 'buyTime'),//订单日期：2015-11-21 16:50:01
								nc: gridObj.getRecordIndexValue(record, 'userName')//昵称：06186010001
							});
						}
					},
					complete : function(e,o){
						var other = eval("("+o.responseText+")").orther;
						if (other){
							https = other.httpUrl;
						}
						
						//查询事件
						shop.bindHeadClick();
						shop.bindClick();
					}
				});
			});
		},
		bindHeadClick : function(){
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

			$("#btnSearch").unbind().on('click',function(){
				if(parseFloat($("#beginPrice").val()) > parseFloat($("#endPrice").val())){
					tablePoint.tablePoint("区间起始价格不能大于结束价格");
					return;
				}else{
					shop.loadBsGridData(shop.queryParam(1));
				}
			})
			
			//下拉列表框，关联实体店
			$("#shopId").unbind().on("change",function(){
		    	$(this).attr("title",$(this).find("option:selected").text());
			})

		},
		bindClick : function(){
			$(".tr_img").hover(
						function(){$(this).find('.shfw_arrow_left, .shfw_arrow_right').stop().fadeIn(100)},
						function(){$(this).find('.shfw_arrow_left, .shfw_arrow_right').stop().fadeOut(100)}
						)
						function viewImg(){
							var n=1;
							
						$(".shfw_arrow_right").off().on('click',function(){
							
							var imgWarp=$(this).siblings(".td_img").children(".inner_img"),
							imgNum=imgWarp.children("img").size(),
							visibleNum=parseInt($(this).siblings(".td_img").width()/73);
								imgWarp.width(imgNum*75+'px');
								n=n<=imgNum-visibleNum?n:n-1;
								imgWarp.animate({left:-75*n+"px"},100);
								n+=1;
								})	
							$(".shfw_arrow_left").off().on('click',function(){
								var imgWarp=$(this).siblings(".td_img").children(".inner_img")
								n=n==1?2:n;
								imgWarp.animate({left:-75*(n-2)+"px"},100);
								n-=1;
						
								})
							}
							viewImg();

						  
							//商品切换
							var goodNum=$(".goods_detail dl").length;
							$(".goods_detail dl").hide().eq(0).show();
							if(goodNum==0){$(".page").hide();}
							else{
								var pHtml=""
								for(var i=1;i<=goodNum;i++){
									pHtml+="<i>"+i+"</i>";
									}
								$(".page").html(pHtml);
								};
							$(".page i").on('click',function(){
								var pIndex=$(this).index();
								$(".goods_detail dl").hide().eq(pIndex).show();
								});   
							
							/*图片点击放大*/
							$('.inner_img img').on('click',function(){
								var arr = new Array();
								$.each($(this).parent().find("img"),function(k,v){
									arr.push($(v).attr("src"));
								})
								tablePoint.imgBig(arr,this);
							});
		}
		
	}
	return shop;
});