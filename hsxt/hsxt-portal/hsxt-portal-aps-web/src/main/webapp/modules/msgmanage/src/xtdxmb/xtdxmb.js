define(['text!msgmanageTpl/xtdxmb/xtdxmb.html',
		'text!msgmanageTpl/xtdxmb/xtdxmb_xz.html',
		'text!msgmanageTpl/xtdxmb/xtdxmb_xg.html',
		'text!msgmanageTpl/xtdxmb/xtdxmb_ck_dialog.html',
		'text!msgmanageTpl/xtdxmb/xtdxmb_qty.html'
		],function(xtdxmbTpl, xtdxmb_xzTpl, xtdxmb_xgTpl, xtdxmb_ck_dialogTpl, xtdxmb_qtyTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(xtdxmbTpl)).append(xtdxmb_xzTpl);	
			
			var self = this;
			
			/*下拉列表*/
			$("#templateState").selectList({
				options:[
					{name:'已启用',value:'1'},
					{name:'已停用',value:'2'},
					{name:'待启用',value:'3'},
					{name:'复核驳回',value:'4'},
					{name:'待复核',value:'5'}
				]
			});
			
			$("#templateUse").selectList({
				width:230,
				optionWidth:230,
				options:[
					{name:'线下核款授权短信',value:'1'},
					{name:'申报企业授权码短信',value:'2'},
					{name:'申报服务公司付款通知短信',value:'3'},
					{name:'申报企业成功短信',value:'4'},
					{name:'密码重置短信',value:'5'},
					{name:'积分预付款账户告急短信',value:'6'},
					{name:'积分预付款账户不足短信',value:'7'},
					{name:'积分预付款账户偏少短信发送模板',value:'8'},
					{name:'意外伤害保障生效',value:'9'},
					{name:'意外伤害保障失效',value:'10'},
					{name:'扣除年费成功短信模版',value:'11'},
					{name:'扣除年费失败短信模版',value:'12'},
					{name:'手机短信验证码发送模版',value:'13'},
					{name:'免费医疗计划生效短信',value:'14'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"授权码模板信息",
							"th_2":"线下核款授权短信",
							"th_3":"xxx",
							"th_4":"已启用",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"授权码模板信息",
							"th_2":"申报企业授权码短信",
							"th_3":"xxx",
							"th_4":"已停用",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"授权码模板信息",
							"th_2":"申报服务公司付款通知短信",
							"th_3":"xxx",
							"th_4":"待启用",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"授权码模板信息",
							"th_2":"申报服务公司付款通知短信",
							"th_3":"xxx",
							"th_4":"复核驳回",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"授权码模板信息",
							"th_2":"申报服务公司付款通知短信",
							"th_3":"xxx",
							"th_4":"待复核",
							"th_5":"2015-08-16 19:25:00"
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
						var link1 = $('<a>查看</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.chaKan(obj);
						}.bind(this) ) ;
						
						return link1;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 = null;
						var obj = gridObj.getRecord(rowIndex);
						if(gridObj.getColumnValue(rowIndex, 'th_4') == '已启用'){
							link2 = $('<a>停用</a>').click(function(e) {
								this.yong(obj);
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_4') == '已停用'){
							link2 = $('<a>启用</a>').click(function(e) {
								this.yong(obj);
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_4') == '待启用'){
							link2 = $('<a>修改</a>').click(function(e) {
								this.xiuGai(obj);
							}.bind(this) ) ;
						}
						return link2;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			$('#xzmb_btn').click(function(){
				self.showTpl($('#xtdxmb_xzTpl'));
				comm.liActive_add($('#xzmb'));
				
				/*下拉列表*/
				$("#templateUse_xz").selectList({
					width:225,
					optionWidth:225,
					options:[
						{name:'线下核款授权短信',value:'1'},
						{name:'申报企业授权码短信',value:'2'},
						{name:'申报服务公司付款通知短信',value:'3'},
						{name:'申报企业成功短信',value:'4'},
						{name:'密码重置短信',value:'5'},
						{name:'积分预付款账户告急短信',value:'6'},
						{name:'积分预付款账户不足短信',value:'7'},
						{name:'积分预付款账户偏少短信发送模板',value:'8'},
						{name:'意外伤害保障生效',value:'9'},
						{name:'意外伤害保障失效',value:'10'},
						{name:'扣除年费成功短信模版',value:'11'},
						{name:'扣除年费失败短信模版',value:'12'},
						{name:'手机短信验证码发送模版',value:'13'},
						{name:'免费医疗计划生效短信',value:'14'}
					]
				});
				/*end*/	
				
				/*富文本框*/
				$('#shortMessage_content_xzmb').xheditor({
					upLinkUrl:"upload.php",
					upLinkExt:"zip,rar,txt",
					upImgUrl:"upload.php",
					upImgExt:"jpg,jpeg,gif,png",
					upFlashUrl:"upload.php",
					upFlashExt:"swf",
					upMediaUrl:"upload.php",
					upMediaExt:"wmv,avi,wma,mp3,mid",
                    width:678,
                    height:150
				}); 
				/*end*/
				
				$('#xz_cancel').click(function(){
					self.showTpl($('#xtdxmbTpl'));
					comm.liActive($('#xtdxmb'), '#xzmb, #xgmb');	
				});
			});
			
		},
		chaKan : function(obj){
			$('#dialogBox').html(_.template(xtdxmb_ck_dialogTpl, obj));	
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"查看模版",
				width:"900",
				modal:true,
				buttons:{ 
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		},
		yong : function(obj){
			$('#busibox').html(_.template(xtdxmb_qtyTpl, obj));
			
			if(obj.th_4 == '已启用'){
				comm.liActive_add($('#tymb'));	
			}
			else if(obj.th_4 == '已停用'){
				comm.liActive_add($('#qymb'));
			}
			
			$('#cancel').triggerWith('#xtdxmb');		
		},
		xiuGai : function(obj){
			$('#busibox').html(_.template(xtdxmb_xgTpl, obj));
			comm.liActive_add($('#xgmb'));
			$('#xg_cancel').triggerWith('#xtdxmb');	
			
			/*下拉列表*/
			$("#templateUse_xg").selectList({
				width:225,
				optionWidth:225,
				options:[
					{name:'线下核款授权短信',value:'1'},
					{name:'申报企业授权码短信',value:'2'},
					{name:'申报服务公司付款通知短信',value:'3',selected: true},
					{name:'申报企业成功短信',value:'4'},
					{name:'密码重置短信',value:'5'},
					{name:'积分预付款账户告急短信',value:'6'},
					{name:'积分预付款账户不足短信',value:'7'},
					{name:'积分预付款账户偏少短信发送模板',value:'8'},
					{name:'意外伤害保障生效',value:'9'},
					{name:'意外伤害保障失效',value:'10'},
					{name:'扣除年费成功短信模版',value:'11'},
					{name:'扣除年费失败短信模版',value:'12'},
					{name:'手机短信验证码发送模版',value:'13'},
					{name:'免费医疗计划生效短信',value:'14'}
				]
			});
			/*end*/
			
			/*富文本框*/
			$('#shortMessage_content_xgmb').xheditor({
				upLinkUrl:"upload.php",
				upLinkExt:"zip,rar,txt",
				upImgUrl:"upload.php",
				upImgExt:"jpg,jpeg,gif,png",
				upFlashUrl:"upload.php",
				upFlashExt:"swf",
				upMediaUrl:"upload.php",
				upMediaExt:"wmv,avi,wma,mp3,mid",
				width:678,
				height:150
			}); 
			/*end*/
			
		},
		showTpl : function(tplObj){
			$('#xtdxmbTpl, #xtdxmb_xzTpl, #xtdxmb_xgTpl, #xtdxmb_qtyTpl').addClass('none');
			tplObj.removeClass('none');
		}	
	} 
	
});