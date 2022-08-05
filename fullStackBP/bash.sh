#!/bin/bash

systemctl start docker

mvn clean package -DskipTests 

sudo docker build -t pichincha .

sudo docker pull mysql:8

sudo docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:8

sleep 10s

sudo docker run -d -p 8080:8080 --name pichincha-back --link mysql-standalone:mysql pichincha




