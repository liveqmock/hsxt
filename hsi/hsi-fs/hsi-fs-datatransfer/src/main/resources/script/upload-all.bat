REM '-------------------------------------------------------------------------------'
REM '*'
REM '*    Welcome to the program for Data Tansfering.'
REM '*'
REM '-------------------------------------------------------------------------------'
call
SET JAVA_OPTS=-Dlog.level=debug -Dfile.encoding=GBK
SET USER_DIR=user.dir=./
SET LOG_HOME=log.home=./
SET ACTION=action=UPLOAD
java -jar %JAVA_OPTS% lib/hsi-fs-datatransfer-3.0.0-SNAPSHOT.jar %USER_DIR% %LOG_HOME% %ACTION% &
pause