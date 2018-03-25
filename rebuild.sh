echo
echo ---------------------------
echo Shutting down HBS Server
echo ---------------------------
echo
#curl -X POST localhost:8080/shutdown
ps -ef | grep gecko | grep -v grep | awk '{print $2}' | xargs sudo kill -9

echo
echo ---------------------------
echo Backup old HBS version
echo ---------------------------
echo
mkdir -p /home/pi/backup/hbs
tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz

echo
echo ---------------------------
echo Update and compile code
echo ---------------------------
echo
git reset --hard origin/master
git pull
mvn -e clean install

echo
echo ---------------------------
echo Deploying HBS Server
echo ---------------------------
echo
rm -rf /opt/hbs/*
cp hbs-server/target/hbs*.jar /opt/hbs/
cp hbs-server/extra-resources/run.sh /opt/hbs/
cp drivers/geckodriver /opt/hbs/
chmod -R 766 /opt/hbs
ln -sf /tmp/hbs.log /opt/hbs/hbs.log

echo
echo ---------------------------
echo Done
echo ---------------------------
echo
