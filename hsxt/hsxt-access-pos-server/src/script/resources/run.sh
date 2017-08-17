loginname=$(whoami)
spid=$(ps -ef|grep pos|grep $loginname|grep -v 'grep'|awk '{print $2}')
if [ ! $spid ]; then
  echo server start
else
  echo server already start,kill server pid=$spid and restart
  kill -9 $spid
fi
nohup java -jar -Dlog.level=debug lib/hsxt-access-pos-server-3.0.0-SNAPSHOT.jar &
echo $! > main.pid