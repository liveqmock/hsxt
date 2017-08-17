文件清单:
run.bat  							Windows操作系统启动文件
run_debug.bat						Windows操作系统debug日志启动文件
run.sh								linux操作系统启动文件
run_debug.sh						linux操作系统debug日志启动文件
Readme.txt							本说明文件
config目录
	keyserver.properties			系统配置文件
	server.p12 						openssl服务器方证书
	applicationContext_deploy.xml	开发配置文件
lib目录								系统运行库
libext目录							系统扩展运行库
log目录								日志文件
配置文件说明：
socket.port			普通侦听端口
------ssl部分------
ssl.port			ssl侦听端口
ssl.password		ssl证书密码
ssl.caFilename		ssl证书文件名
ssl.caCN			ssl证书CN

SubCenter			平台中心名
checkin.DueTime		登录有效期

------oracle数据库部分------
pos.jdbc.driver		数据库驱动
pos.jdbc.url		数据库地址
pos.jdbc.username	登录用户
pos.jdbc.password	登录密码
