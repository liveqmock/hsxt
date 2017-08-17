define(['text!resouceManageTpl/qyzygl/tgqyzyyl.html',
		'resouceManageSrc/qyzygl/qyxx/qyxx_tab',
		"resouceManageDat/qyzygl/qyzygl",
		"resouceManageLan"
		],function(tgqyzyylTpl, qyxx_tab,qyzygl){
	var self= {
		entType:"3", //企业类型:托管公司
		showPage : function(){
			$('#busibox').html(_.template(tgqyzyylTpl)) ;			 
			//条件查询
			$("#btnQuery").click(function(){
				self.pageQuery();
			});
		},
		/** 查看企业详情 */
		chaKan:function(custId){
			//获取企业明细
			qyxx_tab.getEntDetail(custId,self.entType,function(data){
				self.showTpl($('#ckqyxxTpl'));
				qyxx_tab.showPage(data);
					
				$('#back_btn').click(function(){
					self.showTpl($('#tgqyzyylTpl'));
				});
			});
		},
		showTpl : function(tplObj){
			$('#tgqyzyylTpl, #ckqyxxTpl').addClass('none');
			tplObj.removeClass('none');
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {"search_contacts":$("#txtContacts").val(),"search_custType":self.entType,
					"search_queryResNo":$("#txtEntResNo").val(),"search_queryResNoName":$("#txtEntName").val()};
			qyzygl.entResPage(param,function(record, rowIndex, colIndex, options){
				if(colIndex==6){
					return comm.lang("resouceManage").realnameAuthEnum[record["realnameAuth"]];
				}
				if(colIndex==7){
					return comm.lang("resouceManage").entStatusEnum[record["participationScore"]];
				}
				var link1 = $('<a>查看</a>').click(function(e) {
					self.chaKan(record["custId"]);
				}) ;
				return link1;
			});
		}
	};
	return self;
}); 

 