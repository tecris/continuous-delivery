#!/bin/bash

kubectl create -f kubernetes/planets-db-pod.yaml
kubectl create -f kubernetes/planets-db-svc.yaml
kubectl create -f kubernetes/planets-web-rc-v1.yaml
kubectl create -f kubernetes/planets-web-svc.yaml
