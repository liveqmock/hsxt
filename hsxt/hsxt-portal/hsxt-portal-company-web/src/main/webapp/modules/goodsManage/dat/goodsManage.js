define(function () {
	return {
		//得到商品分类的分类树
		seachSpflTree : function(data,callback){
			comm.Request({url : 'query_spfl_tree', domain : 'companyWeb'},{
				data : data,
				success :function(response){
					callback(response);
				},
				error : function(response) {
					alert(comm.lang("accountManage").requestError);
				}
			});
		},
		//查询商品分类关联的商品
		seachAssociateGoods : function(data,callback){
			comm.Request({url : 'query_spfl_goods', domain : 'companyWeb'},{
				data : data,
				success :function(response){
					callback(response);
				},
				error : function(response) {
					alert(comm.lang("accountManage").requestError);
				}
			});
		}
	};
});