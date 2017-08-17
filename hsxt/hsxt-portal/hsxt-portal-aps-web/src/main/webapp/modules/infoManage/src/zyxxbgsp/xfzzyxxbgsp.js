define(['text!infoManageTpl/zyxxbgsp/xfzzyxxbgsp.html',
        'infoManageDat/infoManage',
		'commDat/common'], function(xfzzyxxbgspTpl,infoManage,common){
	var xfzzyxxbgspPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(xfzzyxxbgspTpl));
			
			/*日期控件*/
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#status",comm.lang("infoManage").importantStatus);
			
			
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_entResNo : $("#searchEntResNo").val(),			//消费者互生号
						search_entName : $("#searchentName").val(),			//消费者名称
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_startDate : $("#search_startDate").val(), //开始时间
						search_status : $("#status").attr("optionvalue")//状态
				};
				xfzzyxxbgspPage.gridObj = comm.getCommBsGrid("searchTable","perimportantinfochange_apprlist",params,comm.lang("infoManage"),xfzzyxxbgspPage.detail,xfzzyxxbgspPage.query);
				
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 6)
			{
				return comm.lang("infoManage").importantStatus[record.status];
			}
			else if(colIndex  == 8)
			{
				
				var link1 = null;
				if(record.status == '0')
				{
					link1 = $('<a>'+comm.lang("infoManage").apprval+'</a>').click(function(e) {
						
						xfzzyxxbgspPage.shenPi(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '3' || record.status == '2')
				{
					link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
						
						xfzzyxxbgspPage.chaKan(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '1')
				{
					link1 = $('<a>'+comm.lang("infoManage").fuhe+'</a>').click(function(e) {
						
						xfzzyxxbgspPage.spfh(record);
						
					}.bind(this) ) ;
				}
				return link1;
			}
			else if(colIndex == 9)
			{
				var link =  $('<a>'+comm.lang("infoManage").workTaskRefuseAccept+'</a>').click(function(e) {
                    comm.confirm({
                            imgFlag : true,
                            width:500,
                            imgClass : 'tips_i',
                            content : comm.lang("infoManage").wtCustInfoChangeRefuseConfirm,
                            callOk : function(){
                                    var param={"bizNo":record.applyId};
                                    common.workTaskRefuseAccept(param,function(rsp){
                                            //成功
                                            comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
                                            	xfzzyxxbgspPage.gridObj.refreshPage();
                                            }});
                                    });
                            }        
                    });
	            }.bind(this));
				
	            return link;
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 9){
				return comm.workflow_operate(comm.lang("infoManage").workTaskHangUp, comm.lang("infoManage").wtCustInfoChangePauseConfirm, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                            gdgq.guaQi(record.applyId,xfzzyxxbgspPage.gridObj);
                    });
                });
			}
		},
		shenPi : function(obj){
			xfzzyxxbgspPage.requireSubTab(obj);
		},
		chaKan : function(obj){
			xfzzyxxbgspPage.requireSubTab(obj);
		},
		spfh : function(obj){
			xfzzyxxbgspPage.requireSubTab(obj);
		},
		requireSubTab : function(record){
			infoManage.queryPerImportantInfo({applyId : record.applyId},function(res){
				var obj = res.data;
				obj.status = record.status;
				obj.applyId = record.applyId;
				require(['infoManageSrc/zyxxbgsp/sub_tab'], function(tab){
					tab.showPage(obj);
				});	
			});
		}
		
	};
	return xfzzyxxbgspPage
});