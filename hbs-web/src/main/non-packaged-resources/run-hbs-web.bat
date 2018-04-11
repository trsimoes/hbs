@echo off
cls && java -jar target/${build.finalName}.jar
::cls && java -jar target/${build.finalName}.jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9998,suspend=y