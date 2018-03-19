@echo off
cls && java -jar target/hbs-1.0.1.jar
::cls && java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9999,suspend=y -jar target/hbs-1.0.1.jar