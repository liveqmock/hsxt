define(['text!scoremanageTpl/lphs/lphs.html',
		'text!scoremanageTpl/lphs/lphsxz.html',
		'text!scoremanageTpl/lphs/lphsck.html',
		'text!scoremanageTpl/lphs/lphsxzlist.html',
		'scoremanageDat/pointWelfare',
		'scoremanageLan'
	], function(lphsTpl,lphsxzTpl,lphsckTpl,lphsxzlistTpl,pointWelfare){
	var medical_detail_list = null;
	
	var self = {
			showPage : function(){
				$('#busibox').html(_.template(lphsTpl));	
				var self = this;
				
				/** 查询事件 */
				$("#qry_lphs").click(function(){
					self.pageQuery();
				});
			},
	
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam={
							"proposerResNo":$("#proposerResNo").val(),	//申请人互生号
							"applyWelfareNo":$("#applyWelfareNo").val()		//申请人证件号码
						};
			var gridObj= pointWelfare.listPendingClaimsAccounting("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex == 4){
					return comm.formatMoneyNumberAps(record['hsPayAmount']);
				}
				if(colIndex == 7){
					return comm.getNameByEnumId(record['status'], {0:'待核算',1:'核算中',2:'已核算'});
				}
				if(record['status'] == '2'){
					var link1 = $('<a>查看</a>').click(function(e) {
						self.chakan(record);
					}) ;
					return   link1 ;	
				}else{
					var link1 = $('<a>核算金额</a>').click(function(e) {
						self.count(record);
					}) ;
					return   link1 ;
				}
			});
		},
		chakan : function(record){
			$('#lphs_detail').html(_.template(lphsckTpl,{record:record}));
			var param={'accountingId':record.accountingId};
			var gridObj =pointWelfare.queryClaimsAccountingDetail(param,function(rsp){
				 medical_detail_list =rsp.data;
				 self.refreshData('searchTable2');
				 self.accountingAmount();
				 //lyh
				 $('#lphs_detail').removeClass('none');
				 $('#lphsckTpl').removeClass('none');
				 $('#lphsTpl').addClass('none');
				 $('#busibox').addClass('none');
				 comm.liActive_add($('#ck'));
				 //返回按钮
				 $('#btn_back_lphsck').click(function(){
					 //隐藏头部菜单
					 $('#lphs_detail').addClass('none');
					 $('#lphsckTpl').addClass('none');
					 $('#lphsTpl').removeClass('none');
					 $('#busibox').removeClass('none');
					 $('#ck').addClass("tabNone").find('a').removeClass('active');
					 $('#lphs').find('a').addClass('active');
				 });
			});
			
			
		},
		count : function(record){
			$('#lphs_detail').html(_.template(lphsxzTpl,{record:record})).append(lphsxzlistTpl);
			self.add(record);	
		
	
			
			
			$("#save_lphs_btn").click(function(){
				self.save_lphs();
			});
			
			$("#confirm_lphs_btn").click(function(){
				self.confirm_lphs();
			});
			
			comm.initCitySelect('#cityNo', {}, 90, null,{name:"",value:""});
			
			//初始化
			self.initData(record.provinceNo,record.cityNo);
			
			//日历控件
			$("#timeRange_start").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_end").datepicker({dateFormat:'yy-mm-dd'});
			if(comm.isNotEmpty(record.billsStartDate)){
				$("#timeRange_start").val(record.billsStartDate);
			}
			if(comm.isNotEmpty(record.billsEndDate)){
				$("#timeRange_end").val(record.billsEndDate);
			}
			
			 //lyh
			 $('#lphs_detail').removeClass('none');
			 $('#lphsxzTpl').removeClass('none');
			 $('#lphsTpl').addClass('none');
			 $('#busibox').addClass('none');
			 comm.liActive_add($('#xz'));
			 //返回按钮
			 $('#cancle_lphs_btn').click(function(){
				 //隐藏头部菜单
				 self.pageQuery();
				 $('#lphs_detail').addClass('none');
				 $('#lphsxzTpl').addClass('none');
				 $('#lphsTpl').removeClass('none');
				 $('#busibox').removeClass('none');
				 $('#xz').addClass("tabNone").find('a').removeClass('active');
				 $('#lphs').find('a').addClass('active');
				 $('.ui-tooltip.ui-widget.ui-corner-all.ui-widget-content').remove();
			 });
			
		},
		confirm_lphs : function(){
			if(!self.addValid()){
				return false;
			}
			if(medical_detail_list==null||medical_detail_list.length<1){
				comm.warn_alert(comm.lang("pointWelfare").addItemIsNull);
				return false;
			}
			
			//验证开始时间必须小于结束时间、
			var startTime=$('#timeRange_start').val();
			var endTime=$('#timeRange_end').val();
			if(startTime>endTime){
				comm.warn_alert(comm.lang("pointWelfare").timeRange);
				return false;
			}
			
			$.each(medical_detail_list, function(i,v) {
				v.billsStartDate = $('#timeRange_start').val();
				v.billsEndDate = $('#timeRange_end').val();
				v.provinceNo = $('#provinceNo').attr('optionValue');
				v.cityNo = $('#cityNo').attr('optionValue');
			});
			
			comm.i_confirm('提交确认后不能再修改，你确定已经核算完毕？',function(){
				var param = {"medicalDetailJsonStr":encodeURIComponent(JSON.stringify(medical_detail_list))}; 
				var gridObj = pointWelfare.confirmClaimsAccounting(param,function(rsp){
					$("#i_content").html('确认成功');
					$("#alert_i").dialog({
						title : "提示信息",
						width :  "400",
						/*此处根据文字内容多少进行调整！*/
						modal : true,
						buttons : {
							"关闭" : function () {
								$(this).dialog("destroy");
								$('#cancle_lphs_btn').click();	
							}
						}
					});
				});
			});
		},
		save_lphs : function(){
			if(!self.addValid()){
				return false;
			}
			if(medical_detail_list==null||medical_detail_list.length<1){
				comm.warn_alert(comm.lang("pointWelfare").addItemIsNull);
				return false;
			}
			
			//验证开始时间必须小于结束时间、
			var startTime=$('#timeRange_start').val();
			var endTime=$('#timeRange_end').val();
			if(startTime>endTime){
				comm.warn_alert(comm.lang("pointWelfare").timeRange);
				return false;
			}
			
			
			$.each(medical_detail_list, function(i,v) {
				v.billsStartDate = $('#timeRange_start').val();
				v.billsEndDate = $('#timeRange_end').val();
				v.provinceNo = $('#provinceNo').attr('optionValue');
				v.cityNo = $('#cityNo').attr('optionValue');
			});
			 
			 
			var param = {"medicalDetailJsonStr":encodeURIComponent(JSON.stringify(medical_detail_list))}; 
			var gridObj = pointWelfare.countMedicalDetail(param,function(rsp){
				comm.i_alert('保存成功');
			});
		},
		
		/**
		 * 初始化数据
		 */
		initData : function(prov,city){
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					var provSelect=comm.initProvSelect('#provinceNo', provArray, 80, prov,{name:"",value:""});
					
					provSelect.change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 90, null,{name:"",value:""}).selectListIndex(0);
						});
					});
					
					if(city){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function(cityArray){
							comm.initCitySelect('#cityNo', cityArray, 90, city,{name:"",value:""});
						});
					}
					
				});
			});
		},
		
		refreshData :function(tableId){
			 $.fn.bsgrid.init(tableId, {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					autoLoad : medical_detail_list.length>0,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:medical_detail_list ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							if(colIndex == 5){
								return comm.getNameByEnumId(record['unit'], comm.lang("pointWelfare").unit);
							}
							if(colIndex ==6){
								 return "<span title='"+comm.formatMoneyNumberAps(record['price'])+"'>"+comm.formatMoneyNumberAps(record['price'])+"</span>";
							}
							if(colIndex ==7){
								return "<span title='"+comm.formatMoneyNumberAps(record['amount'])+"'>"+comm.formatMoneyNumberAps(record['amount'])+"</span>";
							}
							if(colIndex == 8){
								return record['proportion']+"%";
							}
							if(colIndex ==9){
								return "<span title='"+comm.formatMoneyNumberAps(record['healthPayAmount'])+"'>"+comm.formatMoneyNumberAps(record['healthPayAmount'])+"</span>";
							}
							if(colIndex ==10){
								return "<span title='"+comm.formatMoneyNumberAps(record['personalPayAmount'])+"'>"+comm.formatMoneyNumberAps(record['personalPayAmount'])+"</span>";
							}
							if(colIndex ==11){
								return "<span title='"+comm.formatMoneyNumberAps(record['hsPayAmount'])+"'>"+comm.formatMoneyNumberAps(record['hsPayAmount'])+"</span>";
							}
							var link1 = $('<a>删除</a>').click(function(e) {
								comm.confirm({
									imgFlag : true,
									imgClass : 'tips_ques',
									content : comm.lang("pointWelfare").confirmDel,
									callOk :function(){
										
										if(rowIndex==0){
											medical_detail_list.shift();
										}else{
											medical_detail_list.splice(rowIndex,1); 
										}
										self.refreshData('searchTable1');
										self.accountingAmount();
									}
								});
							}) ;
							return  link1;
						}
					}
			 });
		},
		accountingAmount :function(){
			 var totalAmount =0;
			 var healthPayAmount =0;
			 var personalPayAmount =0;
			 var hsPayAmount =0;
			 $.each(medical_detail_list, function(i,v) {
				 totalAmount+=Number(v.amount);
				 healthPayAmount+=Number(v.healthPayAmount);
				 hsPayAmount+=Number(v.hsPayAmount);
				 personalPayAmount+=Number(v.personalPayAmount);
			 });
			 
			 $("#counting_totalAmount").text(comm.formatMoneyNumberAps(totalAmount));
			 $("#counting_healthPayAmount").text(comm.formatMoneyNumberAps(healthPayAmount));
			 $("#counting_personalPayAmount").text(comm.formatMoneyNumberAps(personalPayAmount));
			 $("#counting_hsPayAmount").text(comm.formatMoneyNumberAps(hsPayAmount));
		},
		accountingHspayAmount :function(record){
		  var amount = Number(record['amount']);
		  var healthPayAmount = Number(record['healthPayAmount']);
		  var healthPayRate = healthPayAmount/amount ;
		  var hsPayRate = (1-healthPayRate)>0.4?0.4:(1-healthPayRate);
		  hsPayRate=parseFloat(hsPayRate).toFixed(2);
		  var hsPayAmount = amount*hsPayRate;
		  record['hsPayAmount']= parseFloat(hsPayAmount).toFixed(2);
		},
		reaccounting:function(){
			var proportion = parseFloat($.trim($('#proportion').val()));
			var amount = parseFloat($.trim($('#amount').val()));
			if(isNaN(amount)||isNaN(proportion)){
				$('#healthPayAmount').val('');
				$('#personalPayAmount').val('');
				return false;
			}
			var healthPayAmount = (amount*proportion/100).toFixed(2);
			var personalPayAmount = (amount - healthPayAmount).toFixed(2);
			$('#healthPayAmount').val(healthPayAmount);
			$('#personalPayAmount').val(personalPayAmount);
		},
		add : function(record){
			//var self=this;
			$('#lphsTpl').addClass('none');
			$('#lphsxzTpl').removeClass('none');
			comm.liActive_add($('#xz'));
			
			var param={'accountingId':record.accountingId};
			var gridObj =pointWelfare.queryClaimsAccountingDetail(param,function(rsp){
				 medical_detail_list =rsp.data;
				 self.refreshData('searchTable1');
				 self.accountingAmount();
			});
			
			$('#add_list_btn').click(function(){
				//lyh消除提示
				 $('.ui-tooltip.ui-widget.ui-corner-all.ui-widget-content').css("display","none");
				//$('#busibox').append(lphsxzlistTpl);
				$("#lphs_form_rest_btn").click();
				
				comm.initSelect("#leibie",comm.lang("pointWelfare").category);
				$("#leibie").change(function(){
					$(this).attr("optionvalue");
				});
				
				comm.initSelect("#unit",comm.lang("pointWelfare").unit);
				$("#unit").change(function(){
					$(this).attr("optionvalue");
				});
				
				$("#quantity").change(function(){
					var price = parseFloat($.trim($('#price').val()));
					var quantity = parseFloat($.trim($('#quantity').val()));
					if(isNaN(price)||isNaN(quantity)){
						$('#amount').val('');
						return false;
					}
					var amount = price*quantity;
					$('#amount').val(amount.toFixed(2));
					self.reaccounting();
				});
				$("#price").change(function(){
					var price = parseFloat($.trim($('#price').val()));
					var quantity = parseFloat($.trim($('#quantity').val()));
					if(isNaN(price)||isNaN(quantity)){
						$('#amount').val('');
						return false;
					}
					var amount = price*quantity;
					$('#amount').val(amount.toFixed(2));
					self.reaccounting();
				});
				
				$("#proportion").change(function(){
					self.reaccounting();
				});
				
				/*end*/
				$("#lphsxzlist").dialog({
					title:"增加项目",
					width:"700",
					modal:true,
					buttons:{ 
						"再加一条":function(){
							
							//
							if(!self.valid()){
								return false;
							}
							var datas = $("#accountingForm").serializeArray();
							var d = {};
							
						    $.each(datas, function() {
						       d[this.name] = this.value;
						       if(this.name=='unit'){
						    	   d[this.name] = $("#unit").attr('optionValue');
						       }
						       if(this.name=='price'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#price").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='amount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#amount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='healthPayAmount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#healthPayAmount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='personalPayAmount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#personalPayAmount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='proportion'){
						    	   if(comm.isNotEmpty(this.value)){
							    	   d[this.name] = parseFloat(this.value).toFixed(2);
							    	   $("#proportion").val(parseFloat(this.value).toFixed(2));
						    	   }
						       }
						    });
						   
							d['applyWelfareNo'] =record['applyWelfareNo'];
							d['accountingId'] =record['accountingId'];
							
							self.accountingHspayAmount(d);
						    medical_detail_list.push(d);
							self.refreshData('searchTable1');
							self.accountingAmount();
							//
							
							$("#lphs_form_rest_btn").click();
						},
						"确定":function(){
							
							if(!self.valid()){
								return false;
							}
							
							//判断单价金额不能为0，并且金额值可以输入2为小数
							var prices=$("#price").val();
							if(prices<=0){
								comm.warn_alert(comm.lang("pointWelfare").minprice);
								return false;
							}else{
								if(prices.indexOf(".")!=-1){
									var tempPrices=prices.split(".");
									if(tempPrices[1].length>2){
										comm.warn_alert(comm.lang("pointWelfare").wsprice);
										return false;
									}
								}
							
							}
							
							
							var datas = $("#accountingForm").serializeArray();
							var d = {};
							
						    $.each(datas, function() {
						       d[this.name] = this.value;
						       if(this.name=='unit'){
						    	   d[this.name] = $("#unit").attr('optionValue');
						       }
						       if(this.name=='price'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#price").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='amount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#amount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='healthPayAmount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#healthPayAmount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='personalPayAmount'){
						    	   d[this.name] = parseFloat(this.value).toFixed(2);
						    	   $("#personalPayAmount").val(parseFloat(this.value).toFixed(2));
						       }
						       if(this.name=='proportion'){
						    	   if(comm.isNotEmpty(this.value)){
							    	   d[this.name] = parseFloat(this.value).toFixed(2);
							    	   $("#proportion").val(parseFloat(this.value).toFixed(2));
						    	   }
						       }
						    });
						   
							d['applyWelfareNo'] =record['applyWelfareNo'];
							d['accountingId'] =record['accountingId'];
							
							self.accountingHspayAmount(d);
						    medical_detail_list.push(d);
							self.refreshData('searchTable1');
							self.accountingAmount();
							//关闭弹出窗口
							$('.ui-tooltip.ui-widget.ui-corner-all.ui-widget-content').css("display","block");
							$(this).dialog( "destroy" );
						},
						"取消":function(){
							 $('.ui-tooltip.ui-widget.ui-corner-all.ui-widget-content').remove();
							$(this).dialog( "destroy" );
						}
					}
				});		
			});	
		},
		valid : function (){
			return comm.valid({
				left : 10,
				top : 20,
				formID : '#accountingForm',
				rules : {
					category : {
						required : true
					},
					quantity : {
						required : true,
						digits : true,
						min : 1
					},
					price : {
						required : true,
						number : true
					},
					proportion :{
						required : true,
						range:[0,100]
					},
					itemName : {
						required : true
					},
					explain:{
						maxlength:300
					}
					
				},
				
				messages : {
					category : {
						required : comm.lang("pointWelfare").addItemError.categoryNotnull			
					},
					quantity : {
						required : comm.lang("pointWelfare").addItemError.quantityNotnull,	
						digits : comm.lang("pointWelfare").digits,
						min:comm.lang("pointWelfare").minquantity
					},
					price : {
						required : comm.lang("pointWelfare").addItemError.priceNotnull,	
						number :  comm.lang("pointWelfare").addItemError.priceInvalid
					},
					proportion :{
						required : comm.lang("pointWelfare").addItemError.proportionNotnull,	
						range:comm.lang("pointWelfare").addItemError.proportionInvalid	
					},
					itemName : {
						required :comm.lang("pointWelfare").addItemError.itemNameNotnull	
					},
					explain:{
						maxlength:comm.lang("pointWelfare").smMaxlength
					}
				}
			});
		},
		
		addValid : function (){
			return comm.valid({
				left : 10,
				top : 20,
				formID : '#add_form',
				rules : {
					timeRange_start : {
						required : true
					},
					timeRange_end : {
						required : true
					},
					provinceNo :{
						required:true
					},
					cityNo : {
						required : true
					}
					
				},
				messages : {
					timeRange_start : {
						required :comm.lang("pointWelfare").addItemError.timeRange_start_Notnull	
					},
					timeRange_end : {
						required : comm.lang("pointWelfare").addItemError.timeRange_end_Notnull	
					},
					provinceNo :{
						required:comm.lang("pointWelfare").addItemError.provinceNo_Notnull	
					},
					cityNo : {
						required :comm.lang("pointWelfare").addItemError.cityNo_Notnull	
					}
				}
			});
		}
		
	};
	return self;
});