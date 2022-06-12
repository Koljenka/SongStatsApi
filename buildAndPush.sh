#!/bin/bash
VERSION=0.2.0
gradle clean
gradle bootJar
docker build -t kojenka/spotify-songstats-api:$VERSION .
docker tag kojenka/spotify-songstats-api:$VERSION kojenka/spotify-songstats-api:latest
docker push kojenka/spotify-songstats-api:$VERSION
docker push kojenka/spotify-songstats-api:latest