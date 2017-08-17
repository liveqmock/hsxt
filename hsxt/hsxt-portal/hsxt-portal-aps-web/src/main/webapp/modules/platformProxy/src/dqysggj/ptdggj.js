define(['text!platformProxyTpl/dqysggj/ptdggj.html',
		'text!platformProxyTpl/dqysggj/qyhsh.html',
		'text!platformProxyTpl/dqysggj/gjlb.html',
		'text!platformProxyTpl/dqysggj/ddxq.html',
		'text!platformProxyTpl/dqysggj/addAddress.html',
		'text!platformProxyTpl/dqysggj/ddtjqr.html',
		'platformProxyDat/platformProxy',
		'platformProxySrc/dqysggj/deliveryAddr',
        'platformProxyLan'],function(ptdggjTpl, qyhshTpl, gjlbTpl, ddxqTpl, addAddressTpl, ddtjqrTpl,dataMoudle,deliveryAddrJs){
	var self = {
		companyCustId:null,
		companyCustName:null,
		toolMap : null,
		addrMap : null,
		confs : null,
		radioId: null,
		orderTotal:0,
		showPage : function(){
			toolMap = {};
		  	addrMap = {};
			confs = new Array();
			radioId=0;
			
			$('#busibox').html(ptdggjTpl);
			
			$('#ptdggjTpl').html(qyhshTpl);
			self.showTpl($('#ptdggjTpl'));
			$('#qyhsh_query_btn').click(function(){
				var resNo=$("#companyResNo").val();
				if(comm.isEmpty(resNo)){
					comm.error_alert(comm.lang("platformProxy").pleaseInputResNo);
					return;
				}
				if(!comm.isTrustResNo(resNo)&&!comm.isMemberResNo(resNo)){
					comm.error_alert(comm.lang("platformProxy").isTorBHsResNo);
					return;
				}
				dataMoudle.companyInfor({resNo:resNo},function(res){
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
				dataMoudle.searchEntStatusInfo({companyCustId:companyCustId},function(res){
					var reponse = {
							status : res.data.baseStatus
					};
					//加载页面
					$('#gjlbTpl').html(_.template(gjlbTpl,reponse));
					// 加载产品
					self.loadProduct();
				    self.showTpl($('#gjlbTpl'));
				    
				    $('#gjlb_last_btn').click(function(){
				    	self.showTpl($('#ptdggjTpl'));
				    });
					//填写核对订单下一步单击事件
					$('#gjlb_next_btn').click(function(){
						var i = 0;
						orderTotal = 0;
						var falg = false;
						confs.length = 0;
						selectTool = {};
						//获取选中的工具
						$("#gjsg_1 [name='categoryCode']").each(function(){ 
							var check = $(this);
							if(check.attr("checked")){
								var arr = check.attr("id").split('_CHE_');
								var code = arr[0];
								var quanilty = $("#"+code+"_VAL_"+arr[1]).val();
								var product = toolMap[code+"_"+arr[1]];
								product.quanilty = quanilty;
								var totalPrice = Number(product.price)*Number(quanilty);
								product.totalPrice = totalPrice;
								confs[i] = product;
								orderTotal = orderTotal + totalPrice;
								i++;
								if(comm.isEmpty(quanilty)){
									comm.yes_alert(comm.lang("platformProxy").toolNumNotNull);
									falg = true;
									return;
								}
								code = code=='POINT_MCR'?'CONSUME_MCR':code;
								//选中的工具
								var tool = {
										code : code,
										num : Number(quanilty)
								};
								var inTool = selectTool[code];
								if(comm.isNotEmpty(inTool)){
									inTool.num = inTool.num + Number(quanilty);
									selectTool[code] = inTool;
								}else{
									selectTool[code] = tool;
								}
							}
						});
						
						for (var key in selectTool) { 
							var obj = selectTool[key];
							// 验证可以购买的数量
							dataMoudle.serchMayBuyToolNum({toolType:obj.code,companyCustId:companyCustId},function(resp){
								if(resp){
									if(Number(resp.data) < Number(obj.num)){
										var codeMsg = obj.code == 'CONSUME_MCR' ? comm.lang("platformProxy").toolManage_consumePoint :comm.lang("platformProxy").toolManage_categoryCode[obj.code]
										var msg =comm.strFormat(comm.lang("platformProxy").toolManage_toolMaxNum,[codeMsg,resp.data]);
										comm.yes_alert(msg,450);
										falg = true;
										return;
									}
								}
							});
						}
						if(falg){
							return;
						}
						if(confs.length <= 0){
							comm.error_alert(comm.lang("platformProxy").toolManage_toolNameSelect);
							return;
						}
						$('#gjddxqTpl').html(_.template(ddxqTpl,{confs:confs}));
						self.showTpl($('#gjddxqTpl'));
						$('#amountPayable').text(comm.formatMoneyNumberAps(orderTotal));
						//加载地址
						self.loadAddress();
						//新增地址
						deliveryAddrJs.addAddr(companyCustId,function(resp){
							comm.yes_alert(comm.lang("platformProxy").toolManage_addDeliverAddressSucc);
							self.loadAddress();
						});
						$('#syb_btn').click(function(){
							self.showTpl($('#gjlbTpl'));	
						});
					
						//成功提交订单订单提交单击事件
						$('#ddtj_btn').click(function(){
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
									confs:encodeURIComponent(JSON.stringify(confs)),
									addr:encodeURIComponent(JSON.stringify(addrData))
							};
							dataMoudle.commitByToolOrder(param,function(resp){
								if(resp){
									$('#busibox').html(_.template(ddtjqrTpl,resp.data));
									$('#ddqr_success').click(function(){
										self.showPage();
									})
								}
							});
						});	
					});
				});
			});
		},
		showTpl : function(tpl){
			$('#ptdggjTpl, #gjlbTpl, #gjddxqTpl, #modifyAddress_dialog, #addAddress_dialog').addClass('none');
			tpl.removeClass('none');
		},
		//加载产品
		loadProduct : function(){
			dataMoudle.seachMayBuyTool({serviceType:'SWIPE_CARD',companyCustId:companyCustId},function(resp){
				if(resp){
					var tool = resp.data;
					if(tool.length > 0){
						var ul = $('#gjsg_1 #toolUl');
						ul.empty();
						for(var i = 0 ; i < tool.length ; i++){
							var data = {
								categoryCode:tool[i].categoryCode,
								price:tool[i].price,
								productId:tool[i].productId,
								productName:tool[i].productName,
								unit:tool[i].unit
							};
							toolMap[tool[i].categoryCode + '_'+tool[i].productId] = data;
							var li = $('<li></li>');
							var imgDiv = $('<div align="center"><img src="'+comm.getFsServerUrl(tool[i].microPic)+'" width="80" height="120" /></div>');

							var checkDiv = $('<span class="mt10 clearfix">'+
									'<span class="fl"><input type="checkbox" class="checkbox-vm" id="'+tool[i].categoryCode+'_CHE_'+tool[i].productId+'" name="categoryCode" /></span>'+
									'<span class="gjsg-list-name">'+tool[i].productName+'</span></div>');

							var iptDiv = $('<div class="mt5" align="center"><span class="fl pay-icon-sprice pay-text-red pay-h25-middle f14">'+comm.formatMoneyNumberAps(tool[i].price)+'</span>' +
								'<input id="'+tool[i].categoryCode+'_VAL_'+tool[i].productId+'" maxlength="4" class="btn_no fr w60" placeholder="输入数量"></div>');

							li.append(imgDiv).append(checkDiv).append(iptDiv);
							ul.append(li);
							comm.onlyNum(tool[i].categoryCode + '_VAL_'+tool[i].productId);
						}
					}
				}
			});
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
								'<span class="mr5">'+comm.navNull(obj.entName)+'</span>'+
								'<span class="mr5">('+comm.navNull(obj.linkMan)+' 收)</span>'+
								'<span class="mr5">'+comm.navNull(obj.mobile)+'</span>'+
								'<span>'+comm.navNull(obj.zipCode)+'</span>');
						
							divP = $('<p class="pt5 pb5"></p>');
							label = $('<label></label>');
							label.append(span);
							divP.append(label);
						
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