FROM java:8
COPY ./midcloud-sso-server/src/main/resources/config /app/midcloud_sso/
VOLUME /tmp
ADD ./midcloud-sso-server/build/libs/midcloud-sso-server.jar /app/midcloud_sso/midcloud-sso-server.jar
WORKDIR /app/midcloud_sso
