cd /opt/hbs
clear && java -Dwebdriver.gecko.driver=/opt/hbs/geckodriver -jar hbs-1.0.1.jar
# clear && java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9999,suspend=y -Dwebdriver.gecko.driver=/opt/hbs/geckodriver -jar hbs-1.0.1.jar