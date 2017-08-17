define(['text!systemBusinessTpl/toolManage/cardBuy/resSegmentLoad.html',
        'text!systemBusinessTpl/toolManage/cardBuy/cardStyleLoad.html',
        'text!systemBusinessTpl/toolManage/cardBuy/resOrderSubmit.html',
		'text!systemBusinessTpl/toolManage/cardBuy/payment.html',
		"text!systemBusinessTpl/toolManage/deliveryAddr/modifyAddress.html",
		"text!systemBusinessTpl/toolManage/deliveryAddr/addAddress.html",
		'systemBusinessDat/systemBusiness',
		'systemBusinessSrc/toolManage/deliveryAddr'
		], function(resSegmentLoad,cardStyleLoadTpl,resOrderSubmitTpl, paymentTpl, modifyAddressTpl, addAddressTpl,systemBusinessAjax,deliveryAddrJs){
	// 配置单列表
	var product=null;
	//卡样ID
	var cardStyleId=null;
	// 收货地址单选按钮id
	var radioId = null;
	// 选择资源段
	var segment = new Array();
	var segmentId = new Array();
	//所有资源段
	var allSegmentMap = {};
	//订单总金额
	var orderTotal = 0;
	//地址集合
	var addrMap = {};
	var self = {
		showPage : function(){		
			$('#busibox').html(_.template(resSegmentLoad)).append(paymentTpl);
			cardStyleId=null;
			var startResType = $.cookie('startResType');
			if(comm.isNotEmpty(startResType)&&startResType=='3'){
				$('#startResTypeHint').removeClass('none');
				return false;
			}
			//查询企业状态信息  当状态为注销中时 不能操作该项
			systemBusinessAjax.searchEntStatusInfo({},function(res){				
				var obj = {
						status : res.data.info.baseStatus
				};
				if(obj.status == '5'){
					$('#entLogoutHint').removeClass('none');
				}else if(obj.status == '6'){
					$('#stopPointAppHint').removeClass('none');
				}else if(obj.status == '7'){
					$('#stopPointHint').removeClass('none');
				}else if(obj.status != '8'){
					self.normalOptFlow();
				}else{
					$('#entLogoutAppHint').removeClass('none');
				}
			});
		},
		
		//正常的操作流程
		normalOptFlow : function(){
			//显示购买资源协议
			self.showBuyResAgreement();
			
			//显示企业资源详情
			self.showEntResSegmentDetail();
			
			//下一步
			$('#btn_hsksg_xyb').click(function(){
				//验证勾选框 暂时屏蔽协议 by likui 2016-04-19
//				if(!confirmBox.checked){
//					comm.error_alert(comm.lang("systemBusiness").pleaseSelectAgreement);
//					return false;
//				}
				var i = 0;
				orderTotal = 0;
				segment.length = 0;
				$("#xtzysg_list [name='checkboxSegment']").each(function(){ 
					var check = $(this);
					if(check.attr("checked")&&comm.isEmpty(check.attr("disabled"))){
						var obj = allSegmentMap[check.attr("id")];
						orderTotal = orderTotal + Number(obj.segmentPrice);
						segment[i] = obj;
						segmentId[i] = obj.segmentId;
						i++;
					}
				});
				if(segment.length <= 0){
					comm.error_alert(comm.lang("systemBusiness").pleaseSelectSegment);
					return false;
				}
				$("#hsksg_1").addClass('none');
				$("#hsksg_2").removeClass('none').html(_.template(cardStyleLoadTpl));	
				//加载卡样
				self.loadCardStyle();
				
				//上一步
				$('#btn_hsksg_syb1').click(function(){
					$("#hsksg_2").addClass('none');
				    $("#hsksg_1").removeClass('none');	
				});
				//下一步
				$('#btn_hsksg_xyb1').click(function(){
					if(comm.isEmpty(cardStyleId)){
						comm.error_alert(comm.lang("systemBusiness").pleaseSelectCardStyle);
						return false;
					}					
					$("#hsksg_2").addClass('none');
					$("#hsksg_3").removeClass('none').html(_.template(resOrderSubmitTpl,{segment:segment}));	

					//加载地址
					self.loadAddress();
					//新增地址
					deliveryAddrJs.addAddr(addAddressTpl,function(resp){
						comm.yes_alert(comm.lang("systemBusiness").toolManage_addDeliverAddressSucc);
						self.loadAddress();
					});
					//获取平台缓存信息
					cacheUtil.findCacheLocalInfo(function(resp){
						$('#payAmount').text(comm.formatMoneyNumber(orderTotal));
						$('#currencyNameCn').text(resp.currencyNameCn);
						$('#exchangeRate').text(resp.exchangeRate);
						$('#cashAmount').text(comm.formatMoneyNumber(Number(resp.exchangeRate)*Number(orderTotal)));
					});
					//订单提交
					self.orderSumitPage();
				   
					$('#btn_hsksg_syb').click(function(){
						$("#hsksg_3").addClass('none');
					    $("#hsksg_2").removeClass('none');	
					});											
			  });
		  });
		},
		
		//订单提交
		orderSumitPage : function(){
			//成功提交订单订单提交单击事件
			$('#btn_hsksg_ddtj').click(function(){
				var  radio = $('input[name="deliverAddr"]:checked');
				var addrData = addrMap[radio.attr("id")];
				if(comm.isEmpty(addrData)){
					comm.error_alert(comm.lang("systemBusiness").toolManage_selectAddr);
					return;
				}
				if(comm.isEmpty(addrData.fullAddr)){
					comm.error_alert(comm.lang("systemBusiness").contactAddrIsNull,600);
					return;
				}
				var param = {
					product:encodeURIComponent(JSON.stringify(product)),
					//segmentId:encodeURIComponent(JSON.stringify(segmentId)),
					segment:encodeURIComponent(JSON.stringify(segment)),
					addr:encodeURIComponent(JSON.stringify(addrData))
				};
				systemBusinessAjax.commitBytoolOrderCard(param,function(resp){
					if(resp){
						$('#pay_div').removeClass('none');
						$("#hsksg_3").addClass('none');
						require(['systemBusinessSrc/toolManage/pay/pay'], function(pay){
							var obj = resp.data;
							obj.paymentMethod = 'hsbPay';
							obj.isFormat = 'format';
							obj.orderType = '110';
							obj.isFrist = 'true';
							pay.showPage(obj);
						});
					}
				});
			});
		},
		
		//显示企业资源详情
		showEntResSegmentDetail : function(){
			systemBusinessAjax.queryEntResourceSegment(function(resp){
				if(resp){
					if(comm.isNotEmpty(resp.data)){
						product = resp.data.product;
						var startBuyRes = resp.data.startBuyRes;
						var obj = resp.data.segment;
						if(Number(startBuyRes) > 10){
							$('#boughtEnd').removeClass('none');
							return false;
						}
						$('#hsksg_1').removeClass('none');
						var entResSegmentUlY = $('#entResSegmentUlY');
						var entResSegmentUlE = $('#entResSegmentUlE');
						$('#total_span').text('0');
						$('#cardCount_span').text('0');
						for(var i=0;i<obj.length;i++){

							/* 暂时屏蔽修改
							var segment = obj[i];
							var bought = segment.status != 3?'disabled="disabled" checked="checked"':'';
							var boughtStr = segment.status == 1?'已申购':(segment.status == '2'?'已下单':'');
							var checkClass = '';
							if((i + 1) == startBuyRes){
								checkClass = 'show';
							}else if(i + 1 > startBuyRes){
								checkClass = 'none';
							}
							var spanClass = segment.status != 3 ?'cl-gray':'';*/

							var segment = obj[i];
							var bought = segment.buyStatus?'disabled="disabled" checked="checked"':'';
							var boughtStr = segment.buyStatus?'已申购':'';
							var checkClass = '';
							if((i + 1) == startBuyRes){
								checkClass = 'show';
							}else if(i + 1 > startBuyRes){
								checkClass = 'none';
							}
							var spanClass = segment.buyStatus?'cl-gray':'';
							var checkboxId = 'checkbox_segment_'+(i+1);
							var li = $('<li><label><span>'+
									'<input type="checkbox" id='+checkboxId+' name="checkboxSegment" value='+(i+1)+' class="input_checkbox '+checkClass+'" '+bought+'/></span>'+
									'<span class='+spanClass+'>'+segment.segmentDesc+'</span><p>'+boughtStr+'</p></label></li>');
							allSegmentMap[checkboxId] = segment;
							if(i<5){
								entResSegmentUlY.append(li);
							}else{
								entResSegmentUlE.append(li);
							}
						}
						/*选择复选框操作*/
						var checkboxObj = $('#xtzysg_list').find('input[type="checkbox"]');
						checkboxObj.click(function(){
							var inputVal = $(this).val() - 0;
							if($(this).hasClass('show')){
								var cardCount = Number($('#cardCount_span').text());
								if($(this).is(':checked')){
									checkboxObj.eq(inputVal).removeClass('none').addClass('show').attr("checked",false);
									$('#total_span').text(inputVal - startBuyRes + 1);
									var id = 'checkbox_segment_'+inputVal;
									var obj = allSegmentMap[id];
									$('#cardCount_span').text(cardCount + Number(obj.cardCount));
								}
								else{
									$('#xtzysg_list').find('input[type="checkbox"]:gt('+ (inputVal - 1) +')').addClass('none').removeClass('show').attr("checked",false);
									$('#total_span').text(inputVal - startBuyRes);
									var cardCount = 0;
									$("#xtzysg_list [name='checkboxSegment']").each(function(){ 
										var check = $(this);
										if(check.attr("checked")&&comm.isEmpty(check.attr("disabled"))){
											var obj = allSegmentMap[check.attr("id")];
											cardCount = cardCount + Number(obj.cardCount);
										}
									});
									$('#cardCount_span').text(cardCount);
								}
							}
						});
					}else{
						$('#bizOptError').removeClass('none');
					}
				}
			});
		},
		
		//加载卡样
		loadCardStyle : function(){
			systemBusinessAjax.getEntCardStyleByAll(function(resp){
				var obj = resp.data;
				if(obj.length > 0){
					var specificStyleUl = $('#specificStyle');
					var standardStyleUl = $('#standardStyle');
					for(var i = 0; i < obj.length ; i++){
						var style = obj[i];
						var cardId = "view-confirmFile-"+(i+1);
						var downloadDiv = '';
						if(comm.isNotEmpty(style.confirmFile)){
							downloadDiv = '<div class="mt5 clearfix">下载： <a id="'+cardId+'">卡样确认书样本.docx</a></div>';
						}
						var li = $('<li><div align="center">'+
						'<img src="'+comm.getFsServerUrl(style.microPic)+'" width="120" height="80" /></div>'+
						'<div class="mt5 mb5">'+
						'<span class="fl"><input type="radio" name="cardStyleRadio" id=radio_'+style.cardStyleId+' data-cardStyleId='+style.cardStyleId+'></span>' +
						'<span class="gjsg-list-name">'+style.cardStyleName+'</span></div>'+
		                ''+downloadDiv+'</li>');
		                if(style.isSpecial){
		                	specificStyleUl.append(li);
		                }else {
		                	standardStyleUl.append(li);
		                }
						comm.initDownload("#"+cardId, {1000:{fileId:style.confirmFile, docName:''}}, 1000);
						$("#radio_"+style.cardStyleId).change(function() { 
							cardStyleId = $(this).attr('data-cardStyleId')
						}); 
					}
					var appplyCardLi = '<li><div class="mt40"><button id="appplyCardBtn" class="btn-search" type="button">申请个性互生卡定制服务</button></div></li>';
					specificStyleUl.append(appplyCardLi);
					$("#hsksg_2 [name='cardStyleRadio']").each(function(){ 
						var radio = $(this);
						if(cardStyleId == radio.attr('data-cardStyleId')){
							radio.attr("checked",true);
						}
					});
					$("#appplyCardBtn").click(function(){
						 $('#gxkdzfw').click();
						 $('#sqgxk_btn').click();
					});					
				}
			});
		},
		
		//显示购买资源协议
		showBuyResAgreement : function(){
			$('#btn_hsksg_ckxy').click(function(){
				/*弹出框*/
				$( "#ckzyxyTpl" ).dialog({
					title:"协议内容",
					width:"800",
					height:"580",
					modal:true,
					buttons:{ 
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		
		// 加载收货地址
		loadAddress : function(){
			systemBusinessAjax.serchEntAddress(null,function(resp){
				if(resp){
					var addr = resp.data;
					var entAddrList = $('#entAddrList');
					entAddrList.empty();
					for(var i = 1 ; i <= addr.length ; i++){
						var obj = addr[i-1];
						var divP = null;
						var label = null;
						var key = obj.addrType+'_'+i;
						var areaInfo = "";
						if(obj.addrType == 'auto'){
							areaInfo = cacheUtil.getAreaSplitJoint(null, obj.provinceNo, obj.cityNo);;
							if(areaInfo){
								obj.fullAddr = areaInfo.data + obj.fullAddr;
							}
						}
						addrMap[key] = obj;
						var span = $('<span class="mr5"><input type="radio" name="deliverAddr" id="'+key+'" class="m03vm"></span>'+
								'<span class="mr10">['+comm.lang("systemBusiness").toolManage_addrType[obj.addrType]+']</span>'+
								'<span class="mr5">'+comm.valToLongShow(obj.fullAddr,50)+'</span>'+
								'<span class="mr5">'+comm.valToLongShow(obj.entName,30)+'</span>'+
								'<span class="mr5">('+comm.valToLongShow(obj.linkMan,20)+' 收)</span>'+
								'<span class="mr5">'+obj.mobile+'</span>'+
								'<span>'+comm.navNull(obj.zipCode)+'</span>');
						if(obj.addrType == 'auto'){
							divP = $('<div class="pt5 pb5 clearfix"></div>');
							label = $('<label class="fl"></label>');
							var autoLabel = $('<span class="fr"><a class="ml10" data-addrId='+obj.addrId+' data-type="modifyAddr">修改</a><a class="ml10" data-addrId='+obj.addrId+' data-type="removeAddr">删除</a></span>');
							label.append(span);
							divP.append(label).append(autoLabel);
						}else{
							divP = $('<p class="pt5 pb5"></p>');
							label = $('<label></label>');
							label.append(span);
							divP.append(label);
						}
						entAddrList.append(divP);
						if(obj.isDefault == '1'){
							$('#'+key).attr('checked','checked');
						}
					}
					if(radioId){
						$('#'+radioId).attr('checked','checked');
					}
					//删除或修改地址
					deliveryAddrJs.delOrmodAddr(function(resp){
						if(resp){
							self.loadAddress();
							if(resp.type == 'removeAddr'){
								comm.yes_alert(comm.lang("systemBusiness").toolManage_removeDeliverAddressSucc);
							}else if(resp.type == 'modifyAddr'){
								comm.yes_alert(comm.lang("systemBusiness").toolManage_modifyDeliverAddressSucc);
							}
						}
					});
				}
			});
		}
	};
	return self;
});