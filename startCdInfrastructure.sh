#!/bin/bash

docker-compose -f continuous-delivery.yml up -d sonar-db
docker-compose -f continuous-delivery.yml up -d nexus sonar jenkins
