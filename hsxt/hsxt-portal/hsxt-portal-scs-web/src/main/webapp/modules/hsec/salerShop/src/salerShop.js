define(["text!hsec_salerShopTpl/salerShop/salerShopDetail.html",
         "text!hsec_salerShopTpl/salerShop/salerShopList.html",
         "text!hsec_salerShopTpl/salerShop/baiduMap.html",
         "hsec_salerShopDat/salerShopDat"], function(tpl6,tpl9,baiduMapHtml,ajaxRequest) {
var findPageParam={};
/********************************************************************************************************/
/******************************************modifly by zhanghh *******************************************/	
/******************************************修改成BsGrid显示方式*******************************************/
/********************************************************************************************************/	
  //绑定数据
  var shop = {
  	 //初始化ComboxList
	   selectList : function(){
	  		$("#shopTypeSelect").selectList({
				options:[
                    {name:'全部',value:''},
					{name:'零售',value:'1'},
					{name:'餐饮',value:'2'}
				]
			});
	   },
	   queryParam : function(){
	   		//待审核列表
			findPageParam["status"]=1;
			findPageParam["parentCode"]=0;
			findPageParam["shopType"]=$("#shopTypeSelect").attr("optionvalue");
			return findPageParam;
	   },
	   bindData : function() {
			//加载查询头 modifly by zhanghh 2016-02-04 
			$('#busibox').html(_.template(tpl9));
			shop.selectList();
			shop.loadBsGridData(shop.queryParam());
		  },
		  //BsGridData数据加载
		 loadBsGridData : function(queryParam){
		  	var gridObj =  $.fn.bsgrid.init('saler_shop', {
		  		url : {url:comm.UrlList["listSalerShop"],domain:"scs"},//comm.domainList["scs"]+comm.UrlList["listSalerShop"],
		  		pageSizeSelect: true,
                pageSize: 20,
                otherParames:$.param(queryParam),
                stripeRows: true,  //行色彩分隔 
				displayBlankRows: false,   //不显示空白行
				lineWrap:true,
				operate : {
					add : function(record, rowIndex, colIndex, options){
						var data = gridObj.getRecord(rowIndex);
						//console.info(data);
						var categoryNameStr = "";
						var salerShopAddress = "";
						var types = data.lstshopType;
						if(types != null && types != ''){
							$.each(types,function(key,type){
								categoryNameStr += type.categoryName;
							});
						}else{
							categoryNameStr = "无";
						}
						try{
							var area = eval("("+data.area+")");
							if(data.province==data.city){
								salerShopAddress = data.province+area[1]+data.addr;
							}else{
								salerShopAddress = data.province+data.city+area[1]+data.addr;
							}
						}catch(e){
							if(data.province==data.city){
								salerShopAddress = data.province+data.addr;
							}else{
								salerShopAddress = data.province+data.city+data.addr;
							}
						}
						if(colIndex == 0){
							return data.resourceNo;
						}
						if(colIndex == 1){
							return data.companyName;
						}
						if(colIndex == 2){
							return categoryNameStr;
						}
						if(colIndex == 3){
							return salerShopAddress;
						}
						if(colIndex == 4){
							return '<button data-id="'+rowIndex+'" class="btn-search ml10 salerDetail" status="'+data.status+'" salerShopId="'+data.id+'" landmark="'+data.landmark+'" vshopId = "'+data.vShopId+'">查看详情</button>';
						}
					}
				},
				complete : function(o, e, c){
					//查看详情
					$(".salerDetail").on("click", function() {
						var salerShopId = $(this).attr("salershopid");
						var vshopId =  $(this).attr("vshopId");
						var param = {"id":salerShopId,"VShopId":vshopId};
						var landmark = $(this).attr("landmark");
						shop.showDetail(param,landmark);
					});
					
					shop.bindHeadClick();
				}
		  	});
		  },
		  bindHeadClick : function(){
				//编号输入验证
				$("#resourceNo").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$("#findSalerShopList").click(function(){
					findPageParam["resourceNo"] = $("#resourceNo").val();
					findPageParam["companyName"] = $("#companyName").val();
					findPageParam["shopType"]=$("#shopTypeSelect").attr("optionvalue");
					shop.loadBsGridData(findPageParam);
				});
			},
		  //查看营业点详情
		  showDetail:function(param,landmark){
			    ajaxRequest.getSalerShopDetail(param, function(response) {
					if(response.retCode==200){
						$('#showSalerShop').html(_.template(tpl6, response));
						//显示地图
						$("#showSalerShop .stdxq_contentright").html(baiduMapHtml);
						require(["hsec_salerShopSrc/map"],function(shop){
							shop.initMap(landmark,1);
						});//end
						$("#showSalerShop").dialog({
							title : "营业点详情",
							width : 920,
							height: 440,
							closeIcon : true
						});
						$(".ui-widget-header").addClass("norwidth");
					}else if(response.retCode==201){
						tablePoint.tablePoint("对不起，请求失败");
					}else if(response.retCode==212){
						tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
					}
				 })
		  }
	
	  }
	return shop;
});