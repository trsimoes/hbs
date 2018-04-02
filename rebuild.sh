echo
echo ---------------------------
echo Shutting down HBS Server
echo ---------------------------
#curl -X POST localhost:8080/shutdown
ps -ef | grep hbs-server | grep -v grep | awk '{print $2}' | xargs sudo kill -9
echo >>> OK

echo
echo ---------------------------
echo Backup old HBS version
echo ---------------------------
mkdir -p /home/pi/backup/hbs
tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz
echo >>> OK

echo
echo ---------------------------
echo Deploying HBS Server
echo ---------------------------
rm -rf /opt/hbs/*
cp hbs-server/target/hbs*.jar /opt/hbs/
cp hbs-server/target/extra-resources/run.sh /opt/hbs/
chmod -R 766 /opt/hbs
ln -sf /tmp/hbs.log /opt/hbs/hbs.log
echo >>> OK

echo
echo ---------------------------
echo Starting server
echo ---------------------------
echo
cd /opt/hbs/
./run.sh &
echo >>> OK