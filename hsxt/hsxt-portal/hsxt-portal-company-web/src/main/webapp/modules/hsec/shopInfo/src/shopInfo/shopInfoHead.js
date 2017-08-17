define([ "text!hsec_shopInfoTpl/shopInfo/shopInfoHead.html"
         ,"hsec_shopInfoDat/shopInfo"
         ,"text!hsec_shopInfoTpl/shopInfo/shopInfoList.html"
         ,"text!hsec_shopInfoTpl/baiduMap/baiduMap.html"
         ,"hsec_tablePointSrc/tablePoint"],
		function(tpl,ajaxRequest,shopInfoListHtml,baiduMap,tablePoint) {
		var shopListMain;
			var shop = {
				bindData : function(shopId,shopList) {
						shopListMain = shopList;
						tablePoint.bindJiazai(".operationsArea", true);
					ajaxRequest.findSalerShop("id=" + shopId, function(response) {
						tablePoint.bindJiazai(".operationsArea", false);
							var html = _.template(tpl, response.data.SalerShopWebBean.salerShop);
							$('#shopPanel').html(html);
							shop.bindClick();
							shop.bindTable(response.data);
						});
				},
				bindTable : function(response) {
					var html = _.template(shopInfoListHtml, response);
					$('#shopList').html(html);
					var address = response.SalerShopWebBean.salerShop.landmark;
					var landmark = address.split(",");
					shop.bindBaiDuMap(landmark);
				},
				bindClick : function() {
						//返回列表
						$("#returnShopInfo").unbind().on('click',function(){
							shopListMain.bindData();
						});
				},
				bindBaiDuMap : function(landmark){
					//地图定位
					$("#showPosition").unbind().on('click',function(){
						$('#dlg2').html(baiduMap);
						require(["hsec_shopInfoSrc/baiduMap/map"],function(shopMap){
						      var shopName= $("#shopInfoHead").val();
						      var contactWay=$("input[name='hotLine']").val();
						      var longitude = $.trim(landmark[1]);
						      var dimension = $.trim(landmark[0]);
						    
		  				$("#dlg2").dialog({
								title : "百度地图选择",
								width : "628",
								modal : true,
								open : function(){
									shopMap.initMap(shopName,contactWay,longitude,dimension);
								},
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'关闭' : function() {
										$(this).dialog("destroy");
									}
								}
							   });
					     });
					})
				
				}
			}
			return shop;
		});
