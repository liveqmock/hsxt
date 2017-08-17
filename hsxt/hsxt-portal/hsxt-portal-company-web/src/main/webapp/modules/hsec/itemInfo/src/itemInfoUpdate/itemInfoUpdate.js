define([ "hsec_tablePointSrc/tablePoint",
         "hsec_itemInfoSrc/itemInfoCheck"
         ,"text!hsec_itemInfo/tpl/shippingAdd/shippingAdd.html"
         ,"hsec_itemInfoDat/itemInfo"
         ,"hsec_itemInfoSrc/itemInfoUpdate/itemInfoSPU"
         ,"hsec_itemInfoSrc/itemInfoUpdate/itemInfoAddSPU"
         ,"text!hsec_itemInfoTpl/itemInfoUpdate/itemInfoUpdate.html"
         ,"hsec_tablePointSrc/jquery.charcount",'xheditor_cn'
         ], function(tablePoint,itemInfoCheck,tpl2,ajaxRequest,ajaxsSpu,itemInfoAddSPU,tpl) {
	
	/**商品图片上传*/
	var imgDeletearray = new Array();
	var count = 0 ;
	var itemIdOld = '';
	function imgUpload(){
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
      	$("#itemPhotoCheck").html("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB,不超过"+count+"张,建议图片大于700X700!");
          return;  
      }else{  
      			tablePoint.bindJiazai("#pingbi",true);
				ajaxRequest.upLoadFile("itemPhoto", function(response) {
					tablePoint.bindJiazai("#pingbi",false);
					var urlimg,imgName;
					   $.each(eval(response), function(i, items) {
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
					   shop.bindTableClick();
				});
  $("#itemPhoto").replaceWith($("#itemPhoto").html());
	//图片上传
	$("#itemPhoto").unbind().on('change',function(){
		imgUpload();
	})
}	
};
	
	


	

	


	
		//保存商品
		function saveItem(){
			/**获取表单内容*/
			var param = $("#item_add_form").serializeJson();
			
			/**获取商品关联实体店*/
			var listShopIds = new Array();
		    $.each($(".listShopIds"),function(){
		          if ($(this).prop('checked') == true) {
		        	  listShopIds.push($(this).val());
		            }
		     });
		    param["listShopIds"] = listShopIds;
		    
		    param["id"]=itemIdOld;
		    // 抵扣券
		    var hasCoupon = param["hasCoupon"];
		    var cid = 0;
		    var couponInfo = "";
		    if(hasCoupon!=null){
		    	//id:抵扣券ID,name:抵扣卷名称,num:抵扣卷数量,faceValue:面值
		    	var arr = new Array();
		    	$("input[name=dkq]:checked").each(function(index){
		    		$(this).val()
		    		var coupon  = {
		    			id:$(this).val(),
		    			name:$(this).attr("data-name"),
		    			num:$(this).attr("data-num"),
		    			faceValue:$(this).attr("data-fv"),
		    			amount:$(this).attr("data-amount")
		    			};
		    		arr[index] = coupon;
		    	});
		    	couponInfo = JSON.stringify(arr);
		    	cid = $("input[name=dkq]:checked").val();
		    }
		    param["couponInfo"] = couponInfo;
		    param["couponPartitionId"] = cid;
			/**商品详情*/
	    	var itemBody = $('#xheditor').val();
				/**获取SKU关联的图片集合*/
			var itemskuimg = "";
			var itemskuimgDelete = "";
			var skuType = 0;
			if($("#skuTabs").find("div").length > 1){
				$.each($("#skuTabs").find("div"),function(k,v){
					if($(v).attr("isshow") == 2 && $(v).attr("aria-hidden") == "false"){
						itemskuimg = itemInfoAddSPU.skuImg();
						skuType = 1;
					}
				})
			}
			if(skuType == 0){
				itemskuimg = ajaxsSpu.skuImg();
				itemskuimgDelete = ajaxsSpu.skuImgdelete();
			}
			
			
    		var paramUpdate = {};
    		paramUpdate["itemInfostr"] = JSON.stringify(param);
    		paramUpdate["sku"] = JSON.stringify(itemSku());
    		paramUpdate["spu"] = JSON.stringify(itemSpu());
    		paramUpdate["itemImg"] = JSON.stringify(itemImgList());
    		paramUpdate["itemImgDel"] = JSON.stringify(imgDeletearray);
    		paramUpdate["itemSkuImg"] = JSON.stringify(itemskuimg);
    		paramUpdate["itemskuimgDelete"] = JSON.stringify(itemskuimgDelete);
    		paramUpdate["skutype"] = skuType;
    		paramUpdate["itemBody"] = itemBody;
    		paramUpdate["itemType"] = JSON.stringify(itemType());
    		if(skuType == 1){
    			paramUpdate["newSku"] = JSON.stringify(addNewSku2());
			}else{
				paramUpdate["newSku"] = JSON.stringify(addNewSku());
			}
    		tablePoint.bindJiazai("#itemBlock",true);
			ajaxRequest.modifyItem($.param(paramUpdate), function(response) {
				tablePoint.bindJiazai("#itemBlock",false);
				if(response.retCode=="200"){
					tablePoint.tablePoint("更新商品成功!",function(){
						itemInfoCheck.response();
					});
				}else if(response.retCode=="206"){
					tablePoint.tablePoint("商品信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再更新商品！");
				}else{
					tablePoint.tablePoint("修改商品失败！",function(){
						itemInfoCheck.response();
					});
				}
					
			});
		};
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
		
		/**商城商品分类*/
		var category;
		function itemType(){
			var itemsType = new Array();
			var trList = $(".new_del td dl dd");
			$.each(trList,function(key,value){
				var param = {};
				param["itemTypeId"] = $(this).attr("value");
				param["itemTypeName"] = $(this).attr("valuename");
				itemsType[key] = param;
			})
			return itemsType;
		}
		
		/**新增加的SKU*/
		function addNewSku(){
			var itemsSku = new Array();
			$.each($("#skuMain .sku"),function(k,v){
				if($(v).is(":checked")){
					var skuSplit = $(v).val().split("&");
					if(Number(skuSplit[0]) < 0){
						var paramSku = {};
						var skuParent = $(v).parents("dl").find("dt").attr("items").split("&");
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
		
		/**新增加的SKU*/
		function addNewSku2(){
			var itemsSku = new Array();
			$.each($("#skuMainAdd .skuAdd"),function(k,v){
				if($(v).is(":checked")){
					var skuSplit = $(v).val().split("&");
					if(Number(skuSplit[0]) < 0){
						var paramSku = {};
						var skuParent = $(v).parents("dl").find("dt").attr("items").split("&");
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
		
		 /**SPU*/
		function itemSpu(){
			var itemsSpu = new Array();
			var spuli = $("#itemspu").find("li");
			$.each(spuli,function(key,value){
				var param = {};
				param["pId"] = $(this).find("label").attr("valueId");
				param["pName"] = $(this).find("label").html();
				if($(value).attr("displayType") == 1){
					param["pVId"] = "";
					param["pVName"] = $(this).find("input[type='text']").val();				
				}else if($(value).attr("displayType") == 2){
					param["pVId"] = $(this).find("select").find("option:selected").val();
					param["pVName"] = $(this).find("select").find("option:selected").text();				
				}else if($(value).attr("displayType") == 3){
					var strname = "";
					var strpid = "";
					$(this).find("input[type='checkbox']:checked").each(function(index){
						strname += $(this).val()+",";
						strpid += $(this).attr("pid")+","
					});
					param["pVId"] = strpid.substring(0,strpid.length-1);
					param["pVName"] = strname.substring(0,strname.length-1);
				}else if($(value).attr("displayType") == 4){
					param["pVId"] = $(this).find("input[type='radio']:checked").attr("pid");
					param["pVName"] = $(this).find("input[type='radio']:checked").val();
				}
				
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
		
	
		/**页面初始化按钮控件*/
		function clickList(){
			$("ul.tabList li span").unbind().on('click',function(){
				var bool = itemInfoCheck.inputCheckOne($(this).attr("num"));
				if(bool){
					$(this).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page1,#goods_page2,#goods_page3,#goods_page4').hide().eq($('ul.tabList li span').index(this)).show();
				}
				});
			$(".nextstep01").unbind().on('click',function(){
				var bool = itemInfoCheck.inputCheckOne(1);
				if(bool){
					$('ul.tabList li span').eq(1).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page1').hide(); $('#goods_page2').show();
				}	
				return false;
				})
			$(".nextstep02").unbind().on('click',function(){
				var bool = itemInfoCheck.inputCheckOne(2);
				if(bool){
				 	$('ul.tabList li span').eq(2).addClass("active").parent().siblings('li').children('span').removeClass("active");
				 	$('#goods_page2').hide(); $('#goods_page3').show();
				} 	
				return false;
				})
			$(".nextstep03").unbind().on('click',function(){
				var bool = itemInfoCheck.inputCheckOne(3);
				if(bool){
					$('ul.tabList li span').eq(3).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page3').hide(); $('#goods_page4').show();
				}	
				return false;
				})
				$(".upstep04").unbind().on('click',function(){
					$('ul.tabList li span').eq(2).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page3').show(); $('#goods_page4').hide(); return false;
					})
				$(".upstep03").unbind().on('click',function(){
					$('ul.tabList li span').eq(1).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page2').show(); $('#goods_page3').hide(); return false;
					})
				$(".upstep02").unbind().on('click',function(){
					$('ul.tabList li span').eq(0).addClass("active").parent().siblings('li').children('span').removeClass("active");
					$('#goods_page1').show(); $('#goods_page2').hide(); return false;
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
					      buttons: {
					        确定: function(){
					        	$(thisLi).find(".imgObject").html("");
								$(thisLi).find(".preview").html("");
								if($(thisLi).find(".preview").find("img").length < 1){
									$(thisLi).removeClass("imgHoverItem");
								}
								if($(thisLi).find(".imgObject").attr("imgid") != null && $(thisLi).find(".imgObject").attr("imgid") != ""){
									var param = {};
									param["id"] = $(thisLi).find(".imgObject").attr("imgid");
									imgDeletearray.push(param);
									$(thisLi).find(".imgObject").removeAttr("imgid");
								}
								$(this).dialog('destroy');
					        },
					        取消: function(){
					        	 $(this).dialog('destroy');
					        }
					      }
					 })
				})
			

			
			
			
		
			
		}
		/**添加运费*/
		function sysLogistic(){
			// 查询物流
			ajaxRequest.listSysLogistic({"currentPageIndex":1,"eachPageSize":1000}, function(response) {
				var html = _.template(tpl2, response);
				$('#dlg2').html(html);
				itemInfoCheck.shippingCheck();
				$("#cbox").on("click",function(){
					if($(this).is(":checked")==true){
						$("#postageId").show();
						$("#charge").val(0);
					}else{
						$("#postageId").hide();
						$("#charge").val(0);
					}
				})
				$("#dlg2").dialog({
							title : "添加运费",
							width : "600",
							modal : true,
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons : {
								'保存' : function() {
									  var shippingBool = itemInfoCheck.submitShipping();
									  if(shippingBool){
										var param = $(".ui-dialog").find(".shippingForm").serialize()+"&logisticName="+$("#logisticId").find("option:selected").text();
										var shippingName = $(".ui-dialog").find("#shippingItem").val();
												ajaxRequest.saveSalerStorage(param, function(response) {
													if(response.retCode=="200"){
														tablePoint.tablePoint("添加运费模板成功!");
														$("<option value='"+response.data+"'>"+shippingName+"</option>").appendTo("#shippingSelect");
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
		}
		
		var sku = '' ;
		var shop = {
				bindData : function(itemId) {
					        if(category != null){
					        	category = '';
					        }
					        if(count != null){
					        	count = 0;
					        }
					        if(sku != null){
					        	sku = '';
					        }
					        if(imgDeletearray != null){
					        	imgDeletearray.length = 0;
					        }
					    itemIdOld = itemId;
						var param = {"itemId":itemId};
						
						//初始化数据
							tablePoint.bindJiazai(".operationsArea", true);
						ajaxRequest.getDetailById(param, function(response) {
							tablePoint.bindJiazai(".operationsArea", false);
							if(response.data.ItemWebInfo.lstSalerShops.length < 1 || response.data.ItemWebInfo.lstSalerShops == null){
								tablePoint.tablePoint("没有可用的的营业点!请先添加营业点!");
								return false;
							}
							category = response.data.ItemWebInfo.lstSalerShopCategory;
							response["uniqueArray"] = shop.uniqueArray;
							var html = _.template(tpl, response);
							$('#itemHide').html(html);
							sku = response.data;
							shop.bindClick();
							var tool = "GStart,GEnd,BtnBr,Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor," +
							"BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Anchor,Img,Flash,Media,Emot,Table,|,Source,Preview,Print,Fullscreen,skin,layerShadow,showBlocktag";
							var editor = $('#xheditor').xheditor({"tools":tool,upLinkUrl:"",upLinkExt:"zip,rar,txt",upImgUrl:comm.domainList.sportal + "files/xhupload?isIE=false&fileType=image",
									upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"",upFlashExt:"swf",
									upMediaUrl:"",upMediaExt:"wmv,avi,wma,mp3,mid"});
							editor.appendHTML(response.data.ItemDetailInfo.detailsInfo);
							$("#goods_page4").hide();
							shop.checkInput();
						})
						$("#upstep").hide();
				},
				bindClick : function() {
					
					$("#detailsTabs").tabs();
					$('#skuTabs').tabs();
					
				// 运费列表添加
				$("#addShipping").unbind().on('click',function(){
					sysLogistic();
				});
				
				
				//图片上传
				$("#itemPhoto").unbind().on('change',function(){
					imgUpload();
				})
				//SPU事件
				ajaxsSpu.InitSpuSku(ajaxRequest,sku);
				itemInfoAddSPU.InitSpuSku(ajaxRequest);
				//初始化页面事件方法
				clickList();
				
				/**主营分类脚本*/
				var n=1;
				
				$(".primary_add").clone(true).removeClass("primary_add").addClass("new_add").insertBefore($(".primary_add")).show();

				//添加按钮事件
				$(".tr_add").unbind().on('click',function(){
					
					var dd_length=$(".list:visible").find("input:checked").length;
					if(dd_length < 1){
						return false;
					}
					var dd=new Array([dd_length]);
					var dd_html='';
					for(var i=0;i<dd_length;i++){
						var label;
						label=$(".list:visible").find("input:checked");
						dd[i]="<dd value='"+$(label[i]).attr("value")+"' valuename='"+$(label[i]).attr("valuename")+"'>"+$(label[i]).attr("valuename")+"</dd>"					
						dd_html+=dd[i];			
						};
						$(".primary_del").clone(true).removeClass("primary_del").addClass("new_del")
								.insertBefore($(this).parent().parent('tr'))
								.find("dt").text($(".itemtype:visible").text()).end()
								.find("dd").remove().end()
								.find('dl').append(dd_html);
							
							
							$('.tr_cancel').hide();
							$(".new_add").remove();
							$(".primary_add").clone(true).removeClass("primary_add").addClass("new_add").insertBefore($(".primary_add")).show();
							//console.log(n)
						});	
					//删除按钮事件
					$(".tr_del").unbind().on('click',function(){
						$(this).parent().parent("tr").remove();
						
					});
					//文字提示
					$(".itemtype, .storeSelect label").unbind().on('mouseenter',function(){
						var title = $(this).text();
						title = title.replace(/(^\s*)|(\s*$)/g, '');
						$(this).attr("title", title);
					});
				
					//取消按钮
					$('.tr_cancel').click(function () {
						$(".new_add").remove();
						$(".primary_add").clone(true).removeClass("primary_add").addClass("new_add").insertBefore($(".primary_add")).show();
						$('.tr_cancel').hide();	
					})
				//选择类目
					$('.tr_cancel').hide();	
				$(".fir_class").unbind().on('click',function(){
					$("#droplist").show();
					$('.tr_cancel').show();	
					$(".fir_class:visible").on('click','li',function(event){
						var htmlList = '';
						var subtext = $(this).text();
						var lId = $(this).attr("lId");
						for(var i = 0 ;i<category.length;i++){
							if(lId == category[i].parentId){
								var name = category[i].name;
								if(name.length > 7){
									name = name.substring(0,7)+"...";
								}
								htmlList +=	"<li class='cla-detail'><input type='checkbox' value='"+category[i].id+"' valueName='"+category[i].name+"' ><label title='"+category[i].name+"'>"+name+"</label></li>";
							}
						}
						$(".new_add .list").html(htmlList);
						
						$(".fir_class:visible").children('.itemtype').text(subtext);
						$(".droplist").hide();
						$(".new_add .sec_class>span").remove();
						$(".new_add .list").show();
						
						event.stopPropagation();
						})
				})
				shop.bindInputCheck();
			},
			saveItems : function(){
			  	shop.bindItemState(function(retCode){
	        		if(retCode){
	        			return false;
	        		}else{
						// 保存
						saveItem();
	        		}
	        	})
			},
			checkInput : function(){
				var check ={};
				check["shop"] = shop;
				check["update"] = 1;
				itemInfoCheck.validate(check);
			},
			bindItemState : function(callback){
				ajaxRequest.hasRightEdit(null, function(response) {
					if(response.retCode == 600){
						tablePoint.tablePoint("无权进行此操作!请刷新页面!");
						callback(true);
					}else{
						callback(false);
					}
				})
			},
			bindInputCheck : function(){
				$('#itemName').charcount({
					maxLength: 40,
					preventOverage: true,
					position : 'after'
				});
				$('#sellPoint').charcount({
					maxLength: 100,
					preventOverage: false,
					position : 'after'
				});
				$('#itemSummary').charcount({
					maxLength: 150,
					preventOverage: false,
					position : 'after'
				});
				$('.spuCheckMax').charcount({
					maxLength: 30,
					preventOverage: true,
					position : 'after'
				});
				var timeId = "";
				function xheditor(){
					if($("#xheditorInputLength").length > 0){
						if($("#xheditorInputLength").is(":hidden")){
							$("#xheditorInputLength").show();
						}
						var input = 9000;
						if($('#xheditor').val().length != null && $('#xheditor').val().length != ""){
							input =	9000 - $('#xheditor').val().length;
						}
						if(input >= 0){
							$("#xheditorInputLength").html("还能输入<b class='red'>"+input+"</b>字");
						}else{
							$("#xheditorInputLength").html("已超出规定字数<b class='red'>"+Math.abs(input)+"</b>个!");
						}
					}else{
						window.clearInterval(timeId);
					}
				}
				timeId = setInterval(xheditor,1000);
			},
			uniqueArray : function(a){
				    temp = new Array();
				    for(var i = 0; i < a.length; i ++){
				        if(!contains(temp, a[i])){
				            temp.length+=1;
				            temp[temp.length-1] = a[i];
				        }
				    }
				    return temp;
				function contains(a, e){
				    for(j=0;j<a.length;j++)if(a[j]==e)return true;
				    return false;
				}
			}
		}
			return shop;
});

