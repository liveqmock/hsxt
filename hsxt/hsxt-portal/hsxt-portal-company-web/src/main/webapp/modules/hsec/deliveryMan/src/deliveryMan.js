define([ "text!hsec_deliveryManTpl/tab.html"
 		 ,"text!hsec_deliveryManTpl/gridMain.html"
 		 ,"text!hsec_deliveryManTpl/deliveryManHead.html"
         ,"text!hsec_deliveryManTpl/deliveryManList.html"
         ,"hsec_deliveryManDat/deliveryMan"
         ,"text!hsec_deliveryManTpl/deliveryManEdit.html"
         ,"text!hsec_deliveryManTpl/deliveryManAdd.html"
         ,"text!hsec_deliveryManTpl/shop_dlg.html"
         ,"hsec_deliveryManSrc/deliveryManCheck"
         ,"hsec_tablePointSrc/tablePoint"
         ,"hsec_tablePointSrc/jquery.charcount"
         ], function(tabTpl,gridMainTpl,deliveryManHead,tplList,ajaxRequest,tplEdit,tplAdd,shopAdd,deliveryManCheck,tablePoint,charcount) {
	//送货人照片
	var imgResponse = '';
	var objParam ={"currentPageIndex":1};
	var shop ={
			//分页后台事件
			queryParm : function(currentPageIndex){
			    objParam ={};
			    var name = $.trim($("#name").val());//modifly by zhanghh 2016-05-30:去掉空格
			    var shopName = $.trim($("#shopName").val());
			    if(name != null && name != ""){
			    	objParam["name"] = name;
			    }
			    if(shopName != null && shopName != ""){
			    	objParam["shopName"] = shopName;
			    }
				return objParam;
			},
			//初始化后台事件
			bindData : function() {
				if(imgResponse!=null){
					imgResponse='';
				}
				//加载html
				$("#busibox").html(_.template(gridMainTpl));
				//数据加载
				shop.loadBsGridData(shop.queryParm(1));
				//查询事件
				$('#queryMan').unbind().on('click',function(){
					shop.loadBsGridData(shop.queryParm(1));
				});
			},
			//BsGridData数据加载
			loadBsGridData : function(queryParam){
				var gridObj;
				$(function(){
					gridObj = $.fn.bsgrid.init('deliveryGridMain',{
					url: {url:comm.UrlList["listSalerShopDeliver"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["listSalerShopDeliver"],
					pageSizeSelect: true,
	                pageSize: 20,
	                otherParames:queryParam,
	                stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					operate: {
						/**
						 * record:数据源
						 * rowIndex：行
						 * colIndex：列
						 * options：
						 */
						add: function(record, rowIndex, colIndex, options){
							var sTpl = "";
							var id = record.id;
							var name = record.name;
							var picUrl = record.picUrl;
							var sex = record.sex;
							var phone = record.phone;
							var shopName = record.shopName;
							var remark = record.remark;
							var status = record.status;
							switch (colIndex){
								case 0 :
									sTpl = '<input  class="myDeviler" type="checkbox" value="'+id+'"/>';
									break;
								case 1 :
									sTpl =  '<span style="word-wrap: break-word;word-break: break-all;">'+name+'</span>';
									break;
								case 2 :
									if(picUrl != ''){
										sTpl = '<img  width="68" height="68" class="bigImg '+id+'"/>';
									}else{
										sTpl = '<span style="width:68px;height:68px;display:inline-block;"></span>';
									}
									break;
								case 3 :
									if(sex==0){
										sTpl = '女';
									}else{
										sTpl = '男';
									}
									break;
								case 4 :
									sTpl = '<span style="word-wrap: break-word;word-break: break-all;">'+ phone +'</span>';
									break;
								case 5 :
									sTpl = '<span style="word-wrap: break-word;word-break: break-all;">'+shopName +'</span>' ;
									break;
								case 6 :
									sTpl = '<span style="word-wrap: break-word;word-break: break-all;">'+remark+'</span>';
									break;
								case 7 :
									var statusName = "";
									if(status=="1"){
										statusName = '启用';
									}else if(status=="2"){
										statusName = '禁用';
									}
									sTpl = '<span style="word-wrap: break-word;word-break: break-all;">'+statusName+'</span>';
									break;
								case 8 :
									sTpl = '<a href="javascript:void(0)" id="'+id+'" data-phone="'+ phone +'" data-remark="'+remark+'" class="dlg4">所属营业点</a> | ';
									sTpl += ' <a href="javascript:void(0)" id="'+id+'" class="dlg3">修改</a>';
									sTpl += ' | <a class="dlg1" id="'+id+'" href="javascript:void(0)">删除</a>';
									if(status=="1"){
										sTpl += ' | <a class="dlg5" id="'+id+'" href="javascript:void(0)" data-status="'+status+'" data-remark="'+remark+'">禁用</a>';
									}else if(status=="2"){
										sTpl += ' | <a class="dlg5" id="'+id+'" href="javascript:void(0)" data-status="'+status+'" data-remark="'+remark+'" >启用</a>';
									}
									break;
								default :
									break;
							}
							return sTpl;
							}
						},
						complete : function(e,o){							
							var other = eval("("+o.responseText+")").orther;
							var shopType,https ;
							if (other){
								https = other.httpUrl;
								shopType = other.shopType;
							} 
							
							var obj = gridObj.getAllRecords();
							if(obj.length > 0){
								$.each(obj,function(k,v){
									$("."+v.id).attr("src",https+v.picUrl);//图片动态加载
								});
							}
							shop.bindMainClick();
							shop.bindClick();
						}
					});
				});
			},
			bindMainClick : function(){
				$('#queryMan').unbind().on('click',function(){
					shop.loadBsGridData(shop.queryParm(1));
				});
				//批量删除事件
				$(".batch_del").unbind().on('click',function(){
					var length=$("input[class='myDeviler']:checked").length
					if(length>0){
						$( "#dlg1" ).dialog({
							title:"批量删除",
							show: true,
							modal: true,
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
							确定: function() {
								 var param = new Array();
								 $("input[class='myDeviler']:checked").each(function(index){
									 param[index]=$(this).val()
								 });
								ajaxRequest.batchDeleteDeliver("strDeliverIds="+JSON.stringify(param),function(response){
									shop.loadBsGridData(shop.queryParm(1));
								 })
								$( this ).dialog( "destroy" );
							},
							关闭: function() {
								$( this ).dialog( "destroy" );
								}
						  	}});
					}else{
						$( "#dlg11" ).dialog({
							title:"批量删除",
							show: true,
							modal: true,
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
							关闭: function() {
								$( this ).dialog( "destroy" );
								}
						  	}});
					}
				});
				//添加送货人事件
				$( ".dlg2" ).unbind().on('click',function(){
				   $( "#dlg2" ).dialog({
					title:"添加",
					width:"600",
					show: true,
					modal: true,
					open : function() {
						 $(this).html(tplAdd);
							$("#itemPhotoAdd").unbind().on('change',function(){
								imgUpload("#itemPhotoAdd");
						})
						shop["type"] = 0;
						deliveryManCheck.validate(shop);
						shop.bindInputCheck();
					},
					buttons: {
					保存: function() {
						$("#submitMan").submit();	
						},
					关闭: function() {
						$(this).dialog("destroy");
						}
				  }});	
				});
			},
			bindClick : function(){
				//全选
				tablePoint.checkBoxAll($("#all"),$(".myDeviler[type='checkbox']"));
				
				//删除事件
				$( ".dlg1" ).unbind().on('click',function(){
					var thisEle=$(this);
					function del(cur){cur.closest('tr').remove();}		
				   	$( "#dlg1" ).dialog({
					  title:"删除",
					  show: true,
					  modal: true,
					  close: function() { 
					        $(this).dialog('destroy');
					  },
					  buttons: {
					  	确定: function(){
						  		var param = new Array();
						  		param[0]=thisEle.attr("id")
						  		ajaxRequest.batchDeleteDeliver("strDeliverIds="+JSON.stringify(param),function(response){
						  			shop.loadBsGridData(shop.queryParm(1));
								 })
						  		$( this ).dialog( "destroy" );
					  		},
				  		取消: function(){
					  		$( this ).dialog( "destroy" );
				  		}
					  }
					});	
				});
			
				
				//所属实体店事件
				$( ".dlg4" ).unbind().on('click',function(){
					 var deliverId = $(this).attr("id");
					 var phone = $(this).attr("data-phone");
					 var remark = $(this).attr("data-remark");
					ajaxRequest.getDeliverShops("deliverId="+deliverId,function(response){
						var lstRelatedShopId = response.data.lstRelatedShopId;
						   $( "#dlg4" ).dialog({
								title:"所属营业点",
								width:"870",
								height:"600",
								show: true,
								modal: true,
								open : function(){
										var html = _.template(shopAdd, response.data);
										$('#dlg4').html(html);
										//var dlgridObj = $.fn.bsgrid.init('manShopBind',{});
								},
								buttons: {
								保存: function() {
									var shopIds = "";
									var shopNames = "";
									var shopInput = $('input:radio[name="shop"]');
									if(!shopInput.is(":checked")){
										tablePoint.tablePoint("请选择所属营业点！");//添加验证提示 add by kuangrb 2016-05-27 
										//$(this).dialog( "destroy" );
										return false;
									}else{
										shopIds = $(shopInput).filter(":checked").attr("data-id");
										shopNames = $.trim($(shopInput).filter(":checked").attr("data-name"));
									}
									
									if((shopIds == null || shopIds == "") && (lstRelatedShopId == null || lstRelatedShopId == "")){
										$(this).dialog('destroy');
										return false;
									}
									var param  = {"id":deliverId,"shopId":shopIds,"shopName":shopNames,"phone":phone,"remark":remark};
									ajaxRequest.updateSalerShopDeliver(param,function(resp){
										if(resp.retCode == 200){
											if(shopIds != null && shopIds != ""){
												tablePoint.tablePoint("关联所属营业点操作成功！");
												$('#queryMan').click();//zhanghh 2015-11-12 :　刷新页面
											}else{
												tablePoint.tablePoint("解除所属营业点操作成功！");
												$('#queryMan').click();//zhanghh 2015-11-12 :　刷新页面
											}
										}else{
											tablePoint.tablePoint("所属营业点失败！");
										}
										$('#dlg4').dialog("destroy");
										shop.loadBsGridData(shop.queryParm(1));
									})
									},
								关闭: function() {
										$(this).dialog("destroy");
									}
							  }});	
					})
				});
				
				//修改送货人信息对话框事件
				$( ".dlg3" ).unbind().on('click',function(){
					 $("#itemPhotoUpdate").unbind();
					ajaxRequest.findSalerShopDeliver("deliverId="+$(this).attr("id"),function(response){
						   $( "#dlg2" ).dialog({
							title:"修改",
							width:"600",
							show: true,
							modal: true,
							open : function(){
								var html = _.template(tplEdit, response);
								 $(this).html(html);
									$("#itemPhotoUpdate").change(function(){
										imgUpload("#itemPhotoUpdate");
								})
								shop["type"] = 1;
								deliveryManCheck.validate(shop);	
								shop.bindInputCheck();
							},
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
							保存: function() {
								//shop.bindUpdate();//update by zhanghh 2015-11-12 : 调用修改方法
								$("#submitManEdit").submit();	
							},
							关闭: function() {
								$(this).dialog("destroy");
							}
						  }});
					});
				});
				/**
				$('.bigImg').on('click',function(){
					var arr = new Array();
					tablePoint.imgBig(arr,this);
				});
				*/
				/**add by zhanghh 201605028 添加启用禁用开始*/
				$( ".dlg5" ).unbind().on('click',function(){
					debugger;
					var id = $(this).attr("id");
					var remark = $(this).attr("data-remark");
					var status = $(this).attr("data-status");
					var titleTips = "";
					var updateParam = {"id":id,"remark":remark};
					if(status=="1"){
						titleTips = "你正在执行禁用送货员操作,确定继续吗？";
						updateParam.status = 2;//状态 0删除，1启用  2禁用
						comm.i_confirm(titleTips,function(){
							ajaxRequest.updateSalerShopDeliver(updateParam,function(response){
								if(response.retCode=="200"){
									tablePoint.tablePoint("禁用送货员成功！");
									$("#wsscgl_shy").trigger('click');
								}else{
									tablePoint.tablePoint("禁用送货员失败！");
								}
							});
						});
					}else{
						titleTips = "你正在执行启用送货员操作,确定继续吗？";
						updateParam.status = 1;//状态 0删除，1启用  2禁用
						comm.i_confirm(titleTips,function(){
						ajaxRequest.updateSalerShopDeliver(updateParam,function(response){
								if(response.retCode=="200"){
									tablePoint.tablePoint("启用送货员成功！");
									$("#wsscgl_shy").trigger('click');
								}else{
									tablePoint.tablePoint("启用送货员失败！");
								}
							});
						});
					}
				});
				/**add by zhanghh 201605028 添加启用禁用结束*/
			},
			bindSubmit : function(){
				 var param ={};
				 //判断是事选中性别,如果没有提示。add by zhanghh 20160523 Bug：24827
				if($("input[name='sex']").is(":checked")){
					 param["name"]=$("input[name='name']").val()
				     param["phone"]=$("input[name='phone']").val()
				    
				     if($("input[name='sex']:checked").attr("id")=="female"){
				    	 param["sex"]=0;
				     }else{
				    	 param["sex"]=1;
				     }
				     
				     param["remark"]=$("#remark").val();
				     param["picUrl"]=$(".imgLicense").attr("src");
					 ajaxRequest.saveSalerShopDeliver(param,function(response){
						 if(response.retCode == 200){
							 $("#dlg2").html("");
							 $("#dlg2").dialog("destroy");
							 tablePoint.tablePoint("添加送货员成功!",function(){
							 	shop.loadBsGridData(shop.queryParm(1));
							 });
						 }else if(response.retCode == 206){
							 tablePoint.tablePoint("送货员信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后,添加送货员！");
						 }else if(response.retCode == 203){
							 tablePoint.tablePoint("送货员姓名已存在，请重新输入！");
						 }else{
							 tablePoint.tablePoint("添加送货员失败!"); 
						 }
					 })
			     }else{
			     	tablePoint.tablePoint("请选择送货员性别");
			     }
			},
			bindUpdate : function(){
				 var param ={};
				 param["id"]=$("input[name='deliverId']").val();
				 param["name"]=$("input[name='name']").val()
			     param["phone"]=$("input[name='phone']").val()
			     param["sex"]=$("input[name='sex']:checked").val()
			     param["remark"]=$.trim($("#remark").val())+" ";
				 param["picUrl"]=$(".imgLicense").attr("src");
				ajaxRequest.updateSalerShopDeliver(param,function(response){
				    if(response.retCode == 200){
				    	 $("#dlg2").html("");
				    	 $( "#dlg2" ).dialog("destroy");
						 tablePoint.tablePoint("修改送货员成功!",function(){
						 	shop.loadBsGridData(shop.queryParm(1));
						 });
					 }else if(response.retCode == 206){
						 tablePoint.tablePoint("送货员信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后,修改送货员！");
					 }else if(response.retCode == 203){
						 tablePoint.tablePoint("送货员姓名已存在，请重新输入！");
					 }else{
						 tablePoint.tablePoint("修改送货员失败!"); 
					 }
				})
			},
			bindInputCheck : function(){
				$('#deliveryName').charcount({
					maxLength: 20,
					preventOverage: true,
					position : 'after'
				});
				$('#deliveryPhone').charcount({
					maxLength: 20,
					preventOverage: true,
					position : 'after'
				});
				$('#remark').charcount({
					maxLength: 100,
					preventOverage: true,
					position : 'after'
				});
			}
	}
	
	function imgUpload(id){
        var file = $(id).val();  
        var fileType = file.substring(file.lastIndexOf(".")+1);  
        var size = 0;
        if (document.all){
        	
        }else{
        	size = document.getElementById(id.split("#")[1]).files[0].size;
        	size = tablePoint.formatFileSize(size);
        }
        if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
        	$("#shopImg").html("<br/>请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB.");
            return;  
        }else{
        			tablePoint.bindJiazaiUp("#submitMan", true);
					ajaxRequest.upLoadFile(id.split("#")[1], function(response) {
						imgResponse = response;
						var urlimg,imgName;
						   $.each(eval(response), function(i, items) {
							   urlimg = items.httpUrl;
							   imgName = items.finalAbsFileName;
							   $.each(items.imageNails, function(j, item) {
								   if(item.height=="330" && item.width=="330"){
									   imgName = item.imageName;
								   }
					        });
					});
						   $("#shopImg").html("");
						   $(".imgLicense").attr("src",urlimg+imgName); 
						   tablePoint.bindJiazaiUp("#submitMan", false);
				});
        $(id).replaceWith($(id).parent().html());
    	//图片上传
		$(id).unbind().on('change',function(){
			imgUpload(id);
		})
	}	
};
	return shop;
	
});
