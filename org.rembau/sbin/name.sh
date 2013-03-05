#!/bin/bash
SERVER_NAME="test"
SERVER_TAG=test
MAIN_CLASS="org.rembau.command.MainClass";
LIB=../lib
CLASSPATH=.:


for i in $LIB/*.jar ;
        do
                CLASSPATH="$CLASSPATH":"$i"
        done

echo $CLASSPATH

SCRIPT="java -mx3024M -Diname=$SERVER_TAG  -Xrs  -classpath $CLASSPATH $MAIN_CLASS"
SCRIPT_SERVER_IDS=`ps -ef | grep "Diname=$SERVER_TAG" | grep -v grep | awk '{print $2}'`
is_server_run(){
        tmp=`ps -ef |grep "Diname=$SERVER_TAG" | grep -v grep`
        if [ $? -eq 0 ]; then
		return 0
        else
		return 1
        fi
     }
start_server(){
        is_server_run
        if [ $? -eq 0 ]; then 
                echo "$SERVER_NAME already started!"
                return 1
        fi
        #nohup $SCRIPT $P > ../logs/${SERVER_TAG}.out &
	echo $SCRIPT $P > ../logs/${SERVER_TAG}.out &
        echo "$SERVER_NAME started!"
}
if [ $# eq 1 ]; then
	start_server
	return 0
else 
	P=$*
	start_server
	return 0
fi
		