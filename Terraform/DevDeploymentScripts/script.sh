#!/bin/bash

# sudo not necessary as scripts run as root

cd /root
mkdir .ssh
cd .ssh
touch known_hosts
ssh-keyscan git.cardiff.ac.uk >> known_hosts
chmod 644 known_hosts

cat << EOF >> deploykey
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
QyNTUxOQAAACAJUi9qfZVLSL1Q6bmnoHwJgnXxJmw5qRbeR+4jqm+dWQAAAKjwgTcM8IE3
DAAAAAtzc2gtZWQyNTUxOQAAACAJUi9qfZVLSL1Q6bmnoHwJgnXxJmw5qRbeR+4jqm+dWQ
AAAEDybDPbA+A/G/+VZTLTeG1d/Cjos5bQdFNsspMrC55JBAlSL2p9lUtIvVDpuaegfAmC
dfEmbDmpFt5H7iOqb51ZAAAAJWNhaXRsaW55aWhAQ2FpdGxpbnMtTWFjQm9vay1Qcm8ubG
9jYWw=
-----END OPENSSH PRIVATE KEY-----
EOF

chmod 400 deploykey
eval "$(ssh-agent -s)"
ssh-add ./deploykey

cd /root

sudo apt-get update
sudo apt-get install git -y
sudo apt install maven -y

git clone --branch dev git@git.cardiff.ac.uk:c1673030/monitoring-machine-activity.git

sudo apt update -y
sudo apt install python-dev python-pip -y
sudo pip install python-openstackclient
source c1673030-openrc.sh

sed -i -e "s@service@dev@" /root/monitoring-machine-activity/src/main/resources/application.properties

cd monitoring-machine-activity

mvn clean package spring-boot:repackage -Dmaven.test.skip=true
mv target/application-0.0.1-SNAPSHOT.jar /home/debian
sudo -u debian java -jar /home/debian/application-0.0.1-SNAPSHOT.jar &