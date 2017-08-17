define([ "hsec_tablePointSrc/tablePoint"
         ,"hsec_itemInfoSrc/itemInfoCheck"
         ,"text!hsec_itemInfoTpl/shippingAdd/shippingAdd.html"
         ,"hsec_itemInfo/dat/itemInfo"
         ,"hsec_itemInfoSrc/itemInfoAdd/itemInfoSPU"
         ,"hsec_itemInfoSrc/itemList/itemListInfoHead"
         ,"text!hsec_itemInfoTpl/itemInfoAdd/itemInfoAddBind.html"
         ,"text!hsec_itemInfoTpl/itemInfoAdd/itemInfoAdd.html"
         ,"hsec_tablePointSrc/jquery.charcount",'xheditor_cn'
         ], function(tablePoint,itemInfoCheck,tpl2,ajaxRequest,ajaxsSpu,ajaxItemList,itemInfoAddBind,tpl) {
	
	
	/**商品图片上传*/
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
	
	

	//商品信息品牌下拉框
	 function inputBrand(){
		$("#popupmenu").show();
		$("#popupmenu").on('mouseleave',function(){
			$(".popupmenu").hide();
			});
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
		    
		    
		    
		    /**获取品牌ID*/
			param["categoryId"] = itemCatChooseId;
			if('' != $("#brand").attr("valueid") && null != $("#brand").attr("valueid")){
				param["brandId"] = $("#brand").attr("valueid");
			}
			
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
			var itemskuimg = ajaxsSpu.skuImg();
					tablePoint.bindJiazai("#itemBlock",true);
					var paramSubmit = {};
					paramSubmit["itemInfostr"] = JSON.stringify(param);
					paramSubmit["sku"] = JSON.stringify(itemSku());
					paramSubmit["spu"] = JSON.stringify(itemSpu());
					paramSubmit["itemImg"] = JSON.stringify(itemImgList());
					paramSubmit["itemSkuImg"] = JSON.stringify(itemskuimg);
					paramSubmit["itemBody"] = itemBody;
					paramSubmit["itemType"] = JSON.stringify(itemType());
					paramSubmit["newSku"] = JSON.stringify(addNewSku());
				ajaxRequest.addItem($.param(paramSubmit), function(response) {
					tablePoint.bindJiazai("#itemBlock",false);
				
					if(response.retCode=="200"){
						tablePoint.tablePoint("添加商品成功!",function(){
							itemInfoCheck.response();
						});
					}else if(response.retCode=="206"){
						tablePoint.tablePoint("商品信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再添加商品！")
					}else{
						tablePoint.tablePoint("添加商品失败！",function(){
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
				var htmlImg = $(this).find(".imgObject").html();
				if($.trim(htmlImg) != null && $.trim(htmlImg) != ""){
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
		
		 /**SKU*/
		function itemSku(){
			var itemsSku = new Array();
			var tdlength = $("#tablesku tr:eq(0) td").length-4;
			var trlength = $("#tablesku tr").length;
			for(var i=1;i<trlength;i++){
				var param = {};
				var strsku = '';
				var count = tdlength;
				for(var j=0;j<tdlength;j++){
				  if(j>=(tdlength-1)){
					  param["price"]=$("#tablesku tr:eq("+i+") td:eq("+j+") input[type=text]").val();
					  param["auction"]=$("#tablesku tr:eq("+i+") td:eq("+(j+1)+") input[type=text]").val();
				  }else{
					  strsku+="{pId:'"+$("#tablesku tr:eq(0) td:eq("+j+")").attr("items").split("&")[0]
			    	 + "',pName:'"+$("#tablesku tr:eq(0) td:eq("+j+")").attr("items").split("&")[1]
			    	 + "',pVId:'"+$("#tablesku tr:eq("+i+") td:eq("+j+")").attr("items").split("&")[0]
					 + "',pVName:'"+$("#tablesku tr:eq("+i+") td:eq("+j+")").attr("items").split("&")[1]
					  +"',isColor:'"+$("#tablesku tr:eq(0) td:eq("+j+")").attr("isColor")+"'}";
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
						var htmlpa = $(thisLiPrev).find(".imgObject").html();
						var htmlpa2 = $(thisLiPrev).find(".preview").html();
						$(thisLiPrev).find(".imgObject").html(htmlch);
						$(thisLiPrev).find(".preview").html(htmlch2);
						$(thisLi).find(".imgObject").html(htmlpa);
						$(thisLi).find(".preview").html(htmlpa2);
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
						var htmlpa = $(thisLiPrev).find(".imgObject").html();
						var htmlpa2 = $(thisLiPrev).find(".preview").html();
						$(thisLiPrev).find(".imgObject").html(htmlch);
						$(thisLiPrev).find(".preview").html(htmlch2);
						$(thisLi).find(".imgObject").html(htmlpa);
						$(thisLi).find(".preview").html(htmlpa2);
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
		
		var itemCatChooseId ;
		var shop = {
				init : function(itemCatChoose) {
					itemCatChooseId = itemCatChoose;
					shop.bindData();
				},
				bindData : function() {
					//获取类目ID
						var param = "catId="+itemCatChooseId;
						$('.operationsArea').html(itemInfoAddBind);
						//初始化数据
							tablePoint.bindJiazai(".operationsInner", true);
						ajaxRequest.initItemInfo(param, function(response) {
							tablePoint.bindJiazai(".operationsInner", false);
							if((response.data.lstSalerShops.length < 1 || response.data.lstSalerShops == null) && $('#itemMain') != null){
								tablePoint.tablePoint("没有可用的营业点!请先添加营业点!");
								return false;
							}
							category = response.data.lstSalerShopCategory;
							var html = _.template(tpl, response.data);
							$('#itemMain').html(html);
							$("#goods_page4").hide();
							shop.bindClick(response.data.lstItemBrands);
						})
						$("#upstep").hide();
				},
			bindClick : function(lstItemBrands) {
				$("#detailsTabs").tabs();
				shop.checkInput();
				var tool = "GStart,GEnd,BtnBr,Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor," +
						"BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Anchor,Img,Flash,Media,Emot,Table,|,Source,Preview,Print,Fullscreen,skin,layerShadow,showBlocktag";
				$('#xheditor').xheditor({tools:tool,upLinkUrl:"",upLinkExt:"zip,rar,txt",upImgUrl:comm.domainList.sportal + "files/xhupload?isIE=false&fileType=image",
					upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"",upFlashExt:"swf",
					upMediaUrl:"",upMediaExt:"wmv,avi,wma,mp3,mid"});
				
				// 运费列表添加
				$("#addShipping").unbind().on('click',function(){
					sysLogistic();
				});
				
				// 品牌快捷输入
				$("#brand").on('click',function(){
					inputBrand();
					var obj = $(this);
					$(obj).on('keyup',function(){
						$(obj).attr("valueid","");
						$.each(lstItemBrands,function(key,el){
							if($(obj).val() == el.cnName){
								$(obj).attr("valueid",el.id);
							}
						})
					})
				});
				
				$("#popupmenu").on('click','.brandNode',function(){
					$("#brand").val($(this).text());
					$("#brand").attr("valueid",($(this).attr("brandid")));
					$("#popupmenu").hide();
				});
				
				  $("#brand").on('focus',function(){
						$(this).on('keyup',function(){
							var response = new Array();
							var searchKeyWord = $(this).val();
							$.each(lstItemBrands, function(i, n){
								if(n.cnName.toUpperCase().indexOf(searchKeyWord.toUpperCase()) != -1){
									response.push(n);
								}
							});
							var brandHtml = "";
							$.each(response, function(i, n){
								brandHtml += "<li class='brandNode' brandid='"+n.id+"'>"+n.cnName+"</li>"; 
							});
							$('.popupmenu ul').html(brandHtml);
						})
					});
				
				//图片上传
				$("#itemPhoto").unbind().on('change',function(){
					imgUpload();
				})
				//SPU事件
				ajaxsSpu.InitSpuSku(ajaxRequest);
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
				// 保存
				saveItem();
			},
			checkInput : function(){
				var check ={};
				check["shop"] = shop;
				check["update"] = 0;
				itemInfoCheck.validate(check);
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
			}
		}
			return shop;
});

