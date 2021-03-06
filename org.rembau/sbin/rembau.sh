SERVER_NAME="linkAutoBuild"
SERVER_TAG=$SERVER_NAME"Server"
SERVER_TAG_STOP=$SERVER_NAME"Stop"
SERVER_TAG_COMMAND=$SERVER_NAME"Command"
MAIN_CLASS="org.rembau.MainClass"
LIB=../lib
classpath=.
LOG_FILE=../logs/output

for i in $LIB/*.jar
do 
	classpath="$classpath":"$i"
done
classpath="$classpath":../conf

#echo $SERVER_NAME $SERVER_TAG $classpath

JAVA="/usr/jdk/jdk1.6.0_21/bin/java -mx512M"

STARTUP="$JAVA -Diname=$SERVER_TAG -classpath $classpath $MAIN_CLASS $*"

STOP="$JAVA -Diname=$SERVER_TAG_STOP -classpath $classpath $MAIN_CLASS $*"

COMMAND="$JAVA -Diname=$SERVER_TAG_COMMAND -classpath $classpath $MAIN_CLASS $*"
echo $STARTUP

is_server_started(){
	ps -ef |grep "Diname=$SERVER_TAG" | grep -v grep
	if [ $? -eq 0 ]
	then 
		return 0   #yes
	else 
		return $pid    #no
	fi	
}

start_proc(){
	is_server_started
	if [ $? -eq 0 ]; then
                echo "${SERVER_NAME} is already running !"
        else
                echo "Starting ${SERVER_NAME} ..."
                #nohup $STARTUP > ${LOG_FILE} 2>&1 &
		nohup $STARTUP
		sleep 2
		is_server_started
		if [ $? -eq 0 ]; then
                	echo "${SERVER_NAME} started !"
		else
                	echo "${SERVER_NAME} starts failed !"
		fi

        fi
}

status_proc(){
	is_server_started
        if [ $? -eq 0 ]; then
                echo "${SERVER_TAG} ${SERVER_NAME} is running !"
        	ps -ef | grep -w "${PROC_TAG}" | grep -v grep
        else
                echo "${SERVER_NAME} is not running !"
        fi
}

stop_proc(){
        is_server_started
	if [ $? -eq 0 ]; then
		pid=`ps -ef|grep ${SERVER_TAG} |grep -v grep|awk '{print $2}'`
		kill ${pid}
		echo "${SERVER_NAME} is stoped !"
        else
		echo "${SERVER_NAME} is already stoped !"
        fi
}

client(){
	$COMMAND
}

#=======================================================================
# Main Program begin
#=======================================================================

cd `dirname $0`
case $1 in
        start)
                start_proc
                ;;
        status)
                status_proc
                ;;
        stop)
                stop_proc
                ;;
        restart)
                stop_proc
                start_proc
                ;;
        *)
                client
esac