define(['text!accountManageTpl/jfzh/census/jfhsfp.html',
        'text!accountManageTpl/jfzh/mxcx_xq_jfhsfp.html',
        'accountManageDat/jfzh/jfzh',
        "accountManageDat/accountManage",
        'accountManageSrc/jfzh/tab'], function(tpl,mxcx_xq_jfhsfpTpl,jfzh,accountManage,tab){
	return jfhsfp={
		showPage : function(){
			$('#busibox').html(tpl);
			//日期控件
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			//查询单击事件
			$('#scpoint_dj_searchBtn').click(function(){
				var params = {
						search_beginDate : comm.removeNull($("#search_startDate").val()),
						search_endDate : comm.removeNull($("#search_endDate").val())
					};
				if(!comm.queryDateVaild('jfhsfp_form').form()){return;}
				jfzh.queryMlmListPage("tableDetail",params,jfhsfp.grid);
			});
		
		},
		grid : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}else if(colIndex == 1){
				return record.calcStartTime+' - '+record.calcEndTime;
			}
			else if(colIndex == 2){
				return comm.formatMoneyNumber(record.points);
			}
			else if(colIndex == 3){
				return comm.formatMoneyNumber(record.totalRow);
			}
			var link1 =  $('<a>查看</a>').click(function(e) {
				var param = {
						transNo:record.totalId,
						transType:'U16000'
				};
				accountManage.getAccOptDetailed(param,function(resp){
					if(resp){
						var obj = resp.data.data;
						obj.details[0].date=comm.subStringDate(obj.details[0].date,10);
						if(comm.isNotEmpty(obj)){
							$('#busibox').addClass('none');
							$('#busibox_xq').removeClass('none');
							$('#busibox_xq').html(_.template(mxcx_xq_jfhsfpTpl,obj));
							$('#jfzh_xq_xhjffp').css('display','');
							$('#jfzh_xq_xhjffp').find('a').addClass('active');	
							$('#census_jfhsfp').find('a').removeClass('active');
							
							$("#mxcx_xq_btn_fh").on('click', function(){
								//隐藏头部菜单
								comm.liActive($('#census_jfhsfp'));
								$('#jfzh_xq_xhjffp').css('display','none');
								$('#busibox').removeClass('none');
								$('#busibox_xq').addClass('none');
							});
						}else{
							comm.yes_alert(comm.lang("accountManage").dataNotExist);
						}
					}
				});
			});
			return link1;
		}
	};
});