SERVER_NAME="linkAutoBuild"
SERVER_TAG="linkAutoBuild"
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

STARTUP="/usr/jdk/jdk1.6.0_21/bin/java -mx64M -Diname=$SERVER_TAG -classpath $classpath $MAIN_CLASS $*"
echo $STARTUP

is_server_started(){
	ps -ef |grep "Diname=$SERVER_TAG" | grep -v grep
	if [ $? -eq 0 ]
	then 
		return 0   #yes
	else 
		return 1    #no
	fi	
}

start_proc(){
	is_server_started
	if [ $? -eq 0 ]; then
                echo "${SERVER_TAG} ${SERVER_NAME} is already running !"
        else
                echo "${SERVER_TAG} Starting ${SERVER_NAME} ..."
                nohup $STARTUP > ${LOG_FILE} 2>&1 &
		sleep 1
		is_server_started
		if [ $? -eq 0 ]; then
                	echo "${SERVER_TAG} ${SERVER_NAME} started !"
		else
                	echo "${SERVER_TAG} ${SERVER_NAME} starts failed !"
		fi

        fi
}

status_proc(){
	is_server_started
        if [ $? -eq 0 ]; then
                echo "${SERVER_TAG} ${SERVER_NAME} is running !"
        	ps -ef | grep -w "${PROC_TAG}" | grep -v grep
        else
                echo "${SERVER_TAG} ${SERVER_NAME} is not running !"
        fi
}

stop_proc(){
        is_server_started
	if [ $? -eq 0 ]; then
		echo "${SERVER_TAG} ${SERVER_NAME} stoping !"
		$STARTUP
		sleep 1
                is_server_started
		if [ $? -eq 1 ]; then
			echo "${SERVER_TAG} ${SERVER_NAME} stoped !"
		else
			echo "${ERROR_TAG} ${PROC_NAME} stop failed !"
		fi
        else
		echo "${SERVER_TAG} ${SERVER_NAME} is already stoped !"
        fi
}

client(){
	$STARTUP
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