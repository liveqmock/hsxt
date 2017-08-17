define(["hsec_shopCouponManagerDat/shopCouponActivity",
        "text!hsec_shopCouponManagerTpl/shopCouponHead.html",
        "text!hsec_shopCouponManagerTpl/shopShowDetail.html",
        "hsec_tablePointSrc/tablePoint"],function(ajaxRequest,headHtml,DetailHtml,tablePoint){
	
	//全局查询参数
	var queryList = {"currentPageIndex":1};
	var gridObj;
	var shopCoupon = {
		queryParam : function(){
			queryList = {};
			queryList["companyResourceNo"] = $("#companyResourceNo").val();
			//queryList["couponId"] = $("#couponId").val();
			queryList["virtualShopName"] = $("#virtualShopName").val();
			queryList["couponName"] = $("#couponName").val();
			queryList["eachPageSize"] = 10;
		},
		bindData : function(){
			$('#busibox').html(headHtml);
			//shopCoupon.bindHeadClick();
			shopCoupon.queryParam();
			shopCoupon.bindTableData(queryList);
		},
		//查询头事件
		bindHeadClick : function(){
			//编号输入验证
			$("#companyResourceNo").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#searchBar").bind('click',function(){
				shopCoupon.queryParam();
				shopCoupon.bindTableData(queryList);
			});
		},
		bindTableData : function(queryList){
			gridObj = $.fn.bsgrid.init('shop_coupon',{
				url:{url:comm.UrlList['searchCompanyCoupon'],domain:'scs'},
				otherParames:$.param(queryList),
				pageSize:10,
				pageSizeSelect:true,
				displayBlankRows:false,
				lineWrap:false,
				stripeRows:true,
				operate:{
					add : function(record, rowIndex, colIndex, options){
						if(colIndex==3){
							if(record.faceAmount != null && record.faceAmount != ""){
								return record.faceAmount;
							}else{
								return ""
							}
						}
						if(colIndex==4){
							if(record.releaseNumber != null && record.releaseNumber != ""){
								return record.releaseNumber;
							}else{
								return ""
							}
						}
						if(colIndex==5){return record.faceAmount*record.releaseNumber}
						if(colIndex==7){
							if(null != record.expEnd&&'' != record.expEnd){
				              return record.expEnd.split(" ")[0]
				            }else{
				              return '长期'
				            }
						}
						if(colIndex==8){
							if(null != record.expEnd&&'' != record.expEnd){
								return '<a class="couponDetail" couponId="'+record.couponId+'" shopCouponId="'+record.id+'" couponName="'+record.couponName+'" faceAmount="'+record.faceAmount+'" releaseNumber="'+record.releaseNumber+'"  releaseTime="'+record.expEnd.split(" ")[0]+'">详情</a>'
							}else{
								return '<a class="couponDetail" couponId="'+record.couponId+'" shopCouponId="'+record.id+'" couponName="'+record.couponName+'" faceAmount="'+record.faceAmount+'" releaseNumber="'+record.releaseNumber+'"  releaseTime="长期">详情</a>'
							}
						}
					}
				},
				complete:function(o,e,c){
					shopCoupon.bindHeadClick();
					shopCoupon.showDetailClick();
				}
			});
		},
		showDetailClick : function(){
			$(".couponDetail").on("click",function(){
				var couponId = $(this).attr("couponId");
				var shopCouponId = $(this).attr("shopCouponId"); 
			    var couponName = $(this).attr("couponName");
			    var faceAmount = $(this).attr("faceAmount");
			    var releaseNumber = $(this).attr("releaseNumber");
				var releaseTime = $(this).attr("releaseTime");
				ajaxRequest.getShopCouponPartitionList(shopCouponId,function(response){
					/*console.info(response);该句代码导致ie6下报错，所以把它注释 by kuangrb 2016-06-05*/
					var dataVal = {};
					dataVal.data = response.data;
					dataVal.couponId = couponId;
					dataVal.shopCouponId = shopCouponId;
					dataVal.couponName = couponName;
					dataVal.faceAmount = faceAmount;
					dataVal.releaseNumber = releaseNumber;
					dataVal.releaseTime = releaseTime;
					var html = _.template(DetailHtml,dataVal);
					$("#coupon_show_view").html(html);
					$("#coupon_show_view").dialog({
						show: true,
						modal: true,
						title:"抵扣券详情",
						width:550,
						open : function(){
							$(".ui-dialog-titlebar-close").click(function(){
								$("#coupon_show_view").dialog("destroy");
							});
						},
						buttons: {					  
						  '关闭' : function(){
							  $(this).dialog( "destroy" );
						    }	
						}
					 });
				});
			});
		}
	}
	
	return shopCoupon;
});