define([
"text!scoremanageTpl/jfflff/jfflffjlcx.html",
"text!scoremanageTpl/jfflff/ff.html",
'scoremanageDat/pointWelfare',
'scoremanageLan'
],function(jfflffjlcxTpl,ffTpl,pointWelfare){
 
	var self = {
			showPage : function(){
				$('#busibox').html(_.template(jfflffjlcxTpl));	
				/** 查询事件 */
				$("#qry_jfflff").click(function(){
					self.pageQuery();
				});
			},
		/** 分页查询 */
		pageQuery:function(){
			var welfareType=$(".selectList-active").attr("data-Value");
			//查询参数
			var queryParam={
							"search_proposerResNo":$("#proposerResNo").val().trim(),	//申请人互生号
							"search_proposerName":$("#proposerName").val().trim(),	//申请人姓名
							"search_proposerPapersNo":$("#proposerPapersNo").val().trim()		//申请人证件号码
						};
			var gridObj= pointWelfare.listHistoryGrant("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex == 3){
					record['welfareTypeStr'] = comm.getNameByEnumId(record['welfareType'], {0:'互生意外伤害保障',1:'互生医疗补贴计划',2:'代他人申请身故保障金'});
					return comm.getNameByEnumId(record['welfareType'], {0:'互生意外伤害保障',1:'互生医疗补贴计划',2:'代他人申请身故保障金'});
				}
				if(colIndex == 5){
					return comm.formatMoneyNumberAps(record["subsidyBalance"]);
				}
				if(colIndex == 6){
					return comm.formatMoneyNumberAps(record["hsPayAmount"]);
				}
				var link1 = $('<a>查看</a>').click(function(e) {
					self.chaKan(record,'查看');
				}) ;
				return   link1 ;
			});
		},
		chaKan : function(record, str){
			$('#ywshbtff_detail').html(_.template(ffTpl,{record:record, str:str}));
			$('#ywshbtff_detail').removeClass('none');
			$('#jfflffjlcxTpl').addClass('none');
			$('#ffTpl').removeClass('none');
			$('#busibox').addClass('none');
			
			comm.liActive_add($('#ck'));
			//返回按钮
			$('#ck_back').click(function(){
				//隐藏头部菜单
				$('#ywshbtff_detail').addClass('none');
				$('#ffTpl').addClass('none');
				$('#jfflffjlcxTpl').removeClass('none');
				$('#busibox').removeClass('none');
				$('#ck').addClass("tabNone").find('a').removeClass('active');
				$('#jfflffjlcx').find('a').addClass('active');
			});
		}
	} ;
	return self;
});