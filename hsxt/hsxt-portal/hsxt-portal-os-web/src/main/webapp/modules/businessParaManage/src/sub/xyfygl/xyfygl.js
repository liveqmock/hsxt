define(['text!businessParaManageTpl/sub/xyfygl/xyfygl.html',
		'text!businessParaManageTpl/sub/xyfygl/xyfygl_opt.html'
		],function( xyfyglTpl, xyfygl_optTpl ){
	
		var self = null;
		var gridObj = null;
		var flag = true;
	return {
 
		showPage : function(tabid){
			
			self = this;
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(xyfyglTpl )) ;
			
			$('#xyfygl_add_btn').click(function(){
				self.opt_add();	
			});
			
			var queryOptions = [{"name":"","value":""}];
			var datas = {"noPageSearchFlag":"true"};
			comm.validateDatas("xyglSearch",datas,function(results){
				var datas = results.data;
				$.each(datas,function(n,value){
					queryOptions.push({"name":value.agreeName,"value":value.agreeCode});
				});
				$("#xyfygl_query_xydm").selectList({
					borderWidth : 1,
					borderColor : '#999',
					width:150,
					optionWidth:155,
					optionHeight:150,
					overflowY:'auto',
					options: queryOptions
				})
			},"");
			
			/*下拉列表*/
			$("#xyfygl_query_jhzt").selectList({
				borderWidth : 1,
				borderColor : '#999',
				options:[
				    {name:'全部',value:''},
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			var xydm = $("#xyfygl_query_xydm").attr("optionValue");
			var fymc = $("#xyfygl_query_fymc").val();
			var jhzt = $("#xyfygl_query_jhzt").attr("optionValue");
			// 查询条件
			var queryConditon = {"xyfygl_xydm":xydm,"xyfygl_fymc":fymc,"xygl_jhzt":jhzt};
			
		    gridObj = comm.getCommBsGrid('queryList_xyfygl', "xyfyglSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			
		    //查询按钮
			$('#xyfygl_query_btn').click(function(){
				var xydm = $("#xyfygl_query_xydm").attr("optionValue");
				var fymc = $("#xyfygl_query_fymc").val();
				var jhzt = $("#xyfygl_query_jhzt").attr("optionValue");
				// 查询条件
				queryConditon = [
				             {"name":"xyfygl_xydm","value":xydm},
				             {"name":"xyfygl_fymc","value":fymc},
				             {"name":"xygl_jhzt","value":jhzt}
				             ]
				gridObj = comm.getCommBsGrid('queryList_xyfygl', "xyfyglSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			});

		},
		/*end*/
		
		opt_add : function(){
			
			$('#xyfyglDlg').html(xyfygl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xyfyglDlg" ).dialog({
				title:"新增",
				width:"1000",
				height:"420",
				modal:true,
				closeIcon : false,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							if(!flag){
								$("#xyfygl_opt_fydm").attr("title","该费用代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xyfygl_optForm").serializeArray();
							var xydm = $("#xyfygl_opt_xydm").attr('optionValue');
							var jflx = $("#xyfygl_opt_jflx").attr('optionValue');
							var kflx = $("#xyfygl_opt_kflx").attr('optionValue');
							var fylx = $("#xyfygl_opt_fylx").attr('optionValue');
							var bz = $("#xyfygl_opt_bz").attr('optionValue');
							var jhzt = $("#xyfygl_opt_jhzt").attr('optionValue');
							datas[0].value = xydm;
							datas[1].value = jflx;
							datas[2].value = kflx;
							datas[6].value = jhzt;
							datas[10].value = fylx;
							datas[12].value = bz;
							datas.push({"name":"xyfygl_opt_xydm","value":datas[0].value});
							comm.requestFun("xyfyglAdd",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xyfygl_pt_refreshPage').click();
							},"");
						}					
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	

			/*下拉列表*/
			initArrayData();
			
			$("#xyfygl_opt_fydm").change(function(){
				var fydm = $("#xyfygl_opt_fydm").val();
				var data = {"xyfygl_fydm":fydm};
				comm.validateDatas("xyfyglSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						flag = false;
						$("#xyfygl_opt_fydm").attr("title","该费用代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xyfygl_opt_fydm").attr("title","").tooltip().tooltip("destroy");
					}
				},"");
			});
			
			// 
			$('#xyfygl_opt_fyfw_min').change(function(){
				validateAmoutInput(this);
			});
			$('#xyfygl_opt_fyfw_max').change(function(){
				validateAmoutInput(this);
			});
			
		},
		option_edit : function(obj){
			
			$('#xyfyglDlg').html(xyfygl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xyfyglDlg" ).dialog({
				title:"修改",
				width:"1000",
				height:"420",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							if(!flag){
								$("#xyfygl_opt_fydm").attr("title","该费用代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xyfygl_optForm").serializeArray();
							var xydm = $("#xyfygl_opt_xydm").attr('optionValue');
							var jflx = $("#xyfygl_opt_jflx").attr('optionValue');
							var kflx = $("#xyfygl_opt_kflx").attr('optionValue');
							var fylx = $("#xyfygl_opt_fylx").attr('optionValue');
							var bz = $("#xyfygl_opt_bz").attr('optionValue');
							var jhzt = $("#xyfygl_opt_jhzt").attr('optionValue');
							datas[0].value = xydm;
							datas[1].value = jflx;
							datas[2].value = kflx;
							datas[6].value = jhzt;
							datas[10].value = fylx;
							datas[12].value = bz;
							datas.push({"name":"agreeFeeId","value":obj.agreeFeeId});
							datas.push({"name":"xyfygl_opt_xydm","value":datas[0].value});
							comm.requestFun("xyfyglUpdate",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xyfygl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*下拉列表*/
			initArrayData(obj);
			
			/*表单值初始化*/
			$('#xyfygl_opt_fydm').val(obj.agreeFeeCode);
			var curagreeFeeCode = obj.agreeFeeCode;
			var fromAmount = obj.fromAmount;
			var toAmount = obj.toAmount;
			$('#xyfygl_opt_fyfw_min').val(fromAmount);
			$('#xyfygl_opt_fyfw_max').val(toAmount);
			$('#xyfygl_opt_fybl').val(obj.feeRatio);
			$('#xyfygl_opt_fymc').val(obj.agreeFeeName);
			$('#xyfygl_opt_jfqx').val(obj.payPeriod);
			$('#xyfygl_opt_fyje').val(obj.feeAmount);
			
			var billingType = obj.billingType;
			switch(billingType){
					case '0':
						$('#xyfygl_opt_jflx').val("无");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					case 'TIMES':
						$('#xyfygl_opt_jflx').val("次");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					case 'DAILY':
						$('#xyfygl_opt_jflx').val("天");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					case 'WEEKLY':
						$('#xyfygl_opt_jflx').val("周");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					case 'MONTHLY':
						$('#xyfygl_opt_jflx').val("月");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					case 'ANNUAL':
						$('#xyfygl_opt_jflx').val("年");
						$('#xyfygl_opt_jflx').attr('optionValue',billingType);
						break;
					default:
						break;
			}
			var chargingType = obj.chargingType;
			switch(chargingType){
					case '0':
						$('#xyfygl_opt_kflx').val("无");
						break;
					case 'TIMES':
						$('#xyfygl_opt_kflx').val("次");
						break;
					case 'DAILY':
						$('#xyfygl_opt_kflx').val("天");
						break;
					case 'WEEKLY':
						$('#xyfygl_opt_kflx').val("周");
						break;
					case 'MONTHLY':
						$('#xyfygl_opt_kflx').val("月");
						break;
					case 'ANNUAL':
						$('#xyfygl_opt_kflx').val("年");
						break;
					default:
						break;
			}
			
			var isActive = obj.isActive;
			if(isActive == 'Y'){
				$('#xyfygl_opt_jhzt').val("激活");
			}else{
				$('#xyfygl_opt_jhzt').val("禁用");
			};
			
			var feeType = obj.feeType;
			switch(feeType){
				case 'FIXED':
					$('#xyfygl_opt_fylx').val("固定金额资费");
					break;
				case 'RATIO':
					$('#xyfygl_opt_fylx').val("资费比例");
					break;
			}
			
			var currencyCode = obj.currencyCode;
			switch(currencyCode){
					case '000':
						$('#xyfygl_opt_bz').val("互生币");
						break;
					case '001':
						$('#xyfygl_opt_bz').val("积分");
						break;
					case '156':
						$('#xyfygl_opt_bz').val("人民币");
						break;
			}
			
			$("#xyfygl_opt_fydm").change(function(){
				var fydm = $("#xyfygl_opt_fydm").val();
				var data = {"xyfygl_fydm":fydm};
				comm.validateDatas("xyfyglSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						var code = results.data[0].agreeFeeCode;
						if(curagreeFeeCode == code){
							flag = true; 
							$("#xyfygl_opt_fydm").attr("title","").tooltip().tooltip("destroy");
							return;
						}	
						flag = false;
						$("#xyfygl_opt_fydm").attr("title","该费用代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xyfygl_opt_fydm").attr("title","").tooltip().tooltip("destroy");
					}
				},"");
			});
			
		},
		
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '12'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '12'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var agreeFeeId = obj.agreeFeeId;
							var agreeCode = obj.agreeCode;
							var agreeFeeCode = obj.agreeFeeCode;
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							       {"name":"agreeFeeId","value":agreeFeeId},
							       {"name":"agreeCode","value":agreeCode},
							       {"name":"agreeFeeCode","value":agreeFeeCode},
								   {"name":"curPage","value":curPage},
							       {"name":"pageSize","value":pageSize}
							    ];
							comm.requestFun("xyfyglDelete",datas,function(obj){
								$('#queryList_xyfygl_pt_refreshPage').click();
							},"");
						}
					});
				}.bind(this) ) ;
			}
			
			return opt_btn;
		}.bind(this),
		
		detail : function(record, rowIndex, colIndex, options){
			var opt_btn = null;
			if(colIndex == '3'){
				var billingType = gridObj.getColumnValue(rowIndex, 'billingType');
				switch(billingType){
					case '0':
						opt_btn = "无";
						break;
					case 'TIMES':
						opt_btn = "次";
						break;
					case 'DAILY':
						opt_btn = "天";
						break;
					case 'WEEKLY':
						opt_btn = "周";
						break;
					case 'MONTHLY':
						opt_btn = "月";
						break;
					case 'ANNUAL':
						opt_btn = "年";
						break;
				}
			}
			if(colIndex == '4'){
				var chargingType = gridObj.getColumnValue(rowIndex, 'chargingType');
				switch(chargingType){
					case '0':
						opt_btn = "无";
						break;
					case 'TIMES':
						opt_btn = "次";
						break;
					case 'DAILY':
						opt_btn = "天";
						break;
					case 'WEEKLY':
						opt_btn = "周";
						break;
					case 'MONTHLY':
						opt_btn = "月";
						break;
					case 'ANNUAL':
						opt_btn = "年";
						break;
				}
			}
			if(colIndex == '6'){
				var fromAmount = gridObj.getColumnValue(rowIndex, 'fromAmount');
				var toAmount = gridObj.getColumnValue(rowIndex, 'toAmount');
				opt_btn = fromAmount + "~" + toAmount;
			}
			if(colIndex == '8'){
				var feeType = gridObj.getColumnValue(rowIndex, 'feeType');
				switch(feeType){
					case 'FIXED':
						opt_btn = "固定金额资费";
						break;
					case 'RATIO':
						opt_btn = "资费比例";
						break;
					}
			}
			if(colIndex == '10'){
				var currencyCode = gridObj.getColumnValue(rowIndex, 'currencyCode');
				switch(currencyCode){
					case '000':
						opt_btn = "互生币";
						break;
					case '001':
						opt_btn = "积分";
						break;
					case '156':
						opt_btn = "人民币";
						break;
					}
			}
			if(colIndex == '11'){
				var val = gridObj.getColumnValue(rowIndex, 'isActive');
				switch(val){
					case 'Y':
						opt_btn = '激活';
						break;
					case 'N':
						opt_btn = '禁用';
						break;
				
				}
			}
			return opt_btn;
		}.bind(this),
		
		addValidate : function(){
			
			var datas = $("#xyfygl_optForm").serializeArray();
			var fromAmount = datas.xyfygl_opt_fyfw_min;
			var toAmount = datas.xyfygl_opt_fyfw_max;
			
			return comm.valid({
				formID : '#xyfygl_optForm',
				left : 270,
				top : -1,
				rules : {
					xyfygl_opt_xymc : {
						required : true	
					},
					xyfygl_opt_fydm : {
						required : true	
					},
					xyfygl_opt_fymc : {
						required : true	
					},
					xyfygl_opt_kflx : {
						required : true	
					},
					xyfygl_opt_jfqx : {
						max : 30	
					},
					xyfygl_opt_jflx : {
						required : true	
					},
					xyfygl_opt_bz : {
						required : true	
					},
					xyfygl_opt_jhzt : {
						required : true	
					},
					xyfygl_opt_fyfw_min : {
						digits : true,
						min : toAmount
					},
					xyfygl_opt_fyfw_max : {
						digits : true,
						max : fromAmount
					}
				},
				messages : {
					xyfygl_opt_xymc : {
						required : '必填'
					},
					xyfygl_opt_fydm : {
						required : '必填'	
					},
					xyfygl_opt_fymc : {
						required : '必填'	
					},
					xyfygl_opt_kflx : {
						required : '必填'	
					},
					xyfygl_opt_jfqx : {
						max : "输入的值不能大于30"
					},
					xyfygl_opt_jflx : {
						required : '必填'	
					},
					xyfygl_opt_bz : {
						required : '必填'	
					},
					xyfygl_opt_jhzt : {
						required : '必填'	
					},
					xyfygl_opt_fyfw_min : {
						digits : '必须输入整数',
						min : toAmount
					},
					xyfygl_opt_fyfw_max : {
						digits : '必须输入整数',
						max : fromAmount
					}
				}
			});
		}
	}
	
	/*下拉列表*/
	function initArrayData(obj){
		
		$("#xyfygl_opt_jflx").selectList({
			width:250,
			optionWidth:255,
			options:[
				{name:'按年',value:'ANNUAL'},
				{name:'按月',value:'MONTHLY'},
				{name:'按周',value:'WEEKLY'},
				{name:'按日',value:'DAILY'},
				{name:'按次',value:'TIMES'}
			]
		}).change(function(e){
		});
		
		$("#xyfygl_opt_kflx").selectList({
			width:250,
			optionWidth:255,
			options:[
				{name:'按年',value:'ANNUAL'},
				{name:'按月',value:'MONTHLY'},
				{name:'按周',value:'WEEKLY'},
				{name:'按日',value:'DAILY'},
				{name:'按次',value:'TIMES'}
			]
		}).change(function(e){
		});
		
		$("#xyfygl_opt_fylx").selectList({
			width:250,
			optionWidth:255,
			options:[
				{name:'固定金额资费',value:'FIXED'},
				{name:'资费比例',value:'RATIO'}
			]
		}).change(function(e){
		});
		
		$("#xyfygl_opt_jhzt").selectList({
			width:250,
			optionWidth:255,
			options:[
				{name:'激活',value:'Y'},
				{name:'禁用',value:'N'}
			]
		}).change(function(e){
		});
		
		$("#xyfygl_opt_bz").selectList({
			width:250,
			optionWidth:255,
			options:[
				{name:'互生币',value:'000'},
				{name:'积分',value:'001'},
				{name:'人民币',value:'156'}
			]
		}).change(function(e){
		});
		
		var options = [];
		var condition = {"noPageSearchFlag":"true"};
		comm.validateDatas("xyglSearch",condition,function(results){
			var datas = results.data;
			$.each(datas,function(n,value){
				options.push({"name":value.agreeName,"value":value.agreeCode});
			});
			$("#xyfygl_opt_xydm").selectList({
				width:250,
				optionWidth:255,
				optionHeight:150,
				overflowY:'auto',
				//selected:true ,
				options: options
			}).change(function(e){
			});
			if(obj != null){
				$('#xyfygl_opt_xydm').selectListValue(obj.agreeCode);
			}
			},"");
		
		/*end*/
	}
	
	function validateAmoutInput(obj){
		var amountMin = $("#xyfygl_opt_fyfw_min").val();
		var amountMax = $("#xyfygl_opt_fyfw_max").val();
		
		if(amountMin != null && amountMin != ''){
			if(!(/^[0-9]*[1-9][0-9]*$/.test(amountMin))){  
				alert("请输入正整数");
				$(obj).val('');
			}
		}
		if(amountMax != null && amountMax != ''){
			if(!(/^[0-9]*[1-9][0-9]*$/.test(amountMax))){  
				alert("请输入正整数");
				$(obj).val('');
			}
		}
		
		validateAmout(amountMin,amountMax,obj);
	}
	
	function validateAmout(amountMin,amountMax,obj){
		
		if(amountMin == null || amountMin == ''){
			return;
		}
		if(amountMax == null || amountMax == ''){
			return;
		}
		var amountMin_Int = parseInt(amountMin);
		var amountMax_Int = parseInt(amountMax);
		
		if(amountMin_Int > amountMax_Int){
			alert("费用最小金额不能大于费用最大金额，请重输");
			$(obj).val('');
		}
	}
}); 

 