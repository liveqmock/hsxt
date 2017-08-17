define(['text!systemBusinessTpl/toolManage/serviceToolBuy/toolLoad.html',
		'text!systemBusinessTpl/toolManage/serviceToolBuy/submitOrder.html',
		'text!systemBusinessTpl/toolManage/serviceToolBuy/payment.html',
		"text!systemBusinessTpl/toolManage/deliveryAddr/addAddress.html",
		'systemBusinessDat/systemBusiness',
		'systemBusinessSrc/toolManage/deliveryAddr'
		], function(toolLoadTpl, submitOrderTpl,paymentTpl, addAddressTpl,systemBusinessAjax,deliveryAddrJs){
	// 配置单列表
	var confs = new Array();
	// 收货地址单选按钮id
	var radioId = null;
	// 所有工具集合
	var toolMap = {};
	//订单总金额
	var orderTotal = 0;
	var selectTool = null;
	//地址集合
	var addrMap = {};
	var self = {
		showPage : function(){
			//加载页面
			$('#busibox').html(_.template(toolLoadTpl ,{}) ).append(paymentTpl);
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
			// 加载产品
			self.loadProduct();
			//填写核对订单下一步单击事件
			$('#btn_gjsg_xyb').click(function(){
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
						var id = arr[0];
						var quanilty = $("#"+id+"_VAL_"+arr[1]).val();
						var product = toolMap[id+"_"+arr[1]];
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
						id = id=='POINT_MCR'?'CONSUME_MCR':id;
						//选中的工具
						var tool = {
							code : id,
							num : Number(quanilty)
						};
						var inTool = selectTool[id];
						if(comm.isNotEmpty(inTool)){
							inTool.num = inTool.num + Number(quanilty);
							selectTool[id] = inTool;
						}else{
							selectTool[id] = tool;
						}
					}
				});

				for (var key in selectTool) {
					var obj = selectTool[key];
					// 验证可以购买的数量
					systemBusinessAjax.serchMayBuyToolNum({toolType:obj.code},function(resp){
						if(resp){
							if(Number(resp.data) < Number(obj.num)){
								var codeMsg = obj.code == 'CONSUME_MCR' ? comm.lang("systemBusiness").toolManage_consumePoint :comm.lang("systemBusiness").toolManage_categoryCode[obj.code]
								var msg =comm.strFormat(comm.lang("systemBusiness").toolManage_toolMaxNum,[codeMsg,resp.data]);
								comm.yes_alert(msg,500);
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
					comm.error_alert(comm.lang("systemBusiness").toolManage_toolNameSelect);
					return;
				}
				$("#gjsg_1").addClass('none');
				$("#gjsg_2").removeClass('none').html(_.template(submitOrderTpl,{confs:confs}));
				$('#amountPayable').text(comm.formatMoneyNumber(orderTotal));
				//加载地址
				self.loadAddress();
				//新增地址
				deliveryAddrJs.addAddr(addAddressTpl,function(resp){
					comm.yes_alert(comm.lang("systemBusiness").toolManage_addDeliverAddressSucc);
					self.loadAddress();
				});

				//成功提交订单上一步单击事件
				$('#btn_gjsg_syb').click(function(){
					var  radio = $('input[name="deliverAddr"]:checked');
					radioId = radio.attr("id");
					$("#gjsg_1").removeClass('none');
					$("#gjsg_2").addClass('none').html('');
				});

				//成功提交订单订单提交单击事件
				$('#btn_gjsg_ddtj').click(function(){
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
						confs:encodeURIComponent(JSON.stringify(confs)),
						addr:encodeURIComponent(JSON.stringify(addrData))
					};
					//订单提交
					systemBusinessAjax.commitByToolOrder(param,function(resp){
						if(resp){
							$('#pay_div').removeClass('none');
							$("#gjsg_2").addClass('none');
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
		},

		//加载产品
		loadProduct : function(){
			systemBusinessAjax.seachMayBuyTool({serviceType:'SWIPE_CARD'},function(resp){
				if(resp){
					var tool = resp.data;
					if(tool.length > 0){
						$('#gjsg_1').removeClass("none");
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
							toolMap[tool[i].categoryCode+'_'+tool[i].productId] = data;
							var li = $('<li></li>');

							var imgDiv = $('<div align="center"><img src="'+comm.getFsServerUrl(tool[i].microPic)+'" width="80" height="120" /></div>');

							var checkDiv = $('<div class="mt10 clearfix">'+
									'<span class="f1"><input type="checkbox" class="checkbox-vm" id="'+tool[i].categoryCode+'_CHE_'+tool[i].productId+'" name="categoryCode" /></span>'+
									'<span class="gjsg-list-name">'+tool[i].productName+'</span></div>');

							var iptDiv = $('<div class="mt5 clearfix"><span class="fl pay-icon-sprice pay-text-red pay-h25-middle f14">'+comm.formatMoneyNumber(tool[i].price)+'</span>' +
								'<input id="'+tool[i].categoryCode+'_VAL_'+tool[i].productId+'" maxlength="4" class="btn_no fr w60" placeholder="输入数量"></div>');

							li.append(imgDiv).append(checkDiv).append(iptDiv);
							ul.append(li);
							comm.onlyNum(tool[i].categoryCode + '_VAL_'+tool[i].productId);
						}
					}else{
						$('#bizOptError').removeClass('none');
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
						var checkd = null;
						if(obj.isDefault == 1){
							checkd = 'checked="checked"';
						}
						var key = obj.addrType+'_'+i;
						var areaInfo = "";
						if(obj.addrType == 'auto'){
							areaInfo = cacheUtil.getAreaSplitJoint(null, obj.provinceNo, obj.cityNo);;
							if(areaInfo){
								obj.fullAddr = areaInfo.data + obj.fullAddr;
							}
						}
						addrMap[key] = obj;
						var span = $('<span class="mr5"><input type="radio" '+checkd+' name="deliverAddr" id="'+key+'" class="m03vm"></span>'+
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
	return 	self;
});