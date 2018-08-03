#!/bin/bash

. data

# https://wiki.jenkins.io/display/JENKINS/Remote+access+API#RemoteaccessAPI-CSRFProtection

HOST=localhost

CRUMB=$(wget -q --auth-no-challenge --user $USER --password $PASSWORD --output-document - \
         "http://${HOST}:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

echo $CRUMB
