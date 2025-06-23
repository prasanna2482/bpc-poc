FROM tomcat:10.1.24-jdk17-temurin AS build
RUN apt-get update && apt-get install -y maven
RUN mkdir -p /workspace
WORKDIR /workspace
COPY bpc/pom.xml /workspace
COPY bpc/src /workspace/src

RUN mvn -B package --file pom.xml -DskipTests -Dskip
FROM tomcat:10.1.24-jdk17-temurin
LABEL org.opencontainers.image.source="https://github.com/etn-corp/bpc"
RUN chmod -R a+rwx $CATALINA_HOME/webapps
RUN chmod 777 $CATALINA_HOME/conf
RUN rm -rf $CATALINA_HOME/conf/logging.properties
RUN rm -rf $CATALINA_HOME/conf/server.xml
COPY bpc/logging.properties $CATALINA_HOME/conf
COPY bpc/server.xml $CATALINA_HOME/conf
COPY --from=build /workspace/target/*.war $CATALINA_HOME/webapps/bpc.war

EXPOSE 8080
CMD ["catalina.sh", "run"]