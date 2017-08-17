define([ "hsec_tablePointSrc/tablePoint"
         ,"hsec_itemCatChooseDat/itemCatChoose"
         ,"text!hsec_itemCatChooseTpl/itemAdd/itemInfo.html"
         ,"text!hsec_itemCatChooseTpl/itemUpdate/itemInfo.html"
         ,"text!hsec_itemCatChooseTpl/shippingAdd/shippingAdd.html"
         ,"hsec_itemInfoDat/itemInfo"
         ,"hsec_itemCatChooseSrc/category"
         ,"hsec_itemCatChooseSrc/prop"
         ,"hsec_itemCatChooseSrc/itemInfoCheck"
         ,"hsec_itemInfoFoodSrc/titleBar"
         ,"xheditor_cn"], function(tablePoint,ajaxChoose,addTpl,updateTpl,shippingAddTpl,itemInfo,ajaxCategory,prop,itemInfoCheck,titleBarJs) {
		var itemTypeBool = "";
		var imgDeletearray = new Array();
	var shop = {
			bindData : function() {
				imgDeletearray.length = 0;
				itemTypeBool = "add";
				itemInfo.initItemInfoNew({}, function(response) {
					var html = _.template(addTpl, response);
					$('#busibox').html(html);
					shop.pageFun();
					shop.pageNextFun();
					shop.bindClick();
				})
			},
			bindUpdate : function(itemId){
				imgDeletearray.length = 0;
				itemTypeBool = "update";
				var param = {"itemId":itemId};
				itemInfo.getDetailById(param, function(response) {
					$('#busibox').html(_.template(updateTpl, response));
					shop.pageFun(response.data.ItemDetailInfo.detailsInfo);
					shop.pageNextFun();
					shop.bindClick();
					if(response.data.ItemInfo.status != -5){
						prop.bindUpdate(response);
					}
			  })
			},
			//页面效果处理
			pageFun : function(editorStr){
				var tool = "GStart,GEnd,BtnBr,Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor," +
				"BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Img,Flash,Media,Emot,Table,|,Source,Preview,Print,Fullscreen,skin,layerShadow,showBlocktag";
				var editor = $('#xheditor').xheditor({tools:tool,upLinkUrl:"",upLinkExt:"zip,rar,txt",upImgUrl:comm.domainList.sportal + "files/xhupload?isIE=false&fileType=image",
				upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"",upFlashExt:"swf",
				upMediaUrl:"",upMediaExt:"wmv,avi,wma,mp3,mid"});
				if(editorStr != null && editorStr != ""){
					editor.appendHTML(editorStr);
				}
				
				$("#next_item,#prev_item").click(function(e){
					//modify by zhucy 2016-03-30 处理bug 18845
					var _id = 'prev_item'; 
					if($(e.currentTarget).attr('id') == 'next_item'){
						_id = 'next_item';
						//$('#'+_id).css('cssText','display:none !important');
					}else{
						$('#'+_id).css('cssText','display:none !important');
					}
					//2016-3-31 处理bug 15223
					if (_id=='next_item' && $('#dkq').is(':checked') &&  !$('#ul_dikouquan_list >li').length ){
						//选了但无抵扣券
						comm.alert('请先到\"网上商城信息\"设置消费者抵扣券');
						return;
					}
					
					$("#prev_item,#save_item,#release_item,#next_item,.detail,#update_item").toggle(); 
					$('#'+_id).css('cssText','display:none !important');
					
					if(_id == 'prev_item'){
						$('#next_item').css('cssText','block');
					}
					
					$(".search-cont").animate({scrollTop:0},500);
				}.bind(this));
				
				
				$("#save_item").click(function(){//保存
					shop.submit(1);
				});
				$("#update_item").click(function(){//修改
					shop.submit(3);
				});
				$("#release_item").click(function(){//发布
					shop.submit(2);
				});
				
				$("#itemMain").on("keyup keypress charcount change",".remain",function(){
					var lt = $.trim($(this).val()).length;
					var max_lt = $(this).attr("maxlength");
					$(this).parent().next().find(".red").html( max_lt-lt>=0?max_lt-lt:0)
				});
				
				// 详情
				//				$(".detail_tab a").click(function(){
				//					$(".detail .active").removeClass("active");
				//					$(this).addClass("active");
				//					$("#xheditorShow,#Msg_content_phone").toggle();
				//				});
				// 运费模板
				$("#yfmb").click(function(){
					$(".moban").show();
				});
				$("#free").click(function(){
					$(".moban").hide();
				});
				$( ".dlg2" ).click(function() {
					shop.sysLogistic();
				});
				// 抵扣券
				$("#dkq").click(function(){
					$(".dikouquan").show();
				});
				$("#ndkq").click(function(){
					$(".dikouquan").hide();
				});
				
				//营业点相关事件
				function getShopCheckedNum(){
					var lt = $(".storeSelect input[name=shopName]:checked").length;
					$("#checked_num").html(lt)
				}
				var flag = true;
				$("#sectAll").click(function(){
					var ckb = $(".storeSelect input[name=shopName]");
					ckb.prop("checked",flag);
					flag = !flag;
					
					getShopCheckedNum();
				});
				$(".listShopIds").click(function(){
					getShopCheckedNum();
				});
			},
			pageNextFun : function(){
				// 下一页
				$("#goodsCc,#gArrow").click(function(){
					ajaxCategory.bindData();
				});
			},
			bindClick : function() {
				/*商品图片start*/
				//图片上传
				$("#itemPhoto").unbind().on('change',function(){
					shop.imgUpload();
				})
				/**上传图片*/
				$("#itemImgList").on("hover","li",function(){
					if($(this).find(".preview").find("img").length > 0){
						$(this).addClass("imgHoverItem");
					}
				})
				$("#itemImgList").on("mouseleave","li",function(){
					$(this).removeClass("imgHoverItem");
				})
				
				$("#itemImgList").on("click",".toleft",function(){
					var thisLi = $(this).parents("li");
					var thisLiPrev = $(this).parents("li").prev();
					if(thisLiPrev.length > 0){
						var htmlch = $(thisLi).find(".imgObject").html();
						var htmlch2 = $(thisLi).find(".preview").html();
						var htmlch3 = $(thisLi).find(".imgObject").attr("imgid");
						var htmlpa = $(thisLiPrev).find(".imgObject").html();
						var htmlpa2 = $(thisLiPrev).find(".preview").html();
						var htmlpa3 = $(thisLiPrev).find(".imgObject").attr("imgid");
						$(thisLiPrev).find(".imgObject").html(htmlch);
						$(thisLiPrev).find(".preview").html(htmlch2);
						if(htmlch3 != null && htmlch3 != ""){
							$(thisLiPrev).find(".imgObject").attr("imgid",htmlch3);
						}else{
							$(thisLiPrev).find(".imgObject").attr("imgid","");
						}
						$(thisLi).find(".imgObject").html(htmlpa);
						$(thisLi).find(".preview").html(htmlpa2);
						if(htmlpa3 != null && htmlpa3 != ""){
							$(thisLi).find(".imgObject").attr("imgid",htmlpa3);
						}else{
							$(thisLi).find(".imgObject").attr("imgid","");
						}
					}
					if($(thisLi).find(".preview").find("img").length < 1){
						$(thisLi).removeClass("imgHoverItem");
					}
				})
				$("#itemImgList").on("click",".toright",function(){
					var thisLi = $(this).parents("li");
					var thisLiPrev = $(this).parents("li").next();
					if(thisLiPrev.length > 0){
						var htmlch = $(thisLi).find(".imgObject").html();
						var htmlch2 = $(thisLi).find(".preview").html();
						var htmlch3 = $(thisLi).find(".imgObject").attr("imgid");
						var htmlpa = $(thisLiPrev).find(".imgObject").html();
						var htmlpa2 = $(thisLiPrev).find(".preview").html();
						var htmlpa3 = $(thisLiPrev).find(".imgObject").attr("imgid");
						$(thisLiPrev).find(".imgObject").html(htmlch);
						$(thisLiPrev).find(".preview").html(htmlch2);
						if(htmlch3 != null && htmlch3 != ""){
							$(thisLiPrev).find(".imgObject").attr("imgid",htmlch3);
						}else{
							$(thisLiPrev).find(".imgObject").attr("imgid","");
						}
						$(thisLi).find(".imgObject").html(htmlpa);
						$(thisLi).find(".preview").html(htmlpa2);
						if(htmlpa3 != null && htmlpa3 != ""){
							$(thisLi).find(".imgObject").attr("imgid",htmlpa3);
						}else{
							$(thisLi).find(".imgObject").attr("imgid","");
						}
					}
					if($(thisLi).find(".preview").find("img").length < 1){
						$(thisLi).removeClass("imgHoverItem");
					}
				})
				$("#itemImgList").on("click",".del",function(){
					var thisLi = $(this).parents("li");
					 $( "#dlg1" ).dialog({
					      modal: true,
					      title : "删除图片",
					      open : function(){
					    	  $( "#dlg1" ).find(".table-del").html("是否删除图片?");
					      },
					      buttons: {
					        确定: function(){
					        	$(thisLi).find(".imgObject").html("");
								$(thisLi).find(".preview").html("");

								if($(thisLi).find(".imgObject").attr("imgid") != null && $(thisLi).find(".imgObject").attr("imgid") != ""){
									var param = {};
									param["id"] = $(thisLi).find(".imgObject").attr("imgid");
									imgDeletearray.push(param);
									$(thisLi).find(".imgObject").removeAttr("imgid");
								}
								
								if($(thisLi).find(".preview").find("img").length < 1){
									$(thisLi).removeClass("imgHoverItem");
								}
								$(this).dialog('destroy');
					        },
					        取消: function(){
					        	 $(this).dialog('destroy');
					        }
					      }
					 })
				})
				/*商品图片End*/
			},
			/*数据提交*/
			submit : function(type){
				var paramSubmit = {};
				var param = {};
				var name = $.trim($("#itemName").val()).replace(/"/gm,'”');
				param["name"] = name;
				
				var sellPoint = $.trim($("#sellPoint").val());
				param["sellPoint"] = sellPoint;
				
				var code = $.trim($("#code").val());
				param["code"] = code;
				
				var factoryName = $.trim($("#factoryName").val());
				param["factoryName"] = factoryName;
				
				var msgContentPhone = $("#Msg_content_phone").val();
				param["itemSummary"] = msgContentPhone;
				
				var isFreePostage = $('input:radio[name="isFreePostage"]:checked').val();
				param["isFreePostage"] = isFreePostage;
				
				var postageId = "";
	            if(isFreePostage == 0){
	            	postageId = $("#yunfeiMb").val();
	            }else{
	            	postageId = 0;
	            }
	            param["postageId"] = postageId;
	            
	            var hotSell = $('input:radio[name="hotSell"]:checked').val();
	            param["hotSell"] = hotSell;
	            
	            var hasCoupon = $('input:radio[name="dikou"]:checked').val();
	            param["hasCoupon"] = hasCoupon;
	            if(hasCoupon == 1){
	            	param["couponPartitionId"] = $('input:radio[name="dkq"]:checked').val();
	            	var coupon = $('input:radio[name="dkq"]:checked').parent();
	            	var couponList = new Array();
	            	var couponParam = {};
	            	couponParam["id"] = $('input:radio[name="dkq"]:checked').val();
	            	couponParam["name"] = $(coupon).find("label").eq(0).attr("couponname");
	            	couponParam["num"] = $(coupon).find("label").eq(1).attr("couponnum");
	            	couponParam["faceValue"] = $(coupon).find("span").eq(0).attr("couponvalue");
	            	couponParam["amount"] = $(coupon).find("label").eq(1).attr("couponmax");
	            	couponList.push(couponParam);
	            	param["couponInfo"] = JSON.stringify(couponList);
	            }
	            
	        	/**获取商品关联营业点*/
				var listShopIds = new Array();
			    $.each($(".listShopIds"),function(){
			          if ($(this).prop('checked') == true) {
			        	  listShopIds.push($(this).val());
			            }
			     });
			    param["listShopIds"] = listShopIds;
			    
				//商品图片
				function itemImgList(){
					var itemsType = new Array();
					var trList = $("#itemImgList ul li");
					$.each(trList,function(key,value){
						var param = {};
						var oldImg = $(this).find(".imgObject").attr("imgid");
						var htmlImg = $(this).find(".imgObject").html();
						if($.trim(oldImg) != null && $.trim(oldImg) != ""){
							param["id"] = oldImg;
							param["img"] = "";
							itemsType.push(param);
						}
						if($.trim(htmlImg) != null && $.trim(htmlImg) != ""){
							param["id"] = "-1";
							param["img"] = htmlImg;
							itemsType.push(param);
						}
					})
					return itemsType;
				}
				//商城自定义分类
				function itemType(){
					var itemsType = new Array();
					var checkbox = $("#itemSpfl").find("input[type='checkbox']:checked");
					$.each(checkbox,function(key,value){
						var param = {};
						param["itemTypeId"] = $(this).attr("value");
						param["itemTypeName"] = $(this).attr("valueName");
						itemsType[key] = param;
					})
					return itemsType;
				}
				
				var itemBody = "<div style='word-break:break-all;word-wrap:break-word;'>"+$('#xheditor').val()+"</div>";
				
				 /**SPU*/
				function itemSpu(){
					var itemsSpu = new Array();
					var spuli = $("#itemspu").find(".spu");
					$.each(spuli,function(key,value){
						var param = {};
						param["pId"] = $(this).attr("valueId");
						param["pName"] = $(this).attr("valueName");
						param["pVId"] = "";
						param["pVName"] = $(this).find("input[type='text']").val();				
						itemsSpu[key] = param;
					})
					return itemsSpu;
				}
				
				 /**SKU*/
				function itemSku(){
					var tablesku = "tablesku";
					
					if($("#skuTabs").find("div").length > 1){
						$.each($("#skuTabs").find("div"),function(k,v){
							if($(v).attr("isshow") == 2 && $(v).attr("aria-hidden") == "false"){
								tablesku = "tablesku2";
							}
						})
					}
					
					var itemsSku = new Array();
					var tdlength = $("#"+tablesku+" tr:eq(0) td").length-4;
					var trlength = $("#"+tablesku+" tr").length;
					for(var i=1;i<trlength;i++){
						var param = {};
						var strsku = '';
						var count = tdlength;
						for(var j=0;j<tdlength;j++){
						  if(j>=(tdlength-1)){
							  param["price"]=$("#"+tablesku+" tr:eq("+i+") td:eq("+j+") input[type=text]").val();
							  param["auction"]=$("#"+tablesku+" tr:eq("+i+") td:eq("+(j+1)+") input[type=text]").val();
							  if($("#"+tablesku+" tr:eq("+i+")").attr("skuid") != null && $("#"+tablesku+" tr:eq("+i+")").attr("skuid") != ''){
								  param["id"]=$("#"+tablesku+" tr:eq("+i+")").attr("skuid");
							  }else{
								  param["id"]= '';
							  }
						  }else{
							  strsku+="{pId:'"+$("#"+tablesku+" tr:eq(0) td:eq("+j+")").attr("items").split("&")[0]
					    	 + "',pName:'"+$("#"+tablesku+" tr:eq(0) td:eq("+j+")").attr("items").split("&")[1]
					    	 + "',pVId:'"+$("#"+tablesku+" tr:eq("+i+") td:eq("+j+")").attr("items").split("&")[0]
							 + "',pVName:'"+$("#"+tablesku+" tr:eq("+i+") td:eq("+j+")").attr("items").split("&")[1]
							 +"',isColor:'"+$("#"+tablesku+" tr:eq(0) td:eq("+j+")").attr("isColor")+"'}";
							  if((count-1) != 1){
								 strsku +=",";
								 count--;
							  }
						  }
						}
						param["sku"] = "["+strsku+"]";
						 itemsSku[i-1] = param;
					}
					return itemsSku;
				}
				
				/**新增加的SKU*/
				function addNewSku(){
					var itemsSku = new Array();
					$.each($("#skuMain .mySku"),function(k,v){
						if($(v).is(":checked")){
							var skuSplit = $(v).val().split("&");
							if(Number(skuSplit[0]) < 0){
								var paramSku = {};
								var skuParent = $(v).parents("tr").eq(0).find("td").eq(0).attr("items").split("&");
								paramSku["skuParentId"] = skuParent[0];
								paramSku["skuParentName"] = skuParent[1];
								paramSku["skuId"] = skuSplit[0];
								paramSku["skuName"] = skuSplit[1];
								paramSku["skuColor"] = $(v).attr("iscolor");
								itemsSku.push(paramSku);
							}
						}
					})
					return itemsSku;
				}
				
				paramSubmit["itemImg"] = JSON.stringify(itemImgList());
				paramSubmit["itemBody"] = itemBody;
				paramSubmit["itemType"] = JSON.stringify(itemType());
				paramSubmit["newSku"] = JSON.stringify(addNewSku());
				if(type != 0){
					if(itemTypeBool == "add"){
						param["categoryName"] = $("#goodsCc").val();
						param["categoryId"] = $("#goodsCc").attr("itemCatChooseId");
					}
						var itemPinpai = $.trim($("#itemPinpai").val());
						if(itemPinpai != null && itemPinpai != ""){
								param["brandId"] = $("#itemPinpai").attr("valueId");
								param["brandName"] = itemPinpai;
						}else{
							param["brandId"] = "";
							param["brandName"] = "";
						}
					paramSubmit["spu"] = JSON.stringify(itemSpu());
					
					var skuType = 0,itemSkuList = null,itemSkuImgList = null,itemskuimgDelete = null;
					itemSkuList = itemSku();
					itemSkuImgList = ajaxCategory.skuImgList();
					if($("#skuTabs").find("div").length > 1){
						$.each($("#skuTabs").find("div"),function(k,v){
							if($(v).attr("isshow") == 2 && $(v).attr("aria-hidden") == "false"){
								itemSkuImgList = ajaxCategory.skuImgUpdateList();
								skuType = 1;
							}
						})
					}
					paramSubmit["skutype"] = skuType;
					itemskuimgDelete = ajaxCategory.skuImgDeleteList();
					paramSubmit["sku"] = JSON.stringify(itemSkuList);
					paramSubmit["itemSkuImg"] = JSON.stringify(itemSkuImgList);
					paramSubmit["itemskuimgDelete"] = JSON.stringify(itemskuimgDelete);
					paramSubmit["itemImgDel"] = JSON.stringify(imgDeletearray);
					if(type == 1){
						param["status"] = 0;
					}else if(type == 2){
						param["status"] = 1;
					}
				}else if(type == 0){
					param["status"] = -5;
				}
				if($("#itemMain").attr("itemId") != null && $("#itemMain").attr("itemId") != ""){
					param["id"] = $("#itemMain").attr("itemId");
					param["categoryId"] = $("#itemMain").attr("categoryId");
				}
				paramSubmit["itemInfostr"] = JSON.stringify(param);
				//验证数据
				var checkBool = itemInfoCheck.inputCheckBool(type);
				if(checkBool == false){
					return ;
				}
				tablePoint.bindJiazaiUp(".operationsInner",true);
				if($("#itemMain").attr("itemId") != null && $("#itemMain").attr("itemId") != ""){
					itemInfo.modifyItem($.param(paramSubmit), function(response) {
						if(response.retCode=="200"){
							tablePoint.tablePoint("保存成功!",function(){
								if(type != 0){
									shop.response();
									//loadRetail();
								}
							});
						}else if(response.retCode=="205"){
							tablePoint.tablePoint("商品积分值和积分比例验证不通过！")
						}else if(response.retCode=="206"){
							tablePoint.tablePoint("商品信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改！");
						}else{
							tablePoint.tablePoint("保存失败！");
						}
						tablePoint.bindJiazaiUp(".operationsInner",false);
					});
				}else{
					itemInfo.addItem($.param(paramSubmit), function(response) {
						var typeName;
						if(type == 1){
							typeName="保存成功!";
						}else if(type == 2){
							typeName="发布成功!";
						}
						if(response.retCode=="200"){
							tablePoint.tablePoint(typeName,function(){
								$("#itemMain").attr("itemId",response.data);
								if(type != 0){
									//shop.response();
									loadRetail();
								}
							});
						}else if(response.retCode=="205"){
							tablePoint.tablePoint("商品积分值和积分比例验证不通过！")
						}else if(response.retCode=="206"){
							tablePoint.tablePoint("商品信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改！")
						}else{
							tablePoint.tablePoint("信息录入失败！");
						}
						tablePoint.bindJiazaiUp(".operationsInner",false);
					});
				}
			},
			/**商品图片上传*/
			imgUpload : function(){
			        	var file = $("#itemPhoto").val();  
			        	  var size = 0;
					        if (document.all){
					        	
					        }else{
					        	size = document.getElementById('itemPhoto').files[0].size;
					        	size = tablePoint.formatFileSize(size);
					        }
			            //判断上传的文件的格式是否正确  
			            var fileType = file.substring(file.lastIndexOf(".")+1);  
			            var count = 0;
			     	   $.each($(".multimage-gallery ul li"),function(k,v){
						   if($(v).find(".preview").find("img").length > 0){
							   count++;
						   }
					   })
			            if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024 || count >= $(".multimage-gallery ul li").length){  
			                return;  
			            }else{  
			            	tablePoint.bindJiazai("#pingbi",true);
	            			ajaxChoose.upLoadFile("itemPhoto", function(response) {
	            				tablePoint.bindJiazai("#pingbi",false);
	            				
	            				//modify by zhucy 2016-02-23 处理生成tfs 缩略图存在null 问题  == begin
	            				var data = eval(response),urlimg,imgName;
	            				if(data && data.length){
	            					$.each(data, function(i, items) {
		            					urlimg = items.httpUrl;
		            					$.each(items.imageNails, function(j, item) {
		            						if(item.height=="68" && item.width=="68"){
											   imgName = item.imageName;
		            						}
		            					});
		            				});
								   $("#itemPhotoCheck").html("");
								   $.each($(".multimage-gallery ul li"),function(k,v){
									   if($(v).find(".preview").find("img").length < 1){
										   $(v).find(".imgObject").html(response);
										   $(v).find(".preview").append("<img src='"+urlimg+imgName+"' class='countImg' width='111px' height='110px'/>");
										   return false;
									   }
								   })
	            				}else{
	            					comm.i_alert('上传图片失败，请稍后重试！');
	            				}

	            				if(shop.bindTableClick){
	            					shop.bindTableClick();
	            				}
	            				//modify by zhucy 2016-02-23 处理生成tfs 缩略图存在null 问题  == end
						});
			          
			        $("#itemPhoto").replaceWith($("#itemPhoto").html());
			    	//图片上传
					$("#itemPhoto").unbind().on('change',function(){
						shop.imgUpload();
					})
			 }	
			},
			/**添加运费*/
			sysLogistic : function(){
				// 查询物流://此时不用分页。Bug:25351
				itemInfo.listSysLogistic({"currentPageIndex":1,"eachPageSize":1000}, function(response) {
					var html = _.template(shippingAddTpl, response);
					$('#dlg2').html(html);
					itemInfoCheck.shippingCheck();
					$("#cbox").on("click",function(){
						if($(this).is(":checked")==true){
							$("#postageId").show();
							$("#charge").val(1);
						}else{
							$("#postageId").hide();
							$("#charge").val(1);
						}
					})
					$("#dlg2").dialog({
								title : "添加运费",
								width : "600",
								modal : true,
								closeIcon:true,
								buttons : {
									'保存' : function() {
										 var shippingBool = itemInfoCheck.submitShipping();
										 if(shippingBool){
											var param = $(".ui-dialog").find(".shippingForm").serialize()+"&logisticName="+$("#logisticId").find("option:selected").text();
											var shippingName = $(".ui-dialog").find("#shippingItem").val();
											itemInfo.saveSalerStorage(param, function(response) {
														if(response.retCode=="200"){
															tablePoint.tablePoint("添加运费模板成功!");
															$("<option value='"+response.data+"'>"+shippingName+"</option>").appendTo("#yunfeiMb");
														}else{
															tablePoint.tablePoint("添加运费模板失败！请稍后重试!");
														}
													});
											$(this).dialog("destroy");
										 }
									}
								}
						});
				});
			},
			response : function() {
				$("#itemList").trigger("click"); 
			}
		}
		return shop;
		//零售　zhanghh add 2015-11-13
		function loadRetail(){
		  //titleBarJs.bindData();
			/**
			 *   //modifly by zhanghh 2016-01-29
			 *   desc:解决保存或发布时页面跳转的问题。
			 */
			require(['hsec_itemInfoFoodSrc/itemList/tab'],function(tab){
				tab.showPage();
				$("#itemFood").trigger("click"); 
			})
		}
});

