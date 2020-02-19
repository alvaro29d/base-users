FROM mysql:5.7.20


-e MYSQL_HOST=127.0.0.1
-e JDBC_DATABASE_USERNAME=root
-e JDBC_DATABASE_PASSWORD=admin
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=../target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080