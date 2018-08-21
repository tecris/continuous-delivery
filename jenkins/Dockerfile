# docker build -f Dockerfile -t trivialis/jenkinsci-blueocean:1.7.2 ./
FROM jenkinsci/blueocean:1.7.2

ENV DOCKER_COMPOSE_VERSION=1.21.2

USER root
RUN apk --update add --no-cache python py-pip \
  && pip install docker-compose==${DOCKER_COMPOSE_VERSION}

USER jenkins
