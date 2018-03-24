echo Backup old HBS version
mkdir -p /home/pi/backup/hbs
tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz

git pull
mvn -e clean install
rm -rf /opt/hbs/*
cp target/hbs*.jar /opt/hbs/
cp run.sh /opt/hbs/
cp src/main/non-packaged-resources/geckodriver /opt/hbs/

clear && java -Dwebdriver.gecko.driver=/opt/hbs/geckodriver -jar target/hbs-1.0.1.jar
::clear && java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9999,suspend=y -jar target/hbs-1.0.1.jar