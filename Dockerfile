FROM adoptopenjdk/openjdk11:alpine
COPY target/builder-api-1.0.jar backend-builder-api.jar
ENTRYPOINT ["java","-jar","/backend-builder-api.jar"]