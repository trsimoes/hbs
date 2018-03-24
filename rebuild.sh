echo Backup old HBS version
mkdir -p /home/pi/backup/hbs
tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz

git pull
mvn -e clean install
rm -rf /opt/hbs/*
cp target/hbs*.jar /opt/hbs/
cp run.sh /opt/hbs/
cp src/main/non-packaged-resources/geckodriver /opt/hbs/
chmod -R 766 /opt/hbs