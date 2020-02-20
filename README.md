![Java CI](https://github.com/alvaro29d/base-users/workflows/Java%20CI/badge.svg)
# BASE USERS

This is a simple rest API used to admin users.


### Run `base-users` with H2

In order to run the application using an H2 database, use the command:
```
mvn spring-boot run
```


### Create and run a docker container of `base-users`

In order to create a docker image, you can execute the commands:

```
mvn clean install
docker build -t base-users:latest .
```

Then use the following command to start the application (you need to have access to a mysql database and previously run the scripts located in `src/main/resources/creation-scripts`

```
docker run -d --name users -p 8080:8080 \
-e SPRING_PROFILES_ACTIVE=prod \
-e MYSQL_HOST=172.0.0.1 \
-e JDBC_DATABASE_USERNAME=root \
-e JDBC_DATABASE_PASSWORD=admin \
base-users:latest
```


### Run `base-user` with mysql and docker-compose

There's a `docker-compose.yml` file that runs the following components:
- A `mysql` server that initializes the database with the scripts located on `src/main/resources/creation-scripts`.
- The `users` service.

To start docker-compose use these commands:

```
mvn clean install
docker-compose up -d
```
