FROM openjdk:14
WORKDIR /app
COPY ./dist .
COPY ./dist/lib ./lib
EXPOSE 15000
ENTRYPOINT [ "java", "-jar", "servidorAPI.jar" ]