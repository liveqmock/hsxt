/**
 * 呼叫中心后台请求
 * Created by leiyt on 2016/1/30.
 */
define(function () {
    return {
        //查询消费者所有信息
        getPersonAllInfo : function (params,callback){
            comm.requestFun_CallCenter("getPersonAllInfo", params,callback, comm.lang("common"));
        },
        //查询账户余额信息   param  resNo 互生号   accType 账户类型 积分10110 互生币（流通币20110 定向消费币20120 慈善救助金20130） 货币30110
        getAccountBalance : function (params,callback){
            comm.requestFun_CallCenter("getAccountBalance", params,callback, comm.lang("common"));
        },
        //消费者今日积分查询
        searchPerIntegralByToday : function (params,callback){
            comm.requestFun_CallCenter("searchPerIntegralByToday", params,callback, comm.lang("common"));
        },
        //企业昨日积分查询
        searchEntIntegralByYesterday : function (params,callback){
            comm.requestFun_CallCenter("searchEntIntegralByYesterday", params,callback, comm.lang("common"));
        },
        //投资查询
        findInvestDividendInfo:function(params,callback){
          comm.requestFun_CallCenter("findInvestDividendInfo",params,callback,comm.lang("common"))
        },
        //查询积分福利资格 param resNo
        getWelfareQualify : function (params,callback){
            comm.requestFun_CallCenter("getWelfareQualify", params,callback, comm.lang("common"));
        },
/*        //积分福利信息查询，包括历史保单
        getWelfareList : function (params,callback){
            comm.requestFun_CallCenter("getWelfareList", params,callback, comm.lang("common"));
        },
        //积分福利申请列表查询
        getListWelfareApply : function (params,callback){
            comm.requestFun_CallCenter("getListWelfareApply", params,callback, comm.lang("common"));
        },
        //互生卡补办记录查询
        findCardapplyList : function (params,callback){
            comm.requestFun_CallCenter("findCardapplyList", params,callback, comm.lang("common"));
        },*/
        //积分福利信息查询，包括历史保单
        getWelfareList : function (gridId,params,callback){
            //comm.requestFun("getWelfareList", params,callback, comm.lang("common"));
            comm.getCommBsGrid(gridId,"getWelfareList", params,comm.lang("common"), callback);
        },
        //积分福利申请列表查询
        getListWelfareApply : function (gridId,params,callback){
            //comm.requestFun("getListWelfareApply", params,callback, comm.lang("common"));
            comm.getCommBsGrid(gridId,"getListWelfareApply", params,comm.lang("common"), callback);
        },
        //互生卡补办记录查询
        findCardapplyList : function (gridId,params,callback){
            //comm.requestFun("findCardapplyList", params,callback, comm.lang("common"));
            comm.getCommBsGrid(gridId,"findCardapplyList", params,comm.lang("common"), callback);
        },


        //企业联系信息查询  用于呼叫中心右上角
        searchEntContactInfo : function (params,callback){
            comm.requestFun_CallCenter("searchEntContactInfo", params,callback, comm.lang("common"));
        },
        //企业所有信息查询  用于呼叫中心右上角
        searchEntAllInfo : function (params,callback){
            comm.requestFun_CallCenter("searchEntAllInfo", params,callback, comm.lang("common"));
        },
        //报送服务公司信息查询

        abc:function(gridId, params,detail,del, add, edit){
            return comm.getCommBsGrid(gridId,"abc",params,comm.lang("pointWelfare"),detail,del, add, edit);
        },

        //查询银行账号列表信息
        getBankAccountList : function (params,callback){
            comm.requestFun_CallCenter("getBankAccountList", params,callback, comm.lang("common"));
        },
        //手机号码归属地查询
        getMobilePhoneCity:function(params,callback){
            comm.requestFun_CallCenter("getMobilePhoneCity", params,callback, comm.lang("common"));
        },
        //重要信息变更
        getImptChangeInfo:function(params,callback){
            comm.requestFun_CallCenter("getImptChangeInfo", params,callback, comm.lang("common"));
        },
        //快捷支付卡列表
        getQkBankAccountList:function(params,callback){
            comm.requestFun_CallCenter("getQkBankAccountList", params,callback, comm.lang("common"));
        },
        //操作许可查询
        queryBusinessPmInfo:function(params,callback){
            comm.requestFun_CallCenter("queryBusinessPmInfo", params,callback, comm.lang("common"));
        },
        //呼叫中心报表统计导出
        excelExport:function(params,callback){
            comm.requestFun_CallCenter("excelExport",params,callback,comm.lang("common"));
        },
        //企业服务公司年费信息查询
        getAnnualFeeInfo:function(params,callback){
            comm.requestFun_CallCenter("getAnnualFeeInfo",params,callback,comm.lang("common"));
        }
    };
});