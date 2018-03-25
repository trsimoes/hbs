echo -e \n
echo ---------------------------
echo Backup old HBS version
echo ---------------------------
echo -e \n
mkdir -p /home/pi/backup/hbs
tar cvf - /opt/hbs/* | gzip > /home/pi/backup/hbs/$(date +%Y%m%d%H%M%S).tar.gz

echo -e \n
echo ---------------------------
echo Shutting down HBS Server
echo ---------------------------
echo -e \n
curl -X POST localhost:8080/shutdown

echo -e \n
echo ---------------------------
echo Update and compile code
echo ---------------------------
echo -e \n
git pull
mvn -e clean install

echo -e \n
echo ---------------------------
echo Deploying HBS Server
echo ---------------------------
echo -e \n
rm -rf /opt/hbs/*
cp target/hbs*.jar /opt/hbs/
cp run.sh /opt/hbs/
cp src/main/non-packaged-resources/geckodriver /opt/hbs/
chmod -R 766 /opt/hbs
ln -sf /tmp/hbs.log /opt/hbs/hbs.log

echo -e \n
echo ---------------------------
echo Done
echo ---------------------------
echo -e \n
