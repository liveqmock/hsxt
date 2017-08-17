define(['text!callCenterTpl/zxztjk/gzzsz.html'], function(gzzszTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(gzzszTpl));	
			
			/*下拉列表事件*/
			$("#groupName").selectList({
				width:120,
				options:[
					{name:'业务组1',value:'1'},
					{name:'业务组2',value:'2'},
					{name:'业务组3',value:'3'}
				]
			});
			
			$("#scheduling").selectList({
				options:[
					{name:'是',value:'1'},
					{name:'否',value:'0'}
				]
			});
			/*end*/
			
			/*按钮事件*/
			$('#addgzz_btn').click(function(){
				/*弹出框*/
				$( "#addgzz_content" ).dialog({
					title:"增加工作组",
					modal:true,
					width:"650",
					height:"460",
					buttons:{ 
						"确定":function(){
							$( "#addgzz_content" ).dialog( "destroy" );
						},
						"取消":function(){
							 $( "#addgzz_content" ).dialog( "destroy" );
						}
					}
			
				  });
				/*end*/	
				
				/*下拉列表事件*/
				$("#workingGroupName_add").selectList({
					width:120,
					options:[
						{name:'业务组1',value:'1'},
						{name:'业务组2',value:'2'},
						{name:'业务组3',value:'3'}
					]
				});
				/*end*/
				
				$('#add_select_zj').unbind().click(function(){
					comm.selectMultiple_operation('#leftSelect_zj', '#rightSelect_zj', '选择需要添加的工作人员！');
				});
				$('#del_select_zj').unbind().click(function(){
					comm.selectMultiple_operation('#rightSelect_zj', '#leftSelect_zj', '选择需要删除的工作人员！');
				});
				
				$('#leftSelect_zj').dblclick(function(){
					comm.selectMultiple_operation_dbClick($(this), '#rightSelect_zj');	
				});
				
				$('#rightSelect_zj').dblclick(function(){
					comm.selectMultiple_operation_dbClick($(this), '#leftSelect_zj');	
				});
				
			});
			/*end*/
			
			/*表格请求数据*/
			var json= [{
							"th_1":"业务组1",
							"th_2":"洪七公，过儿，小龙女",
							"th_3":"否",
							"th_4":"处理客服信息查询",
							"th_5":"否"
						},
						{
							"th_1":"业务组2",
							"th_2":"张三，李四，王五，赵六，孙七,周八,吴, 郑十",
							"th_3":"是",
							"th_4":"处理客服信息查询",
							"th_5":"否"
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
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >重新分配</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							this.cxfp();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this),
					
					edit: function(record, rowIndex, colIndex, options){
						var link2 =  $('<a index="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >排班</a>').click(function(e) {							 
							//$('#dialog').dialog({autoOpen: true }) ;
							this.pb();
						}.bind(this)); 						
						return   link2  ;
					}.bind(this)

					
				} 			
			});
			
			/*end*/
		},
		
		cxfp : function(option){
			
			/*弹出框*/
			$( "#cxfp_content" ).dialog({
				title:"重新分配工作组",
				modal:true,
				width:"650",
				height:"460",
				buttons:{ 
					"确定":function(){
						$( "#cxfp_content" ).dialog( "destroy" );
					},
					"取消":function(){
						 $( "#cxfp_content" ).dialog( "destroy" );
					}
				}
		
			  });
			/*end*/	
			
			/*下拉列表事件*/
			$("#workingGroupName_cxfp").selectList({
				width:120,
				options:[
					{name:'业务组1',value:'1'},
					{name:'业务组2',value:'2'},
					{name:'业务组3',value:'3'}
				]
			});
			/*end*/
			
			$('#add_select_cxfp').unbind().click(function(){
				comm.selectMultiple_operation('#leftSelect_cxfp', '#rightSelect_cxfp', '选择需要添加的工作人员！');
			});
			$('#del_select_cxfp').unbind().click(function(){
				comm.selectMultiple_operation('#rightSelect_cxfp', '#leftSelect_cxfp', '选择需要删除的工作人员！');
			});
			
			$('#leftSelect_cxfp').dblclick(function(){
				comm.selectMultiple_operation_dbClick($(this), '#rightSelect_cxfp');	
			});
			
			$('#rightSelect_cxfp').dblclick(function(){
				comm.selectMultiple_operation_dbClick($(this), '#leftSelect_cxfp');	
			});

		},
		
		pb : function(option){
			/*弹出框*/
			$( "#pb_content" ).dialog({
				title:"排班人员安排",
				modal:true,
				width:"650",
				height:"460",
				buttons:{ 
					"确定":function(){
						$( "#pb_content" ).dialog( "destroy" );
					},
					"取消":function(){
						 $( "#pb_content" ).dialog( "destroy" );
					}
				}
		
			  });
			/*end*/	
			
			/*下拉列表事件*/
			$("#workingGroupName_pb").selectList({
				width:120,
				options:[
					{name:'业务组1',value:'1'},
					{name:'业务组2',value:'2'},
					{name:'业务组3',value:'3'}
				]
			});
			/*end*/
			
			/*日期控件*/
			$( "#pb_time" ).datepicker({dateFormat:'yy-mm-dd'});
			/*end*/
			
			$('#add_select_pb').unbind().click(function(){
				comm.selectMultiple_operation('#leftSelect_pb', '#rightSelect_pb', '选择需要添加的工作人员！');
			});
			$('#del_select_pb').unbind().click(function(){
				comm.selectMultiple_operation('#rightSelect_pb', '#leftSelect_pb', '选择需要删除的工作人员！');
			});
			
			$('#leftSelect_pb').dblclick(function(){
				comm.selectMultiple_operation_dbClick($(this), '#rightSelect_pb');	
			});
			
			$('#rightSelect_pb').dblclick(function(){
				comm.selectMultiple_operation_dbClick($(this), '#leftSelect_pb');	
			});
		}
	}	
});