define(['text!businessParaManageTpl/sub/xtcszgl/xtcszgl.html',
		'text!businessParaManageTpl/sub/xtcszgl/xtcszgl_opt.html'
		],function( xtcszglTpl, xtcszgl_optTpl ){
	
		var self = null;
		var gridObj = null;
		var flag = true;
	
	return {
 
		showPage : function(tabid){
			
			self = this;
			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(xtcszglTpl )) ;
			
			//新增按钮
			$('#xtcszgl_add_btn').click(function(){
				self.opt_add();	
			});
			
			/*下拉列表*/
			$("#xtcszgl_queryList_jhzt").selectList({
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
 		   	
			var zdm = $("#xtcszgl_query_zdm").val();
			var zmc = $("#xtcszgl_query_zmc").val();
			var jhzt = $("#xtcszgl_queryList_jhzt").attr("optionValue");
			// 查询条件
			var queryConditon = {"xtcszgl_opt_zdm":zdm,"xtcszgl_opt_zmc":zmc,"xtcszgl_opt_jhzt":jhzt};
		    
			gridObj = comm.getCommBsGrid('queryList_xtcszgl', "xtcszSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			
			//查询按钮
			$('#xtcszgl_query_btn').click(function(){
				var zdm = $("#xtcszgl_query_zdm").val();
				var zmc = $("#xtcszgl_query_zmc").val();
				var jhzt = $("#xtcszgl_queryList_jhzt").attr("optionValue");
				// 查询条件
				queryConditon = [
				             {"name":"xtcszgl_opt_zdm","value":zdm},
				             {"name":"xtcszgl_opt_zmc","value":zmc},
				             {"name":"xtcszgl_opt_jhzt","value":jhzt}
				             ]
				gridObj = comm.getCommBsGrid('queryList_xtcszgl', "xtcszSearch", queryConditon,null,self.detail,self.opt_delete,null,self.opt_edit);
			});
		},
		opt_add : function(){
			
			var that = this;
			$('#xtcszglDlg').html(xtcszgl_optTpl);	
			
			/*弹出框*/
			$( "#xtcszglDlg" ).dialog({
				title:"新增",
				width:"500",
				height:"340",
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
								$("#xtcszgl_opt_zdm").attr("title","该参数组代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xtcszgl_optForm").serializeArray();
							var cslx = $("#xtcszgl_opt_cslx").attr('optionValue');
							var jhzt = $("#xtcszgl_opt_jhzt").attr('optionValue');
							datas[2].value = cslx;
							datas[3].value = jhzt;
							comm.requestFun("xtcszAdd",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xtcszgl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog("destroy");
						 
					}
				}
			});
			
			/*下拉列表*/
			$("#xtcszgl_opt_cslx").selectList({
				width:250,																																																							
				optionWidth:255,
				options:[
					{name:'公共参数',value:'Y'},
					{name:'私有参数',value:'N'}
				]
			}).change(function(e){
				console.log($(this).attr('optionValue'));
			});
			
			$("#xtcszgl_opt_jhzt").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			$("#xtcszgl_opt_zdm").change(function(){
				var zdm = $("#xtcszgl_opt_zdm").val();
				var data = {"xtcszgl_opt_zdm":zdm};
				comm.validateDatas("xtcszSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						flag = false;
						$("#xtcszgl_opt_zdm").attr("title","该参数组代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xtcszgl_opt_zdm").attr("title","").tooltip().tooltip("destroy");
					}
					
				},"");
			});
		},
		
		option_edit : function(obj){
			$('#xtcszglDlg').html(xtcszgl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xtcszglDlg" ).dialog({
				title:"修改",
				width:"500",
				height:"340",
				modal:true,
				closeIcon : false,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							var datas = $("#xtcszgl_optForm").serializeArray();
							var cslx = $("#xtcszgl_opt_cslx").attr('optionValue');
							var jhzt = $("#xtcszgl_opt_jhzt").attr('optionValue');
							datas[2].value = cslx;
							datas[3].value = jhzt;
							comm.requestFun("xtcszUpdate",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xtcszgl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			
			/*下拉列表*/
			$("#xtcszgl_opt_cslx").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'公共参数',value:'Y'},
					{name:'私有参数',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			$("#xtcszgl_opt_jhzt").selectList({
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
			$('#xtcszgl_opt_zdm').val(obj.sysGroupCode);
			$('#xtcszgl_opt_zmc').val(obj.sysGroupName);
			var isPublic = obj.isPublic;
			switch(isPublic){
				case 'Y':
					$('#xtcszgl_opt_cslx').val("公共参数");
					break;
				case "N":
					$('#xtcszgl_opt_cslx').val("私有参数");
					break;
			}
			var isActive = obj.isActive;
			if(isActive == 'Y'){
				$('#xtcszgl_opt_jhzt').val("激活");
			}else{
				$('#xtcszgl_opt_jhzt').val("禁用");
			};
			
			//设置组代码栏位为只读项，不能修改
			$('#xtcszgl_opt_zdm').attr("readonly","readonly");
		},
		
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '4'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '4'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var param = obj.sysGroupCode;
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							       {"name":"xtcszgl_opt_zdm","value":param},
								   {"name":"curPage","value":curPage},
							       {"name":"pageSize","value":pageSize}
							    ];
							comm.requestFun("xtcszDelete",datas,function(obj){
								$('#queryList_xtcszgl_pt_refreshPage').click();
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
				var val = gridObj.getColumnValue(rowIndex, 'isPublic');
				switch(val){
					case 'Y':
						opt_btn = '公共参数';
						break;
					case 'N':
						opt_btn = '私有参数';
						break;
				
				}
			}
			if(colIndex == '3'){
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
		}.bind(this),
		
		addValidate : function(){
			return comm.valid({
				formID : '#xtcszgl_optForm',
				left : 270,
				top : -1,
				rules : {
					xtcszgl_opt_zdm : {
						required : true	
					},
					xtcszgl_opt_zmc : {
						required : true	
					},
					xtcszgl_opt_cslx : {
						required : true	
					},
					xtcszgl_opt_jhzt : {
						required : true	
					}
				},
				messages : {
					xtcszgl_opt_zdm : {
						required : '必填'
					},
					xtcszgl_opt_zmc : {
						required : '必填'
					},
					xtcszgl_opt_cslx : {
						required : '必填'
					},
					xtcszgl_opt_jhzt : {
						required : '必填'
					}
				}
			});
		}
	}
}); 

 