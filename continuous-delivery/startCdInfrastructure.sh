#!/bin/bash

sudo mkdir -p /opt/docker_volumes/nexus-data && sudo chown -R 200 /opt/docker_volumes/nexus-data

docker-compose -f continuous-delivery.yml up -d sonar-db
docker-compose -f continuous-delivery.yml up -d nexus sonar jenkins
