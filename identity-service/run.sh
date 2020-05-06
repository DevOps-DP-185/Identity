#!bin/bash

./SumoCollector.sh -q -varfile /home/sumo_credentials.txt -Vcollector.name=identity

cd /home/ && java -jar *.jar
