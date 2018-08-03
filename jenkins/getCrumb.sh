#!/bin/bash

. data

# ./getCrumb.sh localhost 8080

HOST=$1
PORT=$2

# https://wiki.jenkins.io/display/JENKINS/Remote+access+API#RemoteaccessAPI-CSRFProtection


CRUMB=$(wget -q --auth-no-challenge --user $USER --password $PASSWORD --output-document - \
         "http://${HOST}:${PORT}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

echo $CRUMB
