define(['text!toolorderTpl/gjpsgl/gjpsgl.html',
		'text!toolorderTpl/gjpsgl/gjpsgl_pzpsd_dialog.html',
		'text!toolorderTpl/gjpsgl/gjpsgl_pzpsd_2_dialog.html',
		'text!toolorderTpl/gjpsgl/bkpsd_pz.html',
		'text!toolorderTpl/gjpsgl/gjpsgl_ckpsd_dialog.html',
		'toolorderDat/toolorder'
		], function(gjpsglTpl, gjpsgl_pzpsd_dialogTpl, gjpsgl_pzpsd_2_dialogTpl, pzbkpsdTpl, gjpsgl_ckpsd_dialogTpl,toolorder){
	var self = {
		gridObj_2 : null,
		json_2 : null,
		giftObj : null,
		supportObj:null,
		showPage : function(){
			gridObj = null;
			json_2 =null;
			giftObj = null;
			supportObj=null;
			$('#busibox').html(_.template(gjpsglTpl));

			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/

			/**下拉列表  工具类型**/
			comm.initSelect("#categoryCodeType",comm.lang("toolorder").categoryCode);

			/** 下拉列表  订单类型 **/
			comm.initSelect("#orderType",comm.lang("toolorder").gjpsgl_orderType,null,1);

			$("#searchBtn").click(function(){
				if(!self.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_startDate : $("#search_startDate").val(), //配置开始日期
					search_endDate : $("#search_endDate").val(),		//配置结束日期
					search_hsResNo : $("#hsResNo").val().trim(),			//互生号
					search_hsCustName : $("#hsCustName").val().trim(),		//客户名称
					search_type : $("#categoryCodeType").attr("optionvalue"),		//工具类型
					search_selectType : $("#orderType").attr("optionvalue")//查询订单类型
				};
				gridObj = comm.getCommBsGrid("searchTable","query_tooldispatching_list",params,comm.lang("toolorder"),self.detail);

			});
			//点击【配置配送单】按钮
			$('#pzpsd_btn').click(function(){
				if(comm.isEmpty(gridObj)){
					return false;
				}
				var records = gridObj.getCheckedRowsRecords();

				if(records && records.length > 0){

					var orderType = records[0].orderType;
					var confNos = "";
					var entCustId = records[0].entCustId;

					var entResNo = records[0].entResNo;

					for(var i = 0; i < records.length; i++){
                        confNos = confNos + records[i].confNo + ",";
					}
					confNos = confNos.substring(0,confNos.length - 1);

					var obj = {};
					obj.entCustId = entCustId;
					obj.confNo = confNos;
					obj.orderType = orderType;
					obj.entResNo = entResNo;

					self.pzpsd(obj);

				}else{
					comm.error_alert(comm.lang("toolorder").notSelectRows);
					return;
				}
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){
				return comm.lang("toolorder").categoryCode[record.categoryCode];
			}else if(colIndex == 7){
				if(record.orderType=='109'){
					return "申报申购";
				}else {
					return  "新增申购";
				}
			}else if(colIndex == 8){
				return comm.lang("toolorder").orderType3[record.orderType];
			}else if(colIndex == 9){
				//配置配送单
				var link1 = $('<a>'+comm.lang("toolorder").pzpsd+'</a>').click(function(e) {

					self.pzpsd(record);

				}) ;

				return link1;
			}
		},

		edit : function(record, rowIndex, colIndex, options){

			if(colIndex == 6){
				// 查看配送单
				var link2 = $('<a>'+comm.lang("toolorder").ckpsd+'</a>').click(function(e) {

					self.ckpsd(record);

				}) ;

				return link2;
			}
		},

		//当添加或删除 配送物品清单列表中的数据时 重新加载相应的grid
		reloadGridObj : function(grid,tableId,jsonData){
			//重新加载数据  因为调用gridObj_2.refreshPage()无效
			grid = $.fn.bsgrid.init(tableId, {
				autoLoad: true,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,
				displayBlankRows: false ,
				localData:jsonData,
				operate : {
					del : function(record, rowIndex, colIndex, options){
						if(record.flag){
							var link1 = $('<a>'+comm.lang("toolorder").del+'</a>').click(function(e) {

								self.shanChu(grid,tableId,jsonData,rowIndex);

							}) ;

							return link1;
						}

					}
				}

			});
			$('#'+tableId+"_pt").addClass('td_nobody_r_b');
		},

		//配送物品清单列表中 点击删除
		shanChu : function(grid,tableId,jsonData,row){
			var obj = grid.getRecord(row);
			self.addToolNum(obj.categoryCode, obj.productId, obj.quantity);
			$("#toolNum").val(self.getToolNum(obj.categoryCode, obj.productId));
			$("#toolName_2").selectListValue({name:obj.productName,value:obj.productId});
			jsonData.splice(row,1);
			self.reloadGridObj(grid, tableId, jsonData);
		},

		//配送单配置
		pzpsd : function(obj){
			toolorder.queryShippinData({
				orderType : obj.orderType,		//订单类型103：新增申购 101:申报申购
				confNo : obj.confNo,			//配置单号
				searchEntCustId : obj.entCustId,		//客户号
				searchEntResNo:	obj.entResNo	//企业互生号
			},function(res){

				var shippingType = $("#orderType").attr("optionvalue");
				shippingType = comm.isNotEmpty(shippingType)?shippingType:1;
				/**
				 * 传入shippingType 决定添加发货单时添加的是申购类型的还是新增类型的发货单
				 **/
				self.initdialog(res.data,obj.confNo,shippingType,obj.orderType)

			});

		},

		//初始化配送单配置页面
		initdialog : function(obj,confNo,shippingType,orderType){
			obj.orderType = orderType;
			if(orderType=='105'){
				$('#dialogBox_pz2').html(_.template(pzbkpsdTpl, obj));
			}else{
				$('#dialogBox_pz2').html(_.template(gjpsgl_pzpsd_2_dialogTpl, obj));
			}
			/*弹出框*/
			$( "#dialogBox_pz2" ).dialog({
				title:comm.lang("toolorder").pzpsd,//"配置配送单",
				width:"1100",
				height:"980",
				closeIcon:true,
				modal:true,
				buttons:{
					"确定":function(){

						self.commitDespatch(confNo,obj,$(this),shippingType,orderType);

					},
					"取消":function(){
						$('#goodsType_2').tooltip().tooltip("destroy");
						$('#toolName_2').tooltip().tooltip("destroy");
						$('#psNum').tooltip().tooltip("destroy");
						$( this ).dialog( "destroy" );
					}
				}
			});

			giftObj = obj.ship.giftProduct;
			supportObj = obj.ship.supportProduct;
			//配送物品类型下拉列表
			comm.initSelect("#goodsType_2",comm.lang("toolorder").categoryCode2).change(function(e){

				var value = $(this).attr("optionvalue");
				var toolName = [];
				var defaultValue = {};
				if(value == 'GIFT'){
					if(giftObj != null && giftObj.length > 0){
						for(var i = 0 ; i < giftObj.length; i++){
							toolName.push({name:giftObj[i].productName,value:giftObj[i].productId});
							defaultValue.name = giftObj[i].productName;
							defaultValue.value = giftObj[i].productId;
						}
					}
				}else if(value == 'SUPPORT'){
					if(supportObj != null && supportObj.length > 0){
						for(var i = 0 ; i < supportObj.length; i++){
							toolName.push({name:supportObj[i].productName,value:supportObj[i].productId});
							defaultValue.name = supportObj[i].productName;
							defaultValue.value = supportObj[i].productId;
						}
					}
				}
				//工具名称下拉列表  与物品类型下拉列表联动
				$("#toolName_2").selectList({
					options:toolName
				}).change(function(e){
					var productId = $(this).attr("optionvalue");
					var whId = obj.ship.whId;
					var goodsType = $("#goodsType_2").attr("optionvalue");
					var toolNum = self.getToolNum(goodsType,productId);
					if(toolNum>0){
						$("#toolNum").val(toolNum);
					}else{
						//根据下拉选择查询相应的工具数量
						toolorder.queryTollNum({productId:productId,whId:whId},function(res){
							$("#toolNum").val(res.data);
							self.setToolNum(goodsType,productId,res.data);
						});
					}
				});
				$("#toolName_2").selectListValue(defaultValue);
			});

			//初始化工具名称下拉列表
			$("#toolName_2").selectList({
				options:[]
			});

			/*初始化配送方式下拉列表*/
			var psfs = [];
			if(obj.ship.mothods != null && obj.ship.mothods.length > 0){

				for(var i = 0; i < obj.ship.mothods.length; i++){

					psfs.push({name:obj.ship.mothods[i].smName,value:obj.ship.mothods[i].smId});

				}
			}
			$("#psfs_2").selectList({
				width:120,
				options:psfs
			}).change(function(e){

			});

			/*收件信息列表*/
			var addr_json = obj.ship.delivers;
			var searchTable_addr = $.fn.bsgrid.init('searchTable_addr', {
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔
				displayBlankRows: false ,   //显示空白行
				localData:addr_json,
				operate : {
					add : function(record, rowIndex, colIndex, options){
						var value = record.linkman +',' + record.fullAddr +',' + record.mobile+","+record.zipCode;
						var type = record.addrType=='ent' ?'[企业地址]' :'[联系地址]';
						var checked = orderType == '109'?'':'checked = checked';
						return '<input name="deliverId" type="radio" value="' + value + '" '+checked+'/> <span>'+type+'</span> ';
					}
				}

			})

			/*配送物品清单列表*/
			json_2 = obj.ship.configs;

			gridObj_2 = $.fn.bsgrid.init('searchTable_2', {
				autoLoad: true,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,
				displayBlankRows: false ,
				localData:json_2,
				operate : {
					del : function(record, rowIndex, colIndex, options){

					}
				}

			});

			$('#searchTable_2_pt').addClass('td_nobody_r_b');
			$('#searchTable_addr_pt').addClass('td_nobody_r_b');
			/*end*/

			/*复核选择验证方式下拉列表*/
			comm.initSelect("#verificationMode_pzpsd_2",
				comm.lang("toolorder").verificationMode).change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhyPwd_pzpsd_2').removeClass('none');
						$('#verificationMode_prompt_pzpsd_2').addClass('none');
						break;

					case '2':
						$('#fhyPwd_pzpsd_2').addClass('none');
						$('#verificationMode_prompt_pzpsd_2').removeClass('none');
						break;

					case '3':
						$('#fhyPwd_pzpsd_2').addClass('none');
						$('#verificationMode_prompt_pzpsd_2').removeClass('none');
						break;
				}
			});
			//初始化
			$("#verificationMode_pzpsd_2").selectListValue("1");
			//点击配送物品清单列表中的 【添加】按钮
			$("#addToolBtn").click(function(){

				//验证不能为空
				if(!self.addtool_validata()){
					return;
				}
				var goodsType = $("#goodsType_2").attr("optionvalue");
				var toolId = $("#toolName_2").attr("optionvalue");
				var toolNum =  self.getToolNum(goodsType, toolId);
				var psNum = $("#psNum").val();

				//判断配送数量不能大于库存数量
				if(parseInt(psNum) > parseInt(toolNum)){
					comm.error_alert(comm.lang("toolorder").psNumMax);
					return;
				}
				for(var i=0;i<json_2.length;i++){
					if(json_2[i].categoryCode == goodsType && json_2[i].productId == toolId){
						json_2[i].quantity = parseInt(json_2[i].quantity) + parseInt(psNum);
						self.addToolNum(goodsType, toolId, -psNum);
						$("#toolNum").val(self.getToolNum(goodsType, toolId));
						self.reloadGridObj(gridObj_2, "searchTable_2", json_2);
						return ;
					}
				}

				//添加临时数据
				var temp = {
					categoryCode: goodsType,	//配送物品类型
					productId: $("#toolName_2").attr("optionvalue"),		//工具编号
					productName: $("#toolName_2").val(),								//工具名称,
					quantity: $("#psNum").val(),									//配送数量,
					flag : true
				};
				json_2.push(temp);
				self.addToolNum(goodsType, toolId, -psNum);
				$("#toolNum").val(self.getToolNum(goodsType, toolId));
				if(gridObj_2){
					self.reloadGridObj(gridObj_2, "searchTable_2", json_2);
				}
			});
		},
		getToolNum : function(goodsType,toolId){
			if( goodsType == "GIFT" ){
				for(var i=0;i<giftObj.length;i++){
					if(giftObj[i].productId == toolId){
						return giftObj[i].toolNum==undefined?-1:giftObj[i].toolNum;
					}
				}
			}else if( goodsType == "SUPPORT" ){
				for(var i=0;i<supportObj.length;i++){
					return supportObj[i].toolNum==undefined?-1:supportObj[i].toolNum;
				}
			}else{
				comm.error_alert();
				return -1;
			}
		},
		setToolNum : function(goodsType,toolId,toolNum){
			if( goodsType == "GIFT" ){
				for(var i=0;i<giftObj.length;i++){
					if(giftObj[i].productId == toolId){
						giftObj[i].toolNum = toolNum;
					}
				}
			}else if( goodsType == "SUPPORT" ){
				for(var i=0;i<supportObj.length;i++){
					supportObj[i].toolNum = toolNum;
				}
			}
		},
		addToolNum : function(goodsType,toolId,toolNum){
			if( goodsType == "GIFT" ){
				for(var i=0;i<giftObj.length;i++){
					if(giftObj[i].productId == toolId){
						giftObj[i].toolNum = parseInt(giftObj[i].toolNum) + parseInt(toolNum);
					}
				}
			}else if( goodsType == "SUPPORT" ){
				for(var i=0;i<supportObj.length;i++){
					if(supportObj[i].productId == toolId){
						supportObj[i].toolNum = parseInt(supportObj[i].toolNum) + parseInt(toolNum);
					}
				}
			}
		},
		//点击配送物品清单列表中【添加】按钮时的验证
		addtool_validata : function(){
			return comm.valid({
				formID : '#addtool',
				rules : {
					goodsType_2:{
						required : true
					},
					psNum:{
						required : true,
						number : true,
						min : 1
					},
					toolName_2:{
						required : true
					}
				},
				messages : {
					goodsType_2:{
						required : comm.lang("toolorder")[34511]
					},
					psNum:{
						required : comm.lang("toolorder")[34512],
						number : comm.lang("toolorder")[34513],
						max : comm.lang("toolorder")[34514],
						min : comm.lang("toolorder")[34515]
					},
					toolName_2:{
						required : comm.lang("toolorder")[34510]
					}
				}
			});
		},

		/**
		 * 提交配送单
		 * @param confNo 配置单列表id
		 * @param whId 根据选择的赠品或配套物品填入
		 * @param winObj 当前窗口对象 用于关闭窗口
		 * @param shippingType 发货单类型 1：申报工具发货 2：新增工具发货
		 */
		commitDespatch : function(confNo,appObj,winObj,shippingType,orderType){
			var whId = appObj.ship.whId;
			if(!self.addShippin_validata()){
				return;
			}
			//收件信息
			var receiverInfo = $("input[name='deliverId']:checked").val();
			//复核员名称
			var userName = $("#userName").val();
			//复核员密码
			var password = $("#password").val();

			var params = comm.getRequestParams(params);

			var configs = "";
			var targetResNo,targetCustId,targetName,targetCustType;
			if(orderType=='105'){
				//消费者补卡
				targetResNo = appObj.entinfo.baseInfo.perResNo;
				targetCustId = appObj.entinfo.baseInfo.perCustId;
				targetName = appObj.entinfo.authInfo.userName;
				targetCustType = 1;//消费者固定为1
			}else if(orderType=='103'|| orderType=='110'){
				//企业新增}else if(orderType=='103'||orderType=='110'){
				targetResNo = appObj.entinfo.baseInfo.entResNo;
				targetCustId = appObj.entinfo.baseInfo.entCustId;
				targetName = appObj.entinfo.baseInfo.entName;
				targetCustType = appObj.entinfo.baseInfo.entCustType;
			}else if(orderType=='109'){
				//服务公司申报
				//targetResNo = appObj.entinfo.baseInfo.entResNo;
				targetResNo = appObj.serinfo.mainInfo.entResNo;
				targetCustId = appObj.entinfo.baseInfo.entCustId;
				targetName = appObj.serinfo.mainInfo.entName;
				targetCustType = appObj.entinfo.baseInfo.entCustType;
			}
			if(json_2.length > 0){
				for(var i = 0 ;i < json_2.length; i++){
					if(json_2[i].flag){

						var temp =  targetResNo +","+			//互生号 hsResNo
							json_2[i].categoryCode + "," +		// categoryCode 工具类别代码 GIFT：赠品   SUPPORT：配套产品
							json_2[i].productId + "," +	//工具编号  productId
							json_2[i].productName + "," +//工具名称 productName
							json_2[i].quantity + "," +		//配送数量 quantity
							userName +","+					//确认人 confUser
							whId + ":";							//根据选择的赠品或配套物品填入

						configs = configs + temp;

					}
				}
				configs = configs.substring(0,configs.length - 1);
			}
			var reInfo  = receiverInfo.split(",");

			var postData = {

				shippingType:shippingType , 	//发货单类型 1：申报工具发货 2：新增工具发货  3：售后工具发货
				targetResNo 	: targetResNo,		//目标对象互生号
				targetCustId	: targetCustId,//目标对象客户号
				targetName		: targetName,//目标对象名称
				targetCustType	: targetCustType,//目标对象客户类型
				custType : 6,					//客户类型  com.gy.hsxt.common.constant.CustType
				receiver : reInfo[0], 			//收货人
				receiverAddr : reInfo[1], 		//收货地址
				mobile : reInfo[2], 				//手机
				postCode : comm.navNull(reInfo[3]),				//邮编
				smName : $("#psfs_2").val(),					//配送方式
				trackingNo : $("#trackingNo").val(),			//快递编号
				shippingFee : $("#shippingFee").val(),			//快递费
				consignor : params.custId,						//发货人
				confNo : confNo,								//主产品的配置清单编号
				conFigs : configs 								//配送物品清单
			};
			//验证双签
			comm.verifyDoublePwd(userName, password, 1, function(verifyRes){

				toolorder.commitShippinData(postData,function(res){
					comm.yes_alert(comm.lang("toolorder").toolSendSucc,null,function(){
						//关闭窗口
						winObj.dialog( "destroy" );
						//刷新列表
						if(gridObj){
							gridObj.refreshPage();
						}
					})
				});
			});

		},
		//添加配送单验证
		addShippin_validata : function(){
			
	      
			//收件信息
			var receiverInfo = $("input[name='deliverId']:checked").val();
			if(receiverInfo == null || "" == receiverInfo){
				comm.error_alert(comm.lang("toolorder")[34520]);
				return false;
			}
			//配送方式
			var psfs_2 = $("#psfs_2").val();
			if(psfs_2 == null || "" == psfs_2){
				comm.error_alert(comm.lang("toolorder")[34516]);
				return false;
			}
			//货运单号
			var trackingNo = $("#trackingNo").val();
			if(trackingNo == null || "" == trackingNo){
				comm.error_alert(comm.lang("toolorder")[34519]);
				return false;
			}
			//运费
			var shippingFee = $("#shippingFee").val();
			if(shippingFee == null || shippingFee == ""){
				comm.error_alert(comm.lang("toolorder")[34517]);
				return false;
			}
			var re=/^[0-9]*.?[0-9]{1,2}$/;

			if(!re.test(shippingFee)){

				comm.error_alert(comm.lang("toolorder")[34518]);
				return false;

			}
			
			var remark=$("#remark").val();
			if(remark!=""&&remark!=null){
				if(remark.length>300){
					comm.error_alert(comm.lang("toolorder").fuheMaxLength);
					return false;
				}
			}
			
			
			//复核员名称
			var userName = $("#userName").val();
			//复核员密码
			var password = $("#password").val();
			if(userName == null || userName == ""){
				comm.error_alert(comm.lang("toolorder")[34505]);
				return false;
			}
			if(password == null || password == ""){
				comm.error_alert(comm.lang("toolorder")[34506]);
				return false;
			}
			
			
			return true;

		},
		//查看配送单
		ckpsd : function(obj){
			$('#dialogBox_ck').html(_.template(gjpsgl_ckpsd_dialogTpl, obj));
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"查看配送单",
				width:"1100",
				modal:true,
				buttons:{
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/

			/*表格数据模拟*/
			var json_3 = [{
				"th_1":"POS机",
				"th_2":"10",
				"th_3":"订单工具"
			}];

			var gridObj_3;

			gridObj_3 = $.fn.bsgrid.init('searchTable_3', {
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔
				displayBlankRows: false ,   //显示空白行
				localData:json_3,
				operate : {
					del : function(record, rowIndex, colIndex, options){
						var link1 = $('<a>删除</a>').click(function(e) {
							this.shanChu();


						}) ;

						return link1;
					}
				}

			});

			$('#searchTable_3_pt').addClass('td_nobody_r_b');
			/*end*/

		},
		queryDateVaild : function(){
			return $("#search_form").validate({
				rules : {
					search_startDate : {
						required : true,
						endDate : "#search_endDate",
						oneyear : "#search_endDate"
					},
					search_endDate : {
						required : true
					},
					orderType : {
						required : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("common")[10001],
						endDate : comm.lang("common")[10003],
						oneyear : comm.lang("common")[10004]
					},
					search_endDate : {
						required : comm.lang("common")[10002]
					},
					orderType : {
						required : comm.lang("toolorder").pleaseSelectApplyType
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
		}
	};
	return self;
});