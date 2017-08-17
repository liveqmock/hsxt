define(["text!msgmanageTpl/dxmbfh/dxmbfh.html",
		"text!msgmanageTpl/dxmbfh/dxmbfh_ck_dialog.html",
		"text!msgmanageTpl/dxmbfh/dxmbfh_xz.html",
		"text!msgmanageTpl/dxmbfh/dxmbfh_fh_dialog.html",
		"text!msgmanageTpl/dxmbfh/dxmbfh_xg.html",
		"text!msgmanageTpl/dxmbfh/dxmbfh_fh.html"
		],function(dxmbfhTpl, dxmbfh_ck_dialogTpl, dxmbfh_xzTpl, dxmbfh_fh_dialogTpl, dxmbfh_xgTpl, dxmbfh_fhTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(dxmbfhTpl)).append(dxmbfh_xzTpl);	
			
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
					{name:'线下核款授权短信模板',value:'1'},
					{name:'申报企业授权码短信发送模板',value:'2'},
					{name:'申报服务公司付款通知短信发送模版',value:'3'},
					{name:'申报企业成功短信发送模板',value:'4'},
					{name:'密码重置短信发送模版',value:'5'},
					{name:'预付积分账户告急短信发送模板',value:'6'},
					{name:'预付积分账户不足短信发送模板',value:'7'},
					{name:'预付积分账户偏少短信发送模板',value:'8'},
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
							"th_1":"xxx",
							"th_2":"线下核款授权短信",
							"th_3":"xxx",
							"th_4":"已启用",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"xxx",
							"th_2":"申报企业授权码短信",
							"th_3":"xxx",
							"th_4":"已停用",
							"th_5":"2015-09-01 10:35:00"
						},
						{
							"th_1":"xxx",
							"th_2":"申报服务公司付款通知短信",
							"th_3":"xxx",
							"th_4":"待启用",
							"th_5":"2015-09-08 15:25:00"
						},
						{
							"th_1":"xxx",
							"th_2":"申报企业成功短信",
							"th_3":"xxx",
							"th_4":"复核驳回",
							"th_5":"2015-09-08 10:25:00"
						},
						{
							"th_1":"xxx",
							"th_2":"密码重置短信",
							"th_3":"xxx",
							"th_4":"待复核",
							"th_5":"2015-09-08 19:25:00"
						}
						];	
	
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
						if(gridObj.getColumnValue(rowIndex, 'th_4') == '待启用'){
							link2 = $('<a>修改</a>').click(function(e) {
								this.xiuGai(obj);
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_4') == '待复核'){
							link2 = $('<a>复核</a>').click(function(e) {
								this.fuHe(obj);
							}.bind(this) ) ;
						}
						
						return link2;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
			$('#xz_btn').click(function(){
				comm.liActive_add($('#xzdxmb'));
				$('#fhDiv').removeClass('none');
				self.showTpl($('#dxmbfh_xzTpl'));
				
				/*下拉列表*/
				$("#templateUse_xz").selectList({
					width:225,
					optionWidth:225,
					options:[
						{name:'线下核款授权短信',value:'1'},
						{name:'申报企业授权码短信发送',value:'2'},
						{name:'申报服务公司付款通知短信发送',value:'3'},
						{name:'申报企业成功短信发送',value:'4'},
						{name:'密码重置短信发送',value:'5'},
						{name:'预付积分账户告急短信发送',value:'6'},
						{name:'预付积分账户不足短信发送',value:'7'},
						{name:'预付积分账户偏少短信发送',value:'8'},
						{name:'意外伤害保障生效',value:'9'},
						{name:'意外伤害保障失效',value:'10'},
						{name:'扣除年费成功短信',value:'11'},
						{name:'扣除年费失败短信',value:'12'},
						{name:'手机短信验证码发送',value:'13'},
						{name:'免费医疗计划生效短信',value:'14'}
					]
				});
				
				$("#templateState_xz").selectList({
					width:225,
					optionWidth:225,
					options:[
						{name:'已启用',value:'1'},
						{name:'已停用',value:'2'},
						{name:'待启用',value:'3'},
						{name:'复核驳回',value:'4'},
						{name:'待复核',value:'5'}
					]
				});
			
				/*end*/
				
				/*日期控件*/
				$( "#tjsj" ).datepicker({dateFormat:'yy-mm-dd'});
				/*end*/
				
				/*富文本框*/
				$('#shortMessage_content_xz').xheditor({
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
				
				$('#xz_back').click(function(){
					self.showTpl($('#dxmbfhTpl'));	
					comm.liActive($('#dxmbfh'), '#xzdxmb, #xgdxmb');
					$('#fhDiv').addClass('none');
				});
				
				$('#dialogBox_fh').html(_.template(dxmbfh_fh_dialogTpl));
				
				$('#fh_btn').click(function(){
					/*弹出框*/
					$( "#dialogBox_fh" ).dialog({
						title:"复核信息",
						width:"400",
						height:"220",
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
						
				});
				
			});

		},
		chaKan : function(obj){
			$('#dialogBox').html(_.template(dxmbfh_ck_dialogTpl, obj));	
			
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"查看短信模版",
				width:"900",
				modal:true,
				buttons:{ 
					"关闭":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		},	
		xiuGai : function(obj){
			comm.liActive_add($('#xgdxmb'));
			$('#fhDiv').removeClass('none');
			$('#busibox').html(_.template(dxmbfh_xgTpl, obj));
			
			/*下拉列表*/
			$("#templateUse_xg").selectList({
				width:225,
				optionWidth:225,
				options:[
					{name:'线下核款授权短信',value:'1'},
					{name:'申报企业授权码短信发送',value:'2'},
					{name:'申报服务公司付款通知短信发送',value:'3',selected:true},
					{name:'申报企业成功短信发送',value:'4'},
					{name:'密码重置短信发送',value:'5'},
					{name:'预付积分账户告急短信发送',value:'6'},
					{name:'预付积分账户不足短信发送',value:'7'},
					{name:'预付积分账户偏少短信发送',value:'8'},
					{name:'意外伤害保障生效',value:'9'},
					{name:'意外伤害保障失效',value:'10'},
					{name:'扣除年费成功短信',value:'11'},
					{name:'扣除年费失败短信',value:'12'},
					{name:'手机短信验证码发送',value:'13'},
					{name:'免费医疗计划生效短信',value:'14'}
				]
			});
			
			$("#templateState_xg").selectList({
				width:225,
				optionWidth:225,
				options:[
					{name:'已启用',value:'1'},
					{name:'已停用',value:'2'},
					{name:'待启用',value:'3',selected:true},
					{name:'复核驳回',value:'4'},
					{name:'待复核',value:'5'}
				]
			});
		
			/*end*/
			
			/*日期控件*/
			$( "#tjsj_xg" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
			
			/*富文本框*/
			$('#shortMessage_content_xg').xheditor({
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
			
			$('#xg_back').triggerWith('#dxmbfh');
			
			$('#dialogBox_fh_xg').html(_.template(dxmbfh_fh_dialogTpl));
			
			$('#fh_btn').click(function(){
				/*弹出框*/
				$( "#dialogBox_fh_xg" ).dialog({
					title:"复核信息",
					width:"400",
					height:"220",
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
					
			});
			
				
		},
		fuHe : function(obj){
			$('#busibox').html(_.template(dxmbfh_fhTpl, obj));
			$('#dialogBox_fhnr').html(_.template(dxmbfh_fh_dialogTpl));
			comm.liActive_add($('#fhnr'));	
			$('#fhDiv').removeClass('none');
			
			$('#fh_back').triggerWith('#dxmbfh');
			
			$('#fh_btn').click(function(){
				
				/*弹出框*/
				$( "#dialogBox_fhnr" ).dialog({
					title:"复核信息",
					width:"400",
					height:"220",
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
				
			});
			
		},
		showTpl : function(tplObj){
			$('#dxmbfhTpl, #dxmbfh_xzTpl').addClass('none');	
			tplObj.removeClass('none');
		}
	}
});