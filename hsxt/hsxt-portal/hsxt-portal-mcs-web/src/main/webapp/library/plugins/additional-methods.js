 /*  
  *  jQuery Validation Plugin 1.11.1
  * 
  *  修改   by 万华成
  *  2016 -2-2   修正互生号规则 ,银行卡规则
  *  2016 -2-3   未改变内容进行提交 
  *  2016 -2-22  正整数后允许跟.00规则  
  *  2016 -3-08  邮箱禁止输入中文    
  *  2016 -3-10  校验一年范围的日期区间   
  *  2016 -3-16  电话号码规则变更,且手机号和固定电话皆OK
  *  2016 -4-07  互生号后四位可等于0 
  *  016 -4-20  互生号规则为11位数字 
  *  2016 -4-27  身份证规则修改     
  *  2016 -5-3  加入密码不能为111111,或123456的规则
  *  2016 -5-6  电话号码规则加入纯数字(7-20位)
  *  2016-5-9   用户姓名规则
  *  2016-5-19  手机号支持177段 
  *  2016-6-02 护照规则变更,支持全数字
  *  2016-7-01 一年之间的日期区间的规则，若有一项为空，则不校验
  */
 
;eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}(';(t(){t 2f(a){q a.1r(/<.[^<>]*?>/g,\' \').1r(/&80;|&#7S;/7I,\' \').1r(/[.(),;:!?%#$\'"1s+=\\/\\-]*/g,\'\')}u.r.x("7E",t(a,b,c){q Q.1a(b)||2f(a).1B(/\\b\\w+\\b/g).1b<=c},u.r.1D("1l 1v {0} 2L 1X 3p."));u.r.x("7D",t(a,b,c){q Q.1a(b)||2f(a).1B(/\\b\\w+\\b/g).1b>=c},u.r.1D("1l 1v 2k 2m {0} 2L."));u.r.x("7v",t(a,b,c){y d=2f(a);y e=/\\b\\w+\\b/g;q Q.1a(b)||d.1B(e).1b>=c[0]&&d.1B(e).1b<=c[1]},u.r.1D("1l 1v 35 {0} 2x {1} 2L."))}());u.r.x("7t",t(a,b){q Q.1a(b)||/^[a-z\\-.,()\'"\\s]+$/i.O(a)},"2u 1X 7s 2y 27");u.r.x("7r",t(a,b){q Q.1a(b)||/^\\w+$/i.O(a)},"2u, 7q, 2x 7p 2y 27");u.r.x("7o",t(a,b){q Q.1a(b)||/^[a-z]+$/i.O(a)},"2u 2y 27");u.r.x("7n",t(a,b){q Q.1a(b)||/^\\S+$/i.O(a)},"7g 7f 7e 27");u.r.x("7d",t(a,b){q Q.1a(b)||/^75[2-5]\\d\\{2\\}-\\d{4}$/.O(a)},"74 3h-3k 72 71 3n 6Z 6Y 6T-3u 6N 6M-6G-3u");u.r.x("6E",t(a,b){q Q.1a(b)||/\\d{5}-\\d{4}$|^\\d{5}$/.O(a)},"2T 2V 6D 3h 6C 2Z 32");u.r.x("6B",t(a,b){q Q.1a(b)||/^-?\\d+$/.O(a)},"A 6z 1X 6t 6r-6q 1u 27");u.r.x("6p",t(v){I(v.1b!==17){q 1i}y i,n,d,f,2a,28;y a=["A","B","C","D","E","F","G","H","J","K","L","M","N","P","R","S","T","U","V","W","X","Y","Z"];y b=[1,2,3,4,5,6,7,8,1,2,3,4,5,7,9,2,3,4,5,6,7,8,9];y c=[8,7,6,5,4,3,2,10,0,9,8,7,6,5,4,3,2];y e=0;1E(i=0;i<17;i++){f=c[i];d=v.6d(i,i+1);I(i===8){28=d}I(!1W(d)){d*=f}1q{1E(n=0;n<a.1b;n++){I(d.3i()===a[n]){d=b[n];d*=f;I(1W(28)&&n===8){28=a[n]}6b}}}e+=d}2a=e%11;I(2a===10){2a="X"}I(2a===28){q 1k}q 1i},"2T 2V 69 5V 1u (5T) 2Z 32.");u.r.x("5R",t(a,b){y c=1i;y d=/^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$/;I(d.O(a)){y e=a.2j(\'/\');y f=1A(e[0],10);y g=1A(e[1],10);y h=1A(e[2],10);y i=1p 1x(h,g-1,f);I((i.3z()===h)&&(i.3D()===g-1)&&(i.3E()===f)){c=1k}1q{c=1i}}1q{c=1i}q Q.1a(b)||c},"1l 1v a 2S 2n");u.r.x("5P",t(a,b){I(Q.1a(b)){q 1k}I(!(/^([a-1z-2N-9]{4} ){2,8}[a-1z-2N-9]{1,4}|[a-1z-2N-9]{12,34}$/.O(a))){q 1i}y c=a.1r(/ /g,\'\').3i();y d=c.2e(0,2);y e={\'5M\':"\\\\d{8}[\\\\1j-Z]{16}",\'5K\':"\\\\d{8}[\\\\1j-Z]{12}",\'5I\':"\\\\d{16}",\'5E\':"[\\\\1j-Z]{4}\\\\d{20}",\'5D\':"\\\\d{12}",\'5C\':"[A-Z]{4}[\\\\1j-Z]{14}",\'5B\':"\\\\d{16}",\'5A\':"\\\\d{23}[A-Z][\\\\1j-Z]",\'5z\':"[A-Z]{4}\\\\d{6}[\\\\1j-Z]{8}",\'5y\':"\\\\d{17}",\'5v\':"\\\\d{17}",\'5p\':"\\\\d{8}[\\\\1j-Z]{16}",\'5m\':"\\\\d{20}",\'5l\':"\\\\d{14}",\'5j\':"[A-Z]{4}\\\\d{20}",\'5i\':"\\\\d{16}",\'5h\':"\\\\d{14}",\'5g\':"\\\\d{14}",\'5f\':"\\\\d{10}[\\\\1j-Z]{11}\\\\d{2}",\'5e\':"[\\\\1j-Z]{2}\\\\d{16}",\'5d\':"\\\\d{18}",\'5c\':"[A-Z]{4}[\\\\1j-Z]{15}",\'5b\':"\\\\d{7}[\\\\1j-Z]{16}",\'5a\':"\\\\d{14}",\'58\':"[\\\\1j-Z]{4}[\\\\1j-Z]{20}",\'55\':"\\\\d{24}",\'50\':"\\\\d{22}",\'4P\':"[\\\\1j-Z]{4}\\\\d{14}",\'4O\':"\\\\d{19}",\'4N\':"[A-Z]\\\\d{10}[\\\\1j-Z]{12}",\'4J\':"\\\\d{3}[\\\\1j-Z]{13}",\'4I\':"[A-Z]{4}[\\\\1j-Z]{22}",\'4G\':"[A-Z]{4}[\\\\1j-Z]{13}",\'4y\':"\\\\d{4}[\\\\1j-Z]{20}",\'4x\':"\\\\d{5}[\\\\1j-Z]{12}",\'4w\':"\\\\d{16}",\'4u\':"\\\\d{3}[\\\\1j-Z]{13}",\'4t\':"\\\\d{3}[\\\\1j-Z]{10}\\\\d{2}",\'4s\':"[A-Z]{4}\\\\d{5}[\\\\1j-Z]{18}",\'4r\':"\\\\d{23}",\'4q\':"[A-Z]{4}\\\\d{19}[A-Z]{3}",\'4n\':"\\\\d{10}[\\\\1j-Z]{11}\\\\d{2}",\'4k\':"[\\\\1j-Z]{2}\\\\d{18}",\'4j\':"\\\\d{18}",\'4i\':"[A-Z]{4}\\\\d{10}",\'4g\':"\\\\d{11}",\'4f\':"[\\\\1j-Z]{4}\\\\d{16}",\'4c\':"[\\\\1j-Z]{4}\\\\d{21}",\'4b\':"\\\\d{24}",\'49\':"\\\\d{21}",\'46\':"[A-Z]{4}[\\\\1j-Z]{16}",\'45\':"[A-Z]\\\\d{10}[\\\\1j-Z]{12}",\'43\':"\\\\d{2}[\\\\1j-Z]{18}",\'41\':"\\\\d{18}",\'40\':"\\\\d{20}",\'3Z\':"\\\\d{15}",\'3Y\':"\\\\d{20}",\'3X\':"\\\\d{20}",\'3V\':"\\\\d{5}[\\\\1j-Z]{12}",\'3R\':"\\\\d{20}",\'65\':"\\\\d{5}[\\\\1j-Z]{17}",\'3S\':"\\\\d{3}\\\\d{16}",\'3T\':"[A-Z]{4}\\\\d{14}",\'3U\':"[\\\\1j-Z]{4}\\\\d{16}"};y f=e[d];I(1F f!==\'3W\'){y g=1p 1w("^[A-Z]{2}\\\\d{2}"+f+"$","");I(!(g.O(c))){q 1i}}y h=c.2e(4,c.1b)+c.2e(0,4);y j="";y k=1k;y l;1E(y i=0;i<h.1b;i++){l=h.3N(i);I(l!=="0"){k=1i}I(!k){j+="42".1I(l)}}y m=\'\';y n=\'\';1E(y p=0;p<j.1b;p++){y o=j.3N(p);n=\'\'+m+\'\'+o;m=n%4a}q m===1},"1l 1y a 1o 4d");u.r.x("4e",t(a,b){q Q.1a(b)||/^(0?[1-9]|[12]\\d|3[1J])[\\.\\/\\-](0?[1-9]|1[3G])[\\.\\/\\-]([12]\\d)?(\\d\\d)$/.O(a)},"1l 1v a 2S 2n");u.r.x("4h",t(a,b){q Q.1a(b)||/^((\\+|1G(\\s|\\s?\\-\\s?)?)31(\\s|\\s?\\-\\s?)?(\\(0\\)[\\-\\s]?)?|0)[1-9]((\\s|\\s?\\-\\s?)?[0-9]){8}$/.O(a)},"1l 1y a 1o 1Y 1u.");u.r.x("4l",t(a,b){q Q.1a(b)||/^[\\d]{7,20}$/i.O(a)||/^(([\\+]?\\d{2,6}-)|0)?(1[1-9])\\d{9}$|^([\\+]?\\d{2,6}-)?(\\d{3,4}-)(\\d{3,8})$/i.O(a)},"1l 1y a 1o 1Y 1u.");u.r.x("4m",t(a,b){q Q.1a(b)||/^((\\+|1G(\\s|\\s?\\-\\s?)?)31(\\s|\\s?\\-\\s?)?(\\(0\\)[\\-\\s]?)?|0)6((\\s|\\s?\\-\\s?)?[0-9]){8}$/.O(a)},"1l 1y a 1o 3C 1u");u.r.x("4o",t(a,b){q Q.1a(b)||/^[1-9][0-9]{3}\\s?[a-1z-Z]{2}$/.O(a)},"1l 1y a 1o 4p 3k");u.r.x("3B",t(a,b){I(Q.1a(b)){q 1k}I(!(/^[0-9]{9}|([0-9]{2} ){3}[0-9]{3}$/.O(a))){q 1i}y c=a.1r(/ /g,\'\');y d=0;y e=c.1b;1E(y f=0;f<e;f++){y g=e-f;y h=c.2e(f,f+1);d=d+g*h}q d%11===0},"1l 1y a 1o 3A 2G 1u");u.r.x("3y",t(a,b){q Q.1a(b)||/^[0-9]{1,7}$/.O(a)},"1l 1y a 1o 3x 2G 1u");u.r.x("4v",t(a,b){q Q.1a(b)||($.r.3w["3B"].3v(Q,a,b))||($.r.3w["3y"].3v(Q,a,b))},"1l 1y a 1o 3A 1X 3x 2G 1u");u.r.x("2F",t(a,b){q Q.1a(b)||/^([1J]\\d|2[0-3])(:[0-5]\\d){1,2}$/.O(a)},"1l 1v a 1o 2F, 35 1G:1G 2x 23:59");u.r.x("4z",t(a,b){q Q.1a(b)||/^((0?[1-9]|1[3G])(:[0-5]\\d){1,2}(\\ ?[4A]M))$/i.O(a)},"1l 1v a 1o 2F 3n 12-4B 4C/4D 1D");u.r.x("4E",t(a,b){a=a.1r(/\\s+/g,"");q Q.1a(b)||a.1b>9&&a.1B(/^(\\+?1-?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})-?[2-9]\\d{2}-?\\d{4}$/)},"1l 1y a 1o 1Y 1u");u.r.x(\'4F\',t(a,b){a=a.1r(/\\(|\\)|\\s+|-/g,\'\');q Q.1a(b)||a.1b>9&&a.1B(/^(?:(?:(?:1G\\s?|\\+)44\\s?)|(?:\\(?0))(?:\\d{2}\\)?\\s?\\d{4}\\s?\\d{4}|\\d{3}\\)?\\s?\\d{3}\\s?\\d{3,4}|\\d{4}\\)?\\s?(?:\\d{5}|\\d{3}\\s?\\d{3})|\\d{5}\\)?\\s?\\d{4,5})$/)},\'1l 1y a 1o 1Y 1u\');u.r.x(\'4H\',t(a,b){a=a.1r(/\\(|\\)|\\s+|-/g,\'\');q Q.1a(b)||a.1b>9&&a.1B(/^(?:(?:(?:1G\\s?|\\+)44\\s?|0)7(?:[3t]\\d{2}|3r)\\s?\\d{3}\\s?\\d{3})$/)},\'1l 1y a 1o 3C 1u\');u.r.x(\'4K\',t(a,b){a=a.1r(/\\(|\\)|\\s+|-/g,\'\');q Q.1a(b)||a.1b>9&&a.1B(/^(?:(?:(?:1G\\s?|\\+)44\\s?|0)(?:1\\d{8,9}|[23]\\d{9}|7(?:[3t]\\d{8}|3r\\d{6})))$/)},\'1l 1y a 1o 4L 1Y 1u\');u.r.x(\'4M\',t(a,b){q Q.1a(b)||/^((([A-1U-1V][0-9])|([A-1U-1V][0-9][0-9])|([A-1U-1V][A-2B-Y][0-9])|([A-1U-1V][A-2B-Y][0-9][0-9])|([A-1U-1V][0-9][A-4Q])|([A-1U-1V][A-2B-Y][0-9][4R]))\\s?([0-9][4S-4T-4U-Z]{2})|(4V)\\s?(4W))$/i.O(a)},\'1l 1y a 1o 4X 4Y\');u.r.x("4Z",t(a,b,c){q u(a).2i().1b>=c},u.r.1D("1l 1v 2k 2m {0} 51"));u.r.x("52",t(a,b,c){y d=/[\\53-\\54\\1d-\\56\\57-\\1c。，;‘’]/g;I(d.O(a)){q 1i}q Q.1a(b)||/^((([a-z]|\\d|[!#\\$%&\'\\*\\+\\-\\/=\\?\\^1s`{\\|}~]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])+(\\.([a-z]|\\d|[!#\\$%&\'\\*\\+\\-\\/=\\?\\^1s`{\\|}~]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])+)*)|((\\3e)((((\\2q|\\1Z)*(\\2s\\3a))?(\\2q|\\1Z)+)?(([\\39-\\5k\\38\\37\\5n-\\5o\\36]|\\5q|[\\5r-\\5s]|[\\5t-\\5u]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(\\\\([\\39-\\1Z\\38\\37\\2s-\\36]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c]))))*(((\\2q|\\1Z)*(\\2s\\3a))?(\\2q|\\1Z)+)?(\\3e)))@((([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))\\.)+(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))$/i.O(a)},u.r.2t.5w);u.r.x("5x",t(a,b,c){q Q.1a(b)||/^(2v?|2w):\\/\\/(((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))\\.)*(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|[\\2X-\\2W]|\\/|\\?)*)?(#((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$/i.O(a)},u.r.2t.2U);u.r.x("5F",t(a,b,c){I(/[^0-9\\-]+/.O(a)){q 1i}a=a.1r(/\\D/g,"");y d=5G;I(c.5H){d|=2A}I(c.5J){d|=2C}I(c.5L){d|=2I}I(c.5N){d|=2J}I(c.5O){d|=2Q}I(c.5Q){d|=2z}I(c.5S){d|=2r}I(c.5U){d|=2E}I(c.5W){d=2A|2C|2I|2J|2Q|2z|2r|2E}I(d&2A&&/^(5[5X])/.O(a)){q a.1b===16}I(d&2C&&/^(4)/.O(a)){q a.1b===16}I(d&2I&&/^(3[47])/.O(a)){q a.1b===15}I(d&2J&&/^(3(0[5Y]|[68]))/.O(a)){q a.1b===14}I(d&2Q&&/^(2(5Z|61))/.O(a)){q a.1b===15}I(d&2z&&/^(62)/.O(a)){q a.1b===16}I(d&2r&&/^(3)/.O(a)){q a.1b===16}I(d&2r&&/^(63|64)/.O(a)){q a.1b===15}I(d&2E){q 1k}q 1i},"1l 1v a 1o 82 66 1u.");u.r.x("67",t(a,b,c){q Q.1a(b)||/^(25[0-5]|2[0-4]\\d|[1J]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[1J]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[1J]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[1J]?\\d\\d?)$/i.O(a)},"1l 1v a 1o 3m 6a 3j.");u.r.x("6c",t(a,b,c){q Q.1a(b)||/^((([0-1m-1n-f]{1,4}:){7}[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){6}:[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){5}:([0-1m-1n-f]{1,4}:)?[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){4}:([0-1m-1n-f]{1,4}:){0,2}[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){3}:([0-1m-1n-f]{1,4}:){0,3}[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){2}:([0-1m-1n-f]{1,4}:){0,4}[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-1m-1n-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-1m-1n-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-1m-1n-f]{1,4}::([0-1m-1n-f]{1,4}:){0,5}[0-1m-1n-f]{1,4})|(::([0-1m-1n-f]{1,4}:){0,6}[0-1m-1n-f]{1,4})|(([0-1m-1n-f]{1,4}:){1,7}:))$/i.O(a)},"1l 1v a 1o 3m 6e 3j.");u.r.x("6f",t(a,b,c){I(Q.1a(b)){q 1k}I(1F c===\'2R\'){c=1p 1w(\'^(?:\'+c+\')$\')}q c.O(a)},"6g 1D.");u.r.x("6h",t(a,b,c){y d=Q;y e=c[1];y f=$(e,b.2l).3Q(t(){q d.3P(Q)}).1b>=c[0];I(!$(b).1T(\'1S\')){y g=$(e,b.2l);g.1T(\'1S\',1k);g.1o();g.1T(\'1S\',1i)}q f},u.1D("1l 3M 2k 2m {0} 3L 3K 3J."));u.r.x("6i",t(a,b,c){y d=Q,3I=c[0],2O=c[1];y e=$(2O,b.2l).3Q(t(){q d.3P(Q)}).1b;y f=e>=3I||e===0;I(!$(b).1T(\'1S\')){y g=$(2O,b.2l);g.1T(\'1S\',1k);g.1o();g.1T(\'1S\',1i)}q f},u.1D("1l 6j 6k 3K 3J 1X 3M 2k 2m {0} 3L 6l."));u.r.x("6m",t(a,b,c){y d=1F c==="2R"?c.1r(/\\s/g,\'\').1r(/,/g,\'|\'):"6n/*",2K=Q.1a(b),i,2d;I(2K){q 2K}I($(b).6o("3s")==="2d"){d=d.1r(/\\*/g,".*");I(b.2g&&b.2g.1b){1E(i=0;i<b.2g.1b;i++){2d=b.2g[i];I(!2d.3s.1B(1p 1w(".?("+d+")$","i"))){q 1i}}}}q 1k},u.1D("1l 1v a 3f 3d a 1o 6s."));u.r.x("3c",t(a,b,c){c=1F c==="2R"?c.1r(/,/g,\'|\'):"6u|6v?g|6w";q Q.1a(b)||a.1B(1p 1w(".("+c+")$","i"))},u.1D("1l 1v a 3f 3d a 1o 3c."));u.r.x("6x",t(a,b,c){y d=1i;y e=1p 1w(c);q Q.1a(b)||e.O(a)},"请输入正确的格式");u.r.x("2n",t(a,b,c){q Q.1a(b)||/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[6y]|1[2D])-(0[1-9]|[12][0-9]|3[1J]))|((0[6A]|11)-(0[1-9]|[12][0-9]|30))|(2D-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2Y][3O]|[6F][26])|((0[48]|[2Y][3O]|[6H][26])1G))-2D-29)$/i.O(a)},"请输入正确的日期格式(6I-6J-6K)");u.r.x("6L",t(a,b,c){q Q.1a(b)||/^[1-9][0-9]{16}[0-3H]$/i.O(a)||/^[a-1z-Z][\\d]{6}[\\(][\\d][\\)]$/.O(a)||/^[2o][\\/][\\d]{6}[\\/][\\d]$/.O(a)||/^[2o][\\d]{6}[\\(][\\d][\\)]$/.O(a)||/^[a-1z-Z][\\d]{9}$/.O(a)},"请输入正确的身份证号码");u.r.x("6O",t(a,b,c){q Q.1a(b)||/^[1-9][0-9]{16}[0-3H]$/i.O(a)},"请输入中国大陆身份证号码");u.r.x("6P",t(a,b,c){q Q.1a(b)||/^[a-1z-Z][\\d]{6}[\\(][\\d][\\)]$/.O(a)},"请输入香港身份证号码");u.r.x("6Q",t(a,b,c){q Q.1a(b)||/^[2o][\\/][\\d]{6}[\\/][\\d]$/.O(a)||/^[2o][\\d]{6}[\\(][\\d][\\)]$/.O(a)},"请输入澳门身份证号码");u.r.x("6R",t(a,b,c){q Q.1a(b)||/^[a-1z-Z][\\d]{9}$/.O(a)},"请输入台湾身份证号码");u.r.x("6S",t(a,b,c){q Q.1a(b)||/^((\\+?3q)|(\\(\\+3q\\)))?1[1-9]\\d{9}$/i.O(a)},"请输入11位手机号码");u.r.x("6U",t(a,b,c){q Q.1a(b)||/^[0-1m-Z]{9}$/i.O(a)},"请输入真确的护照号码,例：6V");u.r.x("6W",t(a,b,c){q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位资源（互生卡）号");u.r.x("6X",t(a,b,c){I(a.1b===11&&　(1F a=="1u")){y d=(a+\'\').1t(0,2),3o=(a+\'\').1t(2,9);I(d>0&&3o==0){q 1k}1q{q 1i}}1q{q 1i}q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位管理公司资源号");u.r.x("70",t(a,b,c){I(a.1b===11&&　(1F a=="1u")){y d=(a+\'\').1t(0,2),1Q=(a+\'\').1t(2,3),3l=(a+\'\').1t(5,6);I(d>0&&1Q>0&&3l==0){q 1k}1q{q 1i}}1q{q 1i}q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位服务公司资源号");u.r.x("73",t(a,b,c){I(a.1b===11&&　(1F a=="1u")){y d=(a+\'\').1t(0,2),1Q=(a+\'\').1t(2,3),2p=(a+\'\').1t(5,2),2h=(a+\'\').1t(7,4);I(d>0&&1Q>0&&2p>0&&2h==0){q 1k}1q{q 1i}}1q{q 1i}q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位托管企业资源号");u.r.x("76",t(a,b,c){q Q.1a(b)||/^[\\w-]{7,20}$/i.O(a)},"组织代码机构证号输入错误");u.r.x("77",t(a,b,c){q Q.1a(b)||/^[\\w-]{7,20}$/i.O(a)},"纳税人识别号输入错误");u.r.x("78",t(a,b,c){q!/[`~!@#$^&*()+=|[\\]\\{\\}:;\'\\,.<>/?]/i.O(a)},"请输入正确的字符");u.r.x("79",t(a,b,c){I(a.1b===11&&　(1F a=="1u")){y d=(a+\'\').1t(0,2),1Q=(a+\'\').1t(2,3),2p=(a+\'\').1t(5,2),2h=(a+\'\').1t(7,4);I(d>0&&1Q>0&&2p==0&&2h>0){q 1k}1q{q 1i}}1q{q 1i}q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位成员企业资源号");u.r.x("7a",t(a,b,c){q Q.1a(b)||/^[\\d]{11}$/i.O(a)},"请输入11位互生卡号");u.r.x("7b",t(a,b,c){q Q.1a(b)||/^[\\d]{6}$/i.O(a)},"请输入6位登录密码");u.r.x("7c",t(a,b,c){y d=a+\'\',2H=0,2M=0,2P=0,2b=1i;I(/^[\\d]{6}$/i.O(a)){1E(y i=0;i<d.1b;i++){I(d[i]==d[i+1]){2H++}}1E(y i=0;i<d.1b;i++){I(d[i]==d[i+1]*1-1){2M++}}1E(y i=0;i<d.1b;i++){I(d[i]==d[i+1]*1+1){2P++}}I(2H==5||2M==5||2P==5){2b=1k}1q{2b=1i}}1q{2b=1k}q Q.1a(b)||!2b},"密码不规范:数字不能全部重复 。如7h、7i");u.r.x("7j",t(a,b,c){q Q.1a(b)||/^[\\d]{4}$/i.O(a)},"请输入正确的用户名,例如：7k");u.r.x("7l",t(a,b,c){q Q.1a(b)||/^[\\d]{5,30}$/i.O(a)},"请输入5~30位数字");u.r.x("7m",t(a,b,c){y d=1i,1H=-1,1O,1N,1M,1L;I(a.1b<=20&&a.1b>=2){I(a.1I(\'·\')>=1&&a.1I(\'·\')<=19){1H=a.1I(\'·\');1O=a.1t(0,1H);1N=a.1t(1H+1,a.1b-1);1M=1p 1w("^[\\\\1R-\\\\2c]{"+1O.1b+"}$","i");1L=1p 1w("^[\\\\1R-\\\\2c]{"+1N.1b+"}$","i");I(1M.O(1O)&&1L.O(1N)){d=1k}1q{d=1i}}1q I(a.1I(\' \')>=1&&a.1I(\' \')<=19){1H=a.1I(\' \');1O=a.1t(0,1H);1N=a.1t(1H+1,a.1b-1);1M=1p 1w("^[a-1z-Z]{"+1O.1b+"}$","i");1L=1p 1w("^[a-1z-Z]{"+1N.1b+"}$","i");I(1M.O(1O)&&1L.O(1N)){d=1k}1q{d=1i}}1q{1M=1p 1w("^[\\\\1R-\\\\2c]{"+a.1b+"}$","i");1L=1p 1w("^[a-1z-Z]{"+a.1b+"}$","i");I(1M.O(a)||1L.O(a)){d=1k}}}1q{d=1i}q Q.1a(b)||d},"请输入正确的姓名");u.r.x("7u",t(a,b,c){q Q.1a(b)||/^[\\1R-\\3g-1z-Z](\\s*[\\1R-\\3g-1z-Z])*$/i.O(a)},"请输入正确的职业");u.r.x("7w",t(a,b,c){q Q.1a(b)||/^[\\w-]{7,20}$/i.O(a)},"营业执照号输入错误");u.r.x("7x",t(a,b,c){q Q.1a(b)||/^[\\d]{4}$/i.O(a)},"请输入4位动态验证码");u.r.x("7y",t(a,b,c){q Q.1a(b)||/^[\\d]{6,10}$/i.O(a)},"请输入6-10位医保账号");u.r.x("7z",t(a,b,c){q Q.1a(b)||/^[1-9]{1}[0-9]{5}$/i.O(a)},"请输入正确的邮政编码");u.r.x("7A",t(a,b,c){q Q.1a(b)||/^[1-9]{1}[0-9]{5,}$/i.O(a)},"请输入正确的7B号码");$.r.x("7C",t(a,b,c){y d=a;y e=$(c).1K();I(d==""||e=="")q 1k;y f=1p 1w(\'-\',\'g\');d=1p 1x(1x.1P(d.1r(f,"/")));e=1p 1x(1x.1P(e.1r(f,"/")));q d<=e},"开始日期必须小于结束日期");$.r.x("7F",t(a,b,c){y d=$(c).1K();y e=a;I(d==""||e=="")q 1k;y f=1p 1w(\'-\',\'g\');d=1p 1x(1x.1P(d.1r(f,"/")));e=1p 1x(1x.1P(e.1r(f,"/")));q d<=e},"结束日期必须大于开始日期");$.r.x("7G",t(a,b,c){y d=$(c).2i();I(d=="")d=$(c).1K();I(a==""||d==""||1W(a)||1W(d))q 1k;q 1A(a)<=1A(d)},"数值不能大于指定范围");$.r.x("3p",t(a,b,c){y d=$(c).2i();I(d=="")d=$(c).1K();I(a==""||d==""||1W(a)||1W(d))q 1k;q 1A(a)>=1A(d)},"数值不能小于指定范围");$.r.x("7H",t(a,b){I(a%33==0){q 1k}1q{q 1i}},"请填写33的倍数");$.r.x("7J",t(a,b,c){I(c){y d=/^(([1-9]{1}\\d{0,8})|0)(\\.\\d{1,2})?$/;q d.O(a)}q 1k},"请输入正确的货币格式");$.r.x("7K",t(a,b,c){I(c){y d=/^(2v?|2w|7L|7M):\\/\\/[\\w-]+(\\.[\\w-]+)+(\\/\\w*)*(\\w+(\\.)?[\\w-]+)?$/;q d.O(a)}q 1k},"请输入正确的网址");$.r.x("7N",t(a,b){y c=1k;y d=/^[\\1R-\\2c]$/;I(a){1E(y i=0;i<a.1b;i++){I(d.O(a.1t(i,1))){c=1i;q c}}}q c},"不能含有中文");u.r.x("2j",t(a,b,c){y d=c[0];y e=1A(c[1]);y f=a.2j(d);I(f.1b>e)q 1i;q 1k},"超出个数");u.r.x("7O",t(a,b){I(a==\'长期\')q 1k;y c=1i;y d=/^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$/;I(d.O(a)){y e=a.2j(\'-\');y f=1A(e[2],10);y g=1A(e[1],10);y h=1A(e[0],10);y i=1p 1x(h,g-1,f);I((i.3z()===h)&&(i.3D()===g-1)&&(i.3E()===f)){c=1k}1q{c=1i}}1q{c=1i}q Q.1a(b)||c},"1l 1v a 2S 2n");u.r.x("7P",t(a,b,c){q Q.1a(b)||/^((2v?|2w):\\/\\/){0,1}(((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|\\d|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))\\.)*(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])*([a-z]|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|[\\2X-\\2W]|\\/|\\?)*)?(#((([a-z]|\\d|-|\\.|1s|~|[\\1f-\\1g\\1d-\\1h\\1e-\\1c])|(%[\\1C-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$/i.O(a)},u.r.2t.2U);u.r.x("7Q",t(a,b,c){q Q.1a(b)||/^(([\\+]?\\d{2,6}-)|0)?(1[1-9])\\d{9}$|^([\\+]?\\d{2,6}-)?(\\d{3,4}-)(\\d{3,8})$/i.O(a)},"请输入有效的电话号码.");u.r.x("7R",t(a,b,c){q Q.1a(b)||/^(\\d{3}-)(\\d{8})$|^(\\d{4}-)(\\d{7})$|^(\\d{4}-)(\\d{8})$/i.O(a)},"请输入有效的传真号码.");u.r.x("3F",t(a,b,c){q $(b).1K().7T<=7U(c)},"1l 1v a 1o 3F.");$.r.x("7V",t(a,b,c){I(7W!=a&&!a){q 1k}y d=$(c).2i();I(d=="")d=$(c).1K();I(a==""||d=="")q 1k;q!(a==d)},"您的变更申请内容相同，不能提交申请！");$.r.x("7X",t(a,b,c){q Q.1a(b)||/^\\d+(\\.[0]+)?$/.O(a)},"请输入正整数！");$.r.x("7Y",t(a,b,c){y d=$(c).1K();y e=a;I(e==""||d=="")q 1k;y f=1p 1w(\'-\',\'g\');e=1p 1x(1x.1P(e.1r(f,"/")));d=1p 1x(1x.1P(d.1r(f,"/")));y g=7Z*60*60*24*81;q d.3b()-e.3b()<=g},"日期间隔不能大于一年");',62,499,'||||||||||||||||||||||||||return|validator||function|jQuery|||addMethod|var||||||||||if||||||test||this||||||||||||||||||||optional|length|uFFEF|uF900|uFDF0|u00A0|uD7FF|uFDCF|false|dA|true|Please|9A|Fa|valid|new|else|replace|_|substr|number|enter|RegExp|Date|specify|zA|parseInt|match|da|format|for|typeof|00|index|indexOf|01|val|fixRe|preRe|fixStr|preStr|parse|midThree|u4e00|being_validated|data|PR|UWYZ|isNaN|or|phone|x09||||||||please|cdv||cd|flag|u9fa5|file|substring|stripHtml|files|subFour|text|split|at|form|least|date|157|midTwo|x20|0x0040|x0d|messages|Letters|https|ftp|and|only|0x0020|0x0001|HK|0x0002|02|0x0080|time|account|count1|0x0004|0x0008|optionalValue|words|count2|Z0|selector|count3|0x0010|string|correct|The|url|specified|uF8FF|uE000|2468|is|||invalid|100||between|x7f|x0c|x0b|x01|x0a|getTime|extension|with|x22|value|u9fa5a|ZIP|toUpperCase|address|code|subSix|IP|in|subNine|less|86|624|type|45789|xxxx|call|methods|giro|giroaccountNL|getFullYear|bank|bankaccountNL|mobile|getMonth|getDate|xdtmaxlength|012|9Xx|numberRequired|fields|these|of|fill|charAt|048|elementValue|filter|TN|AE|GB|VG|CH|undefined|SE|ES|SI|SK|RS|0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ|SA||SM|RO|||PT|97|PL|PS|IBAN|dateNL|PK|NO|phoneNL|NL|ME|MD|phoneCN|mobileNL|MC|postalcodeNL|postal|MU|MR|MT|MK|LU|bankorgiroaccountNL|LT|LI|LB|time12h|AP|hour|am|pm|phoneUS|phoneUK|LV|mobileUK|KW|KZ|phonesUK|uk|postcodeUK|IT|IL|IE|HJKSTUW|ABEHMNPRVWXY|ABD|HJLNP|UW|GIR|0AA|UK|postcode|strippedminlength|IS|characters|email2|u4E00|u9FA5|HU|uFA2D|uFF00|GT||GL|GR|GI|DE|GE|FR|FI|FO|EE|DO|x08|DK|CZ|x0e|x1f|CY|x21|x23|x5b|x5d|x7e|HR|email|url2|CR|BG|BR|BA|BH|BE|AZ|creditcardtypes|0x0000|mastercard|AT|visa|AD|amex|AL|dinersclub|enroute|iban|discover|dateITA|jcb|VIN|unknown|identification|all|12345|012345|014||149|6011|2131|1800|TR|card|ipv4||vehicle|v4|break|ipv6|slice|v6|pattern|Invalid|require_from_group|skip_or_fill_minimum|either|skip|them|accept|image|attr|vinUS|decimal|non|mimetype|negative|png|jpe|gif|regex|13578|positive|469|integer|Code|US|zipcodeUS|13579|xx|3579|YYYY|MM|DD|IDCard|905|to|IDCardCN|IDCardHK|IDCardMC|IDCardTW|mobileNo|902xx|passport|A12345678|resourceNo|glgsNo|range|the|fwgsNo|be|must|tgqyNo|Your|90|orgcode|taxcode|illegalcode|cyqyNo|hsCardNo|passwordLogin|passwordRule|ziprange|space|white|No|111111|654321|username|0000|bankNo|custname|nowhitespace|lettersonly|underscores|numbers|alphanumeric|punctuation|letterswithbasicpunc|job|rangeWords|businessLicence|validationCode|medicalCardNum|zipCode|qq|QQ|endDate|minWords|maxWords|beginDate|greater|isNumTimes|gi|huobi|isUrl|rtsp|mms|nochinese|dateITA2|url3|telphone|fax|160|lenght|Number|equalContent|null|digits2|oneyear|1000|nbsp|365|credit'.split('|'),0,{}));