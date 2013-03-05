SERVER_NAME="test"
SERVER_TAG="test"
MAIN_CLASS="org.rembau.command.MainClass"
LIB=../lib
classpath=.

for i in $LIB/*.jar
do 
	classpath="$classpath":"$i"
done

#echo $SERVER_NAME $SERVER_TAG $classpath

STARTUP="java -mx64M -Diname=$SERVER_TAG -classpath $classpath $MAIN_CLASS $*"
echo $STARTUP

$STARTUP
is_server_started(){
	tem='ps -ef |grep "Diname=$SERVER_TAG" | grep -v grep'
	if [ $? -eq 0 ]
	then 
		return 0
	else 
		return 1
	fi	
}

