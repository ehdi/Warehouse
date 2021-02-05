#
# Build stage
#

FROM maven:3.6.3-jdk-11-slim AS builder

WORKDIR /build
COPY ./pom.xml .

#Copy source code
COPY ./src ./src

# Build application

RUN mvn package

#
# Package stage
#

FROM adoptopenjdk/openjdk11-openj9:alpine-jre

WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar


ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    START_SLEEP=0 \
    JAVA_OPTS=""

EXPOSE 8080

CMD echo "The application will start..." && \
    sleep ${START_SLEEP} && \
    java ${JAVA_OPTS} -Dsun.misc.URLClassPath.disableJarChecking=true \
                      -Djava.io.tmpdir=/var/tmp \
                      -Djava.security.egd=file:/dev/./urandom \
                      -jar ./app.jar
