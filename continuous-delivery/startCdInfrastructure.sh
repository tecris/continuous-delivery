#!/bin/bash

sudo mkdir -p /opt/docker_volumes/nexus-data && sudo chown -R 200 /opt/docker_volumes/nexus-data

export DOCKER_IP=`ip -o -4 addr list wlan0 | awk '{print $4}' | cut -d/ -f1`
export DOCKER0_IP=`ip -o -4 addr list docker0 | awk '{print $4}' | cut -d/ -f1`

docker-compose -f continuous-delivery.yml up -d 
