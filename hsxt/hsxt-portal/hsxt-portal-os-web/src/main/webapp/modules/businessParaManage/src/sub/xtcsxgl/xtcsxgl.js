define(['text!businessParaManageTpl/sub/xtcsxgl/xtcsxgl.html',
		'text!businessParaManageTpl/sub/xtcsxgl/xtcsxgl_opt.html'
		],function( xtcsxglTpl, xtcsxgl_optTpl ){
	
	var self = null;
	var gridObj = null;
	var flag = true;
	
	return {
		
		showPage : function(tabid){
			
			self = this;
			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(xtcsxglTpl )) ;
			
			$('#xtcsxgl_add_btn').click(function(){
				self.opt_add();	
			});
		 	
			var queryOptions = [{"name":"","value":""}];
			var conditions = [{"name":"noPageSearchFlag","value":"true"}];
			comm.validateDatas("xtcszSearch",conditions,function(results){
				var datas = results.data;
				$.each(datas,function(n,value){
					queryOptions.push({"name":value.sysGroupName,"value":value.sysGroupCode});
				});
				$("#xtcsxgl_query_zdm").selectList({
					borderWidth : 1,
					borderColor : '#999',
					width:150,
					optionWidth:155,
					optionHeight:150,
					overflowY:'auto',
					options: queryOptions
				})
			},"");
			
			/*下拉列表*/
			$("#xtcsxgl_queryList_jhzt").selectList({
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
			
			var zdm = $("#xtcsxgl_query_zdm").val();
			var csxdm = $("#xtcsxgl_query_csxdm").val();
			var jhzt = $("#xtcsxgl_queryList_jhzt").attr("optionValue");
			// 查询条件
			var queryConditon = {"sysGroupCode":zdm,"sysItemsKey":csxdm,"isActive":jhzt};
			
		    gridObj = comm.getCommBsGrid('queryList_xtcsxgl', "xtcsxSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			
		    //查询按钮
			$('#xtcsxgl_query_btn').click(function(){
				var zdm = $("#xtcsxgl_query_zdm").attr("optionValue");
				var csxdm = $("#xtcsxgl_query_csxdm").val();
				var jhzt = $("#xtcsxgl_queryList_jhzt").attr("optionValue");
				// 查询条件
				queryConditon = [
				             {"name":"sysGroupCode","value":zdm},
				             {"name":"sysItemsKey","value":csxdm},
				             {"name":"isActive","value":jhzt}
				             ]
				gridObj = comm.getCommBsGrid('queryList_xtcsxgl', "xtcsxSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			});
			
		},
		opt_add : function(){
			
			$('#xtcsxglDlg').html(xtcsxgl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xtcsxglDlg" ).dialog({
				title:"新增",
				width:"600",
				height:"400",
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
								$("#xtcsxgl_opt_csxdm").attr("title","该参数项代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xtcsxgl_optForm").serializeArray();
							var adm = $("#xtcsxgl_opt_zdm").attr('optionValue');
							var fwjb = $("#xtcsxgl_opt_fwjb").attr('optionValue');
							var jhzt = $("#xtcsxgl_opt_jhzt").attr('optionValue');
//							datas[0].value = adm;
//							datas[4].value = fwjb;
//							datas[5].value = jhzt;
							var param = {};
							param.zdm = adm;
							param.csxdm = datas[1].value;
							param.csxmc = datas[2].value;
							param.csxz = datas[3].value;
							param.fwjb = fwjb;
							param.jhzt = jhzt;
							comm.requestFun("xtcsxAdd",param,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xtcsxgl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});

			/*下拉列表*/
			this.initAarryData();
			
			$("#xtcsxgl_opt_csxdm").change(function(){
				var csxdm = $("#xtcsxgl_opt_csxdm").val();
				var data = {"sysItemsKey":csxdm};
				comm.validateDatas("xtcsxSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						flag = false;
						$("#xtcsxgl_opt_csxdm").attr("title","该参数项代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xtcsxgl_opt_csxdm").attr("title","").tooltip().tooltip("destroy");
					}
				},"");
			});
		},
		
		option_edit : function(obj){
			$('#xtcsxglDlg').html(xtcsxgl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#xtcsxglDlg" ).dialog({
				title:"修改",
				width:"600",
				height:"400",
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
								$("#xtcsxgl_opt_csxdm").attr("title","该参数项代码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#xtcsxgl_optForm").serializeArray();
							var adm = $("#xtcsxgl_opt_zdm").attr('optionValue');
							var fwjb = $("#xtcsxgl_opt_fwjb").attr('optionValue');
							var jhzt = $("#xtcsxgl_opt_jhzt").attr('optionValue');
							var sysItemsCode = obj.sysItemsCode;
							datas[0].value = adm;
							datas[4].value = fwjb;
							datas[5].value = jhzt;
							datas.push({"name":"sysItemsCode","value":sysItemsCode});
							comm.requestFun("xtcsxUpdate",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_xtcsxgl_pt_refreshPage').click();
							},"");
						}
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			
			/*下拉列表*/
			this.initAarryData(obj);
			
			/*表单值初始化*/
			$('#xtcsxgl_opt_csxdm').val(obj.sysItemsKey);
			$('#xtcsxgl_opt_csxmc').val(obj.sysItemsName);
			$('#xtcsxgl_opt_csxz').val(obj.sysItemsValue);
			var cursysItemsKey = obj.sysItemsKey;
			var fwjb = obj.accessLevel;
			switch(fwjb){
					case 0:
						$('#xtcsxgl_opt_fwjb').val("不可见；如密码，证书等");
						break;
					case 1:
						$('#xtcsxgl_opt_fwjb').val("可查看不可修改");
						break;
					case 2:
						$('#xtcsxgl_opt_fwjb').val("可修改不可删除");
						break;
					case 3:
						$('#xtcsxgl_opt_fwjb').val("可修改可删除");
						break;
					default:
						//$('#xtcsxgl_opt_fwjb').val("可修改可删除");
						break;
			}
			var isActive = obj.isActive;
			if(isActive == 'Y'){
				$('#xtcsxgl_opt_jhzt').val("激活");
			}else{
				$('#xtcsxgl_opt_jhzt').val("禁用");
			};
			
			$("#xtcsxgl_opt_csxdm").change(function(){
				var csxdm = $("#xtcsxgl_opt_csxdm").val();
				var data = {"sysItemsKey":csxdm};
				comm.validateDatas("xtcsxSearch",data,function(results){
					var totalRows = results.totalRows;
					
					if(totalRows !=null && totalRows != '' && totalRows >0){
						var key = results.data[0].sysItemsKey;
						if(key == cursysItemsKey){
							flag = true; 
							$("#xtcsxgl_opt_csxdm").attr("title","").tooltip().tooltip("destroy");
							return ;
						}
						flag = false;
						$("#xtcsxgl_opt_csxdm").attr("title","该参数项代码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#xtcsxgl_opt_csxdm").attr("title","").tooltip().tooltip("destroy");
					}
				},"");
			});
			
		},
		
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '6'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '6'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var sysItemsCode = obj.sysItemsCode;
							var sysGroupCode = obj.sysGroupCode;
							var sysItemsKey = obj.sysItemsKey;
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							       {"name":"sysItemsCode","value":sysItemsCode},
							       {"name":"sysGroupCode","value":sysGroupCode},
							       {"name":"sysItemsKey","value":sysItemsKey},
								   {"name":"curPage","value":curPage},
							       {"name":"pageSize","value":pageSize}
							    ];
							comm.requestFun("xtcsxDelete",datas,function(obj){
								$('#queryList_xtcsxgl_pt_refreshPage').click();
							},"");
						}
					});
				}.bind(this) ) ;
			}
			
			return opt_btn;
		}.bind(this),
		
		detail : function(record, rowIndex, colIndex, options){
			var opt_btn = null;
			if(colIndex == '4'){
				var val = gridObj.getColumnValue(rowIndex, 'accessLevel');
				switch(val){
					case '0':
						opt_btn = '不可见；如密码，证书等';
						break;
					case '1':
						opt_btn = '可查看不可修改';
						break;
					case '2':
						opt_btn = '可修改不可删除';
						break;
					case '3':
						opt_btn = '可修改可删除';
						break;
				}
			}
			if(colIndex == '5'){
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
				formID : '#xtcsxgl_optForm',
				left : 270,
				top : -1,
				rules : {
					xtcsxgl_opt_zdm : {
						required : true	
					},
					xtcsxgl_opt_csxdm : {
						required : true	
					},
					xtcsxgl_opt_csxmc : {
						required : true	
					},
					xtcsxgl_opt_csxz : {
						required : true	
					},
					xtcsxgl_opt_jhzt : {
						required : true	
					},
					xtcsxgl_opt_fwjb : {
						required : true	
					}
				},
				messages : {
					xtcsxgl_opt_zdm : {
						required : '必填'
					},
					xtcsxgl_opt_csxdm : {
						required : '必填'
					},
					xtcsxgl_opt_csxmc : {
						required : '必填'
					},
					xtcsxgl_opt_csxz : {
						required : '必填'
					},
					xtcsxgl_opt_jhzt : {
						required : '必填'
					},
					xtcsxgl_opt_fwjb : {
						required : '必填'
					}
				}
			});
		},
		
		initAarryData : function(obj){
			$("#xtcsxgl_opt_jhzt").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			$("#xtcsxgl_opt_fwjb").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'不可见;如密码，证书等',value:'0'},
					{name:'可查看，不可修改',value:'1'},
					{name:'可修改，不可删除',value:'2'},
					{name:'可修改，可删除',value:'3'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			var options = [];
			var datas = [{"name":"noPageSearchFlag","value":"true"}];
			comm.validateDatas("xtcszSearch",datas,function(results){
				var datas = results.data;
				$.each(datas,function(n,value){
					options.push({"name":value.sysGroupName,"value":value.sysGroupCode});
				});
				$("#xtcsxgl_opt_zdm").selectList({
					width:250,
					optionWidth:255,
					optionHeight:150,
					overflowY:'auto',
					options: options
				}).change(function(e){
					console.log($(this).val() );
				});
				if(obj != null){
					$('#xtcsxgl_opt_zdm').selectListValue(obj.sysGroupCode);
				}
				},"");
		}
	}
}); 

 