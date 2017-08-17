	define([ "text!hsec_marketstartTpl/marketstart.html",
	         "hsec_marketstartDat/marketstart",
	         "hsec_tablePointSrc/tablePoint",
	         "hsec_marketmodifySrc/comment/comment",
	         "hsec_marketstartSrc/marketstartCheck"
	         ], function(tpl,ajaxRequest,tablePoint,ajaxComment,marketstartCheck) {//,marketProtocol//zhanghh modfily 20150828
		var shopImgList = new Array();
		
		var shop = {
				bindData : function() {
					if(shopImgList != null){
						shopImgList.length = 0;
					}
					// 读取集合
					ajaxRequest.initMarketInfo(null,function(response){
						     var webShop =response.data.vShop;
						     var vShop =response.data.vShop.salerVirtualShop
							if(response.data.companyInfo.verifyStatus=="N"){//update by zhanghh:2016-04-11
								comm.i_alert("企业未进行实名认证或在审批中,请实名认证之后再进行网上商城开通！",600);
							}else if(webShop.vshopAuth=="2"){
								comm.i_alert("网上商城已经停用！",600);
							}else if(webShop.vshopAuth=="1"){
								var html = _.template(tpl, response);
								$('.operationsArea').html(html);
						 		shop.bindClick();
							}else{
								ajaxComment.bindData();
							}
							marketstartCheck.validate(shop); 
							shop.bindTable();
							//右上角商城图片 add by zhanghh date:2016-04-26
							if(vShop.logo){
								var httUrl = response.data.httpUrl+vShop.logo;
								$("#rightBar_companyPic").attr("src",""+httUrl+"");
							}
						})
				},
				getVshopStatus : function(){
					ajaxRequest.initMarketInfo(null,function(response){
					     var webShop =response.data.vShop;
					     var vShop =response.data.vShop.salerVirtualShop
						if(response.data.companyInfo.verifyStatus=="N"){//update by zhanghh:2016-05-25 Bug:25099
							/*comm.i_confirm("系统未获取到网上商城信息或网上商城未开通,请先开通网上商城！",function(){
								location.href = comm.domainList.homePage;
							},600);*/
							//comm.i_alert("系统未获取到网上商城信息或网上商城未开通,请先开通网上商城！",600);
							$.cookie('vshopstatus',302);
						}else if(webShop.vshopAuth=="2"){
							comm.i_alert("网上商城已经停用！",600);
							$.cookie('vshopstatus',303);
						}else if(webShop.vshopAuth=="1"){
							$.cookie('vshopstatus',200);
						}else{
							$.cookie('vshopstatus',200);
						}
					})
				},
				bindClick : function(){
					//图片上传
					$("#logo1").change(function(){
						logoUpload(ajaxRequest);
					})
					$("#shopImgAdd").unbind().on('change',function(){
					imgUploadList(ajaxRequest,"#shopImgAdd");
					})
					//删除图片
					$("#shopImgMain").on('click',".deleteImg",function(){
						var objdelete = $(this);
						 $( "#dlg1" ).dialog({
						      modal: true,
							  close: function() { 
							        $(this).dialog('destroy');
							  },
						      buttons: {
						        确定: function() {
						        	//删除
						        	var deleteId = $(objdelete).attr("imgObjIndex");
									for(var i = 0; i<shopImgList.length;i++){
										if(JSON.stringify(shopImgList[i]) == deleteId){
											//shopImgList.remove(i);//modifly by zhanghh 2016-05-05,修改删除图片方式
											shopImgList.splice(i,1);
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
					shop.bindFoot();
				},
				bindFoot : function(){
					//商城图片展开
					$("#moreBt").mouseover(function(){
						$(this).hide();	
						$("#imgBox").addClass("imgBoxBig");
						$("#imgMask").removeClass("imgMask_style");
						/*$("#boxClose").show();*/
					});
					
					//商城图片关闭
					$("#imgBox").mouseleave(function(){
						/*$(this).hide();	*/
						$("#imgBox").removeClass("imgBoxBig");
						$("#imgMask").addClass("imgMask_style");
						$("#moreBt").show();
					})
					
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
						 	  title: '删除图片',//开通
						      modal: true,
						      closeIcon: true,
							  /*close: function() { 
							        $(this).dialog('destroy');
							  },*/
						      buttons: {
						        确定: function() {
						        	//删除
						        	var deleteId = $(objdelete).attr("imgObjIndex");
									for(var i = 0; i<shopImgList.length;i++){
										if(JSON.stringify(shopImgList[i]) == deleteId){
											//shopImgList.remove(i);//modifly by zhanghh 2016-05-05,修改删除图片方式
											shopImgList.splice(i,i)
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
				},
				bindTable : function(){
					$("#openVshop").unbind().on('click',function(){
						$("#formStart").submit();
					});//zhanghh modfily 20150828
					$("#lookxieyi").unbind().on('click',function(){
						var marketProtocol = null;
						ajaxRequest.enterpricesServiceContent("7",function(response){
							marketProtocol = response.data.content;//数据库取出协议内容
							$("#xieyi").html(marketProtocol);
							$("#xieyi").dialog({
								title : "企业经营须知",
								width : "1000",
								modal : true,
								buttons : {
								   '确定' : function() {
									   $(this).dialog('destroy');
								   }
								}
							});
						});//end ajaxRequest
					});
			},
			bindSubmit : function(){
				/* 表单提交 */
				var param = {};
				param["strSalerVirtualShop"] = JSON.stringify($("#formStart").serializeJson());
				if(shopImgList.length < 1){
					param["imgList"] = "";
					tablePoint.tablePoint("商城图片不能为空,请上传商城图片!");
					return false;
				}else{
					param["imgList"] = JSON.stringify(shopImgList);
				}
								tablePoint.bindJiazai("#marketStartMain", true);
					ajaxRequest.marketstart(param, function(response) {
						if(response.retCode=="200"){
							tablePoint.tablePoint("开通商城成功!",function(){
								tablePoint.bindJiazai("#marketStartMain", false);
								//ajaxComment.bindData();
								$("#020700000000_subNav_020701000000").click();
							});
						}else{
							tablePoint.tablePoint("开通商城失败，请稍后重试!",function(){
								tablePoint.bindJiazai("#marketStartMain", false);
							});
						}
					});
				}
			}
			return shop;

		function logoUpload(ajaxRequest){
	        var file = $("#logo1").val();  
	           //判断上传的文件的格式是否正确  
            var fileType = file.substring(file.lastIndexOf(".")+1);  
            var size = 0;
	        if (document.all){
	        	
	        }else{
	        	size = document.getElementById('logo1').files[0].size;
	        	size = tablePoint.formatFileSize(size);
	        }
            if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
            	$("#shopImg").html("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB.建议尺寸大于200X200!");
                return;  
            }else{   
	        	ajaxRequest.upLoadFile("logo1","logoPic",function(response) {
							var urlimg,imgName;
							   $.each(eval(response), function(i, items) {
								   urlimg = items.httpUrl;
								   $("#logoSource").val(items.finalAbsFileName);
//								   imgName = items.finalAbsFileName;
								   $.each(items.imageNails, function(j, item) {
//									   if(item.height=="68" && item.width=="68"){
//										   imgName = item.imageName+"."+item.suffix;
//									   }
									   if(item.height=="200" && item.width=="200"){
										   imgName = item.imageName;
									   }
						        });
						});
							   $("#shopImg").html("");
							   $("#logoImage").attr("src",urlimg+imgName).attr("isBool","1");
						       $("input[name='logo']").val(urlimg+imgName);
					});
	        $("#logo1").replaceWith($("#logo1").html());
	    	//图片上传
			$("#logo1").unbind().on('change',function(){
				logoUpload(ajaxRequest);
			})
		}	
	};

	function imgUploadList(ajaxRequest,id){
	    var file = $(id).val();  
	    //判断上传的文件的格式是否正确  
	    var fileType = file.substring(file.lastIndexOf(".")+1);  
	    var size = 0;
	    if (document.all){
	    	
	    }else{
	    	size = document.getElementById(id.split("#")[1]).files[0].size;
	    	size = tablePoint.formatFileSize(size);
	    }
	    var maxLength = $("#imgBox").attr("maximg");
	    if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024 || shopImgList.length >= maxLength){  
	    	$("#shopImgList").html("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB.尺寸建议大于800X800!不超过"+maxLength+"张!");
	        return;  
	    }else{  
	    			var httpUrl,finalAbsFileName,imgWidth,imgHeight;
					ajaxRequest.upLoadFile(id.split("#")[1],"vshop", function(response) {
						 $.each(eval(response), function(i, items) {
							 finalAbsFileName = items.finalAbsFileName;
							 httpUrl = items.httpUrl;
							   $.each(items.imageNails, function(j, item) {
									   imgName = item.imageName;
									   imgHeight = item.height;
									   imgWidth = item.width;
					        });
						   }) 
						    $("#shopImgList").html("");
							var param = {};
							var imgJson = {};
							imgJson[imgWidth+"X"+imgHeight] = imgName;
							imgJson["picSource"] = finalAbsFileName;
							shopImgList.push(imgJson);
						   var imgObj ="<li class='pr'>"+
							"<img src='"+httpUrl+imgName+"' width='80' height='70'/>"+
							"<i class='tips_close pa delImg' title='删除图片' imgObjIndex='"+JSON.stringify(imgJson)+"'></i>"+
							"</li>";
						   $("#imgListUl").append(imgObj);
				});
	     
	    $(id).replaceWith($(id).parent().html());
		//图片上传
		$(id).unbind().on('change',function(){
			imgUploadList(ajaxRequest,id);
		})
	 } 	
	};
	});
