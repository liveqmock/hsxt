define(['text!systemBusinessTpl/toolManage/cardBuy/cardLoad.html',
		'text!systemBusinessTpl/toolManage/cardBuy/submitOrder.html',
		'text!systemBusinessTpl/toolManage/cardBuy/payment.html',
		"text!systemBusinessTpl/toolManage/deliveryAddr/modifyAddress.html",
		"text!systemBusinessTpl/toolManage/deliveryAddr/addAddress.html",
		'systemBusinessDat/systemBusiness',
		'systemBusinessSrc/toolManage/deliveryAddr'
		], function(cardLoad, submitOrder, paymentTpl, modifyAddressTpl, addAddressTpl,systemBusinessAjax,deliveryAddrJs){
	// 配置单列表
	var confs = new Array();
	// 收货地址单选按钮id
	var radioId = null;
	// 所有工具集合
	var toolMap = {};
	//订单总金额
	var orderTotal = 0;
	//地址集合
	var addrMap = {};
	var self = {
		showPage : function(){			
			//查询企业状态信息  当状态为注销中时 不能操作该项
			systemBusinessAjax.searchEntStatusInfo({},function(res){				
				var reponse = {
						status : res.data.info.baseStatus
				};
				$('#busibox').html(_.template(cardLoad,reponse)).append(paymentTpl);
				// 加载产品
				self.loadProduct();
				//填写核对订单下一步单击事件
				$('#btn_hsksg_xyb').click(function(){
					var i = 0;
					orderTotal = 0;
					var falg = false;
					confs.length = 0;
					var code = "";
					var cardNum = 0;
					//获取选中的工具
					$("#hsksg_1 [name='categoryCode']").each(function(){ 
						var check = $(this);
						if(check.attr("checked")){
							var id = check.attr("id").split('_CHE')[0];
							code = id.split('-_-')[0];
							var quanilty = $("#"+id+"_VAL").val();
							var product = toolMap[id];
							product.quanilty = quanilty;
							var totalPrice = Number(product.price)*Number(quanilty);
							product.totalPrice = totalPrice;
							confs[i] = product;
							orderTotal = orderTotal + totalPrice;
							i++;
							var reg = new RegExp("^[0-9]*$"); 
							if(comm.isEmpty(quanilty)||!reg.test(quanilty)||Number(quanilty)==0 ){
								comm.yes_alert(comm.lang("systemBusiness").toolManage_toolNumNotNull);
								falg = true;
								return;
							}
							cardNum = cardNum + Number(quanilty);
							
						}
					});
					// 验证可以购买数量
					systemBusinessAjax.serchMayBuyToolNum({toolType:code},function(resp){
						if(resp){
							var mayBuyNum = Number(resp.data);
							if(mayBuyNum < cardNum){
								var msg =comm.strFormat(comm.lang("systemBusiness").toolManage_toolMaxNum,[comm.lang("systemBusiness").toolManage_categoryCode[code],resp.data]);
								comm.yes_alert(msg);
								falg = true;
								return;
							}
							if(mayBuyNum != cardNum && cardNum%1000 != 0){
								var msg = comm.strFormat(comm.lang("systemBusiness").toolManage_cardBuyNum,[mayBuyNum]);
								comm.yes_alert(msg,600);
								falg = true;
								return;
							}
						}
					});
					if(falg){
						return;
					}
					if(confs.length <= 0){
						comm.error_alert(comm.lang("systemBusiness").toolManage_toolNameSelect);
						return;
					}
					$("#hsksg_1").addClass('none');
					$("#hsksg_2").removeClass('none').html(_.template(submitOrder,{confs:confs}));	
					$('#amountPayable').text(comm.formatMoneyNumber(orderTotal));
					//加载地址
					self.loadAddress();
					//新增地址
					deliveryAddrJs.addAddr(addAddressTpl,function(resp){
						comm.yes_alert(comm.lang("systemBusiness").toolManage_addDeliverAddressSucc);
						self.loadAddress();
					});
					
					//成功提交订单上一步单击事件
					$('#btn_hsksg_syb').click(function(){
						var  radio = $('input[name="deliverAddr"]:checked');
						radioId = radio.attr("id");
						$("#hsksg_1").removeClass('none');
						$("#hsksg_2").addClass('none').html('');
					});
					
					//成功提交订单订单提交单击事件
					$('#btn_hsksg_ddtj').click(function(){
						var  radio = $('input[name="deliverAddr"]:checked');
						var addrData = addrMap[radio.attr("id")];
						if(comm.isEmpty(addrData)){
							comm.error_alert(comm.lang("systemBusiness").toolManage_selectAddr);
							return;
						}
						var param = {
								confs:encodeURIComponent(JSON.stringify(confs)),
								addr:encodeURIComponent(JSON.stringify(addrData))
						};
						systemBusinessAjax.commitByToolOrder(param,function(resp){
							if(resp){
								$('#pay_div').removeClass('none');
								$("#hsksg_2").addClass('none');
								require(['systemBusinessSrc/toolManage/pay/pay'], function(pay){
									var obj = resp.data;
									obj.paymentMethod = 'hsbPay';
									obj.isFormat = 'format';
									obj.orderType = '103';
									obj.isFrist = 'true';
									pay.showPage(obj);
								});
							}
						});
					});	
				});
				
			});
		},
		//加载互生卡
		loadProduct : function(){
			systemBusinessAjax.seachMayBuyTool({serviceType:'CARD'},function(resp){
				if(resp){
					var tool = resp.data;
					if(tool.length > 0){
						var ul = $('#hsksg_1 #toolUl');
						ul.empty();
						for(var i = 0 ; i < tool.length ; i++){
							var data = {
								categoryCode:tool[i].categoryCode,
								price:tool[i].price,
								productId:tool[i].productId,
								productName:tool[i].productName,
								unit:tool[i].unit
							};
							var toolId = tool[i].categoryCode +'-_-'+tool[i].productId;
							toolMap[toolId] = data;
							var li = $('<li></li>');							
							var imgDiv = $('<div align="center"><img src="'+tool[i].microPic+'" width="120" height="120" /></div>');							
							var cardStyle = $('<div class="ml30 mt5 mb5" align="left"><label><input type="checkbox" class="checkbox-vm"/><span class="f13 pay-h25-middle">使用定制卡样</span></label></div>');						
							var checkDiv =$('<div align="left" class="ml30"><label>'+
									'<input type="checkbox" class="checkbox-vm" id="'+toolId+'_CHE" name="categoryCode" />'+
									'<span class="f13 pay-h25-middle">'+tool[i].productName+'</span></label>'+
									'<span class="pay-icon-sprice ml10 pay-text-red pay-h25-middle f14">'+tool[i].price+'</span></div>');																
							var iptDiv = $('<div class="mt5" align="center"><input id="'+toolId+'_VAL" maxlength="4" class="btn_no" placeholder="输入数量"></div>');
							li.append(imgDiv).append(cardStyle).append(checkDiv).append(iptDiv);
							ul.append(li);
							comm.onlyNum(toolId + '_VAL');
						}
					}
				}
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
						addrMap[key] = obj;
						var areaInfo = "";
						if(obj.addrType == 'auto'){
							areaInfo = cacheUtil.getAreaSplitJoint(null, obj.provinceNo, obj.cityNo);;
							if(areaInfo){
								areaInfo = areaInfo.data;
							}
						}
						var span = $('<span class="mr5"><input type="radio" name="deliverAddr" id="'+key+'" class="m03vm"></span>'+
								'<span class="mr10">['+comm.lang("systemBusiness").toolManage_addrType[obj.addrType]+']</span>'+
								'<span class="mr5">'+comm.valToLongShow((areaInfo + obj.fullAddr),50)+'</span>'+
								'<span class="mr5">'+comm.valToLongShow(obj.entName,30)+'</span>'+
								'<span class="mr5">('+comm.valToLongShow(obj.linkMan,20)+' 收)</span>'+
								'<span class="mr5">'+obj.mobile+'</span>'+
								'<span>'+comm.navNull(obj.zipCode)+'</span>');
						if(obj.addrType == 'auto'){
							divP = $('<div class="pt5 pb5 clearfix"></div>');
							label = $('<label class="fl">');
							var autoLabel = $('<span class="fr"><a class="ml10" data-addrId='+obj.addrId+' data-type="modifyAddr">修改</a><a class="ml10" data-addrId='+obj.addrId+' data-type="removeAddr">删除</a></span>');
							label.append(span);
							divP.append(label).append(autoLabel);
						}else{
							divP = $('<p class="pt5 pb5"></p>');
							label = $('<label><label>');
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