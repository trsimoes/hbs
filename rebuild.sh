#!/bin/sh
# -------------------------------------------------------
# HBS Platform Manager Script
# author: trsimoes
# -------------------------------------------------------

# -------------------------------------------------------
# Backup
# -------------------------------------------------------
backup_current_application() {
    echo
    echo ---------------------------
    echo Backup old HBS version
    echo ---------------------------
    mkdir -p /backup/hbs
    tar cvf - /opt/hbs/* | gzip > /backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz
    echo +++ OK
}

# -------------------------------------------------------
# STOP APPLICATION
# -------------------------------------------------------
stop_hbs_generic() {

    SERVER_NAME=$1
    SERVER_PORT=$2

    echo
    echo ---------------------------
    echo Stopping $SERVER_NAME
    echo ---------------------------
    #ps -ef | grep $SERVER_NAME | grep -v grep | awk '{print $2}' | xargs sudo kill -9
    curl http://localhost:$SERVER_PORT/actuator/shutdown
    echo +++ OK
}

stop_hbs_server() {
    stop_hbs_generic hbs-server 9090
}

stop_hbs_web() {
    stop_hbs_generic hbs-web 8090
}

stop_all() {
    stop_hbs_server
    stop_hbs_web
}

# -------------------------------------------------------
# DEPLOY APPLICATION
# -------------------------------------------------------
deploy_hbs_generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------
    echo Deploying HBS $SERVER_NAME
    echo ---------------------------
    rm -rf /opt/hbs/$SERVER_NAME*
    cp /opt/hbs/_staging_/$SERVER_NAME*.jar /opt/hbs/
    cp /opt/hbs/_staging_/run-$SERVER_NAME.sh /opt/hbs/
    chmod -R 766 /opt/hbs
    ln -sf /tmp/$SERVER_NAME.log /opt/hbs/$SERVER_NAME.log
    echo +++ OK
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

# -------------------------------------------------------
# START APPLICATION
# -------------------------------------------------------
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

# -------------------------------------------------------
# VALIDATE PRE CONDITIONS
# -------------------------------------------------------
validate_generic() {

    SERVER_NAME=$1

    echo
    echo ---------------------------------
    echo Validating HBS $SERVER_NAME files
    echo ---------------------------------
    echo
    if [ ! -f /opt/hbs/_staging_/$SERVER_NAME*.jar ]; then
        echo "$SERVER_NAME jar file not found!"
        exit 1
    fi
    if [ ! -f /opt/hbs/_staging_/run-$SERVER_NAME.sh ]; then
        echo "$SERVER_NAME run script file not found!"
        exit 1
    fi
}

validate_server() {
    validate_generic hbs-server
}

validate_web() {
    validate_generic hbs-web
}

validate_all() {
    validate_generic hbs-server
    validate_generic hbs-web
}

# -------------------------------------------------------
# DEPLOY APPLICATION
# -------------------------------------------------------
update_all() {
    validate_all
    stop_all
    backup_current_application
    deploy_all
    start_all
}

update_hbs_server() {
    validate_server
    stop_hbs_server
    backup_current_application
    deploy_hbs_server
    start_hbs_server
}

update_hbs_web() {
    validate_web
    stop_hbs_web
    backup_current_application
    deploy_hbs_web
    start_hbs_web
}

# -------------------------------------------------------
# HELP
# -------------------------------------------------------
usage() {
    echo "usage: ./rebuild.sh [all|server|web]"
}

## Main script starts here
if [ "$1" != "" ]; then
    case    $1 in
        server) update_hbs_server
                ;;
        web)    update_hbs_web
                ;;
        all)    update_all
                ;;
        *)      usage
                exit 1
    esac
else
    usage
    exit 1
fi








