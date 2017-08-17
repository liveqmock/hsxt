echo '-------------------------------------------------------------------------------'
echo '*'
echo '*    Welcome to the server of unified front end system(UF).'
echo '*'
echo '-------------------------------------------------------------------------------'
JAVA_OPTS="-Dlog.level=debug -Dfile.encoding=UTF-8 -server -Xms1024m -Xmx4096m -XX:+UseParNewGC"
USER_DIR=user.dir=./
LOG_HOME=log.home=./
SERVER_IP=server.ip=127.0.0.1
SERVER_PORT=server.port=8088
java -jar $JAVA_OPTS lib/hsxt-uf-service-3.0.0-SNAPSHOT.jar $SERVER_IP $SERVER_PORT $USER_DIR $LOG_HOME &