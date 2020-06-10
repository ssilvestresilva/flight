FROM openjdk:11-jre
MAINTAINER Silvestre Silva <silvestre.silva@outlook.com / silvestrebunz@gmail.com>
VOLUME /tmp
ARG JAR_FILE
ADD target/flight-1.0.0-SNAPSHOT.jar flight-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "flight-1.0.0-SNAPSHOT.jar"]

