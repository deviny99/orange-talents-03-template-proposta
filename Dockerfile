FROM adoptopenjdk/openjdk11:alpine
MAINTAINER Marcos Rocha <nyrocha2010@hotmail.com>
WORKDIR /app
COPY target/*.jar /app/proposta.jar
ENV PORT=8081
ENV PROFILE=dev
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}", "-jar", "proposta.jar"]
EXPOSE ${PORT}