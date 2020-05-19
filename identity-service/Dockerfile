FROM ubuntu:18.04
ENV TZ=Europe/Kiev \
    TOMCAT=apache-tomcat-9.0.34 \
    APP=kick-scooter-identity.war \
    CONF=server.xml   
RUN apt-get update && \
    apt-get install wget default-jdk -y -y && \
    wget https://downloads.apache.org/tomcat/tomcat-9/v9.0.34/bin/apache-tomcat-9.0.34.tar.gz -P /tmp && \
    tar xf /tmp/$TOMCAT.tar.gz -C /opt/ && \
    mv /opt/$TOMCAT /opt/tomcat
WORKDIR /opt/tomcat 
COPY ./target/$APP ./webapps/
COPY $CONF ./conf/
COPY sumo_credentials.txt \
     sumo-sources.json \
     run.sh \
     /home/
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezon && \
    cd /home/ && wget "https://collectors.sumologic.com/rest/download/linux/64" -O SumoCollector.sh && chmod +x SumoCollector.sh
ENTRYPOINT ["bash", "/home/run.sh"]