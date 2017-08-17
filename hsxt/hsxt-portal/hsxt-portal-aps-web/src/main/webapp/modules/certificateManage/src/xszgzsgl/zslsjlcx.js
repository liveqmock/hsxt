define(['text!certificateManageTpl/xszgzsgl/zslsjlcx.html',
		'text!certificateManageTpl/xszgzsgl/zslsjlcx_ck.html',
		'certificateManageSrc/zsffrz/zsffrz',
		'certificateManageLan'
		], function(zslsjlcxTpl, zslsjlcx_ckTpl, zsffrz){
	var self = {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(zslsjlcxTpl));
			
			/*下拉列表*/
			comm.initSelect('#printStatus', comm.lang("certificateManage").printStatusEnum, null, null, {name:'全部', value:''});
			comm.initSelect('#sendStatus', comm.lang("certificateManage").sendStatusEnum, null, null, {name:'全部', value:''});
			comm.initSelect('#sealStatus', comm.lang("certificateManage").sealStatusSelector, null, null, {name:'全部', value:''});
			/*end*/	
			
			/*表格数据模拟*/
			//var self = this;
			$("#qry_sell_certificate_his_btn").click(function(){
				var params = {
						search_entResNo			: $("#entResNo").val().trim(),			//企业互生号
						search_certificateNo	: $("#certificateNo").val().trim(),		//证书唯一识别码
						search_entCustName		: $("#entCustName").val().trim()		//企业名称
				};
				var sealStatus = $("#sealStatus").attr("optionvalue");
				if(sealStatus){
					params.search_sealStatus = sealStatus;
				}
				var printStatus = $("#printStatus").attr("optionvalue");
				if(printStatus){
					params.search_printStatus = printStatus;
				}
				var sendStatus = $("#sendStatus").attr("optionvalue");
				if(sendStatus){
					params.search_sendStatus = sendStatus;
				}
				
				self.searchTable = comm.getCommBsGrid("searchTable","find_sell_certificate_recode_by_page",params,comm.lang("certificateManage"),self.detail);
			});
			/*end*/	
		},
		detail : function(record, rowIndex, colIndex, options){
			if (colIndex == 1) {
                return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
            }
			if(colIndex == 5){
				return comm.lang("certificateManage").sealStatusEnum[record.sealStatus];
			}
			if(colIndex == 6){
				return comm.lang("certificateManage").printStatusEnum[record.isPrint];
			}
			if(colIndex == 8){
				return comm.lang("certificateManage").sendStatusEnum[record.isSend];
			}
			if(colIndex == 9){
				var obj = self.searchTable.getRecord(rowIndex); 
				var link1 = $('<a>查看</a>').click(function(e) {
					zsffrz.showPage(obj,"zslsjlcx");
				});
				return link1;
			}
			
		},
		chaKan : function(obj){
			comm.liActive_add($('#ck'));
			$('#busibox').html(_.template(zslsjlcx_ckTpl, obj));
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"2015-08-31 16:40:00",
							"th_4":"0000（张三）",
							"th_5":"xxx",
							"th_6":"2015-08-31 16:40:00",
							"th_7":"0000（李四）"
						},
						{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"2015-08-31 16:40:00",
							"th_4":"0000（张三）",
							"th_5":"xxx",
							"th_6":"2015-08-31 16:40:00",
							"th_7":"0000（李四）"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var obj = gridObj.getRecord(rowIndex); 
						var link1 = $('<a>查看</a>').click(function(e) {
							if(colIndex == 8){
								this.chaKan_1(obj);
							}
							else if(colIndex == 9){
								this.chaKan_2(obj);	
							}
							
						} ) ;
						
						return link1;
					}
				}
				
			});
			
			/*end*/	
			
			$('#back_zslsjlcx').triggerWith('#zslsjlcx');
			
		},
		chaKan_1 : function(){},
		chaKan_2 : function(){}/*,
		ffzs : function(obj){
			comm.liActive_add($('#ffzs'));	
			$('#busibox').html(_.template(zslsjlcx_ffzsTpl, obj));
			$('#ffzs_cancel').triggerWith('#zslsjlcx');	
		}*/
	};
	return self;
});