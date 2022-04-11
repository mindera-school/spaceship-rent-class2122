FROM openjdk:11
COPY target/*.jar app.jar
ENV DB_USER=postgres
ENTRYPOINT ["java","-jar","/app.jar"]