define([ "hsec_marketmodifyDat/marketmodify"
         ,"text!hsec_marketmodifyTpl/comment/comment.html"
         ,"text!hsec_marketmodifyTpl/marketmodify/marketmodifyInfo.html"
         ,"hsec_marketmodifySrc/marketmodifyCheck"
         ,"hsec_tablePointSrc/tablePoint"], function(ajaxRequest,tpl,tpl2,marketmodifyCheck,tablePoint) {//marketProtocol,//zhanghh modfily 20150828
	//商城图标
	var imgObj = '';
	var shopImgList = new Array();
	
	var shop = {
			bindData : function() {
				if(imgObj != null){
					imgObj = '';
				}
				if(shopImgList != null){
					shopImgList.length = 0;
				}
				// 读取集合
					ajaxRequest.marketmodifyComment(null, function(response) {	
						var html = _.template(tpl, response);
						$('#marketinfo').html(html);
						ajaxRequest.marketmodifyShow(null, function(responses) {
							if(responses.data.vShop.pics != null && responses.data.vShop.pics != ""){
								$.each(eval(responses.data.vShop.pics),function(k,v){
									shopImgList.push(v);
								})
							}
							var html = _.template(tpl2, responses);
							$('#busibox').html(html);
							
							
							//获取网上商城首页
							 $('#virtualShopPage').unbind().on('click',function(){
								 var win= window.open();
								 ajaxRequest.getVirtualShopPage(null,function(responses){
									 win.location=responses.data;
								 })
							 });
							
							 //申请暂停//3为申请停用状态
							$("#stopVshop").unbind().on('click',function(){
								$("#table-point").html("确定进行此重要操作吗?").dialog({
										title:'重要操作确认',
										show: true,
										modal: true,
										closeIcon:true,
										buttons: {
											"确定": function() {
												$("#status").val("3");
												/* 表单提交 */
												var param = {};
												param["strSalerVirtualShop"] = JSON.stringify($("#formModify").serializeJson());
												if(shopImgList.length < 1){
													param["imgList"] = "";
												}else{
													param["imgList"] = JSON.stringify(shopImgList);
												}
												ajaxRequest.marketmodifyUpdate($.param(param), function(resp) {
													if(resp.retCode==200){
														tablePoint.tablePoint("申请成功!",function(){
															shop.bindData();
														})
													}else{
														tablePoint.tablePoint("申请失败!");
													}
												});
												$(this).dialog('destroy');
											},
											"取消": function(){
												$(this).dialog('destroy');
											}
										}
									})
								});
							
							 //申请暂停1为申请启用状态
							$("#startUpVshop").unbind().on('click',function(){
								$("#table-point").html("确定进行此重要操作吗?").dialog({
									title:'重要操作确认',
									show: true,
									modal: true,
									closeIcon:true,
									buttons: {
										"确定": function() {
											$("#status").val("1");
											/* 表单提交 */
											var param = {};
											param["strSalerVirtualShop"] = JSON.stringify($("#formModify").serializeJson());
											if(shopImgList.length < 1){
												param["imgList"] = "";
											}else{
												param["imgList"] = JSON.stringify(shopImgList);
											}
											ajaxRequest.marketmodifyUpdate($.param(param), function(resp) {
													if(resp.retCode==200){
														tablePoint.tablePoint("申请成功!",function(){
															shop.bindData();
														})
													}else{
														tablePoint.tablePoint("申请失败!");
													}
											});
											$(this).dialog('destroy');
										},
										"取消": function(){
											$(this).dialog('destroy');
										}
									}
								})
							});
							
							$("#itemPhotoAdd").unbind().on('change',function(){
								imgUpload(ajaxRequest,"#itemPhotoAdd");
							})
							$("#shopImgAdd").unbind().on('change',function(){
								imgUploadList(ajaxRequest,"#shopImgAdd");
							})
							
							/*图片点击放大*/
							$('#imgListUl').on('click',".bigImg",function(){
								var arr = new Array();
								$.each($(".bigImg"),function(k,v){
									arr.push($(v).attr("src"));
								})
								tablePoint.imgBig(arr,this);
							});
			
							shop.bindUpdate();
							marketmodifyCheck.validate(shop);
							shop.bindFoot();
						});
						
					});
			
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
					 	  title: '删除图片',//修改
					      modal: true,
					      closeIcon : true,
						  /*close: function() { 
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
			},
			bindUpdate : function(){
				$("#fixInfo").unbind().on('click',function(){
					$("#formModify").submit();	
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
//					$("#xieyi").html(marketProtocol);
//					$("#xieyi").dialog({
//						title : "企业经营须知",
//						width : "1000",
//						modal : true,
//						buttons : {
//						   '确定' : function() {
//							   $(this).dialog('destroy');
//						   }
//						}
//					});
				});
			},
			bindSubmit : function(){
				/* 表单提交 */
				var param = {};
				param["strSalerVirtualShop"] = JSON.stringify($("#formModify").serializeJson());
				if(shopImgList.length < 1){
					param["imgList"] = "";
					tablePoint.tablePoint("商城图片不能为空,请上传商城图片!");
					return false;
				}else{
					param["imgList"] = JSON.stringify(shopImgList);
				}
					ajaxRequest.marketmodifyUpdate($.param(param), function(response) {
						if(response.retCode == 200){
							tablePoint.tablePoint("修改成功!");
							$("#020700000000_subNav_020701000000").trigger('click');
						}else{
							tablePoint.tablePoint("修改失败!");
						}
					});
			}

		}
	
	function imgUpload(ajaxRequest,id){
        var file = $(id).val();  
        //判断上传的文件的格式是否正确  
        var fileType = file.substring(file.lastIndexOf(".")+1);  
        var size = 0;
        if (document.all){
        	
        }else{
        	size = document.getElementById(id.split("#")[1]).files[0].size;
        	size = tablePoint.formatFileSize(size);
        }
        if(("JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
        	$("#shopImg").html("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,不超过1024KB.建议尺寸大于200X200!");
            return;  
        }else{  
					ajaxRequest.upLoadFile(id.split("#")[1],"logoPic", function(response) {
						imgObj = response;
						var urlimg,imgName;
						   $.each(eval(response), function(i, items) {
							   urlimg = items.httpUrl;
//							   imgName = items.finalAbsFileName;
							   $("#logoSource").val(items.finalAbsFileName);
							   $.each(items.imageNails, function(j, item) {
//								   if(item.height=="330" && item.width=="330"){
//									   imgweb = item.imageName;
//								   }
								   if(item.height=="200" && item.width=="200"){
									   imgName = item.imageName;
								   }
					        });
					});
						   $("#shopImg").html("");
						   $(".imgLicense").attr("src",urlimg+imgName);
						   $("#logo").val(urlimg+imgName);
				});
         
        $(id).replaceWith($(id).parent().html());
    	//图片上传
		$(id).unbind().on('change',function(){
			imgUpload(ajaxRequest,id);
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
						"<img src='"+httpUrl+imgName+"' width='80' height='70' class='bigImg'/>"+
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
		return shop;
});