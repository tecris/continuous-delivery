#!/bin/bash

. data

# https://wiki.jenkins.io/display/JENKINS/Remote+access+API#RemoteaccessAPI-CSRFProtection

CRUMB=$(wget -q --auth-no-challenge --user $USER --password $PASSWORD --output-document - \
         'http://localhost:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')

echo $CRUMB
