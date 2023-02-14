FROM amazoncorretto:17-alpine as build
LABEL maintainer="juphich <juphich@sugarbricks.co.kr>"
WORKDIR workspace
ARG ARCHIVE_FILE
COPY ["${ARCHIVE_FILE}", "service.jar"]

RUN java -Djarmode=layertools -jar service.jar extract


FROM amazoncorretto:17-alpine as deploy
WORKDIR /var/run/sugarbricks

COPY --from=build workspace/dependencies/ ./
COPY --from=build workspace/spring-boot-loader/ ./
COPY --from=build workspace/snapshot-dependencies/ ./
COPY --from=build workspace/application/ ./

ENV SPRING_PROFILE="default,cloud"
ENV CLOUD_CONFIG_SERVER="http://config-server"
ENV CLOUD_EUREKA_SERVER="http://eureka-server/eureka/"

EXPOSE 8080

ENTRYPOINT ["java", \
    "-Dserver.port=8080", \
    "-Dspring.profiles.active=${SPRING_PROFILE}", \
    "-Dspring.cloud.config.uri=${CLOUD_CONFIG_SERVER}", \
    "-Deureka.client.serviceUrl.defaultZone=${CLOUD_EUREKA_SERVER}", \
    "org.springframework.boot.loader.JarLauncher"]
