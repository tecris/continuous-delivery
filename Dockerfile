FROM casadocker/alpine-wildfly-mysql:10.1.0

ADD target/bookstore.war /opt/jboss/wildfly/standalone/deployments/bookstore.war
