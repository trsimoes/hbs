# Instalation Instructions
1. Download Raspbian Lite in burn it to SD card: https://downloads.raspberrypi.org/raspbian_lite_latest.torrent
2. Activate SSH: https://randomnerdtutorials.com/installing-raspbian-lite-enabling-and-connecting-with-ssh/
3. Install JAVA8: https://www.linuxbabe.com/desktop-linux/install-oracle-java-8-debian-jessie-raspbian-jessie-via-ppa
4. Install GIT (sudo apt-get install git)
5. Install Maven 3: https://xianic.net/2015/02/21/installing-maven-on-the-raspberry-pi/ (remove install binary in the end: rm -rf apache-maven-3.2.5-bin.tar.gz)
6. Install PostgreSQL: https://opensource.com/article/17/10/set-postgres-database-your-raspberry-pi
7. Create user and database. Use same article.
8. Clone HBS project: git clone https://github.com/trsimoes/hbs.git
9. Install FlotJF in Maven local repository: mvn install:install-file -Dfile=libs\dunse\FlotJF\0.2-SNAPSHOT\FlotJF-0.2-SNAPSHOT.jar -DgroupId=dunse -DartifactId=FlotJF -Dversion=0.2-SNAPSHOT -Dpackaging=jar
10. Run **rebuild.sh**
* chmod 766 rebuild.sh
* ./rebuild.sh

# Usefull Links
1. Google Charts: https://gist.github.com/anonymous/315d0e4cb69db3661b46b348f0c7ba32
2. Selenium Firefox Drivers: https://github.com/mozilla/geckodriver/releases
3. Run HBS at boot: https://www.dexterindustries.com/howto/run-a-program-on-your-raspberry-pi-at-startup/ (Method 1: rc.local)