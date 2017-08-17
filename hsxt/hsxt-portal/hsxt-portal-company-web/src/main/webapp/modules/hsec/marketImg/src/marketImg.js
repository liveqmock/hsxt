define([ "hsec_marketImgDat/marketImg"
         ,"text!hsec_marketImgTpl/tab.html"	
         ,"text!hsec_marketImgTpl/gridMain.html"	
         ,"text!hsec_marketImgTpl/marketImgMain.html"	
         ,"text!hsec_marketImgTpl/marketImgTable.html"
		 ,"hsec_tablePointSrc/tablePoint"],function(ajaxRequest,tabTpl,gridMainTpl,marketImgMain,marketImgTable,tablePoint){
			var paramSearch = {};
 			var shop = {
 				bindParam : function(num){
 					paramSearch["eachPageSize"] = 20;
 					//paramSearch["currentPageIndex"] = 1;
 					return paramSearch;
 				},
				bindData : function() {
					//paramSearch["currentPageIndex"] = 1;	
					//$('.operationsArea').html(marketImgMain);
					//shop.bindTable();
					//shop.bindMainClick();
					$("#busibox").html(_.template(gridMainTpl));
					shop.loadBsGridData(shop.bindParam(1));
				},
				//渲染表格
				loadBsGridData : function(queryParam){
					var gridObj;
					$(function(){
						gridObj = $.fn.bsgrid.init('marketImgGridMain',{
							url:{url:comm.UrlList["allImgList"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["allImgList"],
							pageSizeSelect:true,
							pageSize:20,
							otherParames:queryParam,
							stripeRows: true,  //行色彩分隔
							displayBlankRows: false,   //显示空白行
							//rowHoverColor:true,//划过行变色
							operate:{
								add:function(record, rowIndex, colIndex, options){
									var sTpl = "";
									var id = record.id;
									var pic = eval('('+record.pic+')')["800X800"];
									var picSource = record.picSource;
									var sort = record.sort;
									var recoredCount = options.totalRows;
									switch (colIndex){
										case 0 :
											sTpl = rowIndex + 1;
											break;
										case 1 :
											sTpl = '<img class="imgShow '+id+'pic" width="200px" height="200px"/>';//src="http://192.168.229.31:9099/v1/tfs/'+pic+'"
											break;
										case 2 :
											sTpl = '<img class="imgShow '+id+'picSource" width="200px" height="200px"/>';//src="http://192.168.229.31:9099/v1/tfs/'+picSource+'"
											break;
										case 3 :
											sTpl = '<select class="sortUpdate" myNum = "'+sort+'" >'
										    sTpl += '<option selected>'+sort+'</option>'
										          for(var i = 1;i<=recoredCount;i++){
										           if(sort != i){
										    sTpl += '<option>'+i+'</option>}';       
										           }
										          }
										    sTpl += '</select>';
											break;
										case 4 :
											sTpl = '<a href="javascript:;" class="imgDelete">删除</a>';
											break;
										default : 
											break;
									}//end switch;
									return sTpl;
								}//end add;
							},//end operate;
							complete:function(options, XMLHttpRequest, textStatus){
								var other = eval("("+XMLHttpRequest.responseText+")").orther,https;
								if (other){
									https = other.httpUrl;
								}
								 
								//获取所有的行数据
								var obj = gridObj.getAllRecords();
								if(obj.length > 0){
									$.each(obj,function(k,v){
										$("."+v.id+"pic").attr("src",https+eval('('+v.pic+')')["800X800"]);//展示图动态加载
										$("."+v.id+"picSource").attr("src",https+v.picSource);//原图片动态加载
									});
								}
								//绑定事件
								shop.bindTableClick();
							}//end complete;Ajax请求完成后执行方法
						});
					});
				},
				bindTable : function(){
//					if(paramSearch["currentPageIndex"] < 1){
//						paramSearch["currentPageIndex"] = 1;
//					}
//					ajaxRequest.allImgList(paramSearch,function(response) {
//						var html = _.template(marketImgTable, response.data);
//						$('#imgTable').html(html);
//						$("#table1").DataTable({
//							"scrollY":"370px",//垂直限制高度，需根据页面当前情况进行设置
//							"scrollCollapse": true,//垂直限制高度
//							"bFilter" : false, 
//							"bPaginate": false,
//							"bLengthChange" : false, 
//							"bSort":false,
//							"sDom" : '<""l>t<"F"fp>',
//							"oLanguage" : {//改变语言
//								"sZeroRecords" : "没有找到符合条件的数据"
//							}
//						});
//						shop.bindTableClick();
//					})
				},
				bindTableClick : function(){
					$(".imgDelete").on("click",function(){
						var obj = $(this);
						var trSize = $(obj).parents("tbody").find("tr").size() - 1;
						var imgId = $(this).parents("tr").attr("imgId");
							$( "#dlg1" ).dialog({
							      modal: true,
							      buttons: {
							        确定: function() {
							        	var param = {};
							        	param["ids"] = imgId;
							        	ajaxRequest.imgDelete($.param(param), function(response) {
							        		$("#dlg1").dialog('destroy');
							        		if(response.retCode == 200){
							        			if(trSize == 0){
							        				paramSearch["currentPageIndex"] = paramSearch["currentPageIndex"] - 1;	
							        			}
							        			shop.bindTable();
							        		}else{
							        			tablePoint.tablePoint("删除失败!");
							        		}
							        	})
							        },
							        取消: function() {
							        	$(this).dialog('destroy');
							        }
							      }
							  });
					})
					
					/*图片点击放大*/
					$('.imgShow').on('click',function(){
						var arr = new Array();
						tablePoint.imgBig(arr,this);
					});
					$(".imgShow").bind("load", function(){
						DrawImage(this,200,200);
					});
					function DrawImage(ImgD,iwidth,iheight){   
					    //参数(图片,允许的宽度,允许的高度)   
					    var image=new Image();   
					    image.src=ImgD.src;   
					    if(image.width>0 && image.height>0){   
					      if(image.width/image.height>= iwidth/iheight){   
					          if(image.width>iwidth){     
					              ImgD.width=iwidth;   
					              ImgD.height=(image.height*iwidth)/image.width;   
					          }else{   
					              ImgD.width=image.width;     
					              ImgD.height=image.height;   
					          }   
					      }else{   
					          if(image.height>iheight){     
					              ImgD.height=iheight;   
					              ImgD.width=(image.width*iheight)/image.height;           
					          }else{   
					              ImgD.width=image.width;     
					              ImgD.height=image.height;   
					          }   
					      }   
					    }   
					} 
					//分页
				    $(".pageOrder").unbind().on('click',function(){
				    	var num = $(this).attr("page");
				    	if(num == '' || num == null){
				    		return false;
				    	}
				    	paramSearch["currentPageIndex"] = num;
				    	shop.bindTable();
				    });
				    //分页输入
			    	   $('#pageInput').unbind().on('keypress',function(event){
			            if(event.keyCode == "13")    
			            {
			            	var num = $(this).val();
			            	var totalPage = Number($(this).attr("totalPage"));
				            	if(num > totalPage  || num <= 0 || isNaN(num)){
				            		tablePoint.tablePoint("输入范围错误!请再次输入!");
				            		return false;
				            	}else{
				            		paramSearch["currentPageIndex"] = num;
				            		shop.bindTable();
				            	}
			            }
			        });
			    	   
			    	$(".sortUpdate").unbind().on('change',function(){
			    		var imgId = $(this).parents("tr").attr("imgId");
			    		var param = {};
			    		param["myNum"] = $(this).attr("myNum");
			    		param["num"] = $(this).val();
			    		param["myId"] = imgId;
			    		ajaxRequest.updateSortMy($.param(param), function(response) {
			    			if(response.retCode == 200){
			    				shop.bindTable();
			    			}else{
			    				tablePoint.tablePoint("排序失败!");
			    			}
			    		 })
			    		})
				},
				bindMainClick : function(){
					$("#itemPhoto").unbind().on('change',function(){
						shop.imgUpload();
					})
				},
				imgUpload : function(){
		        	var file = $("#itemPhoto").val();  
		        	  var size = 0;
				        if (document.all){
				        	
				        }else{
				        	size = document.getElementById('itemPhoto').files[0].size;
				        	size = tablePoint.formatFileSize(size);
				        }
		            //判断上传的文件的格式是否正确  
		            var fileType = file.substring(file.lastIndexOf(".")+1);  
		            if(("JPG,JPEG,PNG,GIF,BMP,JPG,JPEG,PNG,GIF,BMP").indexOf(fileType.toUpperCase()) == -1 || size > 1024){  
		            	tablePoint.tablePoint("请上传正确格式!如:JPG,JPEG,PNG,GIF,BMP,JPG,JPEG,PNG,GIF,BMP,不超过1024KB!建议图片大于700X700!");
		                return;  
		            }else{  
		            	var httpUrl,finalAbsFileName,imgWidth,imgHeight;
		            			 tablePoint.bindJiazai("#marketMain", true);
							ajaxRequest.upLoadFile("itemPhoto", function(response) {
								 tablePoint.bindJiazai("#marketMain", false);
								 $.each(eval(response), function(i, items) {
									 finalAbsFileName = items.finalAbsFileName;
									   $.each(items.imageNails, function(j, item) {
											   imgName = item.imageName;
											   imgHeight = item.height;
											   imgWidth = item.width;
							        });
								 }) 
								var param = {};
								param["finalAbsFileName"] = finalAbsFileName;
								var imgJson = {};
								imgJson[imgWidth+"X"+imgHeight] = imgName;
								param["imgName"] = JSON.stringify(imgJson);
							ajaxRequest.imgAdd($.param(param),function(response) {
								if(response.retCode == 200){
									tablePoint.tablePoint("上传成功!");
									shop.bindTable();
								}else{
									tablePoint.tablePoint("上传失败!");
								}
								  })
							});
		    	//图片上传
				$("#itemPhoto").unbind().on('change',function(){
					shop.imgUpload();
				})
		     }
		   }
	  }
		return shop;
 });
