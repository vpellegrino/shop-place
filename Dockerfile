FROM openjdk:8-jre-alpine

RUN apk update && apk add bash

WORKDIR /app

COPY /target/shop-place-app.jar /app

EXPOSE 8080

CMD ["java", "-jar", "shop-place-app.jar"]
