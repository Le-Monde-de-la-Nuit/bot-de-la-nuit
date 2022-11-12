FROM gradle:alpine AS builder

WORKDIR /app

COPY . .

RUN ./gradlew shadowJar

FROM openjdk:17-jdk-alpine

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
ARG TOKEN
ARG ROLE_ID

COPY --from=builder /app/$JAR_FILE /app/app.jar

ENTRYPOINT java -jar app.jar $TOKEN $ROLE_ID