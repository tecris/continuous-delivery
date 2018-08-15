FROM trivialis/maven:3 as bookstore-builder

COPY . .
RUN mvn clean -DskipTests package

FROM casadocker/alpine-wildfly-mysql:13.0.0

COPY --from=bookstore-builder target/bookstore.war /opt/jboss/wildfly/standalone/deployments/
