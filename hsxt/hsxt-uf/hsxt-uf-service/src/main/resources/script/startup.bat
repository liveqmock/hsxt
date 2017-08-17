REM '-------------------------------------------------------------------------------'
REM '*'
REM '*    Welcome to the server of unified front end system(UF).'
REM '*'
REM '-------------------------------------------------------------------------------'
call
SET JAVA_OPTS=-Dlog.level=debug -Dfile.encoding=UTF-8
SET USER_DIR=user.dir=./
SET LOG_HOME=log.home=./
SET SERVER_IP=server.ip=127.0.0.1
SET SERVER_PORT=server.port=8088
java -jar %JAVA_OPTS% lib/hsxt-uf-service-3.0.0-SNAPSHOT.jar %SERVER_IP% %SERVER_PORT% %USER_DIR% %LOG_HOME%  &
pause