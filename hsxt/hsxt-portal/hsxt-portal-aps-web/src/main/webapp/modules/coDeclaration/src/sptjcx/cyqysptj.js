define(['text!coDeclarationTpl/sptjcx/cyqysptj.html',
        'coDeclarationDat/sptjcx/cyqysptj',
		'coDeclarationSrc/qyxz/tab',
        'coDeclarationLan'], function(cyqysptjTpl, dataModoule, qysbxzTab){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').empty().html(_.template(cyqysptjTpl));
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			//初始化下拉框
			comm.initSelect('#search_status', comm.lang("coDeclaration").declarationStatusEnum, null, null, {name:'全部', value:''});

			//绑定查询事件
			$('#queryBtn').click(function(){
				self.query();
			});
			comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
			$("#quickDate").change(function(){
				comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
			});
		},
		/**
		 * 查询动作
		 */
		query : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var jsonParam = {
					search_resNo:$("#search_resNo").val(),
					search_entName:$("#search_entName").val(),
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_status:$("#search_status").attr('optionValue'),
					search_custType:2,
				};
			dataModoule.findDeclareStatisticsList(jsonParam, this.detail, this.del);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
			}
			if(record.status == 0){
				return  $('<a id="'+ record['applyId'] +'" >删除</a>').click(function(e) {
					comm.i_confirm(comm.lang("coDeclaration").confirmDel, function(){
						dataModoule.delUnSubmitDeclare({applyId:record['applyId']}, function(res){
							comm.alert({content:comm.lang("coDeclaration").deleteOk, callOk:function(){
								$('#queryBtn').click();
							}});
						});
					}, 400);
				}.bind(this));
			}
			return  $('<a id="'+ record['applyId'] +'" >查看</a>').click(function(e) {
				$("#applyId").val(record['applyId']);
				$("#custType").val("2");
				$('#busibox').addClass('none');
			 	$('#cx_content_detail').removeClass('none');
			 	$('#ckxq_sbxx').click();
			}.bind(this));
		},
		/**
		 * 查看动作
		 */
		del : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return null;
			}
			if(record.status == 0){
				return  $('<a id="'+ record['applyId'] +'" >编辑</a>').click(function(e) {
					dataModoule.findDeclareStep({applyId:record['applyId']}, function(res){
						qysbxzTab.showPage(record['applyId'], res.data, 2);
					});
				}.bind(this));
			}
		}
	}	
});