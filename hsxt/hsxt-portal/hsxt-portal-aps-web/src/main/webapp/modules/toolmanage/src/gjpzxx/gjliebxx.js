define(['text!toolmanageTpl/gjpzxx/gjliebxx.html',
		'text!toolmanageTpl/gjpzxx/gjliebxx_tjgj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjliebxx_gjxq_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjliebxx_xj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjliebxx_xg_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjlbxx_jgfh.html',
		'text!toolmanageTpl/gjpzxx/gjlebxx_xg.html',
		'toolmanageDat/gjpzxx/gjliebxx',
		'toolmanageLan'
		], function(gjliebxxTpl, gjliebxx_tjgj_dialogTpl,
				gjliebxx_gjxq_dialogTpl, gjliebxx_xj_dialogTpl, gjliebxx_xg_dialogTpl,gjlbxx_jgfhTpl,gjlebxx_xgTpl,toolmanage){
	var self = {
		showPage : function(){
			$('#busibox').html(_.template(gjliebxxTpl));
			comm.initSelect($("#statues"),comm.lang("toolmanage").toolStatues,null,null);
			comm.initSelect($("#ratifyStatues"),comm.lang("toolmanage").ratifyStatues,null,null);

			$("#queryBtn").click(function(){
				var param = {};
				param.search_exeCustId = comm.getSysCookie('custId');
				param.search_productName = $("#search_productName").val().trim();
				var search_categoryCode=$("#search_categoryCode").val();
				if(search_categoryCode!="undefined"&&search_categoryCode!=""&&search_categoryCode!="null"){
					param.search_categoryCode = comm.lang("toolmanage").categoryTypeEnumFx[search_categoryCode.trim()];
				}
				param.search_enableStatus = $("#statues").attr('optionValue');
				param.search_status = $("#ratifyStatues").attr('optionValue');
				var bsgrid = toolmanage.findToolProductList(param,self.detail,self.edit,self.add);
			});


			$('#tjgj_btn').click(function(){
				$('#dialogBox_tjgj').html(gjliebxx_tjgj_dialogTpl);
				self.initUpload();

				$('#dialogBox_tjgj').dialog({
					title:"添加工具",
					width:"1020",
					closeIcon : true,
					modal:true,
					buttons:{
						"确定":function(){

							var valid = self.validateData();
							if (!valid.form()) {
								return;
							}

							//增加图片校验
							var txtImgName=$("#txtImgName").val();
							if(txtImgName==""||txtImgName=="null"){
								comm.error_alert(comm.lang("toolmanage").noToolPic);
								return false;
							}

							var sell = this;
							var datas = {};
							datas.categoryCode = $("#dialogBox_tjgj #toolCategory").attr('optionValue');
							datas.productName = $("#dialogBox_tjgj #productName").val();
							datas.price = $("#dialogBox_tjgj #price").val();
							datas.unit = $("#dialogBox_tjgj #unit").val();
							datas.description = $("#dialogBox_tjgj #description").val();
							self.uploadFile(function(data){
								if(data.microPic){
									$("#microPic_hidden").val(data.microPic);
								}
								datas.microPic = data.microPic;
								//
								toolmanage.addToolProduct(datas,function(){
									comm.yes_alert(comm.lang("toolmanage").addToolResult);
									$("#queryBtn").click();
									$(sell).dialog( "destroy" );
								},function(){
									self.initUpload();
								});
							});

						},
						"取消":function(){
							$('#gjtj_form').find('input,textarea,select').tooltip().tooltip( "destroy" );
							$( this ).dialog( "destroy" );
						}
					}
				});

				comm.initSelect($("#toolCategory"),comm.lang("toolmanage").categoryTypeEnum,300,null);
			});

		},

		//上传文件
		uploadFile : function(callBack){
			var microPic_hidden = $('#microPic_hidden').val();
			if(comm.isNotEmpty(microPic_hidden)){
				var resp = {
					microPic:microPic_hidden,
				};
				callBack(resp);
			}else{
				var ids = ['microPic'];
				comm.uploadFile(null, ids, function(resp){
					if(resp){
						callBack(resp);
					}
				},function(err){
					self.initUpload();
				}, {annexType:"zip-pic",permission:"0"});
			}
		},
		//上传文件
		uploadFileXg : function(callBack){
			var microPicHid = $('#microPicHid').val();
			if(comm.isNotEmpty(microPicHid)){
				var resp = {
					microPic:microPicHid,
				};
				callBack(resp);
			}else{
				if(comm.isNotEmpty($('#microPicXg').val())){
					var ids = ['microPicXg'];
					comm.uploadFile(null, ids, function(resp){
						if(resp){
							callBack(resp);
						}
					},function(err){
						self.initUpload();
					}, {annexType:"zip-pic",permission:"0"});
				}else{
					callBack({microPicXg:null});
				}
			}
		},
		//
		/**
		 * 初始化上传
		 */
		initUpload : function(){
			$("#microPic").unbind('change').bind('change', function(){
				var file = $("#microPic").val();
				if(file != ""){
					var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					if(fileExtend != ".bmp"&&fileExtend != ".jpeg"&&fileExtend != ".jpg"&&fileExtend != ".png"){
						comm.warn_alert(comm.lang("toolmanage").isNotToolPic);
						$("#microPic").val("");
						$("#txtImgName").val("");
					}else{
						var strs = file.split("\\");
						$("#txtImgName").val(strs[strs.length-1]);
					}
				}
			});
			$("#xgtjDiv #microPicXg").unbind('change').bind('change', function(){
				var file = $("#xgtjDiv #microPicXg").val();
				if(file != ""){
					var fileExtend = file.substring(file.lastIndexOf('.')).toLowerCase();
					if(fileExtend != ".bmp"&&fileExtend != ".jpeg"&&fileExtend != ".jpg"&&fileExtend != ".png"){
						comm.warn_alert(comm.lang("toolmanage").isNotToolPic);
						$("#xgtjDiv #microPicXg").val("");
						$("#xgtjDiv #microPicFileName").val("");
					}else{
						var strs = file.split("\\");
						$("#xgtjDiv #microPicFileName").val(strs[strs.length-1]);
					}
				}
			});
		},
		//
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.lang("toolmanage").categoryTypeEnum[record.categoryCode];
			}else if(colIndex == 2){
				return comm.formatMoneyNumber(record.price);
			}else if(colIndex == 3){
				return comm.lang("toolmanage").toolStatues[record.enableStatus];
			}else if(colIndex == 4){
				return comm.lang("toolmanage").ratifyStatues[record.status];
			}else if(colIndex == 6){
				var link1 = $('<a>查看</a>').click(function(e) {
					self.gjxq(record);
				}.bind(this) ) ;
				return link1;
			}
		},

		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){
				var link2 = null;
				if(record.enableStatus == '1'){//已上架
					if(record.status == '1' || record.status == '5'){
						link2 = $('<a>下架</a>').click(function(e) {
							self.xiaJia(record);
						}.bind(this) ) ;
					}
				}
				else {//未上架
					if(record.status == '2' || record.status == '4'){
						link2 = $('<a>修改价格</a>').click(function(e) {
							self.xiuGai(record);
						}.bind(this) ) ;
					}
				}
				return link2;
			}									
		},
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){
				var link1 = $('<a>修改</a>').click(function(e) {
					self.xgtj(record);
				}.bind(this) ) ;
				return link1;
			}
		},

		xgtj : function(obj){
			$('#dialogBox_gtxg').html(_.template(gjlebxx_xgTpl));
			$('#xgtjDiv #productId').val(obj.productId);
			$('#xgtjDiv #productName').val(obj.productName);
			$('#xgtjDiv #warningValue').val(obj.warningValue);
			self.initUpload();
			/*弹出框*/
			$( "#dialogBox_gtxg" ).dialog({
				title:"修改工具",
				width:"600",
				modal:true,
				closeIcon : true,
				buttons:{
					"提交":function(){
						var valid = self.validateDataXg();
						if (!valid.form()) {
							return;
						}
						self.uploadFileXg(function(data){
							if(data.microPicXg){
								$("#microPicHid").val(data.microPicXg);
							}
							var param = {
								microPic : data.microPicXg,
								productId : $('#xgtjDiv #productId').val(),
								description : $('#xgtjDiv #description').val(),
								warningValue : $('#xgtjDiv #warningValue').val()
							};
							//
							toolmanage.modifyToolProduct(param,function(){
								comm.yes_alert(comm.lang("toolmanage").modifyToolResult);
								$('#dialogBox_gtxg').dialog( "destroy" );
								$("#queryBtn").click();
							},function(){
								self.initUpload();
							});
						});
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
		},

		gjxq : function(obj){
			var json = {productId:obj.productId,lastApplyId:obj.lastApplyId};
			toolmanage.queryToolProductUpDownById(json,function(res){

				var obj = res.data;

				$('#dialogBox_gjxq').html(_.template(gjliebxx_gjxq_dialogTpl, obj));

				/*弹出框*/
				$( "#dialogBox_gjxq" ).dialog({
					title:"工具详情",
					width:"850",
					modal:true,
					closeIcon : true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				
				$("#gjlbxgtp").click(function(){
					self.initPicPreview("#gjlbxgImg", obj.toolMicroPic, "");
				});

			});
		},
		
		initPicPreview : function(objId, fileId, title, divId, width, height) {
			title = !title?"图片查看":title;
			// width = !width?"800":width;
			// height = !height?"600":height;
			divId = !divId?"#showImage50":divId;
			logId = divId+"Div";
			$(objId).attr("data-imgSrc", comm.getFsServerUrl(fileId));
				var buttons={};
				 buttons['关闭'] =function(){
					$(this).dialog("destroy");
				};
				var url= comm.getFsServerUrl(fileId);
				if (null != url && "" != url) {
					var imgHtml = "<img alt=\"图片加载失败...\" src=\""+url+"\" id=\"showImage50\" style=\"width:100%; height:100%;\">";
					$(logId).html(imgHtml);
					$(logId).dialog({
						title : title,
						width : width,
						height : height,
						modal : true,
						buttons : buttons
					});
				}
		},
		
		xiaJia : function(obj){
			$('#dialogBox_xj').html(gjliebxx_xj_dialogTpl);

			/*弹出框*/
			$( "#dialogBox_xj" ).dialog({
				title:"工具下架",
				width:"800",
				closeIcon : true,
				modal:true,
				buttons:{
					"确定":function(){
						var sell = this;
						var datas = {};
						datas.productId = obj.productId;
						datas.downReason = $("#downReason").val();
						toolmanage.applyToolProductToDown(datas,function(){
							comm.yes_alert(comm.lang("toolmanage").xiajiaToolResult);
							$("#queryBtn").click();
							$(sell).dialog( "destroy" );
						});
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/

			$('#xj_gjmc_txt').text(obj.productName);
			$('#xj_gjlb_txt').text(comm.lang("toolmanage").categoryTypeEnum[obj.categoryCode]);
			$('#xj_jg_txt').text(comm.formatMoneyNumber(obj.price));
			$('#xj_jldw_txt').text(obj.unit);
			$('#xj_gjsm_txt').text(obj.description||'');
			$('#xj_gjtp_txt').html('<a class="blue" id="gjxjtp">点击查看图片<img style="display:none" id="gjxjImg" src="" width="80" height="20"  title="点击查看图片" /></a>');

			
			$("#gjxjtp").click(function(){
				if(obj.microPic){
					self.initPicPreview("#gjxjImg", obj.microPic, "");
				}else{
					self.initPicPreview("#gjxjImg", null, "");
				}
			});
		},
		xiuGai : function(obj){
			obj.categoryCodes=comm.lang("toolmanage").categoryTypeEnum[obj.categoryCode]||'';
			$('#dialogBox_xg').html(_.template(gjliebxx_xg_dialogTpl, obj));
			$('#jg_input_hidden').val(obj.price);
			/*弹出框*/
			$( "#dialogBox_xg" ).dialog({
				title:"修改工具价格",
				width:"600",
				modal:true,
				closeIcon : true,
				buttons:{
					"确定":function(){
						var sell = this;
						var datas = {};
						datas.productId = obj.productId;
						var newPrice=$("#jg_input").val();
						newPrice=newPrice.replaceAll(",","");
						newPrice=newPrice.replace(".00","");
						datas.applyPrice=newPrice;

						var oldPrice=$('#jg_input_hidden').val();
						if(newPrice==oldPrice){
							comm.alert(comm.lang("toolmanage").noPriceUpdate);
							return false;
						}

						toolmanage.applyChangePrice(datas,function(){
							comm.yes_alert(comm.lang("toolmanage").xiugaiPriceResult);
							$("#queryBtn").click();
							$(sell).dialog( "destroy" );
						});
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/
			$("#gjlbxgtps").click(function(){
				self.initPicPreview("#gjlbxgImgs", obj.microPic, "");
			});
			
		},
		validateData : function(){
			return $("#gjtj_form").validate({
				rules : {
					productName : {
						required : true
					},
					toolCategory : {
						required : true
					},
					price : {
						required : true,
						number : true
					},
					unit : {
						required : true
					},
					description:{
					 	maxlength : 300
					}
				},
				messages : {
					productName : {
						required : comm.lang("toolmanage").toolproductName
					},
					toolCategory : {
						required : comm.lang("toolmanage").tooltoolCategory
					},
					price : {
						required : comm.lang("toolmanage").toolprice,
						number : comm.lang("toolmanage").priceNumber
					},
					unit : {
						required : comm.lang("toolmanage").toolunit
					},
					description:{
					 	maxlength : comm.lang("toolmanage").maxlength
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+160 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		validateDataXg : function(){
			return $("#gjxg_form").validate({
				rules : {
					warningValue : {
						digits : true
					},
					description:{
						maxlength : 300
					}
				},
				messages : {
					warningValue : {
						digits : comm.lang("toolmanage").warningValueDigits
					},
					description:{
						maxlength : comm.lang("toolmanage").maxlength
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+160 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		shanChu : function(obj){}
	};
	return self;
});