define(['text!coDeclarationTpl/htgl/htmbwh.html',
		'text!coDeclarationTpl/htgl/xzmb.html',
		'text!coDeclarationTpl/htgl/htmbwh_xgmb.html'
		], function(htmbwhTpl, xzmbTpl, htmbwh_xgmbTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(htmbwhTpl));
			
			var self = this;

			/*按钮事件*/
			$('#xzmb_btn').click(function(){
				/*$('#htmbwhTpl').addClass('none');
				$('#xgmbTpl').addClass('none');
				$('#xzmbTpl').removeClass('none');*/
				$('#busibox').html(_.template(xzmbTpl));
				comm.liActive_add($('#xzmb'));
				
				/*下拉列表*/
				$("#enterprise_type_xzmb").selectList({
					width:180,
					options:[
						{name:'xxx',value:'100'},
						{name:'xxx',value:'101'},
						{name:'xxx',value:'101'}
					]
				});
				
				$("#resources_state_xzmb").selectList({
					width:155,
					options:[
						{name:'创业资源',value:'100'},
						{name:'首段资源',value:'101'},
						{name:'全部资源',value:'101'}
					]
				});
				/*end*/
				
				/*富文本框*/
				$('#template_content_xzmb').xheditor({
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
				
				$('#xzmb_back').triggerWith('#htmbwh');
				
			});
			/*end*/
			
			/*下拉列表*/
			$("#enterprise_type").selectList({
				options:[
					{name:'xxx',value:'100'},
					{name:'xxx',value:'101'}
				]
			});
			
			
			$("#state").selectList({
				options:[
					{name:'已启用',value:'100'},
					{name:'待启用',value:'101'},
					{name:'待复核',value:'101'},
					{name:'复核驳回',value:'101'}
				]
			});
			
			/*end*/
			
			/*表格数据模拟*/
			var json= [{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"已启用",
							"th_5":"2015-06-27 10:40:05"
						},
						{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"待启用",
							"th_5":"2015-06-27 10:40:05"
						},
						{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"待复核",
							"th_5":"2015-06-27 10:40:05"
						},
						{
							"th_1":"xxx",
							"th_2":"xxx",
							"th_3":"xxx",
							"th_4":"复核驳回",
							"th_5":"2015-06-27 10:40:05"
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
						var link1 = $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >查看</a>').click(function(e) {
								//$('#dialog').dialog({autoOpen: true }) ;
								this.chaKan(obj);
								
							}.bind(this) ) ;
						return   link1 ;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 = '';
						if (gridObj.getRecordIndexValue(record, 'th_4') =='已启用'){
							
							link2 =  $('<a>停用</a>').click(function(e) {
								this.tinYong();
								
							}.bind(this) ) ;
							
						}
						else if(gridObj.getRecordIndexValue(record, 'th_4') =='待启用'){
							
							link2 =  $('<a>启用</a>').click(function(e) {
								this.qiYong();
								
							}.bind(this) ) ;
							
						}
							
						return link2;	
					}.bind(this),
					
					add  : function(record, rowIndex, colIndex, options){
						var link3 = null;
						var obj = gridObj.getRecord(rowIndex);
						if (gridObj.getRecordIndexValue(record, 'th_4') =='待启用' || gridObj.getRecordIndexValue(record, 'th_4') =='待复核' || gridObj.getRecordIndexValue(record, 'th_4') =='复核驳回'){
							
							link3 =  $('<a>修改</a>').click(function(e) {
								this.xiuGai(obj);
								
							}.bind(this) ) ;
							
						}
						
						return link3;	
						
					}.bind(this)
				} 		
			});
			
			/*end*/	
		},
		chaKan : function(){
			$( "#chaKan_content" ).dialog({
				title:"查看模版",
				modal:true,
				width:"900",
				closeIcon:true
			  });
		},
		tinYong : function(){
			var confirmObj = {	
				imgFlag:true  ,             //显示图片
				content : '您确认停用此模版？',			 
				callOk :function(){
										
				}
			}
			comm.confirm(confirmObj);
		},
		qiYong : function(){
			var confirmObj = {	
				imgFlag:true  ,             //显示图片
				content : '您确认启用此模版？',			 
				callOk :function(){
										
				}
			}
			comm.confirm(confirmObj);
		},
		xiuGai : function(obj){
			/*$('#htmbwhTpl').addClass('none');
			$('#xzmbTpl').addClass('none');
			$('#xgmbTpl').removeClass('none');*/
			$('#busibox').html(_.template(htmbwh_xgmbTpl, obj));
			comm.liActive_add($('#xgmb'));
			
			/*下拉列表*/
			$("#enterprise_type_xgmb").selectList({
				width:180,
				options:[
					{name:'xxx',value:'100'},
					{name:'xxx',value:'101'},
					{name:'xxx',value:'101'}
				]
			});
			
			$("#resources_state_xgmb").selectList({
				width:155,
				options:[
					{name:'创业资源',value:'100'},
					{name:'首段资源',value:'101'},
					{name:'全部资源',value:'101'}
				]
			});
			/*end*/
			
			/*组合框*/
			$('#template_content_xgmb').xheditor({
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
			
			$('#xgmb_back').triggerWith('#htmbwh');	
		}		
	}	
});