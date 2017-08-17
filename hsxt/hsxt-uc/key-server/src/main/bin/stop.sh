javapid=$(ps -ef|grep key-server-0.0.|grep -v 'grep'|awk '{print $2}')
if [ ! -n "$javapid" ]; then
echo progress not up
else
kill -15 $javapid
fi

