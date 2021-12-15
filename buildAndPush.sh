#!/bin/bash
VERSION=0.0.1
docker build -t kojenka/spotify-songstats-api:$VERSION .
docker tag kojenka/spotify-songstats-api:$VERSION kojenka/spotify-songstats-api:latest
docker push kojenka/spotify-songstats-api:$VERSION
docker push kojenka/spotify-songstats-api:latest