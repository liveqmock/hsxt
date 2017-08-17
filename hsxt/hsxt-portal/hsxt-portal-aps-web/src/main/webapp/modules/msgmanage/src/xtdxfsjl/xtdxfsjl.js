define(['text!msgmanageTpl/xtdxfsjl/xtdxfsjl.html',
		'text!msgmanageTpl/xtdxfsjl/xtdxfsjl_ck_dialog.html',
		'text!msgmanageTpl/xtdxfsjl/xtdxfsjl_fs_dialog.html'
		],function(xtdxfsjlTpl, xtdxfsjl_ck_dialogTpl, xtdxfsjl_fs_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(xtdxfsjlTpl));	
			
			/*日期控件*/
			$("#timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			/*end*/	
			
			/*下拉列表*/
			$("#sendState").selectList({
				options:[
					{name:'已发送',value:'1'},
					{name:'发送失败',value:'2'},
					{name:'发送中',value:'3'}
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
					{name:'手机短信验证码发送模版',value:'13'}
				]
			});
			
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"04001020000",
							"th_2":"万达集团",
							"th_3":"张三",
							"th_4":"13800138000",
							"th_5":"线下核款授权短信模板",
							"th_6":"已发送",
							"th_7":"2015-08-16 19:25:00"
						},
						{
							"th_1":"04001020001",
							"th_2":"万科集团",
							"th_3":"李四",
							"th_4":"13800138001",
							"th_5":"申报企业授权码短信发送模板",
							"th_6":"未发送",
							"th_7":"2015-09-8 09:25:00"
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
						if(gridObj.getColumnValue(rowIndex, 'th_6') == '已发送'){
							var link1 = $('<a>查看</a>').click(function(e) {
								var obj = gridObj.getRecord(rowIndex);
								this.chaKan(obj);
								
							}.bind(this) ) ;
						}
						
						return link1;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 = null;
						var obj = gridObj.getRecord(rowIndex);
						if(gridObj.getColumnValue(rowIndex, 'th_6') == '已发送'){
							link2 = $('<a>重新发送</a>').click(function(e) {
								this.cxfs(obj);
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_6') == '未发送'){
							link2 = $('<a>发送短信</a>').click(function(e) {
								this.fsdx(obj);
							}.bind(this) ) ;
						}
						
						return link2;
					}.bind(this)
				}
				
			});
			
			/*end*/	

		},
		chaKan : function(obj){
			$('#dialogBox_ck').html(_.template(xtdxfsjl_ck_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"查看短信内容",
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
		cxfs : function(obj){
			$('#dialogBox_fs').html(_.template(xtdxfsjl_fs_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_fs" ).dialog({
				title:"重新发送短信",
				width:"900",
				modal:true,
				buttons:{ 
					"重新发送":function(){
						 $( this ).dialog( "destroy" );
						 comm.alert({
							imgClass: 'tips_yes' ,         //图片类名，默认tips_ques
							content : '短信已发送成功！'	 
						});
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/		
		},
		fsdx : function(){}	
	} 
});