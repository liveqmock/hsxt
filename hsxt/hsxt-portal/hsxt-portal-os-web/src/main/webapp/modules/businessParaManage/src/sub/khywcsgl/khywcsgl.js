define(['text!businessParaManageTpl/sub/khywcsgl/khywcsgl.html',
		'text!businessParaManageTpl/sub/khywcsgl/khywcsgl_opt.html'
		],function( khywcsglTpl, khywcsgl_optTpl ){
	
		var self = null;
		var gridObj = null;
		var flag = true;
	return {
 
		showPage : function(tabid){
			
			self = this;
			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(khywcsglTpl )) ;
			
			$('#khywcsgl_add_btn').click(function(){
				self.opt_add();	
			});
		 	
			/*下拉列表*/
			$("#khywcsgl_query_jhzt").selectList({
				borderWidth : 1,
				borderColor : '#999',
				options:[
				    {name:'全部',value:''},
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val());
			});
			
			var custId = $("#khywcsgl_query_custId").val();
			var hsh = $("#khywcsgl_query_hsh").val();
			var csxmc = $("#khywcsgl_query_csxmc").val();
			var jhzt = $("#khywcsgl_query_jhzt").attr("optionValue");
			// 查询条件
			var queryConditon = {"custId":custId,"hsReNo":hsh,"csxmc":csxmc,"isActive":jhzt};
			
		    gridObj = comm.getCommBsGrid('queryList_khywcsgl', "khcsglSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			
		    //查询按钮
			$('#khywcsgl_query_btn').click(function(){
				var custId = $("#khywcsgl_query_custId").val();
				var hsh = $("#khywcsgl_query_hsh").val();
				var csxmc = $("#khywcsgl_query_csxmc").val();
				var jhzt = $("#khywcsgl_query_jhzt").attr("optionValue");
				// 查询条件
				queryConditon = [
				             {"name":"custId","value":custId},
				             {"name":"hsReNo","value":hsh},
				             {"name":"csxmc","value":csxmc},
				             {"name":"isActive","value":jhzt}
				             ]
				gridObj = comm.getCommBsGrid('queryList_khywcsgl', "khcsglSearch", queryConditon,null,self.detail,self.opt_edit,null,self.opt_delete);
			});
		},
		
		opt_add : function(){
			$('#khywcsglDlg').html(khywcsgl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#khywcsglDlg" ).dialog({
				title:"新增",
				width:"600",
				height:"450",
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
								$("#khywcsgl_opt_khqjbm").attr("title","该客户全局编码数据库已经存在").tooltip({
									position:{
										my:"left+"+'30'+" top+"+'32',
										at:"left top"	
									}
								}).tooltip("open");
								return;
							}
							var datas = $("#khywcsgl_optForm").serializeArray();
							var jhzt = $("#khywcsgl_opt_jhzt").attr('optionValue');
							datas[7].value = jhzt;
							comm.requestFun("khcsglAdd",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_khywcsgl_pt_refreshPage').click();
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
			
			$("#khywcsgl_opt_khqjbm").change(function(){
				var khqjbm = $("#khywcsgl_opt_khqjbm").val();
				var data = {"custId":khqjbm};
				comm.validateDatas("khcsglSearch",data,function(results){
					var totalRows = results.totalRows;
					if(totalRows !=null && totalRows != '' && totalRows >0){
						flag = false;
						$("#khywcsgl_opt_khqjbm").attr("title","该客户全局编码数据库已经存在").tooltip({
							position:{
								my:"left+"+'30'+" top+"+'32',
								at:"left top"	
							}
						}).tooltip("open");
					}else{
						flag = true; 
						$("#khywcsgl_opt_khqjbm").attr("title","").tooltip().tooltip("destroy");
					}
				},"");
			});
		},
		
		option_edit : function(obj){
			$('#khywcsglDlg').html(khywcsgl_optTpl);	
			var that = this;
			/*弹出框*/
			$( "#khywcsglDlg" ).dialog({
				title:"修改",
				width:"600",
				height:"450",
				modal:true,
				closeIcon : false,
				buttons:{ 
					"保存":function(){
						var el = this;
						if (!that.addValidate()) {
							return;
						}
						else{
							var datas = $("#khywcsgl_optForm").serializeArray();
							var jhzt = $("#khywcsgl_opt_jhzt").attr('optionValue');
							var custItemsId = obj.custItemsId;
							datas[7].value = jhzt;
							datas.push({"name":"custItemsId","value":custItemsId});
							comm.requestFun("khcsglUpdate",datas,function(obj){
								$(el).dialog("destroy");
								$('#queryList_khywcsgl_pt_refreshPage').click();
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
			
			/*表单值初始化*/
			$('#khywcsgl_opt_khqjbm').val(obj.custId);
			$('#khywcsgl_opt_khmc').val(obj.custName);
			$('#khywcsgl_opt_hsh').val(obj.hsResNo);
			$('#khywcsgl_opt_zdm').val(obj.sysGroupCode);
			$('#khywcsgl_opt_csxjm').val(obj.sysItemsKey);
			$('#khywcsgl_opt_csxmc').val(obj.sysItemsName);
			$('#khywcsgl_opt_csxz').val(obj.sysItemsValue);
			var isActive = obj.isActive;
			if(isActive == 'Y'){
				$('#khywcsgl_opt_jhzt').val("激活");
			}else{
				$('#khywcsgl_opt_jhzt').val("禁用");
			};
			$("#khywcsgl_opt_khqjbm").attr("readonly","readonly");
			
		},
		
		opt_edit : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '8'){
				
				opt_btn = $('<a>修改</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					self.option_edit(obj);
					
				}.bind(this) ) ;
				
			}
			
			return opt_btn;
		}.bind(this),
		
		opt_delete : function(record, rowIndex, colIndex, options){
			
			var opt_btn = null;
			if(colIndex == '8'){
				opt_btn = $('<a>删除</a>').click(function(e) {
					
					var obj = gridObj.getRecord(rowIndex);
					var flag = comm.confirm({
						imgFlag : true,
						imgClass : 'tips_ques',
						content : '确定删除该条记录？',
						callOk : function(){
							var custItemsId = obj.custItemsId;
							var custId = obj.custId;
							var sysItemsKey = obj.sysItemsKey;
							var curPage = gridObj.options.curPage;
							var pageSize = gridObj.options.settings.pageSize;
							var datas = [
							       {"name":"custItemsId","value":custItemsId},
							       {"name":"custId","value":custId},
							       {"name":"sysItemsKey","value":sysItemsKey},
								   {"name":"curPage","value":curPage},
							       {"name":"pageSize","value":pageSize}
							    ];
							comm.requestFun("khcsglDelete",datas,function(obj){
								$('#queryList_khywcsgl_pt_refreshPage').click();
							},"");
						}
					});
				}.bind(this) ) ;
			}
			
			return opt_btn;
		}.bind(this),
		
		detail : function(record, rowIndex, colIndex, options){
			var opt_btn = null;
			
			if(colIndex == '7'){
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
				formID : '#khywcsgl_optForm',
				left : 270,
				top : -1,
				rules : {
					khywcsgl_opt_khqjbm : {
						required : true	
					},
					khywcsgl_opt_zdm : {
						required : true	
					},
					khywcsgl_opt_csxjm : {
						required : true	
					},
					khywcsgl_opt_csxmc : {
						required : true	
					},
					khywcsgl_opt_csxz : {
						required : true	
					},
					khywcsgl_opt_jhzt : {
						required : true	
					}
				},
				messages : {
					khywcsgl_opt_khqjbm : {
						required : '必填'
					},
					khywcsgl_opt_zdm : {
						required : '必填'
					},
					khywcsgl_opt_csxjm : {
						required : '必填'
					},
					khywcsgl_opt_csxmc : {
						required : '必填'
					},
					khywcsgl_opt_csxz : {
						required : '必填'
					},
					khywcsgl_opt_jhzt : {
						required : '必填'
					}
				}
			});
		},
		
		initAarryData : function(){
			$("#khywcsgl_opt_jhzt").selectList({
				width:250,
				optionWidth:255,
				options:[
					{name:'激活',value:'Y'},
					{name:'禁用',value:'N'}
				]
			}).change(function(e){
				console.log($(this).val() );
			});
			
			var options = [];
			var conditons = [{"name":"noPageSearchFlag","value":"true"}];
			comm.validateDatas("xtcszSearch",conditons,function(results){
				var datas = results.data;
				$.each(datas,function(n,value){
					options.push({"name":value.sysGroupCode,"value":value.sysGroupCode});
				});
				$("#khywcsgl_opt_zdm").selectList({
					width:250,
					optionWidth:255,
					optionHeight:150,
					overflowY:'auto',
					options: options
				}).change(function(e){
					$("#khywcsgl_opt_csxjm").val('').attr('optionvalue', '');
					$("#khywcsgl_opt_csxmc").val('');
					$("#khywcsgl_opt_csxz").val('');
					console.log($(this).val() );
					var code = $(this).val();
					var data = {"sysGroupCode":code,"noPageSearchFlag":"true"};
					var keyOptions = [];
					comm.validateDatas("xtcsxSearch",data,function(results){
						var datas = results.data;
						if(datas.length == 0){
							$("#khywcsgl_opt_csxjm").attr("title","该系统参数组没有对应的参数项值").tooltip({
								position:{
									my:"left+"+'30'+" top+"+'32',
									at:"left top"	
								}
							}).tooltip("open");
						}else{
							$("#khywcsgl_opt_csxjm").attr("title","").tooltip().tooltip("destroy");
						}
						$.each(datas,function(n,value){
							keyOptions.push({"name":value.sysItemsKey,"value":value.sysItemsKey});
						});
						$("#khywcsgl_opt_csxjm").removeClass('inputForm-text').selectList({
							width:250,
							optionWidth:255,
							optionHeight:150,
							overflowY:'auto',
							options: keyOptions
						}).change(function(e){
							console.log($(this).val() );
							var sysItemsKey = $("#khywcsgl_opt_csxjm").val();
							var data = {"sysItemsKey":sysItemsKey};
							comm.validateDatas("xtcsxSearch",data,function(results){
								var sysItemsName = results.data[0].sysItemsName;
								var sysItemsValue = results.data[0].sysItemsValue;
								$("#khywcsgl_opt_csxmc").val(sysItemsName);
								$("#khywcsgl_opt_csxz").val(sysItemsValue);
							},"");
						});
						},"");
					});
				},"");
		}
	}
}); 

 