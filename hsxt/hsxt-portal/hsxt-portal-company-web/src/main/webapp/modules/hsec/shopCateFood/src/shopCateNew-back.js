define(["hsec_shopCateFoodDat/shopCate",
   	 	"hsec_tablePointSrc/tablePoint",		
        "text!hsec_shopCateFoodTpl/index.html",
        "text!hsec_shopCateFoodTpl/tab.html",
        "text!hsec_shopCateFoodTpl/gridMain.html",
        "text!hsec_shopCateFoodTpl/list.html",
        "text!hsec_shopCateFoodTpl/update/foodCateDialog.html",
        "text!hsec_shopCateFoodTpl/update/itemCateDialog.html",
        "text!hsec_shopCateFoodTpl/relation/relationFood.html",
        "text!hsec_shopCateFoodTpl/relation/relationItem.html",
        "text!hsec_shopCateFoodTpl/relation/relationItemGrid.html",
        "text!hsec_shopCateFoodTpl/relation/shopName.html",
        "text!hsec_shopCateFoodTpl/relation/addRelationFood.html",
        "text!hsec_shopCateFoodTpl/relation/addRelationFoodHeader.html",
        "text!hsec_shopCateFoodTpl/relation/addRelationItem.html",
        "text!hsec_shopCateFoodTpl/relation/addRelationItemGrid.html",
        "text!hsec_shopCateFoodTpl/update/categoryLi.html",
        "hsec_shopCateFoodSrc/function"],
    function(ajaxRequest,tablePoint,indexTpl,tabTpl,gridMainTpl,listTpl,foodCateDialogTpl,
    		itemCateDialogTpl,relationFoodTpl,relationItemTpl,relationItemGridTpl,shopNameTpl,
    		addRelationFoodTpl,addRelationFoodHeaderTpl,addRelationItemTpl,addRelationItemGridTpl,categoryLiTpl){	
//	var eachPageSize = 10;	//每页显示数据
	var queryParam = {"currentPageIndex":1};
	var https = "http://192.168.229.31:9099/v1/tfs/";
	var industry = "";
	var responseDetail = {};
	var shopCate = {
		init : function(param){
//			$('.operationsArea').html(indexTpl);		
			$(".tabList a").unbind("click").bind("click",function(){
				$(".tabList a").removeClass("active");
				$(this).addClass("active");				
				var did = $(this).attr("data-id");
				queryParam.industry = did;
				shopCate.loadBsGridData(queryParam);
			});
			//$('.operationsArea').html(gridMainTpl);
			$(".shopbox").hide();
			$('#cgbox1').html(gridMainTpl).show();
			//shopCate.bindList({"industry":param!=null?param:1});
			queryParam.industry = param;
			shopCate.loadBsGridData(queryParam);
		},
		loadBsGridData : function(param){
			var gridObj;
			$(function(){
				gridObj = $.fn.bsgrid.init('gridMain',{
					url:comm.domainList["sportal"]+comm.UrlList["queryShopCategory"],
					pageSize:500,
					otherParames:param,
					displayBlankRows:false,
					stripeRows:true,
					operate:{
						add : function(record, rowIndex, colIndex,options){
							var sTpl = "";
							var name = record.name;
							var picUrl = record.picUrl;
							var parentId = record.parentId;
							var id = record.id;
							var categoryId = record.categoryId;
							var sortOrder = record.sortOrder;
							industry = param.industry;
							switch (colIndex) {
								case 0 :
									if(parentId == 0){
										sTpl = name;
									}else{
										sTpl = '<span class="sccg">'+name+'</span><input type="button" class="upgrade-arrow ml10 none" title="提升一级" objId="'+id+'" objParentId="'+parentId+'" objCategoryId="'+categoryId+'" objName="'+name+'" objPicUrl="'+picUrl+'" objSortOrder="'+sortOrder+'">';
									}
									break;
								case 1 :
									sTpl = '<img src="'+https+picUrl+'" width="40">';
									break;
								case 2 :
									if(industry!=1){
						         		sTpl = '<input type="button" class="list-up-arrow" title="向上移动" objId="'+id+'"/>';
						          		sTpl += '<input type="button" class="list-down-arrow" title="向下移动" objId="'+id+'"/>';
						         	}else{
						    			sTpl = sortOrder;
						         	}
									break;
								case 3 :
									if(industry==1){
										if(parentId != 0){
											sTpl = '<a class="spxx" objId="'+id+'" objParentId="'+parentId+'" objCategoryId="'+categoryId+'" objName="'+name+'" objPicUrl="'+picUrl+'" objSortOrder="'+sortOrder+'">商品信息</a>';
										}
									}else{
										sTpl = '<a class="cpxx" objId="'+id+'" objParentId="'+parentId+'" objCategoryId="'+categoryId+'" objName="'+name+'" objPicUrl="'+picUrl+'" objSortOrder="'+sortOrder+'">菜品信息</a>';
									}
									break;
								case 4 :
									sTpl = '<a class="goods_edit mr10" objId="'+id+'" objParentId="'+parentId+'" objCategoryId="'+categoryId+'" objName="'+name+'" objPicUrl="'+picUrl+'" objSortOrder="'+sortOrder+'">编辑</a><a class="goods_del" objId="'+id+'" objName="'+name+'">删除</a>';
									break;
								default :
									break;
							}
							return sTpl;
						}
					},
					complete:function(options, XMLHttpRequest, textStatus){
						var other = eval("("+XMLHttpRequest.responseText+")").orther;
						if (other){
							https = other.itemVisitUrl;
						}
						
						industry = other.industry;
						shopCate.bindFun();			
						if(param.industry=='1'){
							shopCate.upgradeItemSalerCategoryEvent();
						}else if(param.industry=='2'){
							shopCate.listUpOrDown();
						}
						$(".spxx").unbind().on("click",function(){
							shopCate.detailItemOprate($(this).attr("objId"));
						});
					}
				});
			});
		},
		detailItemOprate : function(obj){
			$(".shopbox").hide();
			$("#cgbox2").show();	
			$("#cgbox2").html(_.template(relationItemGridTpl));
			//当前页			
			var $this=this;
			var queryRalationParam={};	//查询关联的商品
				queryRalationParam["status"] = "1";	//查询关联的商品
				queryRalationParam["shopCategoryId"]=obj;
				queryRalationParam["status"]=3;		//查询已经上架的商品				
				queryRalationParam["currentPageIndex"] = 1;  	
			var paramItems = {"jsonItemInfo" : JSON.stringify(queryRalationParam)}; 	
				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
				paramItems.searchStatus="1";	//查询关联的商品 	
				shopCate.loadDetailGridData(paramItems);
			//查询
			$("#cgbox2 #searchItem_Listhead").on('click',function(){	    	
    	    	queryRalationParam["name"]=$("#itemName_Listhead").val();
    	    	queryRalationParam["brandName"]=$("#itemBrand_Listhead").val();
    	    	queryRalationParam["status"] = "1";	//查询关联的商品
				queryRalationParam["shopCategoryId"]=obj;
				queryRalationParam["status"]=3;		//查询已经上架的商品				
				queryRalationParam["currentPageIndex"] = 1;
				var paramItems = {"jsonItemInfo" : JSON.stringify(queryRalationParam)}; 	
				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
				paramItems.searchStatus="1";	//查询关联的商品 	
  				shopCate.loadDetailGridData(paramItems); 
			 });
			 //新增关联页面
			$("#cgbox2 #searchItem_categoryShop").on('click',function(){
				shopCate.addItemRealationGrid(obj);	
			});
		},
		addItemRealationGrid : function(obj){
			$(".shopbox").hide();
			$("#cgbox3").show();	
			$("#cgbox3").html(_.template(addRelationItemGridTpl));
			var $this=this;			
			var queryNotRalationParam={};	//查询关联的商品						
				queryNotRalationParam["shopCategoryId"]=obj;		
				queryNotRalationParam["status"]=3;		//查询已经上架的商品			
				queryNotRalationParam["currentPageIndex"] = 1;
  				var paramItems = {"jsonItemInfo" : JSON.stringify(queryNotRalationParam)}; 
  				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
  				paramItems.searchStatus="0";	//查询未关联的商品 
  				shopCate.loadAddGridData(paramItems);
	  			$("#cgbox3 #searchItem_Listhead2").unbind().on("click",function(){
	  				var queryNotRalationParam={};	//查询关联的商品						
					queryNotRalationParam["shopCategoryId"]=obj;		
					queryNotRalationParam["status"]=3;		//查询已经上架的商品			
					queryNotRalationParam["currentPageIndex"] = 1;
					queryNotRalationParam["name"]=$("#itemName_Listhead2").val();
		    	    queryNotRalationParam["brandName"]=$("#itemBrand_Listhead2").val();	
	  				var paramItems = {"jsonItemInfo" : JSON.stringify(queryNotRalationParam)}; 
	  				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
	  				paramItems.searchStatus="0";	//查询未关联的商品 
		    	    shop.loadAddGridData(paramItems);  					  			
				});
		},
		//零售分类商品信息关联关系
		loadAddGridData : function(queryParam){
			var gridDetailObj = $.fn.bsgrid.init('gridItemAdd',{
				url:comm.domainList["sportal"]+comm.UrlList["getRelationItem"],
				//不显示空白行
				displayBlankRows: false,
				pageSize:20,
				otherParames:queryParam,
				operate : {
					add : function(record, rowIndex, colIndex, options){
						var sTpl = "";
						switch (colIndex) {
							case 0 :
								sTpl = _.template(shopNameTpl,record);
								break;
							case 4 :
								sTpl = '<a class="addItemRelation" data-id="'+record.id+'">设置分类商品</a>';
								break;
							default :
								break;
						}
						return sTpl;
					}
				},
				complete : function(e,o){
					 tablePoint.checkBoxAll($("#all_item2"),$("input[name='itemDataCheckBox']"));
					 //设置分类商品
					 $(".addItemRelation").click(function(){
						shopCate.addItemRealationData($(this).attr("data-id"));
					});
				}
			});
			$(".cancel2").click(function(){
				$(".shopbox").hide();
				$("#cgbox2").show();
			});
		},
		//零售分类商品信息
		loadDetailGridData : function (queryParam){
			var gridDetailObj = $.fn.bsgrid.init('gridItemDetail',{
				url:comm.domainList["sportal"]+comm.UrlList["getRelationItem"],
				//不显示空白行
				displayBlankRows: false,
				pageSize:20,
				otherParames:queryParam,
				operate : {
					add : function(record, rowIndex, colIndex, options){
						var sTpl = "";
						switch (colIndex) {
							case 0 :
								sTpl = _.template(shopNameTpl,record);
								break;
							case 4 :
								sTpl = '<a class="cancelItemRelation" data-id="'+record.id+'">删除</a>';
								break;
							default :
								break;
						}
						return sTpl;
					}
				},
				complete : function(e,o){
					 tablePoint.checkBoxAll($("#all_item"),$("input[name='itemDataCheckBox']"));
					 //取消关联
					 $(".cancelItemRelation").click(function(){
						shopCate.cancelItemRealation($(this).attr("data-id"),$this);
					});
				}
			});
			$(".cancel1").click(function(){
				$(".shopbox").hide();
				$("#cgbox1").show();
			});
		},
		bindList : function(param){
//			ajaxRequest.queryShopCategory(param,function(response){
//				var html = _.template(listTpl,response);
//				$(".shopbox").hide();
//				$('#cgbox1').html(html).show();		
//				shopCate.bindFun();			
//				if(param.industry=='1'){
//					shopCate.upgradeItemSalerCategoryEvent();
//				}else if(param.industry=='2'){
//					shopCate.listUpOrDown();
//				}
//			});
		},
		//餐饮自定义分类排序上升或下降操作
		listUpOrDown : function(){	
			$(".list-up-arrow,.list-down-arrow").click(function(){
				  //var parentTr=$(this).parents("tr")[0];	
				  var param={'id':$(this).attr("objId")}; 
				  //1升序、2降序
				  if($(this).hasClass("list-up-arrow")){
					 if($(this).prev().length==0)
						 param.replaceId="";
					 else
						 param.replaceId=$(this).prev().attr("objId");					
				  }else{
					 if($(this).next().length==0)
						 param.replaceId="";
					 else
						 param.replaceId=$(this).next().attr("objId");
				  }
				  tablePoint.bindJiazai($(".operationsInner"),true);	
				  ajaxRequest.sortFoodCategory(param,function(response) {
					  tablePoint.bindJiazai($(".operationsInner"),false);	
						if (response.retCode == "200") {
							//shopCate.bindList({"industry":"2"});	
							shopCate.loadBsGridData({"industry":"2"})
						}else{
							tablePoint.tablePoint("操作失败!");
						}
							
				   })				 	  
			});
			
		},
		
		//上传图片事件
		uploadPicEvent : function(param){
			 $(".itemPhoto").unbind().on('click',function(){
					var obj = this;
					$(".itemPhoto").attr("id","");
					$(obj).attr("id","itemPhoto");
					$("#itemPhoto").unbind().on('change',function(){
						imgUpload($(obj).parent().find(".imgshowDiv"));
					});
			});
		},
		//加载新增或修改零售的弹出框
		loadOprateItemDialog: function (loadData){
			  if(loadData==null){
			   	 var title="添加分类";  
			  }else{
			     var title="修改分类";  
			  }
			 //新增零售分类
			  var plateFormCateId="";
			  var plateFormCateName="";
			  $( "#dlg2" ).dialog({
				show: true,
				modal: true,
				title:title,
				width:700,
				height:410,
				open : function(){					
					ajaxRequest.getAddBaseData({},function(response){
						var platformList=response.data.platformList;
						var html = _.template(itemCateDialogTpl,response);
					  	$("#dlg2").html(html);
					  	if(loadData!=null){
					  		 $("#dlg2 input[name='name']").val(loadData.name);
					  		 $("#parentSelect").val(loadData.parentId);
					  		 $("#dlg2 input[name='sortOrder']").val(loadData.sortOrder);
					  		 if(comm.isNotEmpty(loadData.srcname)){
					  			 $("#dlg2 .imgshowDiv").html("<img src='' class='imgshow' srcname=''>");
					  			 $("#dlg2 .imgshow").attr("src",loadData.src);
	  							 $("#dlg2 .imgshow").attr("srcname",loadData.srcname);		  								  							
					  		 }					  		 							
					  	}
						shopCate.bindCateGoryClick(platformList);
						//分类目录选择
						$("#gArrow2").click(function(){						
							$(".itemPhoto").removeClass("files");		//上传图片的事件关闭					 
							if($(".gCg").is(":visible")){
								$(".gCg").hide();
							}else{
								$(".gCg").show();
							}
						  });
						  $(".sc a").click(function(){
							$(this).addClass("active").parent().siblings().children().removeClass("active");
						  });
						  shopCate.uploadPicEvent();
						  var subBtn = $(".gCg").children().last().find("input");
						  subBtn.click(function(){							
							  if($(this).hasClass("sno")){
								  $(this).parent().parent().hide();
								  $(".itemPhoto").addClass("files");	//加载图片事件
							  }
							  if($(this).hasClass("syes")){
								  var act = $("#cateGory ul").find("li.active");							
								  if(act.length==3){
									  $("#cgtxt").val(act.eq(2).text()).attr("title",act.eq(2).text());
									  plateFormCateName=act.eq(2).text();
									  plateFormCateId=act.eq(2).attr("data-id");
									  $(".gCg").hide();
									  $(".itemPhoto").addClass("files");	//加载图片事件
								  }
								  else if(act.length==2){
									  $("#cgtxt").val(act.eq(1).text()).attr("title",act.eq(1).text());
									  plateFormCateName=act.eq(1).text();
									  plateFormCateId=act.eq(1).attr("data-id");
									  $(".gCg").hide();
									  $(".itemPhoto").addClass("files");	//加载图片事件
								  }
								  else if(act.length==1){
									  $("#cgtxt").val(act.eq(0).text())
										   .attr("title",act.eq(0).text());
									  plateFormCateName=act.eq(0).text();
									  plateFormCateId=act.eq(0).attr("data-id");
									  $(".gCg").hide();
									  $(".itemPhoto").addClass("files");	//加载图片事件
								  }else{
									  tablePoint.tablePoint("请选择类目名称！"); 
									  return false;
								  }									  								
							  }		
						  });
					  });	 
				},
				buttons: {
				'保存': function() {
						 //保存自定义类目id start							  
						 var faParam={};
						 faParam["parentId"] =$("#parentSelect").val();
						 faParam["industry"]='1';		  							
						 var img = $(".imgshow").attr("srcname");			
						 if(img !=null && img != ''){
							 faParam["picUrl"] = img;
						 }else{
							 faParam["picUrl"] = '';
						 }
						
						 var name = $("#dlg2 input[name='name']").val();
						 //判断新增或修改
						 if(loadData!=null){
							 faParam["id"] = loadData.id;
							 faParam["logicBoolean"] = "1";  //INSERT(0),UPDATE(1),DELETE(2);
						 }else{
							 faParam["logicBoolean"] = "0";  //INSERT(0),UPDATE(1),DELETE(2);	 
						 }
						 faParam["name"] = name;						 
						 //判断名字是否修改
						 if(comm.isNotEmpty(plateFormCateId)&&name==plateFormCateName){
							 faParam["categoryId"] = plateFormCateId; 
						 }
						 faParam["sortOrder"]=$("#dlg2 input[name='sortOrder']").val();
						 faParam["status"] = "1";
						 
						 //验证信息	  									  						
						 if(CheckCode(name)){
							 tablePoint.tablePoint("请检查填写的值!输入有误,值长度为1~15,不能特殊符号!");
							 return false;
						 }
						 //验证排序字段	  									  						
						 if(comm.isEmpty(faParam["sortOrder"]) || (isNaN(faParam["sortOrder"])) ){
							 tablePoint.tablePoint("排序字段不能为空,且必须为数字!");
							 return false;
						 }
						ajaxRequest.saveSalerShopCategoryNew("shopCategory="+JSON.stringify(faParam),function(response) {	  							
							$("#dlg2").dialog( "destroy" );//关闭窗口  						
							if (response.retCode == "200") {
								tablePoint.tablePoint("操作成功！",function(){
									//shopCate.bindList({"industry":"1"});
									shopCate.loadBsGridData({"industry":"1"});
								});																		
							}else if(response.retCode == "206"){
								tablePoint.tablePoint("商品分类信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再添加！")
							}else if(response.retCode == "305"){
								tablePoint.tablePoint("操作失败!"+response.data);
							}else{
								//tablePoint.tablePoint("最多可设置99个自定义分类！");
								tablePoint.tablePoint(response.data);
							}
					   })
					   //保存自定义类目id end
					},
				'取消': function() { $( this ).dialog( "destroy" ); }
				}
			  });		 
			  //end
		},
		bindCateGoryClick : function(cateGoryList){		
			$("#cateGory").on("click","ul>li>ul>li",function(){	
				$(this).parents("li").eq(0).find("li").removeClass("active");
				$(this).addClass("active");
				var cateId = $(this).attr("data-id");
				var param = {};
				param["category"] = cateGoryList;
				param["cateId"] = cateId;
				var html = _.template(categoryLiTpl, param);
				$(this).parents("li").eq(0).next().html(html);
			})
		},
		//零售商品信息关联操作
		cateRealationItemOprate : function(){
			var currentPageIndex1 = 1;				
			var queryRalationParam={};	//查询关联的商品
			queryRalationParam["status"] = "1";	//查询关联的商品
			$(".spxx").click(function(){
				//当前页			
				var $this=this;
				//var parentTr= $($this).parents("tr")[0];			
				var objId=$($this).attr("objId");
				queryRalationParam["shopCategoryId"]=objId;
				queryRalationParam["status"]=3;		//查询已经上架的商品				
				queryRalationParam["currentPageIndex"] = currentPageIndex1;
  				var paramItems = {"jsonItemInfo" : JSON.stringify(queryRalationParam)}; 	
  				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
  				paramItems.searchStatus="1";	//查询关联的商品 	
  				ajaxRequest.getRelationItem(paramItems, function(response) {
					tablePoint.bindJiazai("#typeHide", false);	
					response["responseState"] = "200";
					response["typeState"] ="1";	
					var html = _.template(relationItemTpl,response);
					$(".shopbox").hide();
					$("#cgbox2").show();	
					$("#cgbox2").html(html);
					$("#itemName_Listhead").val(queryRalationParam.name);
	    	        $("#itemBrand_Listhead").val(queryRalationParam.brandName);    	
					$("#cgbox2 #searchItem_Listhead").unbind().on('click',function(){	   
		    	    	var name=$("#itemName_Listhead").val();
		    	    	var brandName=$("#itemBrand_Listhead").val();		    	
		    	    	queryRalationParam["name"]=name;
		    	    	queryRalationParam["brandName"]=brandName;		  			  			
		  				currentPageIndex=1;		  				
		  				queryRalationParam["eachPageSize"] = eachPageSize;						
		  				$($this).click();  
					 });
					 tablePoint.checkBoxAll($("#all_item"),$("input[name='itemDataCheckBox']"));
					 //取消关联
					 shopCate.cancelItemRealation(objId,$this);
					 $(".cancel1").click(function(){
							$(".shopbox").hide();
							$("#cgbox1").show();	
					 });
					 //分页事件
					 $(".pageOrder2").unbind().on('click',function(){
					    	var num = $(this).attr("page");
					    	if(num == '' || num == null){
					    		return false;
					    	}
					    	currentPageIndex1=num;
					    	$($this).click();  
					    });
					    //分页输入
			    	   $('#pageInput2').unbind().on('keypress',function(event){
			            if(event.keyCode == "13")    
			            {
			            	var num = $(this).val();
			            	var totalPage = Number($(this).attr("totalPage"));
				            	if(num > totalPage  || num <= 0 || isNaN(num)){
				            		tablePoint.tablePoint("输入范围错误!请再次输入!");
				            		return false;
				            	}else{
				            		currentPageIndex1=num;
				            		$($this).click();  
				            	}
			            }
				       }); 
					 
					 //新增关联页面
					 shopCate.addItemRealation(objId);							
  				})	
			});
		},
		//零售自定义分类二级菜单更改为一级菜单
		upgradeItemSalerCategoryEvent : function(){
//			$("#lsfllb tbody tr").hover(function(){
			$("#gridMain tbody tr").hover(function(){
				var chrd = $(this).children("td").children();
				if(chrd.hasClass("sccg")){chrd.siblings("input").removeClass("none");}
			},function(){
				var chrd = $(this).children("td").children();
				chrd.siblings("input").addClass("none");
			});
			$(".upgrade-arrow").click(function(){
				  //var parentTr= $(this).parents("tr")[0];
				  var urlPrefix=https;//$("input[name='urlPrefix']").val();
				  $(this).addClass("azz");
				  $( "#dlg1" ).dialog({
					show: true,
					modal: true,
					title:"操作确认",
					width:400,
					buttons: {
					'确认': function() {
						
						var editData={
								"id": $(this).attr("objId"),
								"parentId": '0',
								"name":$(this).attr("objName"),
								"categoryId":$(this).attr("objCategoryId"),							
								"src":urlPrefix+$(this).attr("objPicUrl"),
								"srcname":$(this).attr("objPicUrl"),
								"sortOrder":$(this).attr("objSortOrder"),
								"industry":1,
								"logicBoolean":'3'  // 更改为一级菜单;	
								};						
							ajaxRequest.saveSalerShopCategoryNew("shopCategory="+JSON.stringify(editData),function(response) {	  							
												
							if (response.retCode == "200") {
								tablePoint.tablePoint("操作成功！",function(){									
									//shopCate.bindList({"industry":"1"});	
									shopCate.loadBsGridData({"industry":"1"});
								});																		
							}else if(response.retCode == "206"){
								tablePoint.tablePoint("商品分类信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再添加！")
							}else if(response.retCode == "305"){
								tablePoint.tablePoint("操作失败!一级分类"+editData.name+"已经存在");
							}else{								
								tablePoint.tablePoint("操作失败,"+response.data);
							}
							$("#dlg1").dialog( "destroy" );//关闭窗口  
					   })	
					},
					'取消': function() {$( this ).dialog("destroy");}
					}
				  });
				});
		},
		//取消商品关联
		cancelItemRealation : function(shopCategoryId,refreshObj){
			 //取消关联的按钮
		       $(".cancelItemRelation,.batchCancelItemRelation").unbind().on('click',function(){
		    	   var cancelClick=$(this);
		    	   comm.i_confirm("确定取消关联?", function() {
				   var cancelItemRelationParam={};	
				   if($(cancelClick).hasClass("cancelItemRelation")){						
						  cancelItemRelationParam.itemIds=$(cancelClick).parents("tr").attr("itemid")+",";	//商品id多个商品，号分开
				   }
				   if($(cancelClick).hasClass("batchCancelItemRelation")){
					   var itemIds="";
					   $("input[name='itemDataCheckBox']:checked").each(function(index,obj){
							  itemIds+=$(obj).parents("tr").attr("itemid")+",";							
						});				 								
						if(itemIds==""){
							 tablePoint.tablePoint("请选择商品!");
							 return false;
						}else{
						 	cancelItemRelationParam.itemIds=itemIds;	//商品id多个商品，号分开
						}
				   }
			    	cancelItemRelationParam.salerCategoryId=shopCategoryId;			//商城分类id	
			    	tablePoint.bindJiazai($(".operationsInner"),true);	 
			    	ajaxRequest.delSalerShopCategory(cancelItemRelationParam, function(response) {
			    		if (response.retCode == "200") {
			    				//刷新商品菜单
				    			tablePoint.bindJiazai($(".operationsInner"),false);	 
								tablePoint.tablePoint("操作成功！",function(){
								$(refreshObj).click();  
							});											 																											
						}else{
							tablePoint.bindJiazai($(".operationsInner"),false);
							tablePoint.tablePoint("操作失败!");
						}
			    	});
			   })					   					   	
		    });
		},
		//新增关联的按钮GridClick
		addItemRealationData : function(shopCategoryId){
			//新增关联的按钮
	       $(".addItemRelation,.batchAddItemRelation").unbind().on('click',function(){
	    	   var addClick=$(this);
	    	   comm.i_confirm("确定新增关联?", function() {
			   var addItemRelationParam={};	
			   if($(addClick).hasClass("addItemRelation")){						
					  addItemRelationParam.itemIds=$(addClick).parents("tr").attr("itemid")+",";	//商品id多个商品，号分开
			   }
			   if($(addClick).hasClass("batchAddItemRelation")){
				   var itemIds="";
				   $("input[name='itemDataCheckBox']:checked").each(function(index,obj){
						  itemIds+=$(obj).parents("tr").attr("itemid")+",";							
					});				 								
					if(itemIds==""){
						 tablePoint.tablePoint("请选择商品!");
						 return false;
					}else{
					 	addItemRelationParam.itemIds=itemIds;	//商品id多个商品，号分开
					}
			   }
		    	addItemRelationParam.salerCategoryId=shopCategoryId;			//商城分类id	
		    	tablePoint.bindJiazai($(".operationsInner"),true);	 
		    	ajaxRequest.addSalerShopCategory(addItemRelationParam, function(response) {
		    		if (response.retCode == "200") {
		    				//刷新商品菜单
			    			tablePoint.bindJiazai($(".operationsInner"),false);	 
							tablePoint.tablePoint("操作成功！",function(){
							$($this).click();  
						});											 																											
					}else{
						tablePoint.tablePoint("操作失败!");
					}
		    	});
		   })					   					   	
	    });		
		},
		//新增商品关联
		addItemRealation : function(shopCategoryId){
			var currentPageIndex2 = 1;				
			var queryNotRalationParam={};	//查询关联的商品				
			$(".szflsp").click(function(){			
				var $this=this;			
				queryNotRalationParam["shopCategoryId"]=shopCategoryId;		
				queryNotRalationParam["status"]=3;		//查询已经上架的商品			
				queryNotRalationParam["currentPageIndex"] = currentPageIndex2;
				queryNotRalationParam["eachPageSize"] = eachPageSize;	
  				var paramItems = {"jsonItemInfo" : JSON.stringify(queryNotRalationParam)}; 
  				paramItems.industry= "1";	//用来区分查询哪个表餐饮或零售
  				paramItems.searchStatus="0";	//查询未关联的商品 		
  				ajaxRequest.getRelationItem(paramItems, function(response) {
					tablePoint.bindJiazai("#typeHide", false);	
					response["responseState"] = "200";
					response["typeState"] ="1";												
					var html = _.template(addRelationItemTpl,response);
					$(".shopbox").hide();
					$("#cgbox3").show();	
					$("#cgbox3").html(html);
					$("#itemName_Listhead2").val(queryNotRalationParam.name);
	    	        $("#itemBrand_Listhead2").val(queryNotRalationParam.brandName);    	
					$("#searchItem_Listhead2").unbind().on('click',function(){	   
		    	    	var name=$("#itemName_Listhead2").val();
		    	    	var brandName=$("#itemBrand_Listhead2").val();
		    	    	queryNotRalationParam["name"]=name;
		    	    	queryNotRalationParam["brandName"]=brandName;		  					  			
		  				currentPageIndex2=1;		  				  							
		  				$($this).click();  
					 });
					 //分页事件
					$(".pageOrder2").unbind().on('click',function(){
					    	var num = $(this).attr("page");
					    	if(num == '' || num == null){
					    		return false;
					    	}
					    	currentPageIndex2=num;
					    	$($this).click();  
					    });
					    //分页输入
			    	 $('#pageInput2').unbind().on('keypress',function(event){
			            if(event.keyCode == "13")    
			            {
			            	var num = $(this).val();
			            	var totalPage = Number($(this).attr("totalPage"));
				            	if(num > totalPage  || num <= 0 || isNaN(num)){
				            		tablePoint.tablePoint("输入范围错误!请再次输入!");
				            		return false;
				            	}else{
				            		currentPageIndex2=num;
				            		$($this).click();  
				            	}
			            }
				    });
					 tablePoint.checkBoxAll($("#all_item2"),$("input[name='itemDataCheckBox']"));
					 //新增关联的按钮
				       $(".addItemRelation,.batchAddItemRelation").unbind().on('click',function(){
				    	   var addClick=$(this);
				    	   comm.i_confirm("确定新增关联?", function() {
						   var addItemRelationParam={};	
						   if($(addClick).hasClass("addItemRelation")){						
								  addItemRelationParam.itemIds=$(addClick).parents("tr").attr("itemid")+",";	//商品id多个商品，号分开
						   }
						   if($(addClick).hasClass("batchAddItemRelation")){
							   var itemIds="";
							   $("input[name='itemDataCheckBox']:checked").each(function(index,obj){
									  itemIds+=$(obj).parents("tr").attr("itemid")+",";							
								});				 								
								if(itemIds==""){
									 tablePoint.tablePoint("请选择商品!");
									 return false;
								}else{
								 	addItemRelationParam.itemIds=itemIds;	//商品id多个商品，号分开
								}
						   }
					    	addItemRelationParam.salerCategoryId=shopCategoryId;			//商城分类id	
					    	tablePoint.bindJiazai($(".operationsInner"),true);	 
					    	ajaxRequest.addSalerShopCategory(addItemRelationParam, function(response) {
					    		if (response.retCode == "200") {
					    				//刷新商品菜单
						    			tablePoint.bindJiazai($(".operationsInner"),false);	 
										tablePoint.tablePoint("操作成功！",function(){
										$($this).click();  
									});											 																											
								}else{
									tablePoint.tablePoint("操作失败!");
								}
					    	});
					   })					   					   	
				    });				    				       
			       $(".cancel2").click(function(){
						$(".shopbox").hide();
						$("#cgbox2").show();	
					});		       
  				});					
			});
		},			
		queryNotRelationFood : function(qureyNotRealationFood){
			ajaxRequest.cateAndItemCollection(qureyNotRealationFood,function(response){
				var html=_.template(addRelationFoodTpl,response);
				$("#cgbox6 .foodlist1").html(html).show();
				$(".cancel4").click(function(){								
					//shopCate.bindList({"industry":2});
					shopCate.loadBsGridData({"industry":2});
				});								
				//确认关联 开始
				$(".confirm4").unbind().on('click',function(){								
					var addRelationFoodIds="";	//新增关联关系的商品id
					var cpGetArray=$("#cgbox6").find("[class='cp_get']");
					if(cpGetArray.length==0){
						tablePoint.tablePoint("请选择关联的菜品!");
						return false;
					}
					$.each(cpGetArray,function(k,v){
						addRelationFoodIds+=$(v).attr("itemId")+',';
					});
					var itemRelationParam={};
					itemRelationParam.itemIds=addRelationFoodIds;	//商品id多个商品，号分开
  			    	itemRelationParam.salerCategoryId=qureyNotRealationFood.salerCategoryId;			//自定义商城分类id
  			    	tablePoint.bindJiazai($(".operationsInner"),true);
  			    	ajaxRequest.addSalerShopCategory(itemRelationParam, function(response) {
  			    		tablePoint.bindJiazai($(".operationsInner"),false);
  			    		if (response.retCode == "200") {
  			    		    //刷新	  			    		
							tablePoint.tablePoint("操作成功！",function(){											
		  			    		//shopCate.bindList({"industry":2});
								shopCate.loadBsGridData({"industry":2});
							});											 																											
						}else{
							tablePoint.tablePoint("操作失败!");
						}	  			    		
  			    	});
			   });
			   //确认关联 结束	
				//全选与取消
				$(".allgetchk").click(function(){
					var lis = $(this).parents("h1").next().find("li").length;
					var ico = $(this).parents("h1").next().find(".cp_get");
					$(this).prop("checked")?ico.removeClass("none"):ico.addClass("none");	
				});
				//单选与取消
				$(".addcp").click(function(){
					$(this).find("i").toggleClass("none");
					//新增关联操作
					var m = $(this).parents("ul").find(".cp_get:visible").length;
					var n = $(this).parents("ul").find("li").length;
					if(m !== n){
						$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",false);	
					}else{
						$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",true);
					}
				});
			})							
		},		
		bindFun : function(){
			var industry = $(".tabList a[class=active]").attr("data-id");	
			if(industry=='1'){
				shopCate.cateRealationItemOprate();
			}
			
			// 新增分类
			$("#addItem").unbind("click").bind("click",function(){					
				if(industry=='1'){
					shopCate.loadOprateItemDialog();
				}
				
				if(industry=='2'){					
					//新增餐饮分类
					var html = _.template(foodCateDialogTpl,{});
					$("#dlg3").html(html);
					//图片上传事件
					shopCate.uploadPicEvent();				   
					$("#dlg3").dialog({
						title : "添加分类",
						width : "500",
						modal : true,
						close: function() { 
					        $(this).dialog('destroy');
						},
						buttons : {
							'保存' : function() {
								 var $this = $(this);
								 var faParam={};
								 faParam["parentId"] ='0';
								 faParam["industry"]=industry;		  							
								 var img = $(".imgshow").attr("srcname");			
								 if(img !=null && img != ''){
									 faParam["picUrl"] = img;
								 }else{
									 faParam["picUrl"] = '';
								 }
								 faParam["categoryId"] = "";
								 var name = $("#name_addShopCat").val(); 
								 faParam["name"] = name;
								 faParam["logicBoolean"] = "0";  //INSERT(0),UPDATE(1),DELETE(2);	
								 faParam["status"] = "1";
								 //father[0] = faParam;
								 //验证信息	  									  						
								 if(CheckCode(name)){
									 tablePoint.tablePoint("请检查填写的值!输入有误,值长度为1~15,不能特殊符号!");
									 return false;
								 }								
								ajaxRequest.saveSalerShopCategoryNew("shopCategory="+JSON.stringify(faParam),function(response) {	  							
									$("#dlg3").dialog( "destroy" );//关闭窗口  						
									if (response.retCode == "200") {
										tablePoint.tablePoint("新增成功！",function(){
											//shopCate.bindList({"industry":industry});
											shopCate.loadBsGridData({"industry":industry});
										});																		
									}else if(response.retCode == "206"){
										tablePoint.tablePoint("商品分类信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再添加！")
									}else if(response.retCode == "305"){
										tablePoint.tablePoint("新增失败!"+response.data);
									}else{
										tablePoint.tablePoint("新增失败!");
									}
							   })
							},
							'取消' : function() {
								$(this).dialog("destroy");
							}
						}
					});							
				}					
			});
			
			//修改
			$(".goods_edit").unbind().click(function(){				
				var $this = $(this);
				//var parentTr= $($this).parents("tr")[0];
				var urlPrefix=https;//$("input[name='urlPrefix']").val();
				//var industry = $(".tabList a[class=active]").attr("data-id");
				if(industry=='1'){
					//修改零售分类
					var editData={
							"id": $this.attr("objId"),
							"parentId": $this.attr("objParentId"),
							"name":$this.attr("objName"),
							"categoryId":$this.attr("objCategoryId"),							
							"src":urlPrefix+$this.attr("objPicUrl"),
							"srcname":$this.attr("objPicUrl"),
							"sortOrder":$this.attr("objSortOrder")
							};
				  shopCate.loadOprateItemDialog(editData);
				}				
				if(industry=='2'){					
					//修改餐饮分类
					  $( "#dlg3" ).dialog({
	  						title:'修改商品分类',
	  						show: true,
	  						modal: true,
	  						width:"450",
	  						open : function(){
	  							var html = _.template(foodCateDialogTpl,{});
	  							$("#dlg3").html(html);	  								  											   	  							  											    
	  							//图片上传事件
	  							shopCate.uploadPicEvent();	  						 
	  							//加载页面修改的信息	  						
	  							$("#name_addShopCat").attr("value",$this.attr("objName"));
	  							$(".imgshowDiv").html("<img src='' class='imgshow' srcname=''>");
	  							$(".imgshow").attr("src",urlPrefix+$this.attr("objPicUrl"));
	  							$(".imgshow").attr("srcname",$this.attr("objPicUrl"));	  								  			  							
	  						},
	  						buttons: {
	  							"确定": function() {	  								
	  								 //获得输入的类目的信息
	  								 //var father = new Array();
	  								 var faParam = {};
	  								 faParam["id"] = $this.attr("objId");
	  								 faParam["parentId"] =0;		 		
	  								 var img = $(".imgshow").attr("srcname");			
	  								 if(img !=null && img != ''){
	  									 faParam["picUrl"] = img;
	  								 }else{
	  									 faParam["picUrl"] = '';
	  								 }
	  								 faParam["categoryId"] = "";
	  								 var name = $("#name_addShopCat").val(); 
	  								 faParam["name"] = name;
	  								 faParam["logicBoolean"] = "1";  //INSERT(0),UPDATE(1),DELETE(2);	
	  								 faParam["industry"] =industry;
	  								 faParam["status"] = "1";
	  								 if(CheckCode(name)){
	  									 tablePoint.tablePoint("请检查填写的值!输入有误,值长度为1~15,不能特殊符号!");
	  									 return false;
	  								 }	
	  								 //father[0] = faParam;
	  								 ajaxRequest.saveSalerShopCategoryNew("shopCategory="+JSON.stringify(faParam),function(response) {	  							
	  									$("#dlg3").dialog( "destroy" );//关闭窗口  						
										if (response.retCode == "200") {
											tablePoint.tablePoint("修改成功！",function(){
												//shopCate.bindList({"industry":industry});
												shopCate.loadBsGridData({"industry":industry});
											});											 																															
										}else if(response.retCode == "206"){
											tablePoint.tablePoint("商品分类信息不符合规范，包含违禁字:<br>"+response.data+"<br>请修改之后再添加！")
										}else if(response.retCode == "305"){
											tablePoint.tablePoint("修改失败!"+response.data);
										}else{
											tablePoint.tablePoint("修改失败!");
										}
	  							   })
	  							},
	  							"取消": function() {
	  							    $(this).dialog( "destroy" );
	  							}
	  						}
					   });										
				}			
			});
			//修改结束
			
			//删除开始
			$(".goods_del").unbind().click(function(){				
				var $this = $(this);
				//var parentTr= $($this).parents("tr")[0];
				var urlPrefix=https;//$("input[name='urlPrefix']").val();
				//var industry = $(".tabList a[class=active]").attr("data-id");
				var name= $this.attr("objName");
				var id= $this.attr("objId");						
				if(industry=='1'||industry=='2'){				
					 //删除餐饮分类
				       var alertHtml="删除后将取消商品关联,确定删除"+name+"?";
				       //判断是否有关联的商品				     
	 	    	  		$( "#dlg0" ).dialog({
		   	  				title:'删除',
		   	  				show: true,
		   	  				open : function(){
		   	  					 $("#table-point").html(alertHtml);
	   	  				},
	   	  				buttons: {
	   	  					"确定": function() {					   	  						
 								 var faParam = {};
 								 faParam["id"] =id; 								
 								 faParam["logicBoolean"] = "2";  //INSERT(0),UPDATE(1),DELETE(2);
 								 faParam["status"] = "0"; 
 								 faParam["industry"] =industry;
 								 //father[0] = faParam;
 								 ajaxRequest.saveSalerShopCategoryNew("shopCategory="+JSON.stringify(faParam),function(response) {  								
 									$("#dlg0").dialog( "destroy" );//关闭窗口  						
									if (response.retCode == "200") {
										tablePoint.tablePoint("删除成功！",function(){
											//shopCate.bindList({"industry":industry});
											shopCate.loadBsGridData({"industry":industry});
										});	
									}else{
										tablePoint.tablePoint(response.data);
									}
 							   })
	   	  					},
	   	  					"取消": function() {
	   	  					    $( this ).dialog( "destroy" );
	  	  					}
	   	  				}
	 	    	  	});
				}	 		
			}); 	  		
			//删除结束
			
			//设置餐饮菜品信息开始				
			var categoryId='';	//平台类目id串
			$(".cpxx").unbind().on('click',function(){
				var qureyRealationFood = {};
				var removeRelationFoodIds="";	//取消关联关系的商品id	
				$(".shopbox").hide();	
				var $this=this;
				var salerCategoryId=$($this).attr("objId");//$($this).parents("tr").attr("objId");//自定义类目id									
				//查询已经关联的菜品		
				if(comm.isNotEmpty(categoryId))qureyRealationFood.categoryId=categoryId;
				qureyRealationFood.salerCategoryId=salerCategoryId;
				qureyRealationFood.realationType=1;	// 0未关联 1 已关联 
				//ajax 开始	
				ajaxRequest.cateAndItemCollection(qureyRealationFood,function(response){
					if(response.data.result.length == 0){
						tablePoint.tablePoint("该自定义分类还没有菜品关联,是否开始关联菜品?",function(){
							ajaxRequest.notRelationCate({'salerCategoryId':salerCategoryId},function(response){
								var headerhtml=_.template(addRelationFoodHeaderTpl,response);
								$(".shopbox").hide();
								$("#cgbox6").html(headerhtml).show();		
								$("#categoryId_adr").selectbox({width:150}).bind('change', function(){
									console.log("");
								});
								var qureyNotRealationFood = {};			
								//if(comm.isNotEmpty(categoryId))param.categoryId=categoryId;
								qureyNotRealationFood.salerCategoryId=salerCategoryId;
								qureyNotRealationFood.realationType=0;	// 0未关联 1 已关联 
								shopCate.queryNotRelationFood(qureyNotRealationFood);
								$("#headerSearch_adr").unbind().on('click',function(){	   
					    	    	var name=$("#name_adr").val();
					    	    	var code=$("#code_adr").val();	
					    	     	var categoryId=$("#categoryId_adr").val();
					    	    	qureyNotRealationFood["name"]=name;
					    	    	qureyNotRealationFood["code"]=code;		
					    	    	qureyNotRealationFood["categoryId"]=categoryId;		
					    	    	shopCate.queryNotRelationFood(qureyNotRealationFood);  
								 });	
							});	
						});	
					}else{
						var html = _.template(relationFoodTpl,response);
						$("#cgbox5").html(html).show();
						$("#ppai2,#flei2").selectbox({width:100}).bind('change', function(){
							console.log("");
						});
						/*鼠标滑动显示操作图标*/
						$(".cookbook-list a").hover(function(){
							if($(this).children(".ckbkedit-ipt").has(":hidden")){
								if($(this).children(".cookbook-ipt").prop("readonly")==false)
								{$(this).children(".shopcg-list-edit").hide();}	
								else{$(this).children(".shopcg-list-edit").show();}
							}
							else{
								$(this).children(".shopcg-list-edit").hide();	
							}
						},function(){
							$(this).children(".shopcg-list-edit").hide();	
						});
						$(".delcp").hover(function(){
							$(this).find("i").removeClass("none");
						},function(){
							$(this).find("i").addClass("none");
						});
						$(".cp_close").click(function(){
							//删除菜品关联关系 参数自定义类目id，商品id
							removeRelationFoodIds+=$(this).attr("itemid")+",";
							$(this).parents("li").remove();
						});
						$(".addcp").click(function(){
							$(this).find("i").toggleClass("none");
							//新增关联操作
							var m = $(this).parents("ul").find(".cp_get:visible").length;
							var n = $(this).parents("ul").find("li").length;
							if(m !== n){
								$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",false);	
							}else{
								$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",true);
							}
						});
						
						//平台类目关联查询事件
						$("#cgbox5 .btn-search").unbind().on('click',function(){						
							categoryId=$(this).attr("id");
							$($this).click();
						});
				
						
						//关联商品菜单 开始
						$(".szflcp").click(function(){						
							var param = {};			
							//if(comm.isNotEmpty(categoryId))param.categoryId=categoryId;
							param.salerCategoryId=salerCategoryId;
							param.realationType=0;	// 0未关联 1 已关联 
							ajaxRequest.notRelationCate({'salerCategoryId':salerCategoryId},function(response){
								var headerhtml=_.template(addRelationFoodHeaderTpl,response);
								$(".shopbox").hide();
								$("#cgbox6").html(headerhtml).show();
								$("#categoryId_adr").selectbox({width:150}).bind('change', function(){
									console.log("");
								});		
								shopCate.queryNotRelationFood(param);
								$("#headerSearch_adr").unbind().on('click',function(){	   
					    	    	var name=$("#name_adr").val();
					    	    	var code=$("#code_adr").val();
					    	    	var categoryId=$("#categoryId_adr").val();
					    	    	param["name"]=name;
					    	    	param["code"]=code;
					    	    	param["categoryId"]=categoryId;		
					    	    	shopCate.queryNotRelationFood(param);  
								 });
							});
																				
						});
						//关联商品菜单 结束
						
						$(".cancel3").click(function(){
							$(".shopbox").hide();
	  			    		$("#cgbox1").show();								
						});
						
						//确认取消关联 开始
						$(".confirm3").unbind().on('click',function(){					
							var cancelItemRelationParam={};
							cancelItemRelationParam.itemIds=removeRelationFoodIds;	//商品id多个商品，号分开
							if(comm.isEmpty(removeRelationFoodIds)){
								tablePoint.tablePoint("没有选择取消关联的菜品!");
								return false;
							};
		  			    	cancelItemRelationParam.salerCategoryId=salerCategoryId;			//自定义商城分类id
		  			    	tablePoint.bindJiazai($(".operationsInner"),true);
		  			    	ajaxRequest.delSalerShopCategory(cancelItemRelationParam, function(response) {
		  			    		tablePoint.bindJiazai($(".operationsInner"),false);
		  			    		if (response.retCode == "200") {
		  			    		    //刷新	  			    		
									tablePoint.tablePoint("操作成功！",function(){										
				  			    		//shopCate.bindList({"industry":industry});
										shopCate.loadBsGridData({"industry":industry});
									});											 																											
								}else{
									tablePoint.tablePoint("操作失败!");
								}	  			    		
		  			    	});
					   });
					   //确认取消关联 结束	 
					}
					//end else
					
			});
			//ajax 结束	
		  });
		  //设置餐饮菜品信息结束
		}	
	}
	  //上传图片
    function imgUpload(obj){
   	    var file = $("#itemPhoto").val();  
   	    var fileType = file.substring(file.lastIndexOf(".")+1); 
   	    var size = 0;
   	    if (document.all){
   	    	
   	    }else{
   	        size = document.getElementById('itemPhoto').files[0].size;
   	        size = tablePoint.formatFileSize(size);
   	    }
   	    if(("JPG,JPEG,PNG,GIF,BMP,JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
   	    	tablePoint.tablePoint("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,JPG,JPEG,PNG,GIF,BMP,不超过1024KB.建议550X550!");
   	        return;  
   	    }else{  
   	    		tablePoint.bindJiazaiUp("#wrap", true);
				ajaxRequest.upLoadFile("itemPhoto", function(response) {
				tablePoint.bindJiazaiUp("#wrap", false);	
					imgResponse = response;
					var urlimg,imgName;
					   $.each(eval(response), function(i, items) {
						   urlimg = items.httpUrl;   							   
						   $.each(items.imageNails, function(j, item) {
							   if(item.height=="68" && item.width=="68"){
								   imgName = item.imageName;
							   }
						   });
					   });
					  $(obj).html("<img src='' class='imgshow' srcname=''>");
					  $(obj).find(".imgshow").attr("src",urlimg+imgName);
					  $(obj).find(".imgshow").attr("srcName",imgName);
				});
   					
   			$("#itemPhoto").unbind().on('change',function(){
   				imgUpload(obj);
   			});  					
   	    }  
   	};
   	
    return shopCate;
})