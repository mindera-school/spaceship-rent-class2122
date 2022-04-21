FROM openjdk:11
COPY target/*.jar app.jar
ENV DB_USER=nunoneto
ENTRYPOINT ["java","-jar","/app.jar"]