#!/bin/bash

./mvnw clean install -DskipTests=false

# set profile

export APP_CONFIG="$PWD/test-config.yml"
echo $APP_CONFIG

java -jar api/target/api-0.0.1-SNAPSHOT.jar
