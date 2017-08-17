define(['text!companyInfoTpl/zyxxbg/zyxxbgcx.html',
	'text!companyInfoTpl/zyxxbg/ck.html' ,
	'companyInfoDat/companyInfo'
	],function(zyxxbgcxTpl,ckTpl,companyInfo ){
	return {
		 
		showPage : function(){
			$('#contentWidth_3').html(zyxxbgcxTpl);
			var self = this,
			searchTable = null;
			
			/*下拉列表*/
			$("#sel_kjrq").selectList({
				options:[
					{name:comm.lang("companyInfo").universal.today,value:'today'},
					{name:comm.lang("companyInfo").universal.current_week,value:'week'},
					{name:comm.lang("companyInfo").universal.current_moth,value:'month'}
				]
			}).change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				
				var arr = method ? comm[method]() : ['', ''];
				$("#scpoint_beginDate").val(arr[0]);
				$("#scpoint_endDate").val(arr[1]);
				
			});

			comm.initSelect('#sel_spzt', comm.lang("companyInfo")["applyStatus"]);

			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
					
					//查询单击事件
					var curr=$("#scpoint_result_table_pt_gotoPageInput").val();
					if(curr==undefined || curr==''){
						curr=1;
					}
					var sType=$('#sel_spzt option:selected').attr('optionValue');
					var loginInfo = companyInfo.getSystemInfo();
					var jsonData={
							'search_entCustId' : loginInfo.entCustId,
							'search_startDate' : $("#scpoint_beginDate").val(),
							'search_endDate' : $("#scpoint_endDate").val(),
							'search_entType' :loginInfo.entCustType,
							'search_status' : sType,
							'search_resNo' : "07186630000",
							'pageSize' : 10,
							'curPage' : curr
					};
					
					//查找变更记录
					companyInfo.findChangeRecord(jsonData,function(resData){
						if(resData.data==""){
							return;
						}
						searchTable = $.fn.bsgrid.init('scpoint_result_table', {
							localData : resData.data,
							otherParames: {
								beginDate : $("#scpoint_beginDate").val(),
								endDate : $("#scpoint_endDate").val(),
								status : $("#scpoint_status").attr('optionValue')
							},
							//不显示空白行
							displayBlankRows: false,
							//不显示无页可翻的提示
							pageIncorrectTurnAlert: false,
							//隔行变色
							stripeRows: true,
							//不显示选中行背景色
							rowSelectedColor: false,
							pageSize: 10,
							operate: {
								add: function(record, rowIndex, colIndex, options){
									if(colIndex == '2'){
										return comm.lang("companyInfo")["applyStatus"][searchTable.getColumnValue(rowIndex, 'status')];
									}
									else if(colIndex == '4'){
										return $('<a applyId="' + searchTable.getColumnValue(rowIndex, 'applyId') + '" title="'+comm.lang("companyInfo").universal.view_detail+'">'+comm.lang("companyInfo").universal.view+'</a>').click(function(e){
											//查看详情点击事件
											self.showDetail($(e.currentTarget));
										});
									}
								}
							}
						});
						
					});
				
			});
			//这一行需要去掉，此处用于显示数据
			$('#scpoint_searchBtn').click();
		},
		
		showDetail : function(target){
			var serialNo = target.attr('applyId');
				self = this;
				//显示头部菜单
				comm.liActive_add($('#zyxxbg_ck'));
				//$('#contentWidth_3').addClass('none');
				companyInfo.findEntInfoChangedDetail({'applyId':serialNo},function(response){
					var resData=response.data;
					 $('#zyxxbg_ck').css('display','').animate({scrollTop:"0px"});
					 $('#contentWidth_3').html(_.template(ckTpl,resData));
					 
			         $("#busLicenceFileId").attr("text",companyInfo.getFsServerUrl(resData.bizLicenseCrePicNew!=null?(resData.bizLicenseCrePicNew):(resData.bizLicenseCrePicOld)));
			         $("#organizeFileId").attr("text",companyInfo.getFsServerUrl(resData.organizerCrePicNew!=null?(resData.organizerCrePicNew):(resData.organizerCrePicOld)));
			         $("#taxpayerFileId").attr("text",companyInfo.getFsServerUrl(resData.taxpayerCrePicNew!=null?(resData.taxpayerCrePicNew):(resData.taxpayerCrePicOld)));
			         $("#legalfrontFileId").attr("text",companyInfo.getFsServerUrl(resData.legalRepCreFacePicNew!=null?(resData.legalRepCreFacePicNew):(resData.legalRepCreFacePicOld)));
			         $("#legalBackFileId").attr("text",companyInfo.getFsServerUrl(resData.legalRepCreBackPicNew!=null?(resData.legalRepCreBackPicNew):(resData.legalRepCreBackPicOld)));
			         
			         self.viewPic();
			         
			         $('#approveResult').html( comm.lang("companyInfo")["applyStatus"][resData.status]);
			         $('#lrcType_new').html( comm.lang("companyInfo")["credenceTypes"][resData.legalRepCreTypeNew]);
			         $('#lrcType_old').html( comm.lang("companyInfo")["credenceTypes"][resData.legalRepCreTypeOld]);
			         
			       //返回按钮
					$('#btn_ck_fh').click(function(){
						//隐藏头部菜单
						$("#zyxxbg_jlcx").trigger('click');
						
					});
			         
				});
		},
		//点击查附件
		viewPic : function (){
			var ids = ['#busLicenceFileId','#organizeFileId','#taxpayerFileId','#legalfrontFileId','#legalBackFileId'] ; 
			var obj;
			$.each(ids,function(i,v){
				obj=$(v);
				comm.bindPicViewer(obj,obj.attr("text"));
			});
		},
	}
}); 

 