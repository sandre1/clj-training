FROM clojure:temurin-11-tools-deps-bullseye AS build

RUN apt-get update && apt-get install -y git && apt-get clean

WORKDIR /
COPY . /

RUN clj -Sforce -T:build all

ARG PORT=3000

FROM clojure:temurin-17-bullseye

COPY --from=build /target/htmx-demo-standalone.jar /htmx-demo/htmx-demo-standalone.jar

EXPOSE $PORT

ENTRYPOINT exec java $JAVA_OPTS -jar /htmx-demo/htmx-demo-standalone.jar
