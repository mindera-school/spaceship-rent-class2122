FROM openjdk:11
COPY target/*.jar app.jar
ENV DB_USER=ruiteixeira
ENV DB_PASS=dro
ENV RABBIT_USER=guest
ENV RABBIT_PASSWORD=guest
ENTRYPOINT ["java","-jar","/app.jar"]
