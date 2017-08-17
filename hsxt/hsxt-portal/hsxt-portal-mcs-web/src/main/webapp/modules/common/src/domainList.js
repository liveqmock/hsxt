define(function () {
    comm.domainList = {
        "local": "/",
        //管理公司web访问
  //      "mcsWeb": "/mcsWeb/",  //整合nginx及接入层
  //      "apsWeb": "../apsWeb/",  //整合nginx及接入层 -管理公司呼叫中心调用 leiyt 2016-03-25
        "apsWeb" : "http://192.168.41.16:8085/hsxt-access-web-aps/",  //本地开发
        'hsim':'/hsim/',
        'xmpp':'/xmpp',
        'xmppTs':'/xmppTs',
        'tfs':"/tfs",
        "mcsWeb" : "http://192.168.41.16:8080/hsxt-access-web-mcs/",//本地开发
        "fsServerUrl": "http://192.168.229.27:8080/hsi-fs-server/fs/download/",
        "quitUrl": "http://hsxt.dev.gy.com:9300"  //用户登出页面
    };
    return comm.domainList;
});

