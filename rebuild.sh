#!/bin/sh
# -------------------------------------------------------
# HBS Platform Manager Script
# author: trsimoes
# -------------------------------------------------------

backup-current-application() {
    echo
    echo ---------------------------
    echo Backup old HBS version
    echo ---------------------------
    mkdir -p /home/pi/backup/hbs
    tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz
    echo +++ OK
}

compile-project() {
    echo
    echo ---------------------------
    echo Compile HBS platform
    echo ---------------------------
    mvn -e clean install
    echo +++ OK
}

stop-hbs-generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------
    echo Stopping $SERVER_NAME
    echo ---------------------------
    ps -ef | grep $SERVER_NAME | grep -v grep | awk '{print $2}' | xargs sudo kill -9
    echo +++ OK
}

deploy-hbs-generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------
    echo Deploying HBS $SERVER_NAME
    echo ---------------------------
    rm -rf /opt/hbs/$SERVER_NAME*
    cp $SERVER_NAME/target/$SERVER_NAME*.jar /opt/hbs/
    cp $SERVER_NAME/target/extra-resources/run-$SERVER_NAME.sh /opt/hbs/
    chmod -R 766 /opt/hbs
    ln -sf /tmp/$SERVER_NAME.log /opt/hbs/$SERVER_NAME.log
    echo +++ OK
}

start-hbs-generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------
    echo Starting HBS $SERVER_NAME
    echo ---------------------------
    echo
    cd /opt/hbs/
    ./run-$SERVER_NAME.sh &
    echo +++ OK
}

stop-hbs-server() {
    stop-hbs-generic hbs-server
}

stop-hbs-web() {
    stop-hbs-generic hbs-web
}

stop-all() {
    stop-hbs-server
    stop-hbs-web
}

deploy-hbs-server() {
    deploy-hbs-generic hbs-server
}

deploy-hbs-web() {
    deploy-hbs-generic hbs-web
}

deploy-all() {
    deploy-hbs-server
    deploy-hbs-web
}

start-hbs-server() {
    start-hbs-generic hbs-server
}

start-hbs-web() {
    start-hbs-generic hbs-web
}

start-all() {
    start-hbs-server
    start-hbs-web
}

rebuild-all() {
    stop-all
    backup-current-application
    compile-project
    deploy-all
    start-all
}

rebuild-hbs-server() {
    stop-hbs-server
    backup-current-application
    compile-project
    deploy-hbs-server
    start-hbs-server
}

rebuild-hbs-web() {
    stop-hbs-web
    backup-current-application
    compile-project
    deploy-hbs-web
    start-hbs-web
}

usage() {
    echo "usage: ./rebuild.sh [all|server|web]"
}

## Main script starts here
if [ "$1" != "" ]; then
    case    $1 in
        server) rebuild-hbs-server
                ;;
        web)    rebuild-hbs-web
                ;;
        all)    rebuild-all
                ;;
        *)      usage
                exit 1
    esac
else
    usage
    exit 1
fi








