# RSO: returnPolicy microservice

## Prerequisites

```bash
docker run -d --name returnPolicy -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=returnPolicy -p 5432:5432 postgres:latest
```

## Run application in Docker

```bash
docker run -p 8080:8080 -e KUMULUZEE_CONFIG_ETCD_HOSTS=http://192.168.99.100:2379 amela/return_policy
```

## Travis status 
[![Build Status](https://travis-ci.org/cloud-computing-project/returnPolicy.svg?branch=master)](https://travis-ci.org/cloud-computing-project/returnPolicy)