define(['text!toolorderTpl/shddgl/shgjpsqddy.html',
		'text!toolorderTpl/shddgl/shgjpsqddy_ckpsd_dialog.html',
		'text!toolorderTpl/shddgl/shpsddy.html',
        'toolorderDat/shddgl/shgjpsqddy',
        'toolorderDat/toolorder',
        'toolorderLan'
		], function(shgjpsqddyTpl, shgjpsqddy_ckpsd_dialogTpl,psdTpl,dataModoule,toolorder){
	var shgjpsqddy_self ={
		showPage : function(){
			shgjpsqddy_self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(shgjpsqddyTpl));
			/** 查询事件 */
			$("#queryBtn").click(function(){
				shgjpsqddy_self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_hsResNo = $("#search_hsResNo").val().trim();
			params.search_hsCustName = $("#search_hsCustName").val().trim();
			dataModoule.findShippingAfterList(params, shgjpsqddy_self.detail, shgjpsqddy_self.printDetail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			return $('<a id="'+ record['shippingId'] +'" >查看配送单</a>').click(function(e) {
				shgjpsqddy_self.ckpsd(record.shippingId, record.entCustId);
			}.bind(this));
		},
		/**
		 * 打印
		 */
		printDetail : function(record, rowIndex, colIndex, options){
			return $('<a id="'+ record['shippingId'] +'" >打印配送单</a>').click(function(e) {

				var reqParam = {
					shippingId	: record.shippingId
				};
				dataModoule.findPrintDetailAfterById(reqParam,function(res){
					var data = res.data;
					$('#dialogBox_dy').html(_.template(psdTpl, data)).dialog({
						title : '打印配送单',
						width : '960',
						closeIcon : true,
						modal : true,
						buttons : {
							'打印' : function(){
								var isIE = function(){
									var version = 0, userAgent = navigator.userAgent.toLowerCase();
									
									if (/(msie)\D+(\d[\d.]*)/.test(userAgent)) {
										version = RegExp.$2;
									}
											   
									version = parseInt(version,10);
									return version;
								}();
								
								if(isIE && isIE < 9){
									var data = $("#dialogBox_dy").html();
		                        	var css1 = window.location.protocol+"\/\/"+"/resources/css/base.css";
									var css2 = window.location.protocol+"\/\/"+"/resources/css/style.css";
									var css3 = window.location.protocol+"\/\/"+"/resources/css/bsgrid.all.min.css";
									myWindow=window.open('','','');
									head = myWindow.document.getElementsByTagName('head')[0];
									linkTag1 = myWindow.document.createStyleSheet(css1);
									linkTag2 = myWindow.document.createStyleSheet(css2);
									linkTag3 = myWindow.document.createStyleSheet(css3);
		                            myWindow.document.body.innerHTML = data;
		                            myWindow.focus();
		                            myWindow.print();
								}
								else{
									var bdhtml = window.document.body.innerHTML;
	                                var sprnstr = "<!--startprint-->";
	                                var eprnstr = "<!--endprint-->";
	                                var prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr) + 17);
	                                prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
	                                var myWindow = window.open('', '', '');
	                                myWindow.document.write(window.document.documentElement.outerHTML);
	                                myWindow.document.body.innerHTML = prnhtml;
	                                myWindow.focus();
	                                myWindow.print();
								}
                                /*
								bdhtml=window.document.body.innerHTML;
								sprnstr="<!--startprint-->";
								eprnstr="<!--endprint-->";
								prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
								prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
								window.document.body.innerHTML=prnhtml;
								window.print();
								*/
							},
							'取消' : function(){$( this ).dialog( "destroy" );}
						}
					});
				});
			}.bind(this));

		},
		/**
		 * 查看配送单
		 */
		ckpsd : function(shippingId, entCustId){
			dataModoule.findShippingAfterById({shippingId:shippingId}, function(res){
				$('#dialogBox').html(_.template(shgjpsqddy_ckpsd_dialogTpl, res.data));
				//填充物品配送清单
				comm.getEasyBsGrid("searchTable_1", res.data.details);
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"配送单详情",
					width:"1100",
					modal:true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				$('#searchTable_1_pt').addClass('td_nobody_r_b');
				//查询企业信息
				shgjpsqddy_self.findEntInfo(entCustId);
			});
		},
		/**
		 * 查询企业信息
		 */
		findEntInfo : function(entCustId){
			dataModoule.findCompanyInfo({companyCustId:entCustId}, function(res){
				if(res.data){
					$("#entResNo").html(comm.removeNull(res.data.entResNo));
					$("#entCustName").html(comm.removeNull(res.data.entCustName));
					$("#contactPerson").html(comm.removeNull(res.data.contactPerson));
					$("#contactPhone").html(comm.removeNull(res.data.contactPhone));
					$("#contactAddr").html(comm.removeNull(res.data.contactAddr));
					$("#postCode").html(comm.removeNull(res.data.postCode));
				}
			});
		}
	};
	return shgjpsqddy_self;
});