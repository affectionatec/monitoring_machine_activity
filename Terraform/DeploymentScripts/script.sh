#!/bin/bash

whoami
pwd

apt update
sudo apt install unzip
apt install maven -y

export AWS_ACCESS_KEY_ID=AKIAQTEPV5V3JDY5BPGK
export AWS_SECRET_ACCESS_KEY=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
export AWS_DEFAULT_REGION=eu-west-2

aws s3 cp s3://build-jars/application-0.0.1-SNAPSHOT.jar app.jar

java -jar app.jar &