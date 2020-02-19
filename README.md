![Java CI](https://github.com/alvaro29d/base-users/workflows/Java%20CI/badge.svg)
# BASE USERS

This is a simple rest API used to admin users.






In order to create a docker image, you can excecute the command
```
docker build -t base-users:0.0.1 .
``` 

Then use the following command to start the application

```
docker run -d --name users -p 8080:8080 base-users:0.0.1 \
-e SPRING_PROFILES_ACTIVE=prod
-e MYSQL_HOST=127.0.0.1
-e JDBC_DATABASE_USERNAME=root
-e JDBC_DATABASE_PASSWORD=admin
```

```
docker run -d -p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=admin \
mysql:5.7.20

```

