/**
 * @author zhanghh
 * @version v1.0.0.1
 * @description 服务开通
 * @date 2015/10/20 11:48
 */
define(["hsec_tablePointSrc/tablePoint"
		,"hsec_provisioningFoodDat/provisioning"
		,"text!hsec_provisioningFoodTpl/tab.html"
		,"text!hsec_provisioningFoodTpl/gridMain.html"
		,"text!hsec_provisioningFoodTpl/headProvisioning.html"
		,"text!hsec_provisioningFoodTpl/bodyProvisioning.html"]
		,function(tablePoint,ajaxRequest,tabTpl,gridMainTpl,headTpl,bodyTpl){
	var param = {};
	var isFrozen = false;
	var provisioning = {
		//查询参数
		queryParam : function(num){
			param={"currentPageIndex":num!=null?num:1,"eachPageSize":20};
			return param;
		},
		//页面初始化
		bindData : function(){
			$("#busibox").html(_.template(gridMainTpl));
			var param = provisioning.queryParam(1);
			provisioning.loadBsGridData(param);
		},
		//绑定服务数据
		loadBsGridData : function(queryParam){
			var gridObj;
			$(function(){
				gridObj = $.fn.bsgrid.init('serviceGridMain',{
					url : {url:comm.UrlList["provisioningFood"],domain:'sportal'},//comm.domainList["sportal"]+comm.UrlList["provisioningFood"],
					pageSizeSelect:false,//是否显示分页大小选择下拉框
					pageSize:20,
					stripeRows: true,//隔行变色, 默认值false
					displayBlankRows:false,//是否显示空白行, 默认值true
					operate:{
						add:function(record, rowIndex, colIndex, options){
							var sTpl = "";
							var id = record.sysSer.id;
							var name = record.sysSer.name;
							var desc = record.sysSer.desc;
							var chargingRules = record.sysSer.chargingRules;
							var rulesDesc = record.sysSer.rulesDesc;
							var status = record.status;
							switch(colIndex){//列
								case 0 : 
									sTpl = name;
									break;
								case 1 : 
									sTpl = desc;
									break;
								case 2 : 
									if(status=="0"){
										sTpl = '未开通';
									}else if(status=="1"){
										sTpl = '已开通';
									} else if(status=="2"){
										sTpl = '暂停';
									}
									break;
								case 3 : 
									if(status == "0" || status == "2" ){
										if(!isFrozen){//停止积分活动屏蔽按钮
											sTpl = '<a class="dlg2 '+id+'shopname" href="javascript:void(0)" data-id="'+id+'" data-month="'+chargingRules+'" data-desc="'+rulesDesc+'" data-name="'+name+'">开通</a>';
										}
							          }else{ 
							           		if(name != '外卖服务费'){     
							           			sTpl = '<a class="dlg1" href="javascript:void(0)" data-id="'+id+'">关闭</a>';  
							           		}
							          }
									break;
								default :
									break;
							}//end switch;
							return sTpl;
						}//end add;
					},//end operate;
					complete : function(options, XMLHttpRequest, textStatus){//Ajax请求完成后执行方法, 参数: options, XMLHttpRequest, textStatus
						var other= eval("("+XMLHttpRequest.responseText+")").orther,shopName='';
						if (other){
							shopName = other.shopName;
						}
					 
						isFrozen = other.status == '7' ? true : false;
						 
						//获取所有的行数据
					    var obj = gridObj.getAllRecords();
					    if(obj.length > 0){
							$.each(obj,function(k,v){
								$("."+v.id+"shopname").attr("data-shop-name",shopName);
							});
						}
						//开通
						provisioning.openServiceClick();
						//关闭
						provisioning.closedServiceClick();
					}
				});//end gridObj;
			});//end $;
		},
		//开通服务
		openServiceClick : function(){
			$('.dlg2').unbind().on('click',function(){
				/**
				 * closest会首先检查当前元素是否匹配，如果匹配则直接返回元素本身。
				 * 如果不匹配则向上查找父元素，一层一层往上，直到找到匹配选择器的元素。
				 * 如果什么都没找到则返回一个空的jQuery对象。
				 * closest和parents的主要区别是：//alert($(this).parent().parent("tr").attr("operid"));
				 * 1，前者从当前元素开始匹配寻找，后者从父元素开始匹配寻找；
				 * 2，前者逐级向上查找，直到发现匹配的元素后 就停止了，后者一直向上查找直到根元素，然后把这些元素放进一个临时集合中，再用给定的选择器表达式去过滤；
				 * 3，前者返回0或1个元素，后者可能包含0 个，1个，或者多个元素。
				 */
				//主键ID
				//var id = $(this).closest("tr").attr("data-id");
				//服务ID
				//var sysSerId = $(this).closest("tr").attr("data-sys-ser-id");
				//网上商城ID
				//var vShopId = $(this).closest("tr").attr("data-vshop-id");
				var id = $(this).attr("data-id");
				var param = {"sysSerId":id};
				//var descrption = $(this).closest("td").attr("data-desc");
				//var serviceName = $(this).closest("td").attr("data-name");
				//var vshopName = $(this).closest("td").attr("data-shop-name");
				var descrption = $(this).attr("data-desc");
				var serviceName = $(this).attr("data-name");
				var vshopName = $(this).attr("data-shop-name");
				$("#dlg2").dialog({
					title:"开通服务",
					show:true,
					modal:true,
					width:640,
					open:function(){
						var htmlText = "";
						htmlText += "<table class='detail-table' width='100%' border='0' cellspacing='0' cellpadding='0'>";
    					htmlText += "<tbody><tr>";
      					htmlText += "<td align='right' width='30%' class='blue'>服务名称：</td>";
      					htmlText += "<td>"+serviceName+"</td>";
    					htmlText += "</tr>";
   						htmlText += " <tr class='bdr-top'>";
     					htmlText += " <td align='right' valign='top' class='blue'>计费标准和规则：</td>";
     					htmlText += " <td>"+descrption+"</td>";
    					htmlText += "</tr>";
//    					htmlText += "<tr class='bdr-top'>";   //Modified by zhanghh @date:2015-12-10
//      				htmlText += "<td align='right' class='blue'>开通商城：</td>";
//     					htmlText += "<td>"+vshopName+"</td>";
//    					htmlText += "</tr>";
   						htmlText += " <tr class='bdr-top white'>";
    					htmlText += "  <td colspan='2'></td>";
   						htmlText += " </tr>";
 						htmlText += " </tbody></table>";
 						htmlText += " <div class='tc'><label><input type='checkbox'>已阅读并同意</label></div>";
						$("#dlg2").html(htmlText);
					},
					buttons:{
						"立即开通":function(){
							if($("input:checkbox").is(':checked')){
								$("#dlg3").dialog({
									title:"确认开通服务提示",
									show:true,
									modal:true,
									width:450,
									buttons:{
										"同意开通":function(){
											ajaxRequest.openService(param,function(response){
												if(response.data.retCode == 200){
													$("#dlg3").dialog("destroy");
													tablePoint.tablePoint("开通外卖服务成功");
													var param = provisioning.queryParam(1);
													provisioning.loadBsGridData(param);
												}else if(response.data.retCode == 205){
													tablePoint.tablePoint("开通外卖服务失败，网上商城服务已暂停!");
													$("#dlg3").dialog("destroy");
												}else if(response.data.retCode == 589){
													tablePoint.tablePoint("开通外卖服务失败，互生币账户余额不足！");
													$("#dlg3").dialog("destroy");
												}/*else if(response.data.retCode == 201){
													tablePoint.tablePoint("开通外卖服务失败");
													$("#dlg3").dialog("destroy");
												}*/else{
													/**
													 * modifly by zhanghh 2016-01-29
													 * desc:开通服务失败时，后台调用交易中心返回的是N，这里做个显示处理。
													 */
													if("N"==response.data.msg){
														tablePoint.tablePoint("开通外卖服务失败");
													}else{
														tablePoint.tablePoint("开通外卖服务失败");
													}
													$("#dlg3").dialog("destroy");
												}
											});
										},
										"取消":function(){
											$(this).dialog("destroy");
										}
									}
								});
								$(this).dialog("destroy");
							}else{
								tablePoint.tablePoint("必须同意协议才能开通此服务!");
							}
						},
						"暂不开通":function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
		},
		//关闭服务
		closedServiceClick : function(){
			$('.dlg1').unbind().on('click',function(){
				//主键ID
				//var id = $(this).closest("tr").attr("data-id");
				//服务ID
				//var sysSerId = $(this).closest("tr").attr("data-sys-ser-id");
				//网上商城ID
				//var vShopId = $(this).closest("tr").attr("data-vshop-id");
				var id = $(this).attr("data-id");
				var param = {"sysSerId":id};
				$("#dlg1").dialog({
					title:"操作确认",
					show:true,
					modal:true,
					width:420,
					open:function(){	
					},
					buttons:{
						"确定":function(){
							ajaxRequest.closedService(param,function(response){
								if(response.data.retCode == 200){
									tablePoint.tablePoint("关闭外卖服务成功",function(){
										var param = provisioning.queryParam(1);
										provisioning.loadBsGridData(param);
									});//"外卖服务关闭成功"
								}else{
									tablePoint.tablePoint(response.data.msg);
									var param = provisioning.queryParam(1);
									provisioning.loadBsGridData(param);
								}
								$(this).dialog("destroy");
							});
							$(this).dialog("destroy");
						},
						"取消":function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
		},
		//分页
		pageClick : function(){
			$(".page-nact").bind().on('click',function(){
		    	var num = $(this).attr("page");
		    	if(num == '' || num == null){
		    		return false;
		    	}
		    	var param = provisioning.queryParam(num);
		    	provisioning.bindServiceData(param);
		    });
		    //分页输入
	    	   $('#pageInput').bind().on('keypress',function(event){
	            if(event.keyCode == "13")    
	            {
	            	var num = $(this).val();
	            	var totalPage = Number($(this).attr("totalPage"));
	            	if(num > totalPage  || num <= 0 || isNaN(num)){
	            		tablePoint.tablePoint("输入范围错误!请再次输入!");
	            		return false;
	            	}else{
	            		var param = provisioning.queryParam(num);
		    			provisioning.bindServiceData(param);
	            	}
	            }
	        });  
		}
	};
	return provisioning;
});