FROM dvmarques/openjdk-17-jdk-alpine-with-timezone AS builder

RUN mkdir -p /app

WORKDIR /app

COPY ./ /app

WORKDIR /app

RUN mvn -Dmaven.test.skip=true clean package

FROM openjdk:17-alpine

MAINTAINER Rizky


WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar","app.jar"]