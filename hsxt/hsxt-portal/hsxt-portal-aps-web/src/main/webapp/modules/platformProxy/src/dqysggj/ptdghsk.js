define(['text!platformProxyTpl/dqysggj/ptdghsk.html',
		'text!platformProxyTpl/dqysggj/qyhsh.html',
		'text!platformProxyTpl/dqysggj/hsklb.html',
		'text!platformProxyTpl/dqysggj/hsksgtj.html',
		'text!platformProxyTpl/dqysggj/addAddress.html',
		'text!platformProxyTpl/dqysggj/ddtjqr.html',
		'platformProxyDat/platformProxy',
		'platformProxySrc/dqysggj/deliveryAddr',
		"commDat/common",
        'platformProxyLan'
		],function(ptdghskTpl, qyhshTpl, hsklbTpl, ddxqTpl, addAddressTpl, ddtjqrTpl,dataMoudle,deliveryAddrJs,commonAjax){
	var self = {
		companyCustId:null,
		companyCustName : null,
		addrMap : null,
		radioId: null,
		product : null,
		orderTotal:0,
		// 选择资源段
		segment:null,
		segmentId:null,
		//所有资源段
		allSegmentMap:null,
		showPage : function(){
		  	addrMap = {};
		  	segment = new Array();
			segmentId = new Array();
			radioId=null;
			allSegmentMap = {},
			
			$('#busibox').html(ptdghskTpl);

			$('#ptdghskTpl').html(qyhshTpl);
			self.showTpl($('#ptdghskTpl'));
			
			$('#qyhsh_query_btn').click(function(){
				var resNo=$("#companyResNo").val();
				if(!comm.isHsResNo(resNo)){
					comm.error_alert(comm.lang("platformProxy").isHsResNo,400);
					return;
				}
				if(!comm.isTrustResNo(resNo)){
					comm.error_alert(comm.lang("platformProxy").isTEntHsResNo);
					return;
				}
				commonAjax.findCompanyMainInfoByResNo({companyResNo:resNo},function(res){
					var data=res.data;
					if(data!=null){
						companyCustId=data.entCustId;
						companyCustName=data.entName;
						$("#companyName").html(data.entName);
						$("#contactPerson").html(data.contactPerson);
						$("#contactPhone").html(data.contactPhone);
						$('#qyhsh_next_tpl').removeClass('none')
					}
				});
			});
			
			$('#qyhsh_next_btn').click(function(){
				//加载页面
				$('#hsklbTpl').html(_.template(hsklbTpl));
				self.showTpl($('#hsklbTpl'));
				commonAjax.findCompanyExtInfo({companyCustId:companyCustId},function(resp){
					if(resp){
						var startResType = resp.data.startResType;
						if(comm.isEmpty(startResType) || startResType =='3'){
							$('#startResTypeHint').removeClass('none');
						}else{
							dataMoudle.searchEntStatusInfo({companyCustId:companyCustId},function(res){
								if(res){
									var obj = {
											status : res.data.baseStatus
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
								}
							});
						}
					}
				});
			});		
		},
		
		//正常的操作流程
		normalOptFlow : function(){
						
			//显示购买资源协议
			self.showBuyResAgreement();
			
			//显示企业资源详情
			self.showEntResSegmentDetail();
			
			$('#hsklb_last_btn').click(function(){
				self.showTpl($('#ptdghskTpl'));	
			});
			
			//填写核对订单下一步单击事件
			$('#hsklb_next_btn').click(function(){
				//验证勾选框  (暂时屏蔽协议 by likui 2016-04-19)
//				if(!confirmBox.checked){
//					comm.error_alert(comm.lang("platformProxy").pleaseSelectAgreement);
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
					comm.error_alert(comm.lang("platformProxy").pleaseSelectSegment);
					return false;
				}				
				$('#hskddxqTpl').html(_.template(ddxqTpl,{segment:segment}));
				self.showTpl($('#hskddxqTpl'));
				
				//加载地址
				self.loadAddress();
				//新增地址
				deliveryAddrJs.addAddr(companyCustId,function(resp){
					comm.yes_alert(comm.lang("platformProxy").toolManage_addDeliverAddressSucc);
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
					self.showTpl($('#hsklbTpl'));	
				});
			});			
		},
		
		//订单提交
		orderSumitPage : function(){
			//成功提交订单订单提交单击事件
			$('#btn_hsksg_ddtj').bind('click',function(){
				var  radio = $('input[name="deliverAddr"]:checked');
				var addrData = addrMap[radio.attr("id")];
				if(comm.isEmpty(addrData)){
					comm.error_alert(comm.lang("platformProxy").toolManage_selectAddr);
					return;
				}
				if(comm.isEmpty(addrData.fullAddr)){
					comm.error_alert(comm.lang("platformProxy").contactAddrIsNull);
					return;
				}
				var param = {
					companyCustId:companyCustId,
					companyName : companyCustName,
					product:encodeURIComponent(JSON.stringify(product)),
					//segmentId:encodeURIComponent(JSON.stringify(segmentId)),
					segment:encodeURIComponent(JSON.stringify(segment)),
					addr:encodeURIComponent(JSON.stringify(addrData))
				};
				dataMoudle.commitBytoolOrderCard(param,function(resp){
					if(resp){
						$('#busibox').html(_.template(ddtjqrTpl,resp.data));
						$('#ddqr_success').click(function(){
							self.showPage();
						})
					}
				});
			});
		},

		//显示企业资源详情
		showEntResSegmentDetail : function(){
			dataMoudle.queryEntResourceSegment({companyCustId:companyCustId},function(resp){
				if(resp){
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

						/*暂时屏蔽修改
						var segment = obj[i];
						var bought = segment.status != 3?'disabled="disabled" checked="checked"':'';
						var boughtStr = segment.status == 1?'已申购':(segment.status == '2'?'已下单':'');
						var checkClass = '';
						if((i + 1) == startBuyRes){
							checkClass = 'show';
						}else if(i + 1 > startBuyRes){
							checkClass = 'none';
						}
						var spanClass = segment.buyStatus?'cl-gray':'';*/


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
		/* 显示模版方法
		 * tpl:模版对象
		 */
		showTpl : function(tpl){
			$('#ptdghskTpl, #hsklbTpl, #hskddxqTpl, #modifyAddress_dialog, #addAddress_dialog').addClass('none');
			tpl.removeClass('none');
		},
		// 加载收货地址
		loadAddress : function(){
			dataMoudle.serchEntAddress({companyCustId:companyCustId},function(resp){
				if(resp){
					var addr = resp.data;
					var entAddrList = $('#entAddrList');
					entAddrList.empty();
					for(var i = 1 ; i <= addr.length ; i++){
						var obj = addr[i-1];
						var divP = null;
						var label = null;
						var checkd = null;
						if(obj.isDefault == 1){
							checkd = 'checked="checked"';
						}
						var key = obj.addrType+'_'+i;
						var areaInfo = "";
						if(obj.addrType == 'auto'){
							areaInfo = cacheUtil.getRegionByCode(obj.countryNo,obj.provinceNo, obj.cityNo,' ');
							if(areaInfo){
								obj.fullAddr = areaInfo.data + obj.fullAddr;
							}
						}
						addrMap[key] = obj;
						var span = $('<span class="mr5"><input type="radio" '+checkd+' name="deliverAddr" id="'+key+'" class="m03vm"></span>'+
								'<span class="mr10">['+comm.lang("platformProxy").toolManage_addrType[obj.addrType]+']</span>'+
								'<span class="mr5">'+comm.valToLongShow(obj.fullAddr,50)+'</span>'+
								'<span class="mr5">'+obj.entName+'</span>'+
								'<span class="mr5">('+obj.linkMan+' 收)</span>'+
								'<span class="mr5">'+obj.mobile+'</span>'+
								'<span>'+comm.navNull(obj.zipCode)+'</span>');{
							divP = $('<p class="pt5 pb5"></p>');
							label = $('<label></label>');
							label.append(span);
							divP.append(label);
						}
						entAddrList.append(divP);
						if(obj.isDefault == '1'){
							$('#'+obj.addrType+'_'+i).attr('checked','checked');
						}
					}
					if(radioId){
						$('#'+radioId).attr('checked','checked');
					}
				}
			});
		}
	};
	return self;
});