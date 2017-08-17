echo '-------------------------------------------------------------------------------'
echo '*'
echo '*    Welcome to the program for Data Tansfering.'
echo '*'
echo '-------------------------------------------------------------------------------'
JAVA_OPTS=-Dlog.level=debug -Dfile.encoding=GBK -server -Xms1024m -Xmx4096m -XX:+UseParallelGC
USER_DIR=user.dir=./
LOG_HOME=log.home=./
ACTION=action=UPLOAD
java -jar $JAVA_OPTS lib/hsi-fs-datatransfer-3.0.0-SNAPSHOT.jar $USER_DIR $LOG_HOME $ACTION &