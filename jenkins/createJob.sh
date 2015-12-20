#!/bin/bash

# ./createJob.sh localhost planets config.xml

HOST=$1
JOB_NAME=$2
FILE=$3


curl -X POST "http://$HOST:8088/createItem/createItem?name=$JOB_NAME" --data-binary "@$FILE" -H "Content-Type: text/xml"
