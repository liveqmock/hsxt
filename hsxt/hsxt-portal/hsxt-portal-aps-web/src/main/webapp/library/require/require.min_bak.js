/*
 RequireJS 2.1.5 Copyright (c) 2010-2012, The Dojo Foundation All Rights Reserved.
 Available via the MIT or new BSD license. 
 */
/*  modified by 万华成 
 *  mvc框架版本维护信息
 *	2015.03.02  添加combobox插件功能
 *  2015.03.03  修改了语言国际化功能
 *  2015.03.10  selectBox新增超长加省略符且tooltip
 *  2015.03.27  优化远程加载导致配置文件失败的问题
 *  2015.03.28  解决ie6,7,8(JSON)兼容性
 *  2015.03.31  ie8 jqMigrate 弹警告问题
 *  2015.04.08  formatDate在ie中不兼容问题
 *  2015.04.09  dialog点击"X"改为destroy(以前是close)
 *  2015.04.10  validate校验文案规则补充
 *  2015.04.17  validate(解决校验提示语不会更新，残留着上一次的校验结果
 *  2015.04.18  jquery ui dialog (禁用esc键，因esc关闭可能导致dialog未销)
 *  2015.04.27  修正arrFontsize未定义bug
 *  2015.05.08  添加特殊字符替换功能
 *  2015.05.20  解决图片预览插件在ie6,7,8下兼容性问题
 *  2015.06.05  修正日期插件中文默认格式
 *  2015.06.23  新增ztree功能
 *  2015.07.01  新增时分秒选择
 */

var requirejs, require, define,reqConfig ,globalPath,localPath ;

