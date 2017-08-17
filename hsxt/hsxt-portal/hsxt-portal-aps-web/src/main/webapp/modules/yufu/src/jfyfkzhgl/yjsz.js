define(['text!yufuTpl/jfyfkzhgl/yjsz.html',
		'text!yufuTpl/jfyfkzhgl/yjsz_ck_dialog.html',
		'text!yufuTpl/jfyfkzhgl/yjsz_xz.html',
		'text!yufuTpl/jfyfkzhgl/yjsz_xg.html'
		], function(yjszTpl, yjsz_ck_dialogTpl, yjsz_xzTpl, yjsz_xgTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(yjszTpl)).append(yjsz_xzTpl);
			
			var self = this;
			
			/*下拉列表*/
			$("#statues").selectList({
				options:[
					{name:'全部',value:'1'},
					{name:'正常',value:'2'},
					{name:'无效',value:'3'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"20020",
							"th_2":"20020",
							"th_3":"发送短信",
							"th_4":"xxx",
							"th_5":"指定时间 2014-10-13 14:30",
							"th_6":"2015-08-16 19:25:00",
							"th_7":"有效"
						},
						{
							"th_1":"20021",
							"th_2":"20021",
							"th_3":"推送消息",
							"th_4":"xxx",
							"th_5":"每日 15:00",
							"th_6":"2015-09-06 19:25:00",
							"th_7":"有效"
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
						var link2 = $('<a>修改</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.xiuGai(obj);
							
							
						}.bind(this) ) ;
						
						return link2;
					}.bind(this),
					
					del : function(record, rowIndex, colIndex, options){
						var link3 = $('<a>删除</a>').click(function(e) {
							//var obj = gridObj.getRecord(rowIndex);
							this.shanChu();
							
							
						}.bind(this) ) ;
						
						return link3;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			$('#xzgz_btn').click(function(){
				comm.liActive_add($('#xzgz'));	
				self.showTpl($('#yjsz_xzTpl'));
				
				$('#xz_cancel').click(function(){
					self.showTpl($('#yjszTpl'));
					comm.liActive($('#yjsz'), '#xzgz, #xggz');
				});
				$('#qd_xz_btn').click(function(){
					comm.alert({
						imgClass: 'tips_yes' ,         //图片类名，默认tips_ques
						content : '操作成功！'	
					});	
				});
				
				/*日期控件*/
				$( "#timeRange_start" ).datepicker({dateFormat:'yy-mm-dd'});
				$( "#timeRange_end" ).datepicker({dateFormat:'yy-mm-dd'});
				/*end*/
				
				/*下拉列表*/
				$("#mbxz").selectList({
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
				
			});	
			
		},
		chaKan : function(obj){
			$('#dialogBox').html(_.template(yjsz_ck_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"预警设置详情",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
				
		},
		xiuGai : function(obj){
			$('#busibox').html(_.template(yjsz_xgTpl, obj));
			comm.liActive_add($('#xggz'));	
			
			/*日期控件*/
			$( "#timeRange_start_xg" ).datepicker({dateFormat:'yy-mm-dd'});
			$( "#timeRange_end_xg" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/	
			
			/*下拉列表*/
			$("#mbxz_xg").selectList({
				width:225,
				optionWidth:225,
				options:[
					{name:'线下核款授权短信',value:'1',selected:true},
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
			
			$('#xg_cancel').triggerWith('#yjsz');
			$('#qd_xg_btn').click(function(){
				comm.alert({
					imgClass: 'tips_yes' ,         //图片类名，默认tips_ques
					content : '操作成功！'	
				});	
			});

		},
		shanChu : function(){
			comm.alert({
				imgClass: 'tips_yes' ,         //图片类名，默认tips_ques
				content : '删除成功！'	
			});	
		},
		showTpl : function(tplObj){
			$('#yjszTpl, #yjsz_xzTpl, #yjsz_xgTpl').addClass('none');	
			tplObj.removeClass('none');
		}
			
	}	
});