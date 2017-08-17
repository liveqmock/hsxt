define(['text!businessParaManageTpl/sub/xygl/xygl.html',
		'text!businessParaManageTpl/sub/xygl/xygl_opt.html'
		],function( xyglTpl, xygl_optTpl ){
	
		var self = null;
		var gridObj = null;
		var flag = true;
	
	return {
 
		showPage : function(tabid){
			
			self = this;
			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(xyglTpl)) ;
			
			//新增按钮
			$('#xygl_add_btn').click(function(){
				self.opt_add();	
			});
			
			/*下拉列表*/
			$("#xygl_query_jhzt").selectList({
				borderWidth : 1,
				borderColor : '#999',
				options:[
				    {name:'全部',value:''},
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
 		   	
			var mc = $("#xygl_query_mc").val();
			var dm = $("#xygl_query_dm").val();
			var jhzt = $("#xygl_query_jhzt").attr("optionValue");
			// 查询条件
			var queryConditon = {"xygl_mc":mc,"xygl_dm":dm,"xygl_jhzt":jhzt};
		    
			gridObj = comm.getCommBsGrid('queryList_xygl', "xyglSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			
			//查询按钮
			$('#xygl_queryList_jhzt').click(function(){
				var mc = $("#xygl_query_mc").val();
				var dm = $("#xygl_query_dm").val();
				var jhzt = $("#xygl_query_jhzt").attr("optionValue");
				// 查询条件
				queryConditon = [
				             {"name":"xygl_mc","value":mc},
				             {"name":"xygl_dm","value":dm},
				             {"name":"xygl_jhzt","value":jhzt}
				             ]
				gridObj = comm.getCommBsGrid('queryList_xygl', "xyglSearch", queryConditon,null,self.detail,self.opt_delete,null,self.opt_edit);
			});
		},
		opt_add : function(){
			
			var that = this;
			$('#xyglDlg').html(xygl_optTpl);	
			
			/*弹出框*/
			$( "#xyglDlg" ).dialog({
				title:"新增",
				width:"500",
				height:"300",
				modal:true,
				closeIcon : false,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							if(!flag){
								$("#xygl_opt_xydm").attr("title","该协议代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xygl_optForm").serializeArray();
							var jhzt = $("#xygl_opt_jhzt").attr('optionValue');
							datas[2].value = jhzt;
							comm.requestFun("xyglAdd",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xygl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog("destroy");
						 
					}
				}
			});
			
			
			$("#xygl_opt_jhzt").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			$("#xygl_opt_xydm").change(function(){
				var xydm = $("#xygl_opt_xydm").val();
				var data = {"xygl_dm":xydm};
				comm.validateDatas("xyglSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						flag = false;
						$("#xygl_opt_xydm").attr("title","该协议代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xygl_opt_xydm").attr("title","").tooltip().tooltip("destroy");
					}
					
				},"");
			});
		},
		
		option_edit : function(obj){
			$('#xyglDlg').html(xygl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xyglDlg" ).dialog({
				title:"修改",
				width:"500",
				height:"300",
				modal:true,
				closeIcon : false,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							var datas = $("#xygl_optForm").serializeArray();
							var jhzt = $("#xygl_opt_jhzt").attr('optionValue');
							var agreeId = obj.agreeId;
							datas[2].value = jhzt;
							datas.push({"name":"agreeId","value":agreeId});
							comm.requestFun("xyglUpdate",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xygl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			
			
			$("#xygl_opt_jhzt").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			
			/*表单值初始化*/
			$('#xygl_opt_xymc').val(obj.agreeName);
			$('#xygl_opt_xydm').val(obj.agreeCode);
			var isActive = obj.isActive;
			if(isActive == 'Y'){
				$('#xygl_opt_jhzt').val("激活");
			}else{
				$('#xygl_opt_jhzt').val("禁用");
			};
			
			//设置协议代码栏位为只读项，不能修改
			$('#xygl_opt_xydm').attr("readonly","readonly");
		},
		
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '3'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '3'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var agreeId = obj.agreeId;
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							       {"name":"agreeId","value":agreeId},
								   {"name":"curPage","value":curPage},
							       {"name":"pageSize","value":pageSize}
							    ];
							comm.requestFun("xyglDelete",datas,function(obj){
								$('#queryList_xygl_pt_refreshPage').click();
							},"");
						}
					});
				}.bind(this) ) ;
			}
			
			return opt_btn;
		}.bind(this),
		
		callback : function(obj){
			$(obj).dialog( "destroy" );
		},
		detail : function(record, rowIndex, colIndex, options){
			var opt_btn = null;
			if(colIndex == '2'){
				var val = gridObj.getColumnValue(rowIndex, 'isActive');
				switch(val){
					case 'Y':
						opt_btn = '激活';
						break;
					case 'N':
						opt_btn = '禁用';
						break;
				
				}
			}
			return opt_btn;
		},
		
		addValidate : function(){
			return comm.valid({
				formID : '#xygl_optForm',
				left : 270,
				top : -1,
				rules : {
					xygl_opt_xymc : {
						required : true	
					},
					xygl_opt_xydm : {
						required : true	
					},
					xygl_opt_jhzt : {
						required : true	
					}
				},
				messages : {
					xygl_opt_xymc : {
						required : '必填'
					},
					xygl_opt_xydm : {
						required : '必填'
					},
					xygl_opt_jhzt : {
						required : '必填'
					}
				}
			});
		}
	}
}); 
