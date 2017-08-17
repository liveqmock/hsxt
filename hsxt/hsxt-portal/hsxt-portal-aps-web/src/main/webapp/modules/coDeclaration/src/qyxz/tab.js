define(['text!coDeclarationTpl/qyxz/tab.html',
		'coDeclarationSrc/qyxz/change_custtype',
		'coDeclarationSrc/qyxz/qygsdjxx',
		'coDeclarationSrc/qyxz/qylxxx',
		'coDeclarationSrc/qyxz/qyyhzhxx',
		'coDeclarationSrc/qyxz/qysczl',
		'coDeclarationDat/qyxz/change_custtype',
		'coDeclarationLan'
		],function(tab, change_custtype, qygsdjxx, qylxxx, qyyhzhxx, qysczl, dataModoule){
	return {	 
		showPage : function(applyId, curStep, custType){
			//curStep = (curStep == 3)?4:curStep;//处理银行账户非必填情况，再次启用银行为必填
			this.initData(applyId, curStep, custType);
			this.initForm();
		},
		/**
		 * 初始化缓存数据
		 */
		initData : function(applyId, curStep, custType){
			comm.delCache("coDeclaration", "entDeclare");
			if(!applyId){
				comm.setCache("coDeclaration", "entDeclare", {"curStep": 0, "isSubmit": false, "applyId": null});
			}else{
				comm.setCache("coDeclaration", "entDeclare", {"curStep": curStep, "isSubmit": false, "applyId": applyId, "custType":custType});
			}
		},
		/**
		 * 初始化页面及事件
		 */
		initForm : function(){
			var self = this;
			$('.operationsInner').html(_.template(tab));
	        /*企业系统注册信息*/
			$('#qyxtzcxx').click(function(e) {
				if (comm.isNotEmpty(change_custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
					comm.confirm({
						imgFlag: true,
						title: comm.lang("coDeclaration").confirmJump,
						content: comm.lang("coDeclaration").giveUpChanged,
						callOk: function () {
							change_custtype.showPage();
							comm.liActive($('#qyxtzcxx'));
							comm.delCache("coDeclaration", "updateFlag");
						},
						callCancel: function () {}
					});
				}else{
					change_custtype.showPage();
					comm.liActive($('#qyxtzcxx'));
				}
            }.bind(this)).click();
			
			/*企业工商登记信息*/
			$('#qygsdjxx').click(function(e) {
				if(this.gotoStep(2)){
					if (comm.isNotEmpty(change_custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
						comm.confirm({
							imgFlag: true,
							title: comm.lang("coDeclaration").confirmJump,
							content: comm.lang("coDeclaration").giveUpChanged,
							callOk: function () {
								qygsdjxx.showPage();
								comm.liActive($('#qygsdjxx'));
								comm.delCache("coDeclaration", "updateFlag");
							},
							callCancel: function () {}
						});
					}else{
						qygsdjxx.showPage();
						comm.liActive($('#qygsdjxx'));
					}
				}
            }.bind(this));
			
			/*企业联系信息*/
			$('#qylxxx').click(function(e) {
				if(this.gotoStep(3)){
					if (comm.isNotEmpty(change_custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
						comm.confirm({
							imgFlag: true,
							title: comm.lang("coDeclaration").confirmJump,
							content: comm.lang("coDeclaration").giveUpChanged,
							callOk: function () {
								qylxxx.showPage();
								comm.liActive($('#qylxxx'));
								comm.delCache("coDeclaration", "updateFlag");
							},
							callCancel: function () {}
						});
					}else{
						qylxxx.showPage();
						comm.liActive($('#qylxxx'));
					}
				}
            }.bind(this));
			
			/*企业银行账户信息*/
			$('#qyyhzhxx').click(function(e) {
				if(this.gotoStep(4)){
					if (comm.isNotEmpty(change_custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
						comm.confirm({
							imgFlag: true,
							title: comm.lang("coDeclaration").confirmJump,
							content: comm.lang("coDeclaration").giveUpChanged,
							callOk: function () {
								qyyhzhxx.showPage();
								comm.liActive($('#qyyhzhxx'));
								comm.delCache("coDeclaration", "updateFlag");
							},
							callCancel: function () {}
						});
					}else{
						qyyhzhxx.showPage();
						comm.liActive($('#qyyhzhxx'));
					}
				}
            }.bind(this));
			
			/*企业上传资料*/
			$('#qysczl').click(function(e) {
				if(this.gotoStep(5)){
					if (comm.isNotEmpty(change_custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
						comm.confirm({
							imgFlag: true,
							title: comm.lang("coDeclaration").confirmJump,
							content: comm.lang("coDeclaration").giveUpChanged,
							callOk: function () {
								qysczl.showPage();
								comm.liActive($('#qysczl'));
								comm.delCache("coDeclaration", "updateFlag");
							},
							callCancel: function () {}
						});
					}else{
						qysczl.showPage();
						comm.liActive($('#qysczl'));
					}
				}
            }.bind(this));
	        
	        //申报提交
			$('#sbSubmit_btn').click(function(){
				if(self.gotoStep(4)){
					var params = {};
					params.applyId = change_custtype.getApplyId();
					comm.confirm({
						imgFlag:true,
						title: comm.lang("coDeclaration").confirmDeclareTitle,
						content : comm.lang("coDeclaration").confirmDeclareContent,
						callOk:function(){
							dataModoule.submitDeclare(params, function(res){
								var entDeclare = comm.getCache("coDeclaration", "entDeclare");
								entDeclare.isSubmit = true;
								comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
									if ($('#050100000000_subNav_050101000000').hasClass('s_hover')){
										$('#050100000000_subNav_050101000000').click();
									} else {
										$('#050100000000_subNav_050103000000').click();
									}
								}});
							});
						},
						callCancel:function(){
							
						}
					});
				}
			})
		},
		/**
		 * 控制步骤
		 * @param toStep 目标步骤
		 */
		gotoStep : function(toStep){
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if((entDeclare.curStep+1) >= toStep){
				return true;
			}
			comm.warn_alert(comm.lang("coDeclaration").entDeclareStepEnum[entDeclare.curStep+1]);
			return false;
		}
	}
}); 

