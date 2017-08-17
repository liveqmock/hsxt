define(['text!infoManageTpl/zyxxbgsp/fwgszyxxbgsp.html',
        'infoManageDat/infoManage',
		'commDat/common'], function(fwgszyxxbgspTpl,infoManage,common){
	var fwgszyxxbgspPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(fwgszyxxbgspTpl));
			
			/*日期控件*/
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#status",comm.lang("infoManage").importantStatus);
			
			//初始化国家、省份、城市缓存
			cacheUtil.findProvCity();
			
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_entType : '4',							//企业类型 2:成员,3:托管,4:服务
						search_entResNo : $("#entResNo").val(),			//消费者互生号
						search_entName : $("#entName").val(),			//消费者名称
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_startDate : $("#search_startDate").val(), //开始时间
						search_status : $("#status").attr("optionvalue")//状态
				};
				fwgszyxxbgspPage.gridObj = comm.getCommBsGrid("searchTable","entimportantinfochange_apprlist",params,comm.lang("infoManage"),fwgszyxxbgspPage.detail,fwgszyxxbgspPage.query);
				
			});			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4)
			{
				return comm.getRegionByCode(record.countryNo,record.provinceNo,record.cityNo,"-");
			}
			if(colIndex == 6)
			{
				return comm.lang("infoManage").importantStatus[record.status];
			}
			else if(colIndex  == 7)
			{
				
				var link1 = null;
				if(record.status == '0')
				{
					link1 = $('<a>'+comm.lang("infoManage").apprval+'</a>').click(function(e) {
						
						fwgszyxxbgspPage.shenPi(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '3' || record.status == '2')
				{
					link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {
						
						fwgszyxxbgspPage.chaKan(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '1')
				{
					link1 = $('<a>'+comm.lang("infoManage").fuhe+'</a>').click(function(e) {
						
						fwgszyxxbgspPage.spfh(record);
						
					}.bind(this) ) ;
				}
				return link1;
			}
			else if(colIndex == 8)
			{
				var link =  $('<a>'+comm.lang("infoManage").workTaskRefuseAccept+'</a>').click(function(e) {
                    comm.confirm({
                            imgFlag : true,
                            width:500,
                            imgClass : 'tips_i',
                            content : comm.lang("infoManage").wtServiceInfoChangeRefuseConfirm,
                            callOk : function(){
                                    var param={"bizNo":record.applyId};
                                    common.workTaskRefuseAccept(param,function(rsp){
                                            //成功
                                            comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
                                            	fwgszyxxbgspPage.gridObj.refreshPage();
                                            }});
                                    });
                            }        
                    });
	            }.bind(this));
				
	            return link;
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
				
	            return comm.workflow_operate(comm.lang("infoManage").workTaskHangUp, comm.lang("infoManage").wtServiceInfoChangePauseConfirm, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                            gdgq.guaQi(record.applyId,fwgszyxxbgspPage.gridObj);
                    });
                });
			}
		},
		shenPi : function(obj){
			fwgszyxxbgspPage.requireSubTab(obj);
		},
		chaKan : function(obj){
			fwgszyxxbgspPage.requireSubTab(obj);
		},
		spfh : function(obj){
			fwgszyxxbgspPage.requireSubTab(obj);
		},
		requireSubTab : function(record){
			
			infoManage.queryEntimportantinfochange({applyId : record.applyId},function(res){
				var obj = res.data;
				obj.status = record.status;
				obj.applyId = record.applyId;
				require(['infoManageSrc/zyxxbgsp/sub_tab'], function(tab){
					tab.showPage(obj);
				});	
			});
		}
		
	};
	return fwgszyxxbgspPage
});