(function(aa) { 
 
	var js=document.scripts ,
   // localPath = null, 
	//globalPath = null,
	remotePath = null,
	arrAreaId = null,
	//全局语言定义reqConfi
	globalLanguage = null ;
	 
	//ie6,7,8 不支持 indexOf
	if (! (Array.prototype.indexOf instanceof Function ))
	{
	  Array.prototype.indexOf = function(elt  )
	  {
		var len = this.length >>> 0;
		var from = Number(arguments[1]) || 0;
		from = (from < 0)
			 ? Math.ceil(from)
			 : Math.floor(from);
		if (from < 0)
		  from += len;
		for (; from < len; from++)
		{
		  if (from in this &&
			  this[from] === elt)
			return from;
		}
		return -1;
	  };
	} 
	 
	if (! (String.prototype.trim  instanceof Function )){
		 String.prototype.trim = function() 
		{ 
		  return this.replace(/(^\s*)|(\s*$)/g, ""); 
		} 	
	}
    
 
	for(var i=js.length;i>0;i--){
	 if(js[i-1].src.indexOf("require.min.js")>-1){
	    
	   localPath=js[i-1].src.substring(0,js[i-1].src.lastIndexOf("/")+1);
	   
	 }
	}
	 
	// 全局路径定义
	globalPath = localPath.replace('library/require/','') ;
	 
	  
	//现有支持的区域,对应国际化文案文件夹（若arrAreaId过多，则可能会在程序中打不到对应的文件夹，则用zh-cn文案）
	arrAreaId= ['zh-cn','zh-tw','en-us','fr-fr','de-de','ja-jp','ko-kr'];
	
	if(window.navigator.appName.toLowerCase()=="microsoft internet explorer")
	{
		  if (window.navigator.userLanguage){
				  //globalLanguage =  window.navigator.userLanguage.substr(0,2) ;
				  globalLanguage =  window.navigator.userLanguage.toLowerCase()  ;
		  } else if  (window.navigator.browserLanguage) {
				  //globalLanguage = window.navigator.browserLanguage.substr(0,2) ;		
				  globalLanguage = window.navigator.browserLanguage.toLowerCase() ;				  
		  } else {
				  globalLanguage = 'zh-cn' ;
		  }             
		  //alert(window.navigator.browserLanguage);
	}
	else if(window.navigator.appName.toLowerCase()=="netscape" || window.navigator.appName.toLowerCase()=="firefox")
	{ 
		 globalLanguage = window.navigator.language.toLowerCase()  ;
	} 
	
	
	 
	arrAreaId.indexOf(globalLanguage) === -1 ? globalLanguage ='zh-cn' :'';
	
	   
	//配置通用路径
	
	reqConfig = {
		version:'2015.07.01',		
		baseUrl : globalPath,
		//urlArgs: "clear=" + (new Date()).getTime() ,
		config: {
			text: {
			  useXhr: function (url, protocol, hostname, port) {
				return true;
			  }
			}
		},
		
		shim : {
			'jquery' : {		 
				exports : '$'
			},
			'underscore' : {
				exports : '_'
			},
			'jqueryui' : {
				deps : ['jquery' ],
				exports : 'jqueryui'
			},
			'jquerymigrate':{
				deps : ['jquery'],
				exports : 'jquerymigrate'	
			},
			'xheditor' : {
				deps : ['jquerymigrate' ],
				exports : 'xheditor'	
			},
			'xheditor_cn' : {
				deps : [  'xheditor' ],
				exports : 'xheditor_cn'	
			},
			'jquerycookie' : {
				deps : ['jquery' ],
				exports : 'jquerycookie'	
			},
			'jqueryslide':{
				deps : ['jquery' ],
				exports : 'jqueryslide'	
			},
			'jqueryalert' : {
				deps : ['jquery' ],
				exports : 'jqueryalert'	
			},
			'jqueryjscrollpane' :{
				deps : ['jquery' ],
				exports : 'jqueryjscrollpane'	
			},
			'jquerymousewheel' : {
				deps : ['jquery' ],
				exports : 'jquerymousewheel'	
			},
			'jquerydataTables':{
				deps : ['jquery' ],
				exports : 'jquerydataTables'
			},
			'jqueryselectbox' : {
				deps : ['jquery' ],
				exports : 'jqueryselectbox'	
			},
			'jquerycustomer':{
				deps : ['jquery','jqueryui' ],
				exports : 'jquerycustomer'	
			},
			
			'jqueryvalidate' : {
				deps : ['jquery' ],
				exports : 'jqueryvalidate'	
			},
			'additionalmethods' : {
				deps : ['jquery','jqueryvalidate'  ],			 
				exports : 'jqueryvalidate'	
			},
			'jqueryuploadPreview' : {
				deps : ['jquery' ],
				exports : 'jqueryuploadPreview'	
			},
			'jquerycutimage' : {
				deps : ['jqueryuploadPreview' ],
				exports : 'jquerycutimage'	
			},
			'jqueryajaxfileupload' : {
				deps : ['jquery' ],
				exports : 'jqueryajaxfileupload'	
			},
			'jqzoom' : {
				deps : ['jquery' ],
				exports : 'jqzoom'
			},
			'jquerytreeview' : {
				deps : ['jquery','jquerycookie' ],
				exports : 'jquerytreeview'	
					
			},
			'jqueryztree' : {
				deps : ['jquery'  ],
				exports : 'jqueryztree'	
			},
			'jquerytimepicker' : {
				deps : ['jqueryui' ],
				exports : 'jquerytimepicker'	
			},
			
			'jquerydialogr' : {
				//deps : ['jquery' ,'jqueryui' ,'typeface','museo_700' ], //typeface在ie7有问题，去掉可以用
				deps : ['jquery' ,'jqueryui'],
				exports :'jquerydialogr'
				
			},
			'localStorage' : {
				deps : ["storeJson2","json"] ,
				exports : "localStorage"		
			},
			'bsgrid' : {
				deps : ['jquery'] ,
				exports : "bsgrid"	
			} ,	
			
			'GY' : {
				 deps : ['jquery','jqueryui','jquerycookie','jquerytimepicker','bsgrid',
						 'jqueryjscrollpane','jquerymousewheel','jqueryselectbox','jquerydataTables',
						 'jqueryvalidate','additionalmethods','jquerycustomer','jqueryuploadPreview','jqzoom',
						 'jquerycutimage',
						 'jqueryajaxfileupload', 'underscore','commSrc/comm','commSrc/base',"localStorage","xheditor_cn"],
				 exports : 'GY'	
			}
		}, 
		
		//加载css插件 
		map: {
		  '*': {
				 'css': 'library/require/css'
		  }
		}, 
		  
		paths : {
			
			text : 'library/jquery/text',
			async : 'library/require/async',
			jquery : 'library/jquery/jquery.min',
			//jqueryui : 'library/jquery/jquery-ui.min',
			jqueryui : 'library/jquery/jquery-ui.min',
			
			xheditor : 'library/plugins/xheditor/xheditor-1.2.1.min',
			xheditor_cn : 'library/plugins/xheditor/xheditor_lang/zh-cn',
			jquerymigrate :'library/plugins/xheditor/jquery/jquery-migrate-1.2.1',
			underscore  : 'library/underscore/underscore' ,
			//plugins
			jquerycookie: 'library/plugins/jquery.cookie' ,
			jqueryslide :  'library/plugins/jquery.flexslider' ,
			jquerydataTables :  'library/plugins/jquery.dataTables.min' ,
			jquerycustomer : 'library/plugins/jquery.customer',
			jqueryalert : 'library/plugins/jquery.alert',
			jqueryjscrollpane : 'library/plugins/jquery.jscrollpane.min',
			jquerymousewheel : 'library/plugins/jquery.mousewheel',
			jqueryselectbox : 'library/plugins/jquery.selectbox',
			additionalmethods : 'library/plugins/additional-methods',
			jqueryvalidate : 'library/plugins/jquery.validate',	
			jqueryuploadPreview : 'library/plugins/jquery.uploadPreview',
			jquerycutimage : 'library/plugins/jquery.imgareaselect',
			jqueryajaxfileupload : 'library/plugins/jquery.ajaxfileupload',
			jquerytreeview : 'library/plugins/jquery.treeview',
			jqueryztree : 'library/plugins/jquery.ztree.min' , 
			jqzoom : 'library/plugins/jquery.jqzoom',
			jquerytimepicker : 'library/plugins/jquery.timepicker',
			//dialogr
			jquerydialogr : 'library/plugins/dialogr/ui.dialogr',
			typeface : 'library/plugins/dialogr/typeface-0.14',
			museo_700: 'library/plugins/dialogr/museo_700.typeface',
			json  : 'library/plugins/localStorage/json' ,
			storeJson2 : 'library/plugins/localStorage/store_json2.min',
			localStorage : 'library/plugins/localStorage/store',
			
			bsgrid : 'library/plugins/bsgrid.all.min',
			
			GY : 'library/gym/gym' ,
			 
			
			// 公共模块路径
			commSrc :  'modules/common/src',
			commTpl :  'modules/common/tpl',
			commCss :  'modules/common/css',
			commDat :  'modules/common/dat',
			commLan :  'modules/common/lan/'+globalLanguage +"/lang"
		}  ,
		
		getLocalFlag : function(){
			//判断是否在本地环境
			var host = location.host ;	
			var localFlag = host.indexOf( 'localhost' )!=-1 || host.indexOf('192.168.1')!=-1 || host.indexOf('127.0.0')!=-1 ;
			//return localFlag;
			return false;
		},
		
		setLocalPath : function(moduleName,agentName) {		
			if (agentName && !this.getLocalFlag()  ) {
				//设置远程路径 
				this.paths[agentName+ '_' +moduleName+"Src"] =   agentName+ "/modules/"+	moduleName +"/src";
				this.paths[agentName+ '_' +moduleName+"Tpl"] =   agentName+ "/modules/"+	moduleName +"/tpl";	
				this.paths[agentName+ '_' +moduleName+"Css"] =   agentName+ "/modules/"+	moduleName +"/src";
				this.paths[agentName+ '_' +moduleName+"Dat"] =   agentName+ "/modules/"+	moduleName +"/tpl";	
				this.paths[agentName+ '_' +moduleName+"Lan"] =   agentName+ "/modules/"+	moduleName +"/lan/"+ globalLanguage +"/lang" ;
			} else {
				//设置本地路径
				this.paths[moduleName+"Src"] = "modules/"+	moduleName +"/src";
				this.paths[moduleName+"Tpl"] = "modules/"+	moduleName +"/tpl";
				this.paths[moduleName+"Css"] = "modules/"+	moduleName +"/css";
				this.paths[moduleName+"Dat"] = "modules/"+	moduleName +"/dat";
				this.paths[moduleName+"Lan"] = "modules/"+	moduleName +"/lan/"+ globalLanguage +"/lang" ;
			}
		} ,
		
		setAgentPath : function(agentName,moduleName) {
			//设置远程路径 
			this.paths[agentName+ '_' +moduleName+"Src"] =   agentName+ "/modules/"+	moduleName +"/src";
			this.paths[agentName+ '_' +moduleName+"Tpl"] =   agentName+ "/modules/"+	moduleName +"/tpl";	
			this.paths[agentName+ '_' +moduleName+"Css"] =   agentName+ "/modules/"+	moduleName +"/src";
			this.paths[agentName+ '_' +moduleName+"Dat"] =   agentName+ "/modules/"+	moduleName +"/tpl";	
			this.paths[agentName+ '_' +moduleName+"Lan"] =   agentName+ "/modules/"+	moduleName +"/lan/"+ globalLanguage +"/lang" ;		
		}
		
		
	} ;
	
	 
	 
	 
	 
	function I(b) {
		return "[object Function]" === L.call(b)
	}
	function J(b) {
		return "[object Array]" === L.call(b)
	}
	function y(b, c) {
		if (b) {
			var d;
			for (d = 0; d < b.length && (!b[d] || !c(b[d], d, b)); d += 1);
		}
	}
	function M(b, c) {
		if (b) {
			var d;
			for (d = b.length - 1; - 1 < d && (!b[d] || !c(b[d], d, b)); d -= 1);
		}
	}
	function s(b, c) {
		return ga.call(b, c)
	}
	function m(b, c) {
		return s(b, c) && b[c]
	}
	function G(b, c) {
		for (var d in b) if (s(b, d) && c(b[d], d)) break
	}
	function R(b, c, d, m) {
		c && G(c, function(c, j) {
			if (d || !s(b, j)) m && "string" !== typeof c ? (b[j] || (b[j] = {}), R(b[j], c, d, m)) : b[j] = c
		});
		return b
	}
	function u(b, c) {
		return function() {
			return c.apply(b, arguments)
		}
	}
	function ba(b) {
		if (!b) return b;
		var c = aa;
		y(b.split("."), function(b) {
			c = c[b]
		});
		return c
	}
	function B(b, c, d, m) {
		c = Error(c + "\nhttp://requirejs.org/docs/errors.html#" + b);
		c.requireType = b;
		c.requireModules = m;
		d && (c.originalError = d);
		return c
	}
	function ha(b) {
		function c(a, f, b) {
			var e, n, c, g, d, S, i, h = f && f.split("/");
			e = h;
			var j = k.map,
				l = j && j["*"];
			if (a && "." === a.charAt(0)) if (f) {
				e = m(k.pkgs, f) ? h = [f] : h.slice(0, h.length - 1);
				f = a = e.concat(a.split("/"));
				for (e = 0; f[e]; e += 1) if (n = f[e], "." === n) f.splice(e, 1), e -= 1;
				else if (".." === n) if (1 === e && (".." === f[2] || ".." === f[0])) break;
				else 0 < e && (f.splice(e - 1, 2), e -= 2);
				e = m(k.pkgs, f = a[0]);
				a = a.join("/");
				e && a === f + "/" + e.main && (a = f)
			} else 0 === a.indexOf("./") && (a = a.substring(2));
			if (b && j && (h || l)) {
				f = a.split("/");
				for (e = f.length; 0 < e; e -= 1) {
					c = f.slice(0, e).join("/");
					if (h) for (n = h.length; 0 < n; n -= 1) if (b = m(j, h.slice(0, n).join("/"))) if (b = m(b, c)) {
						g = b;
						d = e;
						break
					}
					if (g) break;
					!S && (l && m(l, c)) && (S = m(l, c), i = e)
				}!g && S && (g = S, d = i);
				g && (f.splice(0, d, g), a = f.join("/"))
			}
			return a
		}
		function d(a) {
			A && y(document.getElementsByTagName("script"), function(f) {
				if (f.getAttribute("data-requiremodule") === a && f.getAttribute("data-requirecontext") === i.contextName) return f.parentNode.removeChild(f), !0
			})
		}
		function z(a) {
			var f = m(k.paths, a);
			if (f && J(f) && 1 < f.length) return d(a), f.shift(), i.require.undef(a), i.require([a]), !0
		}
		function h(a) {
			var f, b = a ? a.indexOf("!") : -1; - 1 < b && (f = a.substring(0, b), a = a.substring(b + 1, a.length));
			return [f, a]
		}
		function j(a, f, b, e) {
			var n, C, g = null,
				d = f ? f.name : null,
				j = a,
				l = !0,
				k = "";
			a || (l = !1, a = "_@r" + (M += 1));
			a = h(a);
			g = a[0];
			a = a[1];
			g && (g = c(g, d, e), C = m(q, g));
			a && (g ? k = C && C.normalize ? C.normalize(a, function(a) {
				return c(a, d, e)
			}) : c(a, d, e) : (k = c(a, d, e), a = h(k), g = a[0], k = a[1], b = !0, n = i.nameToUrl(k)));
			b = g && !C && !b ? "_unnormalized" + (Q += 1) : "";
			return {
				prefix: g,
				name: k,
				parentMap: f,
				unnormalized: !! b,
				url: n,
				originalName: j,
				isDefine: l,
				id: (g ? g + "!" + k : k) + b
			}
		}
		function r(a) {
			var f = a.id,
				b = m(p, f);
			b || (b = p[f] = new i.Module(a));
			return b
		}
		function t(a, f, b) {
			var e = a.id,
				n = m(p, e);
			if (s(q, e) && (!n || n.defineEmitComplete))"defined" === f && b(q[e]);
			else r(a).on(f, b)
		}
		function v(a, f) {
			var b = a.requireModules,
				e = !1;
			if (f) f(a);
			else if (y(b, function(f) {
				if (f = m(p, f)) f.error = a, f.events.error && (e = !0, f.emit("error", a))
			}), !e) l.onError(a)
		}
		function w() {
			T.length && (ia.apply(H, [H.length - 1, 0].concat(T)), T = [])
		}
		function x(a) {
			delete p[a];
			delete V[a]
		}
		function F(a, f, b) {
			var e = a.map.id;
			a.error ? a.emit("error", a.error) : (f[e] = !0, y(a.depMaps, function(e, c) {
				var g = e.id,
					d = m(p, g);
				d && (!a.depMatched[c] && !b[g]) && (m(f, g) ? (a.defineDep(c, q[g]), a.check()) : F(d, f, b))
			}), b[e] = !0)
		}

		function D() {
			var a, f, b, e, n = (b = 1E3 * k.waitSeconds) && i.startTime + b < (new Date).getTime(),
				c = [],
				g = [],
				h = !1,
				j = !0;
			if (!W) {
				W = !0;
				G(V, function(b) {
					a = b.map;
					f = a.id;
					if (b.enabled && (a.isDefine || g.push(b), !b.error)) if (!b.inited && n) z(f) ? h = e = !0 : (c.push(f), d(f));
					else if (!b.inited && (b.fetched && a.isDefine) && (h = !0, !a.prefix)) return j = !1
				});
				if (n && c.length) return b = B("timeout", "Load timeout for modules: " + c, null, c), b.contextName = i.contextName, v(b);
				j && y(g, function(a) {
					F(a, {}, {})
				});
				if ((!n || e) && h) if ((A || da) && !X) X = setTimeout(function() {
					X = 0;
					D()
				}, 50);
				W = !1
			}
		}
		function E(a) {
			s(q, a[0]) || r(j(a[0], null, !0)).init(a[1], a[2])
		}
		function K(a) {
			var a = a.currentTarget || a.srcElement,
				b = i.onScriptLoad;
			a.detachEvent && !Y ? a.detachEvent("onreadystatechange", b) : a.removeEventListener("load", b, !1);
			b = i.onScriptError;
			(!a.detachEvent || Y) && a.removeEventListener("error", b, !1);
			return {
				node: a,
				id: a && a.getAttribute("data-requiremodule")
			}
		}
		function L() {
			var a;
			for (w(); H.length;) {
				a = H.shift();
				if (null === a[0]) return v(B("mismatch", "Mismatched anonymous define() module: " + a[a.length - 1]));
				E(a)
			}
		}
		var W, Z, i, N, X, k = {
			waitSeconds: 7,
			baseUrl: "./",
			paths: {},
			pkgs: {},
			shim: {},
			config: {}
		},
			p = {},
			V = {},
			$ = {},
			H = [],
			q = {},
			U = {},
			M = 1,
			Q = 1;
		N = {
			require: function(a) {
				return a.require ? a.require : a.require = i.makeRequire(a.map)
			},
			exports: function(a) {
				a.usingExports = !0;
				if (a.map.isDefine) return a.exports ? a.exports : a.exports = q[a.map.id] = {}
			},
			module: function(a) {
				return a.module ? a.module : a.module = {
					id: a.map.id,
					uri: a.map.url,
					config: function() {
						return k.config && m(k.config, a.map.id) || {}
					},
					exports: q[a.map.id]
				}
			}
		};
		Z = function(a) {
			this.events = m($, a.id) || {};
			this.map = a;
			this.shim = m(k.shim, a.id);
			this.depExports = [];
			this.depMaps = [];
			this.depMatched = [];
			this.pluginMaps = {};
			this.depCount = 0
		};
		Z.prototype = {
			init: function(a, b, c, e) {
				e = e || {};
				if (!this.inited) {
					this.factory = b;
					if (c) this.on("error", c);
					else this.events.error && (c = u(this, function(a) {
						this.emit("error", a)
					}));
					this.depMaps = a && a.slice(0);
					this.errback = c;
					this.inited = !0;
					this.ignore = e.ignore;
					e.enabled || this.enabled ? this.enable() : this.check()
				}
			},
			defineDep: function(a, b) {
				this.depMatched[a] || (this.depMatched[a] = !0, this.depCount -= 1, this.depExports[a] = b)
			},
			fetch: function() {
				if (!this.fetched) {
					this.fetched = !0;
					i.startTime = (new Date).getTime();
					var a = this.map;
					if (this.shim) i.makeRequire(this.map, {
						enableBuildCallback: !0
					})(this.shim.deps || [], u(this, function() {
						return a.prefix ? this.callPlugin() : this.load()
					}));
					else return a.prefix ? this.callPlugin() : this.load()
				}
			},
			load: function() {
				var a = this.map.url;
				U[a] || (U[a] = !0, i.load(this.map.id, a))
			},
			check: function() {
				if (this.enabled && !this.enabling) {
					var a, b, c = this.map.id;
					b = this.depExports;
					var e = this.exports,
						n = this.factory;
					if (this.inited) if (this.error) this.emit("error", this.error);
					else {
						if (!this.defining) {
							this.defining = !0;
							if (1 > this.depCount && !this.defined) {
								if (I(n)) {
									if (this.events.error) try {
										e = i.execCb(c, n, b, e)
									} catch (d) {
										a = d
									} else e = i.execCb(c, n, b, e);
									this.map.isDefine && ((b = this.module) && void 0 !== b.exports && b.exports !== this.exports ? e = b.exports : void 0 === e && this.usingExports && (e = this.exports));
									if (a) return a.requireMap = this.map, a.requireModules = [this.map.id], a.requireType = "define", v(this.error = a)
								} else e = n;
								this.exports = e;
								if (this.map.isDefine && !this.ignore && (q[c] = e, l.onResourceLoad)) l.onResourceLoad(i, this.map, this.depMaps);
								x(c);
								this.defined = !0
							}
							this.defining = !1;
							this.defined && !this.defineEmitted && (this.defineEmitted = !0, this.emit("defined", this.exports), this.defineEmitComplete = !0)
						}
					} else this.fetch()
				}
			},
			callPlugin: function() {
				var a = this.map,
					b = a.id,
					d = j(a.prefix);
				this.depMaps.push(d);
				t(d, "defined", u(this, function(e) {
					var n, d;
					d = this.map.name;
					var g = this.map.parentMap ? this.map.parentMap.name : null,
						h = i.makeRequire(a.parentMap, {
							enableBuildCallback: !0
						});
					if (this.map.unnormalized) {
						if (e.normalize && (d = e.normalize(d, function(a) {
							return c(a, g, !0)
						}) || ""), e = j(a.prefix + "!" + d, this.map.parentMap), t(e, "defined", u(this, function(a) {
							this.init([], function() {
								return a
							}, null, {
								enabled: !0,
								ignore: !0
							})
						})), d = m(p, e.id)) {
							this.depMaps.push(e);
							if (this.events.error) d.on("error", u(this, function(a) {
								this.emit("error", a)
							}));
							d.enable()
						}
					} else n = u(this, function(a) {
						this.init([], function() {
							return a
						}, null, {
							enabled: !0
						})
					}), n.error = u(this, function(a) {
						this.inited = !0;
						this.error = a;
						a.requireModules = [b];
						G(p, function(a) {
							0 === a.map.id.indexOf(b + "_unnormalized") && x(a.map.id)
						});
						v(a)
					}), n.fromText = u(this, function(e, c) {
						var d = a.name,
							g = j(d),
							C = O;
						c && (e = c);
						C && (O = !1);
						r(g);
						s(k.config, b) && (k.config[d] = k.config[b]);
						try {
							l.exec(e)
						} catch (ca) {
							return v(B("fromtexteval", "fromText eval for " + b + " failed: " + ca, ca, [b]))
						}
						C && (O = !0);
						this.depMaps.push(g);
						i.completeLoad(d);
						h([d], n)
					}), e.load(a.name, h, n, k)
				}));
				i.enable(d, this);
				this.pluginMaps[d.id] = d
			},
			enable: function() {
				V[this.map.id] = this;
				this.enabling = this.enabled = !0;
				y(this.depMaps, u(this, function(a, b) {
					var c, e;
					if ("string" === typeof a) {
						a = j(a, this.map.isDefine ? this.map : this.map.parentMap, !1, !this.skipMap);
						this.depMaps[b] = a;
						if (c = m(N, a.id)) {
							this.depExports[b] = c(this);
							return
						}
						this.depCount += 1;
						t(a, "defined", u(this, function(a) {
							this.defineDep(b, a);
							this.check()
						}));
						this.errback && t(a, "error", this.errback)
					}
					c = a.id;
					e = p[c];
					!s(N, c) && (e && !e.enabled) && i.enable(a, this)
				}));
				G(this.pluginMaps, u(this, function(a) {
					var b = m(p, a.id);
					b && !b.enabled && i.enable(a, this)
				}));
				this.enabling = !1;
				this.check()
			},
			on: function(a, b) {
				var c = this.events[a];
				c || (c = this.events[a] = []);
				c.push(b)
			},
			emit: function(a, b) {
				y(this.events[a], function(a) {
					a(b)
				});
				"error" === a && delete this.events[a]
			}
		};
		i = {
			config: k,
			contextName: b,
			registry: p,
			defined: q,
			urlFetched: U,
			defQueue: H,
			Module: Z,
			makeModuleMap: j,
			nextTick: l.nextTick,
			onError: v,
			configure: function(a) {
				a.baseUrl && "/" !== a.baseUrl.charAt(a.baseUrl.length - 1) && (a.baseUrl += "/");
				var b = k.pkgs,
					c = k.shim,
					e = {
						paths: !0,
						config: !0,
						map: !0
					};
				G(a, function(a, b) {
					e[b] ? "map" === b ? (k.map || (k.map = {}), R(k[b], a, !0, !0)) : R(k[b], a, !0) : k[b] = a
				});
				a.shim && (G(a.shim, function(a, b) {
					J(a) && (a = {
						deps: a
					});
					if ((a.exports || a.init) && !a.exportsFn) a.exportsFn = i.makeShimExports(a);
					c[b] = a
				}), k.shim = c);
				a.packages && (y(a.packages, function(a) {
					a = "string" === typeof a ? {
						name: a
					} : a;
					b[a.name] = {
						name: a.name,
						location: a.location || a.name,
						main: (a.main || "main").replace(ja, "").replace(ea, "")
					}
				}), k.pkgs = b);
				G(p, function(a, b) {
					!a.inited && !a.map.unnormalized && (a.map = j(b))
				});
				if (a.deps || a.callback) i.require(a.deps || [], a.callback)
			},
			makeShimExports: function(a) {
				return function() {
					var b;
					a.init && (b = a.init.apply(aa, arguments));
					return b || a.exports && ba(a.exports)
				}
			},
			makeRequire: function(a, f) {
				function d(e, c, h) {
					var g, k;
					f.enableBuildCallback && (c && I(c)) && (c.__requireJsBuild = !0);
					if ("string" === typeof e) {
						if (I(c)) return v(B("requireargs", "Invalid require call"), h);
						if (a && s(N, e)) return N[e](p[a.id]);
						if (l.get) return l.get(i, e, a, d);
						g = j(e, a, !1, !0);
						g = g.id;
						return !s(q, g) ? v(B("notloaded", 'Module name "' + g + '" has not been loaded yet for context: ' + b + (a ? "" : ". Use require([])"))) : q[g]
					}
					L();
					i.nextTick(function() {
						L();
						k = r(j(null, a));
						k.skipMap = f.skipMap;
						k.init(e, c, h, {
							enabled: !0
						});
						D()
					});
					return d
				}
				f = f || {};
				R(d, {
					isBrowser: A,
					/*  2015/1/7
					 *  万华成改
					 *  修正原因：处理本地开发及生产跨域
					 *
					toUrl: function(b) {
						var d, f = b.lastIndexOf("."),
							g = b.split("/")[0];
						if (-1 !== f && (!("." === g || ".." === g) || 1 < f)) d = b.substring(f, b.length), b = b.substring(0, f);
						return i.nameToUrl(c(b, a && a.id, !0), d, !0)
					},
					 */
					toUrl: function (moduleNamePlusExt) {
                        var ext, index = moduleNamePlusExt.lastIndexOf('.') , currHref = location.href ;
					 
                        var segment = moduleNamePlusExt.split('/')[0] ;
					 
							 
							 segment = 	segment.split('_')[1];
							
						 
						
                        var isRelative = segment === '.' || segment === '..';
							 

                        //Have a file extension alias, and it is not the
                        //dots from a relative path.
                        if (index !== -1 && (!isRelative || index > 1)) {
                            ext = moduleNamePlusExt.substring(index, moduleNamePlusExt.length);
                            moduleNamePlusExt = moduleNamePlusExt.substring(0, index);
                        }

                        return i.nameToUrl(c(moduleNamePlusExt,
                                                a && a.id, true), ext,  true);
                    },
					 
					defined: function(b) {
						return s(q, j(b, a, !1, !0).id)
					},
					specified: function(b) {
						b = j(b, a, !1, !0).id;
						return s(q, b) || s(p, b)
					}
				});
				a || (d.undef = function(b) {
					w();
					var c = j(b, a, !0),
						d = m(p, b);
					delete q[b];
					delete U[c.url];
					delete $[b];
					d && (d.events.defined && ($[b] = d.events), x(b))
				});
				return d
			},
			enable: function(a) {
				m(p, a.id) && r(a).enable()
			},
			completeLoad: function(a) {
				var b, c, e = m(k.shim, a) || {},
					d = e.exports;
				for (w(); H.length;) {
					c = H.shift();
					if (null === c[0]) {
						c[0] = a;
						if (b) break;
						b = !0
					} else c[0] === a && (b = !0);
					E(c)
				}
				c = m(p, a);
				if (!b && !s(q, a) && c && !c.inited) {
					if (k.enforceDefine && (!d || !ba(d))) return z(a) ? void 0 : v(B("nodefine", "No define call for " + a, null, [a]));
					E([a, e.deps || [], e.exportsFn])
				}
				D()
			},
			nameToUrl: function(a, b, c) {
				
				var e, d, h, g, j, i;
				if (l.jsExtRegExp.test(a)) g = a + (b || "");
				else {
					e = k.paths;
					d = k.pkgs;
					g = a.split("/");
					
					for (j = g.length; 0 < j; j -= 1) {						 
						 
						if (i = g.slice(0, j).join("/"), h = m(d, i), i = m(e, i) ) {
							 
							J(i) && (i = i[0]);
							g.splice(0, j, i);
							break
						} else if (h) {
							a = a === h.name ? h.location + "/" + h.main : h.location;
							g.splice(0, j, a);
							break
						}
						
						/*whc begin*/
						if (i = g.slice(1, j).join("/"), h = m(d, i), i = m(e, i) ) {
							 
							J(i) && (i = i[0]);
							g.splice(0, j,g[0]+'/'+ i);
							break
						} else if (h) {
							a = a === h.name ? h.location + "/" + h.main : h.location;
							g.splice(0, j, a);
							break
						}
						/*whc end*/
					
					}
					g = g.join("/");
					g += b || (/\?/.test(g) || c ? "" : ".js");
					g = ("/" === g.charAt(0) || g.match(/^[\w\+\.\-]+:/) ? "" : k.baseUrl) + g
				}
				return k.urlArgs ? g + ((-1 === g.indexOf("?") ? "?" : "&") + k.urlArgs) : g
			},
			load: function(a, b) {
				l.load(i, a, b)
			},
			execCb: function(a, b, c, d) {
				return b.apply(d, c)
			},
			onScriptLoad: function(a) {
				if ("load" === a.type || ka.test((a.currentTarget || a.srcElement).readyState)) P = null, a = K(a), i.completeLoad(a.id)
			},
			onScriptError: function(a) {
				var b = K(a);
				if (!z(b.id)) return v(B("scripterror", "Script error", a, [b.id]))
			}
		};
		i.require = i.makeRequire();
		return i
	}
	var l, w, x, D, t, E, P, K, Q, fa, la = /(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/mg,
		ma = /[^.]\s*require\s*\(\s*["']([^'"\s]+)["']\s*\)/g,
		ea = /\.js$/,
		ja = /^\.\//;
	w = Object.prototype;
	var L = w.toString,
		ga = w.hasOwnProperty,
		ia = Array.prototype.splice,
		A = !! ("undefined" !== typeof window && navigator && document),
		da = !A && "undefined" !== typeof importScripts,
		ka = A && "PLAYSTATION 3" === navigator.platform ? /^complete$/ : /^(complete|loaded)$/,
		Y = "undefined" !== typeof opera && "[object Opera]" === opera.toString(),
		F = {},
		r = {},
		T = [],
		O = !1;
	if ("undefined" === typeof define) {
		if ("undefined" !== typeof requirejs) {
			if (I(requirejs)) return;
			r = requirejs;
			requirejs = void 0
		}
		"undefined" !== typeof require && !I(require) && (r = require, require = void 0);
		l = requirejs = function(b, c, d, z) {
			  //debugger
			/* whc  			
			if (b && b.length > 0){
				for (var i =  0 ;i < b.length ;i++){
					if (reqConfig.getLocalFlag() &&  b[i] && b[i].indexOf("whc_")>-1 ) {
						b[i] = b[i].replace('whc_','')
					}
				}
			}
			*/
			var h, j = "_";
			!J(b) && "string" !== typeof b && (h = b, J(c) ? (b = c, c = d, d = z) : b = []);
			h && h.context && (j = h.context);
			(z = m(F, j)) || (z = F[j] = l.s.newContext(j));
			h && z.configure(h);
			return z.require(b, c, d)
		};
		l.config = function(b) {
			return l(b)
		};
		l.nextTick = "undefined" !== typeof setTimeout ?
		function(b) {
			setTimeout(b, 4)
		} : function(b) {
			b()
		};
		require || (require = l);
		l.version = "2.1.5";
		l.jsExtRegExp = /^\/|:|\?|\.js$/;
		l.isBrowser = A;
		w = l.s = {
			contexts: F,
			newContext: ha
		};
		l({});
		y(["toUrl", "undef", "defined", "specified"], function(b) {
			l[b] = function() {
				var c = F._;
				return c.require[b].apply(c, arguments)
			}
		});
		if (A && (x = w.head = document.getElementsByTagName("head")[0], D = document.getElementsByTagName("base")[0])) x = w.head = D.parentNode;
		l.onError = function(b) {
			throw b;
		};
		l.load = function(b, c, d) {
			var l = b && b.config || {},
				h;
			if (A) return h = l.xhtml ? document.createElementNS("http://www.w3.org/1999/xhtml", "html:script") : document.createElement("script"), h.type = l.scriptType || "text/javascript", h.charset = "utf-8", h.async = !0, h.setAttribute("data-requirecontext", b.contextName), h.setAttribute("data-requiremodule", c), h.attachEvent && !(h.attachEvent.toString && 0 > h.attachEvent.toString().indexOf("[native code")) && !Y ? (O = !0, h.attachEvent("onreadystatechange", b.onScriptLoad)) : (h.addEventListener("load", b.onScriptLoad, !1), h.addEventListener("error", b.onScriptError, !1)), h.src = d, K = h, D ? x.insertBefore(h, D) : x.appendChild(h), K = null, h;
			if (da) try {
				importScripts(d), b.completeLoad(c)
			} catch (j) {
				b.onError(B("importscripts", "importScripts failed for " + c + " at " + d, j, [c]))
			}
		};
		A && M(document.getElementsByTagName("script"), function(b) {
			x || (x = b.parentNode);
			if (t = b.getAttribute("data-main")) return r.baseUrl || (E = t.split("/"), Q = E.pop(), fa = E.length ? E.join("/") + "/" : "./", r.baseUrl = fa, t = Q), t = t.replace(ea, ""), r.deps = r.deps ? r.deps.concat(t) : [t], !0
		});
		define = function(b, c, d) {
			//debugger
			var l, h;
			"string" !== typeof b && (d = c, c = b, b = null);
			J(c) || (d = c, c = []);
			!c.length && I(d) && d.length && (d.toString().replace(la, "").replace(ma, function(b, d) {

				c.push(d)
			}), c = (1 === d.length ? ["require"] : ["require", "exports", "module"]).concat(c));
			if (O) {
				if (!(l = K)) P && "interactive" === P.readyState || M(document.getElementsByTagName("script"), function(b) {
					if ("interactive" === b.readyState) return P = b
				}), l = P;
				l && (b || (b = l.getAttribute("data-requiremodule")), h = F[l.getAttribute("data-requirecontext")])
			}(h ? h.defQueue : T).push([b, c, d])
		};
		define.amd = {
			jQuery: !0
		};
		l.exec = function(b) {
			return eval(b)
		};
		l(r)
	}
	

	
	
	
	
	
})(this);

 
//错误处理
require.onError = function (err) {
	//console.log(err.requireType);
	if (err.requireType === 'timeout') {
		console.log('modules: ' + err.requireModules);
	}
	throw err;
};

 