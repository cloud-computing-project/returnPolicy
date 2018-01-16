# RSO: return_policy microservice

## Prerequisites

```bash
docker run -d --name rso-return_policy -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=return_policy -p 5432:5432 postgres:latest
```

## Run application in Docker

```bash
docker run -p 8083:8083 -e KUMULUZEE_CONFIG_ETCD_HOSTS=http://192.168.99.100:2379 amela/return_policy
```

## Travis status 
[![Build Status](https://travis-ci.org/cloud-computing-project/return_policy.svg?branch=master)](https://travis-ci.org/cloud-computing-project/return_policy)