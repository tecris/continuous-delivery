#!/bin/bash

. data

# ./createJob.sh localhost 8080 bookstore config.xml

HOST=$1
PORT=$2
JOB_NAME=$3
FILE=$4

#curl -X POST "http://$HOST:$PORT/createItem/createItem?name=$JOB_NAME" --data-binary "@$FILE" -H "Content-Type: text/xml"

curl -X POST "http://$HOST:$PORT/iv-jenkins/createItem/createItem?name=$JOB_NAME&token=$TOKEN" \
         -u $USER:$PASSWORD \
         --data-binary "@$FILE" \
         -H "Content-Type: text/xml"
