define([ "text!hsec_itemCatChooseTpl/itemAdd/prop.html"
         ,"text!hsec_itemCatChooseTpl/itemUpdate/prop.html"
         ,"text!hsec_itemCatChooseTpl/itemUpdate/pinpaiList.html"
         ,"hsec_itemInfoDat/itemInfo"
         ,"hsec_itemCatChooseDat/itemCatChoose"
         ,"hsec_itemCatChooseSrc/itemAddSKU/itemInfoAddSKU"
         ,"hsec_itemCatChooseSrc/itemUpdateSKU/itemInfoAddSKU"
         ,"hsec_itemCatChooseSrc/itemUpdateSKU/itemInfoUpdateSKU"
         ], function(addporpTpl,updateporpTpl,pinpaiListTpl,ajaxItemInfo,itemCatChoose,itemInfoAddSKU,itemInfoUpdateAddSKU,itemInfoUpdateSKU) {
	
		var prop = {
			bindData : function(catId){
				ajaxItemInfo.initItemProp({"catId":catId},function(response){
					var html = _.template(addporpTpl, response);
					$('#prop_div').html(html);
					prop.bindFun();
					itemInfoAddSKU.InitSpuSku(ajaxItemInfo);
				});
			},
			bindUpdate : function(response){
				response["uniqueArray"] = prop.uniqueArray;
				$('#prop_div').html( _.template(updateporpTpl, response));
				try{
					itemCatChoose.initItemPinpaiInfo("catId="+response.data.ItemInfo.categoryId, function(pinpai) {
							$('#itemPinpaiList').html(_.template(pinpaiListTpl, pinpai));	
							
							prop.bindFun();
							itemInfoUpdateSKU.InitSpuSku(ajaxItemInfo,response.data);
							itemInfoUpdateAddSKU.InitSpuSku(ajaxItemInfo);
					})
				}catch(e){
					prop.bindFun();
					itemInfoUpdateSKU.InitSpuSku(ajaxItemInfo,response.data);
					itemInfoUpdateAddSKU.InitSpuSku(ajaxItemInfo);
				}
			},
			skuImgList : function(){
				var img = null;
				if($("#itemMain").attr("itemId") != null && $("#itemMain").attr("itemId") != ""){
					img = itemInfoUpdateSKU.skuImg();
				}else{
					img = itemInfoAddSKU.skuImg();
				}
				return img;
			},
			skuImgUpdateList : function(){
				return itemInfoUpdateAddSKU.skuImg();
			},
			skuImgDeleteList : function(){
				return itemInfoUpdateSKU.skuImgdelete();
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
			},
			bindFun : function(){
				$('#skuTabs').tabs();
				$("#itemPinpai").on("click",function(){
					$("#itemPinpaiList").show();
				});
				
				$("#itemPinpaiList").on("mouseover","li",function(){
					$("#itemPinpaiList>li").removeClass("bg_f6f6f6");
					$(this).addClass("bg_f6f6f6");
				});
				
				$("#itemPinpaiList").on("click","li",function(){
					$("#itemPinpai").val($(this).html());
					$("#itemPinpai").attr("valueId",$(this).attr("data-code"));
					$("#itemPinpaiList").hide();
				});
				
				
				$("#itemPinpai").on('focus',function(){
					$(this).on('keyup',function(){
					 var inputValue = $.trim($(this).val());
					 var bool = 0,list = 0;
					 $.each($("#itemPinpaiList>li"),function(k,v){
						 if($(v).html().indexOf(inputValue) > -1){
							 list = 1;
							 $(v).show();
						 }else{
							 $(v).hide();
						 }
						 if($(v).html() == inputValue){
							 bool = 1;
							 $("#itemPinpai").val(inputValue);
							 $("#itemPinpai").attr("valueId",$(v).attr("data-code"));
						 }
					 })
					 if(list == 1){
						 $("#itemPinpaiList").show();
					 }else{
						 $("#itemPinpaiList").hide();
					 }
					 if(bool == 0){
						 $("#itemPinpai").attr("valueId","");
					 }
					})
				});
				
				$("#itemPinpaiSelect").on("click",function(){
					if($("#itemPinpaiList").is(":hidden")){
						$("#itemPinpaiList").show();
						$("#itemPinpaiList>li").removeClass("bg_f6f6f6").show();
					}else{
						$("#itemPinpaiList").hide();
					}
				})
				
				$("#itemMain").on("mouseover",".editval li",function(){		
					$(this).find("i").css("display","block");
				});
				$("#itemMain").on("mouseout",".editval li",function(){	
					$(this).find("i").css("display","none");
				});
			}
		}
		return prop;
});