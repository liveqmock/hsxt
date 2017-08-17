javapid=$(ps -ef|grep key-server-0.0.|grep -v 'grep'|awk '{print $2}')
if [ ! -n "$javapid" ]; then 
  echo "progress not up" 
else 
  kill -15 $javapid
fi 
nohup /usr/jdk1.7.0_72/bin/java -jar -Dlog.level=all lib/key-server-0.0.1-SNAPSHOT.jar &
