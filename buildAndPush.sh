#!/bin/bash
VERSION=2.1.0-SNAPSHOT
./gradlew clean
./gradlew bootJar
docker build -t kojenka/spotify-songstats-api:$VERSION .
docker tag kojenka/spotify-songstats-api:$VERSION kojenka/spotify-songstats-api:swagger-latest
docker push kojenka/spotify-songstats-api:$VERSION
docker push kojenka/spotify-songstats-api:swagger-latest