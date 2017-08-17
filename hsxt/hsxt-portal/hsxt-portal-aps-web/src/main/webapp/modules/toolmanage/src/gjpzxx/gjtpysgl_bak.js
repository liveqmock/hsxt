define(['text!toolmanageTpl/gjpzxx/gjtpysgl.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_tj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_ck_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_ztxg_dialog.html',
		'text!toolmanageTpl/gjpzxx/gjtpysgl_ztfh_dialog.html'
		], function(gjtpysglTpl, gjtpysgl_tj_dialogTpl, gjtpysgl_ck_dialogTpl, gjtpysgl_ztxg_dialogTpl, gjtpysgl_ztfh_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(gjtpysglTpl));
			
			/*下拉列表*/
			$("#toolName").selectList({
				options:[
					{name:'POS机',value:'1'},
					{name:'刷卡器',value:'2'},
					{name:'互生卡',value:'3'},
					{name:'参考手册',value:'4'}
				]
			});
			
			$("#toolPicStyle").selectList({
				width: 125,
				options:[
					{name:'普通样式',value:'1'},
					{name:'个性样式',value:'2'}
				]
			});
			
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"xxxxxx互生卡",
							"th_2":"互生卡",
							"th_3":"普通样式",
							"th_4":"2015-09-11 10:25:00",
							"th_5":"0001（张三）",
							"th_6":"启用"
						},
						{
							"th_1":"xxxxxx互生卡",
							"th_2":"互生卡",
							"th_3":"个性样式",
							"th_4":"2015-09-11 16:50:00",
							"th_5":"0002（李四）",
							"th_6":"禁用"
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
							
						if(gridObj.getColumnValue(rowIndex, 'th_6') == '启用'){
							link2 = $('<a>状态修改</a>').click(function(e) {
								
								this.ztxg(obj);
								
							}.bind(this) ) ;
						}
						else if(gridObj.getColumnValue(rowIndex, 'th_6') == '禁用'){
							link2 = $('<a>状态复核</a>').click(function(e) {

								this.ztfh(obj);
								
							}.bind(this) ) ;	
						}
						
						return link2;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
			$('#tj_btn').click(function(){
				$('#dialogBox_tj').html(_.template(gjtpysgl_tj_dialogTpl));
				
				/*弹出框*/
				$( "#dialogBox_tj" ).dialog({
					title:"添加工具图片样式",
					width:"600",
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
				
				/*下拉列表*/
				$("#toolName").selectList({
					options:[
						{name:'xxx',value:'1'},
						{name:'xxx',value:'2'}
					]
				});
				/*end*/
			
			});	
			
		},
		chaKan : function(obj){
			$('#dialogBox_ck').html(_.template(gjtpysgl_ck_dialogTpl, obj));	
			
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"工具图片样式详情",
				width:"600",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		},
		ztxg : function(obj){
			$('#dialogBox_ztxg').html(_.template(gjtpysgl_ztxg_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_ztxg" ).dialog({
				title:"状态修改",
				width:"400",
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
		},
		ztfh : function(obj){
			$('#dialogBox_ztfh').html(_.template(gjtpysgl_ztfh_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_ztfh" ).dialog({
				title:"状态复核",
				width:"600",
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
		}
	}	
});