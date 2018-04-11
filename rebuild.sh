#!/bin/sh
# -------------------------------------------------------
# HBS Platform Manager Script
# author: trsimoes
# -------------------------------------------------------

backup_current_application() {
    echo
    echo ---------------------------
    echo Backup old HBS version
    echo ---------------------------
    mkdir -p /home/pi/backup/hbs
    tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz
    echo +++ OK
}

compile_project() {
    echo
    echo ---------------------------
    echo Compile HBS platform
    echo ---------------------------
    mvn -e clean install
    echo +++ OK
}

stop_hbs_generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------
    echo Stopping $SERVER_NAME
    echo ---------------------------
    ps -ef | grep $SERVER_NAME | grep -v grep | awk '{print $2}' | xargs sudo kill -9
    echo +++ OK
}

deploy_hbs_generic() {

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

start_hbs_generic() {

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

stop_hbs_server() {
    stop_hbs_generic hbs-server
}

stop_hbs_web() {
    stop_hbs_generic hbs-web
}

stop_all() {
    stop_hbs_server
    stop_hbs_web
}

deploy_hbs_server() {
    deploy_hbs_generic hbs-server
}

deploy_hbs_web() {
    deploy_hbs_generic hbs-web
}

deploy_all() {
    deploy_hbs_server
    deploy_hbs_web
}

start_hbs_server() {
    start_hbs_generic hbs-server
}

start_hbs_web() {
    start_hbs_generic hbs-web
}

start_all() {
    start_hbs_server
    start_hbs_web
}

rebuild_all() {
    stop_all
    backup_current_application
    compile_project
    deploy_all
    start_all
}

rebuild_hbs_server() {
    stop_hbs_server
    backup_current_application
    compile_project
    deploy_hbs_server
    start_hbs_server
}

rebuild_hbs_web() {
    stop_hbs_web
    backup_current_application
    compile_project
    deploy_hbs_web
    start_hbs_web
}

usage() {
    echo "usage: ./rebuild.sh [all|server|web]"
}

## Main script starts here
if [ "$1" != "" ]; then
    case    $1 in
        server) rebuild_hbs_server
                ;;
        web)    rebuild_hbs_web
                ;;
        all)    rebuild_all
                ;;
        *)      usage
                exit 1
    esac
else
    usage
    exit 1
fi








