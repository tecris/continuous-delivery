#!/bin/bash

. data

# ./createJob.sh localhost 8080 continuous-delivery config_jenkins2x.xml

HOST=$1
PORT=$2
JOB_NAME=$3
FILE=$4

# https://wiki.jenkins.io/display/JENKINS/Remote+access+API#RemoteaccessAPI-CSRFProtection

CRUMB=$(wget -q --auth-no-challenge --user $USER --password $PASSWORD --output-document - \
         "http://${HOST}:${PORT}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

curl -H "Content-Type: application/xml" \
        -H "$CRUMB" \
        -X POST "http://$HOST:$PORT/createItem?name=$JOB_NAME&token=$TOKEN" \
        -u $USER:$PASSWORD \
        --data-binary "@$FILE"
