# FROM amazoncorretto:17-alpine
FROM maven:3.8.3-amazoncorretto-17

WORKDIR /app

COPY . /app
RUN mvn package

CMD bash
