FROM node:23 AS ngbuild

WORKDIR /frontend

COPY frontend/src src
COPY frontend/*.json .
COPY frontend/public public

RUN npm ci \
    && npm run build



FROM eclipse-temurin:23-noble AS javabuild

WORKDIR /server

COPY server/pom.xml .
COPY server/.mvn .mvn
COPY server/mvnw .
COPY server/src src

COPY --from=ngbuild /frontend/dist/frontend/browser src/main/resources/static

RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true


# jre does not contain compiler
FROM eclipse-temurin:23-jre-noble

WORKDIR /app
COPY --from=javabuild /server/target/csf_36w-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

SHELL ["/bin/sh", "-c"]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar