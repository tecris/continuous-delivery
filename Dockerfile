FROM casadocker/alpine-wildfly-mysql:13.0.0

ADD target/bookstore.war /opt/jboss/wildfly/standalone/deployments/bookstore.war
