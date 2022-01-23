#!/bin/bash
sudo apt update
echo "installing gitlab server key..."
sudo touch ~/.ssh/known_hosts
sudo chmod 777 ~/.ssh/known_hosts
sudo ssh-keyscan git.cardiff.ac.uk >> ~/.ssh/known_hosts
sudo chmod 644 ~/.ssh/known_hosts

echo "installing MariaDB..."
sudo apt update
sudo apt install mariadb-server -y
sudo systemctl start mariadb
sudo systemctl status mariadb
sudo systemctl enable mariadb

sudo apt update
sudo apt install wget -y
sudo apt install unzip -y
sudo apt install git -y


cat << `EOF` >> private_key.key
-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----
`EOF`
chmod 400 private_key.key

sudo ssh-agent bash -c 'ssh-add private_key.key; git clone -b dev git@git.cardiff.ac.uk:c1673030/monitoring-machine-activity.git;' -y


echo "Installing Java 11..."
sudo apt install default-jdk -y


echo "Installing maven..."
sudo apt update
sudo apt install maven


sudo chmod 777 ~/monitoring-machine-activity
cd monitoring-machine-activity


./mvnw spring-boot:run
