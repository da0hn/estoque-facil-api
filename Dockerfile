FROM amazoncorretto:21

COPY target/*.jar /api.jar

LABEL authors="da0hn, nandobrazil"
LABEL version="1.0.0"

ENV ESTOQUE_FACIL_DB_HOST=localhost
ENV ESTOQUE_FACIL_DB_PORT=5432
ENV ESTOQUE_FACIL_DB_USER=user
ENV ESTOQUE_FACIL_DB_PASSWORD=123456
ENV DOCKER_COMPOSE_ENABLED=false

EXPOSE 8200

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Duser.region=BR -Duser.language=pt ${JAVA_OPTS} -jar /api.jar"]
