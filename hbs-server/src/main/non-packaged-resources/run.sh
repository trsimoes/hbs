cd /opt/hbs
clear && java -Dwebdriver.gecko.driver=/opt/hbs/geckodriver -jar ${build.finalName}.jar
# clear && java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9999,suspend=y -Dwebdriver.gecko.driver=/opt/hbs/geckodriver -jar ${build.finalName}.jar