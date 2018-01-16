FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./api/target/return_policy-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8083

CMD ["java", "-jar", "return_policy-api-1.0.0-SNAPSHOT.jar"]