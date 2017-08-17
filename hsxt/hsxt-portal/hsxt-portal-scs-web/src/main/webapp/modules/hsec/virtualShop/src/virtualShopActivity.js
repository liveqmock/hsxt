define(["text!hsec_virtualShopTpl/virtualHead.html",
        "text!hsec_virtualShopTpl/updateVirtual_shop.html",
        "text!hsec_virtualShopTpl/pic.html",
        "hsec_virtualShopDat/virtualShopActivity",
        "hsec_tablePointSrc/tablePoint"],  function(tpl,updateVirtual,picHtml,ajaxRequest,tablePoint){
       var showTypeInit = "tabAll";//默认选择的Tab
       var showType = "";
       var queryParam = {"currentPageIndex":1};
       //图片集合 zhanghh add 20150824
		var shopImgList = new Array();
		var virtualShop = {
			queryParma : function(){
				queryParam = {};
				queryParam["eachPageSize"]=20;
				queryParam["queryType"]=showType!=""?showType:showTypeInit; 
				queryParam["companyResourceNo"]=$("#companyResourceNoInPage").val();//...企业资源号
				queryParam["country"]=$("#countryInPage").attr("optionvalue"); //...地区
				queryParam["province"]= $("#provinceInPage").attr("optionvalue"); 
				queryParam["city"]=$("#cityInPage").attr("optionvalue"); 
				queryParam["companyType"]=$("#companyTypeInPage").attr("optionvalue");//...企业类型
				queryParam["companyname"]=$("#companyNameInPage").prop("value");//...企业名称
				queryParam["currentPageIndex"] = 1;
				return queryParam;
			},
			bindData : function(){
				$('#busibox').html(_.template(tpl,{"showType":showType!=""?showType:showTypeInit}));
				virtualShop.selectList();
				virtualShop.tabSelect();
				virtualShop.loadBsGridData(virtualShop.queryParma());
			},
			queryHeadClick:function(){
				//编号输入验证
				$("#companyResourceNoInPage").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$('#vsQuery').unbind().on('click',function(){
					var param = virtualShop.queryParma();
					if($("#countryInPage").attr("optionvalue")==""){
						virtualShop.loadBsGridData(param);
					}else if($("#provinceInPage").attr("optionvalue")!=""&&$("#cityInPage").attr("optionvalue")!=""){
						virtualShop.loadBsGridData(param);
					}else{
						tablePoint.tablePoint("地区查询不满足条件！如：中国-北京市-西城区");
					}
				});
			},
			//初始化表格
			loadBsGridData : function(queryParam){
				var flag = queryParam.queryType;
				var gridObj = $.fn.bsgrid.init('virtual_shop',{
					url:{url:comm.UrlList["findVirtualShops"],domain:"scs"},//comm.domainList["scs"]+comm.UrlList["findVirtualShops"],
					pageSizeSelect: true,
	                pageSize: 20,
	                otherParames:$.param(queryParam),
	                stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					//lineWrap:false,//自动换行
					operate: {
						add : function(record, rowIndex, colIndex, options){
							var data = gridObj.getRecord(rowIndex);
							if(colIndex==0){ return '<img alt="" src="'+data.logoUrl+'" height="100px">' }
							if(colIndex==1){ return data.resourceNo }
							if(colIndex==2){ return data.companyName }
							if(colIndex==3){ return data.busLicence }
							if(colIndex==4){ return data.companyType }
							if(colIndex==5){ return data.shopNumber }
							if(colIndex==6){ return data.createDate }
							if(colIndex==7){ return data.status }
							if(colIndex==8){
								sTpl = '';
								if(flag == "tabOpenApply"){
									sTpl = '<button name="openVs" type="button" class="btn-search ml10" value="'+data.virtualShopId+'">开启</button>'
								} else if(flag == "tabCloseApply"){
									sTpl = '<button name="stopVs" type="button" class="btn-search ml10" value="'+data.virtualShopId+'">暂停</button>'
								} else if(flag == 'tabAll'){
									/* if(data.status == '启用'){
									   //edit by liaoc, 20160413, 服务公司无权对开启中的网上商城进行暂停操作，进行屏蔽
								       //sTpl = '<button  type="button" class="btn-search ml10 changeVsStatus" vshopId="'+data.virtualShopId+'" status="4">暂停</button>'
								    }else if(data.status == '停用'){
								       //sTpl = '<button  type="button" class="btn-search ml10 changeVsStatus" vshopId="'+data.virtualShopId+'" status="2">开启</button>'
								    }*/
									if(data.logoUrl != null && data.logoUrl != ''){
										sTpl = '<button name="cleanVsLogo" type="button" class="btn-search ml10" value="'+data.virtualShopId+'">清除logo</button>'
									}
									if(data.pics != null && data.pics != ''){
										sTpl += '<button name="lookShopPic" type="button" class="btn-search ml10" value="'+data.virtualShopId+'">商城图片</button>'
									}
								}
								return sTpl;
							}
						}
					},
					complete : function(e,o){
						virtualShop.queryHeadClick();
						//virtualShop.changeVsStatus();
						virtualShop.openVs();
						virtualShop.stopVs();
						virtualShop.lookShopPicFn();
						virtualShop.cleanVsLogo();
					}
				});
			},
			//初始化ComboxList
			selectList : function(){
				var param={"areaCode":null};
				area(param,$("#countryInPage"));//国家
				$("#countryInPage").change(function(){
					if($(this).attr("optionvalue")!=null&&$(this).attr("optionvalue")!=""){
						$("#provinceInPage").val("");
						$("#cityInPage").val("");
						var param={"areaCode":$(this).attr("optionvalue")};
						area(param,$("#provinceInPage"));//省
					}else{
						$("#provinceInPage").val("");
						$("#cityInPage").val("");
					}
				});
				$("#provinceInPage").change(function(){
					if($(this).attr("optionvalue")!=null&&$(this).attr("optionvalue")!=""){
						$("#cityInPage").val("");
						var param={"areaCode":$(this).attr("optionvalue")};
						area(param,$("#cityInPage"));//市
					}else{
						$("#cityInPage").val("");
					}
				});
				function area(param,select){
					//加载行政区数据【省】
					ajaxRequest.selectArea(param,function(response){
						if (response.retCode == "200") {
							var opts = new Array();
							opts[0] = {name:"-请选择-",value:""};
							$.each(response.data,function(key,value){
								opts[key+1] = {name:value.areaName,value:value.areaCode}
							});
							$(select).selectList({
								options:opts,
								width:115
							});
						}
					});
				}
				$("#companyTypeInPage").selectList({
					options:[
	                    {name:'全部',value:''},
						{name:'托管企业',value:'T'},//企业类型    0：成员企业,1:托管企业  
						{name:'成员企业',value:'B'}//企业类型      B：成员企业,T:托管企业 
					]
				});
			},
			//【事件注册】Tab点击
			tabSelect : function(){
				$("#vsTabs li").click(function(){
					//设置当前选中的li标识样式
					$("#vsTabs li").removeClass("cur");
					$(this).addClass("cur");
					//根据选择的Tab设置当前展示类型
					if($(this).attr("tag") == "tabOpenApply"){
						//申请重开
						showType = "tabOpenApply";
					}else if($(this).attr("tag") == "tabCloseApply"){
						//申请暂停
						showType = "tabCloseApply";
					}else if($(this).attr("tag") == "tabPvStop"){
						//积分暂停
						showType = "tabPvStop";
					}else if($(this).attr("tag") == "tabClose"){
						//已关闭
						showType = "tabClose";
					}else if($(this).attr("tag") == "tabNormal"){
						//正常
						showType = "tabNormal";
					}else if($(this).attr("tag") == "tabAll"){
						//全部
						showType = "tabAll";
					}
					//刷新数据
					virtualShop.bindData();
				});
			},
			//暂停启用商城
			changeVsStatus : function(){
				$('.changeVsStatus').click(function(){
					var vshopId = $(this).attr("vshopId");
					var status = $(this).attr("status");
					var param = {"vshopId":vshopId,"status":status};
						ajaxRequest.changeVshopStatus(param,function(response){
							if(response.retCode == 200){
								virtualShop.bindData();
								tablePoint.tablePoint("操作成功");
							}else{
								tablePoint.tablePoint("操作失败");
							}
						});
					});
			},
			//【事件注册】申请重开页面点击“开启”
			openVs : function(){
				$("button[name='openVs']").click(function(){
				//获取网上商城ID
				var vsId = $(this).attr("value");
					ajaxRequest.openVs({"virtualShopId":vsId}, function(response){
						if(response.retCode==200){
							tablePoint.tablePoint("网上商城开启成功!");
							//开启完成,刷新列表
							$('#vsQuery').click();
						}else if(response.retCode==201){
							tablePoint.tablePoint("开启失败");
						}else if(response.retCode==212){
							tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
						}
					});
				});
			},
			//【事件注册】申请暂停页面点击“暂停”
			stopVs : function(){
				$("button[name='stopVs']").click(function(){
				//获取网上商城ID
				var vsId = $(this).attr("value");
					ajaxRequest.stopVs({"virtualShopId":vsId}, function(response){
						if(response.retCode==200){
							tablePoint.tablePoint("网上商城暂停成功!");
							//开启完成,刷新列表
							$("#vsQuery").click();
						}else if(response.retCode==201){
							tablePoint.tablePoint("暂停失败");
						}else if(response.retCode==212){
							tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
						}
					});
				});
			},
			//【事件注册】清除网上商城logo
			cleanVsLogo : function(){
				$("button[name='cleanVsLogo']").click(function(){
					//获取网上商城ID
					var vsId = $(this).attr("value");
					//确认提示框
					$("#dialog-msg").dialog({
						show: true,
						modal: true,
						title:"提示信息",
						closeIcon:true,//添加关闭按钮。
						width:"350",
						open : function(){ $("#dialog-msg").html("<center style='margin-top:20px;color:red;font-size:14px;'>确认清除商城logo吗？</center>");},
						buttons: {
							确定: function() {
								$("#dialog-msg").dialog("destroy");
								//清除logo
								ajaxRequest.cleanVsLogo({"virtualShopId":vsId}, function(response){
									if(response.retCode==200){
										tablePoint.tablePoint("清除网上商城logo成功!");
										//开启完成,刷新列表
										$("#vsQuery").click();
									}else if(response.retCode==201){
										tablePoint.tablePoint("清除网上商城失败!");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}
								});
							}
						},
						close:function(){
							$("#dialog-msg").dialog("destroy");
							changeImg($this);
						}
					});
				});
			},
			bindDeletePicClick : function(){
				/*鼠标移上图片显示删除按钮*/
				$("#imgListUl").on("mouseover","li:not('.noImgLi')",function(){
					$(this).children(".delImg").show();	
				});
				
				/*鼠标移开图片隐藏删除按钮*/
				$("#imgListUl").on("mouseleave","li:not('.noImgLi')",function(){
					$(this).children(".delImg").hide();		
				});
				//删除图片
				$("#imgListUl").on('click',".delImg",function(){
					var objdelete = $(this);
					 $( "#dlg1" ).dialog({
					 	  title: '删除图片',
					      modal: true,
					      closeIcon:true,//添加关闭按钮。
						 /* close: function() { 
						        $(this).dialog('destroy');
						  },*/
					      buttons: {
					        确定: function() {
					        	//删除
					        	var deleteId = $(objdelete).attr("imgObjIndex");
								for(var i = 0; i<shopImgList.length;i++){
									if(JSON.stringify(shopImgList[i]) == deleteId){
										//shopImgList.remove(i);
										shopImgList.splice(i,1)
									}
								}	
								if(shopImgList.length < 1){
									$("#morenImg").show();
								}
								$(objdelete).parent().remove();
					            $( this ).dialog( "destroy" );
					        }
					      }
					  });
				});
				/*//删除图片
				$("#imgListUl").on('click',".delImg",function(){
					var objdelete = $(this);
					$("#dlg1").dialog({
						title : "提示信息",
						modal : true,
						show : true,
						open : function(){
							$(".ui-dialog-titlebar-close").click(function(){
								$("#imgListUl").dialog("destroy");
								$(this).dialog('destroy');
							});
						},
						buttons : {
							'确定' : function(){
								var deleteId = $(objdelete).attr("imgObjIndex");
								for(var i = 0; i<shopImgList.length;i++){
									if(JSON.stringify(shopImgList[i]) == deleteId){
										//shopImgList.remove(i);
										shopImgList.splice(0,i);
									}
								}
								if(shopImgList.length < 1){
									$("#morenImg").show();
								}
								//$(objdelete).parent().remove();
								$(objdelete).parent().splice(0,0);
								$(this).dialog("destroy");
							}
						}
					});
				});*/
			},
			//查看商城图片
			lookShopPicFn : function(){
				$("button[name='lookShopPic']").on('click',function(){
					var virtualShopId = $(this).attr("value");
					ajaxRequest.getVirtualShopByIdNew(virtualShopId, function(response){
						//把图片赋值给集合
						if(response.data.pics != null && response.data.pics != ""){
							shopImgList.splice(0,shopImgList.length);
							$.each(eval(response.data.pics),function(k,v){
								shopImgList.push(v);
							})
						}
						var html = _.template(picHtml, response);
						//对话框数据初始化
						//弹出对话框
						$("#virtualShopPic").html(html);
						//删除图片事件
						virtualShop.bindDeletePicClick();
						$("#virtualShopPic").dialog({
							title : '商城图片',
							show : true,
						  	modal : true,
						    width :650,
						    height :250,
						    closeIcon:true,
//						    open : function(){
//						    	$(".ui-dialog-titlebar-close").click(function(){
//						    		$("#virtualShopPic").dialog("destroy");
//						    		$(this).dialog("destroy");
//						    	});
//						    },
						    buttons : {
						    	"确定" : function(){
						    		var param  = {};
						    		param["id"] = virtualShopId;
						    		if(shopImgList.length < 1){
										param["pics"] = "";
									}else{
										param["pics"] = JSON.stringify(shopImgList);
									}
					    			ajaxRequest.deleteVirtualShopPics(param,function(responseResult){
					    				if(responseResult.retCode == 200){
					    					$("#virtualShopPic").dialog("destroy");
					    				}else{
					    					$("#virtualShopPic").dialog("destroy");
					    				}
					    			});
						    	}
						    }//end buttons;
						});
					});
				});
			}//end lookShopPicFn
		}
		return virtualShop;
    });