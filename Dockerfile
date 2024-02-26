FROM maven:3.8.4-openjdk-17-slim AS builder

RUN mkdir -p /app

WORKDIR /app

COPY ./ /app

WORKDIR /app

RUN mvn -Dmaven.test.skip=true clean package

FROM openjdk:11-ea-17-jre-slim

MAINTAINER Rizky


WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar","app.jar"]