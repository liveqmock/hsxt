define(['text!toolorderTpl/gjpsgl/gjqddy.html',
        'text!toolorderTpl/gjpsgl/gjpsgl_ckpsd_dialog.html',
        'text!toolorderTpl/gjpsgl/xzpsddy.html',
        'text!toolorderTpl/gjpsgl/sbpsddy.html',
        'text!toolorderTpl/gjpsgl/bkpsddy.html',
        'toolorderDat/toolorder'], function(gjqddyTpl,gjpsgl_ckpsd_dialogTpl,xzTpl,sbTpl,bkTpl,toolorder){
	var self = {
		showPage : function(){
			$('#busibox').html(_.template(gjqddyTpl));

			/**下拉列表  订单类型*/
			comm.initSelect("#orderType",comm.lang("toolorder").shippingType);
			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/

			var gridObj;

			$("#serchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_operNo : $.cookie('custId'),				//发货人(当前登录者)
					search_startDate : $("#search_startDate").val(), //配置开始日期
					search_endDate : $("#search_endDate").val(),		//配置结束日期
					search_hsResNo : $("#hsResNo").val().trim(),			//互生号
					search_hsCustName : $("#hsCustName").val().trim(),		//客户名称
					search_type : $("#orderType").attr("optionvalue")	//订单类型
				};
				gridObj = comm.getCommBsGrid("searchTable","query_toollistprint",params,comm.lang("toolorder"),self.detail,self.edit);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4)
			{

				return comm.lang("toolorder").shippingType[record.shippingType];

			}
			else if(colIndex == 5)
			{
				var link1 = $('<a>'+comm.lang("toolorder").ckpsd+'</a>').click(function(e){

					self.ckpsd(record);

				}) ;

				return link1;

			}
		},
		detailpz : function(record, rowIndex, colIndex, options){
			if(colIndex == 2)
			{
				return comm.lang("toolorder").categoryTypeEnum[record["categoryCode"]];
			}

		},
		edit : function(record,rowIndex,colIndex,options){
			if(colIndex == 5){

				var link1 = $('<a>'+comm.lang("toolorder").dypsd+'</a>').click(function(e){

					self.dypsd(record);

				}) ;

				return link1;
			}
		},
		//打印配送单
		dypsd : function(obj){
			var dyTpl = xzTpl;
			var shippingType = obj.shippingType;
			if(shippingType==1){
				//申报
				dyTpl = sbTpl;
			}else{
				//新增
				dyTpl = xzTpl;
				if(comm.isPersonResNo(obj.entResNo)){
					//补卡
					dyTpl = bkTpl;
					shippingType = 3;
				}
			}
			var reqParam = {
				shippingId	: obj.shippingId,
				shippingType: shippingType
			};
			toolorder.findPrintDetailById(reqParam,function(res){
				var data = res.data;
				$('#dialogBox_dy').html(_.template(dyTpl, data)).dialog({
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
							$('#dialogBox_dy').dialog( "destroy" );
						},
						'取消' : function(){$( this ).dialog( "destroy" );}
					}
				});
			});
		},
		ckpsd : function(obj){
			var dyTpl = xzTpl;
			var shippingType = obj.shippingType;
			if(shippingType==1){
				//申报
				dyTpl = sbTpl;
			}else{
				//新增
				dyTpl = xzTpl;
				if(comm.isPersonResNo(obj.entResNo)){
					//补卡
					dyTpl = bkTpl;
					shippingType = 3;
				}
			}
			//如果是服务公司申购
			if(shippingType==1){
				shippingType=shippingType+","+obj.entResNo;
			}
			var reqParam = {
				shippingId	: obj.shippingId,
				shippingType: shippingType
			};

			toolorder.findPrintDetailById(reqParam,function(res){
				var data=res.data;
				var hsResNo=data.hsResNo;
				var tempResNo1=hsResNo.substring(hsResNo.length-6);
				var tempResNo2=hsResNo.substring(hsResNo.length-4);
				//服务公司
				if(tempResNo1=="000000"){
					shippingType=1;
					//企业
				}else if(tempResNo2=="0000"&&tempResNo1!="000000"){
					shippingType=2;
				}else{
					shippingType=3;
				}
				data.shippingType=shippingType;
				$('#dialogBox_ck').html(_.template(gjpsgl_ckpsd_dialogTpl, data));
				/*弹出框*/
				$( "#dialogBox_ck" ).dialog({
					title:"查看配送单",
					width:"1100",
					closeIcon : true,
					modal:true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/
				comm.getEasyBsGrid("searchTable_3", res.data.content.configs,self.detailpz);

				$('#searchTable_3_pt').addClass('td_nobody_r_b');
				/*end*/

			});
		}
	};
	return self;
});