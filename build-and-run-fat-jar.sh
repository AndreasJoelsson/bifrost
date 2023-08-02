#!/bin/bash

./mvnw clean install -DskipTests=false

# set profile
if [ "$1" == "-l" ] || [ "$1" == "--local" ]; then
  export SPRING_CONFIG_NAME="application-local"
  export SPRING_CONFIG_LOCATION=file:${PWD}/
fi

export APP_CONFIG="$PWD/test-config.yml"
echo $APP_CONFIG

java -jar api/target/api-0.0.1-SNAPSHOT.jar
