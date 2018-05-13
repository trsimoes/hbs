node {
   def mvnHome
   stage('Preparation') { // for display purposes
      git 'https://github.com/trsimoes/hbs.git'
      mvnHome = tool 'Maven3'

      // copy hbs.property files from server
      sh 'scp pi@127.0.0.1://home/pi/hbs-files/hbs-server.properties hbs-server/src/main/resources/hbs.properties'
      sh 'scp pi@127.0.0.1://home/pi/hbs-files/hbs-web.properties hbs-web/src/main/resources/hbs.properties'
   }
   stage('Build') {
      sh "'${mvnHome}/bin/mvn' clean package"
   }
   stage('Upload') {
       // cleanup
       sh 'ssh pi@127.0.0.1 mkdir -p /tmp/hbs/_staging_'
       sh 'ssh pi@127.0.0.1 rm -rf /tmp/hbs/_staging_/*'

       // copy hbs-server
       sh 'scp hbs-server/target/hbs-server-*.jar pi@127.0.0.1:/tmp/hbs/_staging_/'
       sh 'scp hbs-server/target/extra-resources/run-hbs-server.sh pi@127.0.0.1:/tmp/hbs/_staging_/'

       // copy hbs-web
       sh 'scp hbs-web/target/hbs-web-*.jar pi@127.0.0.1:/tmp/hbs/_staging_/'
       sh 'scp hbs-web/target/extra-resources/run-hbs-web.sh pi@127.0.0.1:/tmp/hbs/_staging_/'

       // copy install script
       sh 'scp rebuild.sh pi@127.0.0.1:/tmp/hbs/_staging_/'

       // change permissions
       sh 'ssh pi@127.0.0.1 chmod -R 777 /tmp/hbs/_staging_'
   }
   stage('Deploy') {
      sh 'ssh pi@127.0.0.1 /tmp/hbs/_staging_/rebuild.sh all'
   }
   stage('Start HBS Server') {
      sh 'ssh pi@127.0.0.1 sudo systemctl start hbs-server'
   }
   stage('Start HBS Web') {
      sh 'ssh pi@127.0.0.1 sudo systemctl start hbs-web'
   }
}