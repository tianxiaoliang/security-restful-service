#!/bin/bash
baseDirForScriptSelf=$(cd "$(dirname "$0")"; pwd) 
PRJDIR=$(dirname $baseDirForScriptSelf)
echo $PRJDIR
if test ! -d "$PRJDIR"; then
    echo "$PRJDIR does not exist"
    exit 1
fi
 

CLOUDPI_USER="$(id -u -n)"

PIDFILE=/tmp/jetty.pid
LOGFILE=/tmp/log
#must use hard code for mvn cmd
MVNCMD=`which mvn`
CLOUDPI_OPTIONS=""
TMPSHELL="-s /bin/bash"
START_CMD="$MVNCMD ${CLOUDPI_OPTIONS} process-resources jetty:run -s $PRJDIR/settings.xml"
STOP_CMD="$MVNCMD -q jetty:stop"

cd $PRJDIR
case $1 in
start)
    export MAVEN_OPTS="-Xms2048m -Xmx2048m -XX:PermSize=512m -XX:MaxPermSize=512m"
    started=false
    nohup $START_CMD > $LOGFILE 2>&1 &
    pid=$!
    echo $pid > $PIDFILE

    # Remove one line in the below because it contains `ps` labels
    while [ `ps -p$pid -o pid | sed "1 d" | wc -l` != 0 ];
    do
        if grep -q "Started Jetty Server" $LOGFILE; then
            started=true
            break
        fi
        sleep 1
    done

    if [ $started == true ]; then
        echo "service successfully started"
    else
        wait $pid
        if [ $? != 0 ]; then
            echo "Failed to start service "
            echo " Consult the log for more details: $LOGFILE"
        fi
    fi
    ;;
stop)
    MAVEN_OPTS=""
    $STOP_CMD
    rm -f $PIDFILE
    echo "service successfully stopped"
    ;;
restart)
    $0 stop
    $0 start
;;
status)
    if [ -f $PIDFILE ]; then
        echo "service is running"
    else
        echo "service was stopped"
    fi
    ;;
*)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 3
    ;;
esac
