cd /opt/hbs
clear && java -jar ${build.finalName}.jar
# clear && java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9999,suspend=y -jar ${build.finalName}.jar