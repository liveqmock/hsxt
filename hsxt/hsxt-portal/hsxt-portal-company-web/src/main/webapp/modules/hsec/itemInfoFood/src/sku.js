define(["text!hsec_itemInfoFoodTpl/sku.html",
        "text!hsec_itemInfoFoodTpl/propSkuMuBan.html"], function(skuTpl,propSkuMuBanTpl) {
	
	
	var sku = {
		bindData : function(data){
			//var arr = [{id:1,name:"份量",value:[{id:11,name:"小份"},{id:12,name:"中份"},{id:13,name:"大份"}]}]
			//var html = _.template(skuHeadTpl,arr);
			//$("#sku_thead").html(html);
			if(data.length==0){
				var html = _.template(skuTpl,null);
				$("#sku_tbody").append(html);
				//gridObj = $.fn.bsgrid.init('skuTable',{});
				sku.skuFun();
			}
			sku.pageFun();
			sku.bindSkuCheck();
			sku.newSkuAdd();
		},
		pageFun : function(){
			
			$("#propTable").on("click",".prop input[type=checkbox]",function(){
				var arr = new Array();
				var index = 0;
				var param = {};
				var vid = $(this).val();
				var vname;
				vname = $(this).next().text();
				if(vname == null || vname == ""){
					vname = $.trim($(this).next().val());
					if(vname == null || vname == ""){
						return false;
					}
				}
//				param[vid] = vname;
				
				param["pVId"] = vid;
				param["pVName"] = vname;
				arr[index] = param;
				// 扩展多个
				$(this).parents(".prop").siblings().each(function(){
					
				});
				if($(this).is(":checked")){
					sku.setSku(arr);
				}else{
					$("#sku_tbody").find("tr[data-id="+vid+"]").remove();
				}
			});
		},
		setSku : function(arr){
			if(arr!=null && arr!=""){
				var html = _.template(skuTpl,arr);
				$("#sku_tbody").append(html);
				sku.skuFun();
			}
		},
		skuFun : function(){
			$(".sku_tr .price,.sku_tr .auction").unbind("focus").bind("focus",function(){
			$(this).on('keyup',function(){
				var sku_tr = $(this).parents(".sku_tr");
				var price_val = sku_tr.find(".price").val();
				
				if(isNaN(price_val)){
					sku_tr.find(".price").val("0");
				}
				
				var auction_val = sku_tr.find(".auction").val();
				if(isNaN(auction_val)){
					sku_tr.find(".auction").val("0");
				}
				var auctionScale = (auction_val / price_val * 2 * 100).toFixed(2);
				var deductAuction = (Number(auction_val)*2).toFixed(2);
				var gainAuction = Number(auction_val).toFixed(2);
				
				if(isNaN(auctionScale)){
					auctionScale = "";
				}
				if(isNaN(deductAuction)){
					deductAuction = "";
				}
				if(isNaN(gainAuction)){
					gainAuction = "";
				}
				sku_tr.find(".auctionScale").val(auctionScale);
				sku_tr.find(".deductAuction").text(deductAuction);
				sku_tr.find(".gainAuction").text(gainAuction);
			});	
			});
			$(".sku_tr .price,.sku_tr .auctionScale").unbind("focus").bind("focus",function(){
				$(this).on('keyup',function(){
					var sku_tr = $(this).parents(".sku_tr");
					var price_val = sku_tr.find(".price").val();
					
					if(isNaN(price_val)){
						sku_tr.find(".price").val("0");
					}
					
					var auction_val = sku_tr.find(".auctionScale").val();
					if(isNaN(auction_val)){
						sku_tr.find(".auctionScale").val("0");
					}
					var auctionScale = ((auction_val * price_val)/100/2).toFixed(2);
					var deductAuction = auctionScale*2;
					var gainAuction = auctionScale;
					
					if(isNaN(auctionScale)){
						auctionScale = "";
					}
					if(isNaN(deductAuction)){
						deductAuction = "";
					}
					if(isNaN(gainAuction)){
						gainAuction = "";
					}
					sku_tr.find(".auction").val(auctionScale);
					sku_tr.find(".deductAuction").text(deductAuction);
					sku_tr.find(".gainAuction").text(gainAuction);
				});	
				});
		},
		bindSkuCheck : function(){
			$("#skuTable").on("focus",".sku",function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/\.\d{3,}/;
					if(!isNaN(num) && p1.test(num)){
						$(this).val((new Number(num)).toFixed(2));
					}
					if(isNaN(num)){
						$(this).val("");
					}
				})
			});
		},
		newSkuAdd : function(){
			$("#propTable").on('focus','.skuInput',function(){
				$(this).on('keyup',function(){
					var prevInput = $(this).prev();

					var arr = new Array();
					var index = 0;
					var param = {};
					var vid = $(prevInput).val();
					var vname;
					vname = $(prevInput).next().text();
					if(vname == null || vname == ""){
						vname = $.trim($(prevInput).next().val());
						if(vname == null || vname == ""){
							return false;
						}
					}
//					param[vid] = vname;
					$("#sku_tbody").find("tr[data-id="+vid+"]").remove();
					param["pVId"] = vid;
					param["pVName"] = vname;
					arr[index] = param;
					if($(prevInput).is(":checked")){
						sku.setSku(arr);
					}
				})
			});
			
			$("#propTable").on("click",".editval-del i",function(){
				var vid = $(this).parents("li").eq(0).find(".mySku").val();
				$("#sku_tbody").find("tr[data-id="+vid+"]").remove();
				$(this).parents("li").eq(0).remove();
				$(".editval-add").show();
			});
			
			$("#propTable").on("click",".editval-add",function(){
				   var liLenth = $(this).parents("tr").eq(0).find("td").eq(1).find("ul").find("li").length;
					var systemTime = sku.showLeftTime();
					var param = {};
					param["systemTime"] = sku.showLeftTime();
					var skuNode = _.template(propSkuMuBanTpl, param);
					var thisDl = $(this).parents("tr").eq(0);
					$(thisDl).find("td").eq(1).find("ul").append(skuNode);
					if((Number(liLenth)+1) > 2){
						$(this).hide();
					}
			})
			
			$("#propTable").on("mouseover",".edipt",function(){		
				$(this).find("i").css("display","block");
			});
			$("#propTable").on("mouseout",".edipt",function(){	
				$(this).find("i").css("display","none");
			});
		},
		showLeftTime : function(){
			var now=new Date();
			var year=now.getFullYear();
			var month=now.getMonth()+1;
			var day=now.getDate();
			var hours=now.getHours();
			var minutes=now.getMinutes();
			var seconds=now.getSeconds();
			var milliseconds = now.getMilliseconds(); 
			var xitongtime ="-"+year+""+month+""+day+""+hours+""+minutes+""+seconds+""+milliseconds;
			//一秒刷新一次显示时间
			return xitongtime;
		}
	}
	return sku;
});
