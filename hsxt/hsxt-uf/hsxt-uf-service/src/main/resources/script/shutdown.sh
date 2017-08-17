loginname=$(whoami)
spid=$(ps -ef|grep hsxt-uf|grep $loginname|grep -v 'grep'|awk '{print $2}')
if [ $spid ]; then
  kill -9 $spid
fi
echo 'Operation finished.';