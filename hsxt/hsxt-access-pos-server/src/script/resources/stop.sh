filepath=main.pid
if [ -f $filepath ]; then
echo stop pid=`cat $filepath` and delete $filepath
kill -9 `cat $filepath`
rm -f $filepath 
else
echo $filepath not exist 
fi