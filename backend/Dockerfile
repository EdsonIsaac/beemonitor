FROM openjdk:latest

VOLUME /tmp

ADD ./target/beemonitor-api-1.0.0.jar beemonitor-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/beemonitor-api.jar"]