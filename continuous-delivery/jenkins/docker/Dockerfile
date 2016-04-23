FROM casadocker/alpine-wildfly-mysql:10.0.0

ENV MAVEN_GROUP_ID org.terra
ENV MAVEN_ARTIFACT_ID planet

ENV MAVEN_REPOSITORY_URL nexus

RUN wget "http://${MAVEN_REPOSITORY_URL}:8081/service/local/artifact/maven/redirect?r=snapshots&g=${MAVEN_GROUP_ID}&a=${MAVEN_ARTIFACT_ID}&v=LATEST&e=war" -O /opt/jboss/wildfly/standalone/deployments/bookstore.war 
