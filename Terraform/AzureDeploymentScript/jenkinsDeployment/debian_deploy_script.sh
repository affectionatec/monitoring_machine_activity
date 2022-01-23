#!/bin/bash
su debian
cd /home/debian
whoami
echo logged in as $USER.
echo in directory $PWD



sudo apt update
echo "installing MariaDB..."
sudo apt install mariadb-server -y
sudo systemctl start mariadb
sudo systemctl status mariadb
sudo systemctl enable mariadb
sudo mysql -u root -e "USE mysql; UPDATE user SET password=PASSWORD('comsc') WHERE User='root' AND Host = 'localhost'; FLUSH PRIVILEGES;"
sudo mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'comsc' WITH GRANT OPTION;"


sudo apt update
sudo apt install wget -y
sudo apt install unzip -y
sudo apt install git -y


echo "installing gitlab server key..."
sudo touch ~/.ssh/known_hosts
sudo chmod 777 ~/.ssh/known_hosts
sudo ssh-keyscan gitlab.cs.cf.ac.uk >> ~/.ssh/known_hosts
sudo chmod 644 ~/.ssh/known_hosts


echo "Installing Java 11..."
sudo apt install default-jdk -y
echo java -version


echo "install Jenkins"
sudo apt update
sudo apt-get install -y gnupg2 gnupg gnupg1
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt update
sudo apt install jenkins -y
sudo systemctl start jenkins
systemctl status jenkins
sudo systemctl enable jenkins

sudo apt update
sudo apt update
sudo apt install maven -y

sudo apt update
echo "Installing terraform..."
wget https://releases.hashicorp.com/terraform/0.12.28/terraform_0.12.28_linux_amd64.zip
unzip terraform_0.12.28_linux_amd64.zip
sudo mv terraform /usr/local/bin/

sudo apt update
sudo apt install chromium chromium-l10n -y