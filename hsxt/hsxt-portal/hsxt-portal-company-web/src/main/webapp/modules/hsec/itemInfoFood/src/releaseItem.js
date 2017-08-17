define(["hsec_tablePointSrc/tablePoint",
        "hsec_itemInfoFoodDat/item",
        "hsec_itemInfoFoodSrc/category",
        "hsec_itemInfoFoodSrc/itemList/itemListInfoHead",
        "text!hsec_itemInfoFoodTpl/item.html"
        ,"hsec_itemInfoFoodSrc/pinyin"], function(tablePoint,itemajax,category,itemListSrc,itemTpl,pinyinJs) {
	var item = {
		bindData : function(param){
			itemajax.initItemInfo(param,function(response){
				var html = _.template(itemTpl,response);
				$('#busibox').html(html);
				item.setInfo(response.msg)
				item.pageFun();
			})
		},
		setInfo : function(data){
			var name="";
			var pinyingCode="";
			var pic="";
			var taste="";
			var categoryName = "";
			if(data){
				var item = data.item;
				try{
					var picList = eval(item.pics)[0].web;
					$.each(picList,function(key,p){
						if(p.size=="110*110"){
							pic =  p.name;
						}
					});
				}catch(e){}
				//alert(data.relation)
				var categoryId = item.categoryId;
				$("#itemId").val(item.id);
				$("#itemName").val(item.name);
				$("#pinyingCode").val(item.pinyingCode);
				try{
					var kouwei = eval("("+item.taste+")");
					var kouweiStr = "";
					$.each(kouwei,function(k,v){
						if((kouwei.length - 1) != k){
							kouweiStr+=v.value+",";
						}else{
							kouweiStr+=v.value;
						};
					})
					$("#feellist").val(kouweiStr);
					$("#feellist").attr("kouwei",item.taste);
				}catch(e){}
				$("#categoryName").val(item.categoryName);
				$("#categoryId").val(categoryId);
				$("#code").val(data.item.code)
				$("#price_un").val(item.price);
				if(pic!=""){
					$("#shopPhoto").next().attr("src",data.tfs +pic);
				}
				$("#picVal").val(item.pics.replace(/\"/g, "'"));
				var arr = categoryId.split(">");
				var catId = arr[arr.length-1];
				var standard = item.standard;

				category.queryPropBycategoryId(catId,eval(standard),item.price,item.pv);
				
				var relation = data.relation;
				$.each(relation,function(key,r){
					$("#c_"+r.salerCategoryId).prop("checked",true);
				})
			}
			
			
		},
		/**商品图片上传*/
		imgUpload : function(){
           var file = $("#shopPhoto").val();  
        	  var size = 0;
		        if (document.all){
		        	
		        }else{
		        	size = document.getElementById('shopPhoto').files[0].size;
		        	size = tablePoint.formatFileSize(size);
		        }
            //判断上传的文件的格式是否正确  
           var fileType = file.substring(file.lastIndexOf(".")+1);  
//           var count = 0;
//     	   $.each($(".multimage-gallery ul li"),function(k,v){
//			   if($(v).find(".preview").find("img").length > 0){
//				   count++;
//			   }
//		   })
            if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024 ){  
                return;  
            }else{  
            	tablePoint.bindJiazai("#pingbi",true);
            	itemajax.upLoadFile("shopPhoto", function(response) {
            		
            		tablePoint.bindJiazai("#pingbi",false);
            		//modify by zhucy 2016-02-23 处理生成tfs 缩略图存在null 问题  == begin
					var data = eval(response),urlimg,imgName;
					if(data && data.length){
						$.each(data, function(i, items) {
						   urlimg = items.httpUrl;
						   $.each(items.imageNails, function(j, item) {
							   if(item.height=="110" && item.width=="110"){
								   imgName = item.imageName;
							   }
						   });
						});
						
						$("#shopPhoto").next().attr("src",urlimg+imgName);
						$("#shopPhoto").prev().val(response);
					}else{
						comm.i_alert('上传图片失败，请稍后重试！');
					}
					//modify by zhucy 2016-02-23 处理生成tfs 缩略图存在null 问题  == end
				});
        		$("#shopPhoto").replaceWith($("#shopPhoto").html());
		    	//图片上传
				$("#shopPhoto").unbind().on('change',function(){
					item.imgUpload();
				})
            }	
		},
		verifyOne : function(param){
			//tablePoint.tablePoint("添加商品失败！");
			function shopImgList(){
				var itemsType = new Array();
				var trList = $("#shopImgList ul li");
				$.each(trList,function(key,value){
					var htmlImg = $(this).find(".imgObject").val();
					if($.trim(htmlImg) != null && $.trim(htmlImg) != ""){
						itemsType.push(eval(htmlImg)[0]);
					}
				})
				return itemsType;
			}
			param = $("#itemOneForm").serializeJson();
			
			if($("#flag").val()==0){
				var pics = shopImgList();
				if(pics.length>0){
					param["pics"] = JSON.stringify(pics);
				}
				var vshopCategory = new Array();
				$("input[name=vshopCategory]:checked").each(function(){
					vshopCategory.push($(this).val());
				})
				param["vshopCategory"] = vshopCategory.join(",");
				
				// 规格
				var arr = new Array();
				
				var min_p = null;
				var max_p = null;
				
				var min_a = null;
				var max_a = null;
				
				var price_arr = new Array();
				var auction_arr = new Array();
				$("#sku_tbody>tr").each(function(key){
					var did = $(this).attr("data-id");
					var p = {};
					var price = $(this).find(".price").val();
					var auction = $(this).find(".auction").val();
					var auctionScale = $(this).find(".auctionScale").val();
					
					if(did){
						var index = $(this).find("td").index();
						var pId = $("#sku_thead td").eq(index).attr("data-id");
						
						var pName = $("#sku_thead td").eq(index).attr("data-name");
						p["pId"] = pId;
						p["pName"] = pName;
						p["pVId"] = $(this).find(".propVal").attr("data-id");
						p["pVName"] = $(this).find(".propVal").text();
						p["price"] = price;
						p["auction"] = auction;
						arr[key] = p;
					}else{
						param["price"] = price;
						param["pv"] = auction;
						param["pvRate"] = auctionScale;
					}
					price_arr[key] = price;
					auction_arr[key] = auction;
				});
				
				Array.max=function(array){  
                    return Number(Math.max.apply(Math,array)).toFixed(2);  
                } 
				Array.min=function(array){  
                    return Number(Math.min.apply(Math,array)).toFixed(2);  
                } 
				
				if(price_arr.length==1){
					param["priceRegion"] =  Number(price_arr[0]).toFixed(2);
				}else if(price_arr.length>1){
					min_p = Array.min(price_arr);
					max_p = Array.max(price_arr);
					if(Number(min_p) < Number(max_p)){
						param["priceRegion"] =  min_p+","+max_p;
					}else{
						param["priceRegion"] = min_p;
					}
				}
				if(auction_arr.length==1){
					param["pvRegion"] =  Number(auction_arr[0]).toFixed(2);
				}else if(auction_arr.length>1){
					min_a = Array.min(auction_arr);
					max_a = Array.max(auction_arr);
					if(Number(min_a) < Number(max_a)){
						param["pvRegion"] =  min_a+","+max_a;
					}else{
						param["pvRegion"]=  min_a;
					}
				}
				
				if(arr.length>0){
					param["standard"] = JSON.stringify(arr);
				}
			}else{
				delete param["taste"];
				param["price"] = $("#price_un").val();
				param["pv"] = 0;
				param["pvRate"] = 0;
			}
			param["kouwei"] = $("#feellist").attr("kouwei");
			return param;
		},
		saveItemFood : function(param,callblack){
			itemajax.saveItemFood(param,function(res){
				var code = JSON.stringify(res.retCode);
				if(code == 200){
					$("#itemTwoForm #itemId,#itemOneForm #itemId").val(res.data);
					
					if(callblack){
						callblack();
					}
				}else if(code=="206"){
					tablePoint.tablePoint("商品信息不符合规范，包含违禁字:<br>"+res.data+"<br>请修改！");
				}
			});
		},
		commError : function(obj,content){
			obj.parents("tr").find(".errorInfo").html(content).addClass("red");
		},
		verifyItemWujifen : function(){
			return $("#itemOneForm").validate({
				onkeypress:true,
				rules : {
					name : {required : true },
					pinyingCode : {required : true},
					code : {number : true,rangelength : [1,5]},
					categoryName : {required : true },
					price_un : {required : true,number : true}
				},
				messages : {
					name : {required : "请输入菜品名"},
					pinyingCode : {required : "请输入拼音码"},
					code : {number : "请输入1-5位数字",rangelength : "请输入1-5位数字"},
					categoryName : {required : "请选择菜品分类"},
					price_un : {required : '请输入商品价格',number : "请输入商品价格"}
				} ,
				errorPlacement: function(error, element) {
					element.parents("tr").find(".errorInfo").html($(error).text()).addClass("red");
			    },
			    success:function(element){	      
			    	$("input[name='imgObject']").val("");
			    	$("input[name='taste']").val("");
			    	$("#feellist").attr("kouwei","");
			    	$("input[name=vshopCategory]:checked").each(function(){
						$(this).attr("checked",false);
					})
			    }
				 
			});
		},
		verifyAddItem : function(){
			return $("#itemOneForm").validate({
				onkeypress:true,
				rules : {
					name : {required : true },
					pinyingCode : {required : true},
					code : {number : true,rangelength : [1,5]},
					imgObject : {required : true },
					categoryName : {required : true}
				},
				messages : {
					name : {required : "请输入菜品名"},
					pinyingCode : {required : "请输入拼音码"},
					code : {number : "请输入1-5位数字",rangelength : "请输入1-5位数字"},
					imgObject : {required : "请上传菜品图片"},
					categoryName : {required : "请选择菜品分类"}
				} ,
				errorPlacement: function(error, element) {
					element.parents("tr").find(".errorInfo").html($(error).text()).addClass("red");
			    },
			    success:function(element){	      
			    	
			    }
				 
			});
		},
		verifyUpdateItem : function(){
			return $("#itemOneForm").validate({
				onkeypress:true,
				rules : {
					name : {required : true },
					pinyingCode : {required : true},
					code : {number : true,rangelength : [1,5]},
					categoryName : {required : true }
				},
				messages : {
					name : {required : "请输入真实姓名"},
					pinyingCode : {required : "请输入拼音码"},
					code : {number : "请输入1-5位数字",rangelength : "请输入1-5位数字"},
					categoryName : {required : "请选择菜品分类"}
				},
				errorPlacement: function(error, element) {
					element.parents("tr").find(".errorInfo").html($(error).text()).addClass("red");
			    },
			    success:function(element){	      
			    	
			    }
				 
			});
		},
		skuCheck : function(){
			var sku = $("#skuTable .sku");
			var bool = false;
			if($(sku).length < 1){
				return true;
			}else{
				$.each($("#sku_tbody tr"),function(k,v){
					var price = $(v).find(".price").eq(0).val();
					var auction = $(v).find(".auction").eq(0).val();
					//add by zhanghh :2016-04-05
					var auctionScale = $(v).find(".auctionScale").eq(0).val();
					    if(price != null && price != "" && Number(price) > 0 && Number(price) < 999999){
					    }else{
					    	//tablePoint.tablePoint("价格必须在0~999999999 之间!");
					    	bool = true;
					    } 
					    // 去掉 Number(price) / 10000 < Number(auction) 判断 2016-01-04 梁启富
					    if(auction != null && auction != "" && Number(auction) >= 0.05  && Number(auction)/Number(price)*100 <= 15){
					    }else{
					    	//tablePoint.tablePoint("积分值不能小于0.05!");
					    	bool = true;
					    } 
					    if(auctionScale != null && auctionScale != "" && Number(auctionScale) >= 0.01  && Number(auctionScale)<= 30){
					    }else{
					    	//tablePoint.tablePoint("积分比例设置在0.01% ~ 30% 之间!");
					    	bool = true;
					    } 
				})
			}
			return bool;
		},
		pageFun : function(){
			
			$(".shopDiyClass>li").each(function(){
				var lt = $(this).find("ul li").length;
				if(lt==0){
					$(this).children().eq(0).removeClass("none");
				}
			});
			$("#itemName").on('focus',function(){
				$(this).on('keyup',function(){
					var pinyin = pinyinJs.pinyin($.trim($(this).val()));
					$("#pinyingCode").val(pinyin);
				})
			});
	
			
			$("#saveBtn").click(function(){
				var itemId = $("#itemId").val();
				if($(".priceTr").is(":hidden")){
					if(itemId!=""){
						if(!item.verifyUpdateItem().form()){
							return;
						}
					}else{
						if(!item.verifyAddItem().form()){
							return;
						}
					}
					if(item.skuCheck()){
						//tablePoint.tablePoint("根据下方提示，填写正确的规格!<br>注意：你的积分比例计算结果值在0.01% ~ 30% 之间");
						tablePoint.tablePoint("根据下方提示，填写正确的规格!");
						return false;
					}
				}else{
					if(!item.verifyItemWujifen().form()){
						return;
					}
				}
				var param = {};
				param = item.verifyOne(param);
				item.saveItemFood(param,function(){
					require(['hsec_itemInfoFoodSrc/itemList/tab'],function(tab){
						tab.showPage();
						$("#itemListFood").trigger("click"); 
					})
				});
				
			});
			// 口味
			$("#gArrow1").click(function(){
				$(this).next().toggle();
			});
			
			$("#gArrow,#goodsCc").click(function(){
				category.bindData();
			});
			$("input[name=kouwei]").click(function(){
				var maxsize = $("#kouwei_max").text();
				var checked = $("input[name=kouwei]:checked");
				if(checked.length>maxsize){
					$(this).prop("checked",false);
				}else{
					var arr = new Array();
					var kouweiJson = new Array();
					checked.each(function(index){
						arr[index]=$(this).val();
						var param = {};
						param["id"] = $(this).attr("valueid");
						param["value"] = $(this).val();
						kouweiJson.push(param);
					});
					$("#feellist").val(arr.join(","));
					if(kouweiJson.length > 0){
						$("#feellist").attr("kouwei",JSON.stringify(kouweiJson));
					}else{
						$("#feellist").attr("kouwei","");
					}
				}
			});
			
			
			/*营业点照片start*/
			//图片上传
			$("#shopPhoto").unbind().on('change',function(){
				item.imgUpload();
			})
			$("#shopImgList").on("mouseover mouseout","li",function(event){
				 if(event.type == "mouseover"){
					 if($(this).find(".preview").find("img").length > 0){
							$(this).addClass("imgHoverItem");
						}
				 }else if(event.type == "mouseout"){
					 $(this).removeClass("imgHoverItem");
				 }
			})
			
//			$("#shopImgList").on("click",".toleft",function(){
//				var thisLi = $(this).parents("li");
//				var thisLiPrev = $(this).parents("li").prev();
//				if(thisLiPrev.length > 0){
//					var htmlch = $(thisLi).find(".imgObject").val();
//					var htmlch2 = $(thisLi).find(".preview").html();
//					var htmlpa = $(thisLiPrev).find(".imgObject").val();
//					var htmlpa2 = $(thisLiPrev).find(".preview").html();
//					$(thisLiPrev).find(".imgObject").val(htmlch);
//					$(thisLiPrev).find(".preview").html(htmlch2);
//					$(thisLi).find(".imgObject").val(htmlpa);
//					$(thisLi).find(".preview").html(htmlpa2);
//				}
//				if($(thisLi).find(".preview").find("img").length < 1){
//					$(thisLi).removeClass("imgHoverItem");
//				}
//			})
//			$("#shopImgList").on("click",".toright",function(){
//				var thisLi = $(this).parents("li");
//				var thisLiPrev = $(this).parents("li").next();
//				if(thisLiPrev.length > 0){
//					var htmlch = $(thisLi).find(".imgObject").val();
//					var htmlch2 = $(thisLi).find(".preview").html();
//					var htmlpa = $(thisLiPrev).find(".imgObject").val();
//					var htmlpa2 = $(thisLiPrev).find(".preview").html();
//					$(thisLiPrev).find(".imgObject").val(htmlch);
//					$(thisLiPrev).find(".preview").html(htmlch2);
//					$(thisLi).find(".imgObject").val(htmlpa);
//					$(thisLi).find(".preview").html(htmlpa2);
//				}
//				if($(thisLi).find(".preview").find("img").length < 1){
//					$(thisLi).removeClass("imgHoverItem");
//				}
//			})
			$("#shopImgList").on("click",".del",function(){
				var thisLi = $(this).parents("li");
				 $( "#dlg1" ).dialog({
				      modal: true,
				      title : "删除图片",
				      open : function(){
				    	  $( "#dlg1" ).find(".table-del").html("是否删除图片?");
				      },
				      buttons: {
				        "确定": function(){
				        	$(thisLi).find(".imgObject").val("");
							$(thisLi).find(".preview").html("");
							if($(thisLi).find(".preview").find("img").length < 1){
								$(thisLi).removeClass("imgHoverItem");
							}
							$(this).dialog('destroy');
				        },
				        "取消": function(){
				        	 $(this).dialog('destroy');
				        }
				      }
				 })
			})
			/*营业点照片End*/
			
			$(".remain").unbind("keyup").bind("keyup",function(){
				var lt = $.trim($(this).val()).length;
				var max_lt = $(this).attr("maxlength");
				$(this).parent().next().find(".red").html( max_lt-lt>=0?max_lt-lt:0)
			});
			
			$("#zhoubian").on("mouseleave",function(){
				$("#zhoubianDiv").hide();
			})
		}
	}
	
	return item;
});
