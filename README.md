# Sistemas Distribuidos - Laboratorio#2

### Estructura del Dockerfile del servidor de almacen y reenvio.

    FROM openjdk:14
    WORKDIR /app
    COPY ./dist .
    COPY ./dist/lib ./lib
    EXPOSE 15000
    ENTRYPOINT ["java","-jar","servidorAPI.jar"]

### Comandos de ejecucion

1) Red en docker

        docker network create --subnet 10.0.0.0/16 sd2021

2) Imagen del servidor (construir el dockerfile)

        docker build -t servidorapi:latest .

3) Ejecucion del contenedor, levantar una instancia de servidor de almacenamiento (ejecutar esto primero):

        docker run -it -p 15000:15000 --network-alias servidoralma --network sd2021 --ip 10.0.0.20 --name "servidoralma" servidorapi:latest

4) Ejecucion del contenedor, levantar una istancia de servidor de reenvio (una vez el servidor de almacen este en ejecucion):

        docker run -it -p 15001:15000 --network-alias servidorreen --network sd2021 --name "servidorreen" servidorapi:latest 10.0.0.20 15000

> NOTA: para crear un contenedor con un servidor de reenvio, la imagen servidorapi recibe dos parametros, ip donde esta el contenedor del servidor del almacen, y su puerto.

5) Detener el/los contenedor/es

        docker stop servidoralma
        docker stop servidorreen

6) Iniciar el/los contenedor/es

        docker start -i servidoralma
        docker start -i servidorreen

7) El cliente se ejecuta en la IDE netbeans, conectándose al servidor de reenvio en localhost 15001, en el host.