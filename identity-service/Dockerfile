FROM openjdk:9
ENV TZ=Europe/Kiev
COPY target/kick-scooter-identity.jar /kick-scooter-identity.jar \
     sumo_credentials.txt /home/sumo_credentials.txt \
     sumo-sources.json /home/sumo-sources.json \
     run.sh run.sh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezon \
    wget "https://collectors.sumologic.com/rest/download/linux/64" -O SumoCollector.sh && chmod +x SumoCollector.sh
ENTRYPOINT ["./run.sh"]
