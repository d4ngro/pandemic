FROM openjdk:alpine

MAINTAINER Daniel Grohmann <daniel@tosto.de>

ENV AWS_ACCESS_KEY_ID=not_used_but_do_not_remove
ENV AWS_SECRET_ACCESS_KEY=not_used_but_do_not_remove

COPY pandemic-0.1.0.jar /pandemic.jar

ENTRYPOINT ["java", "-jar", "pandemic.jar"]
