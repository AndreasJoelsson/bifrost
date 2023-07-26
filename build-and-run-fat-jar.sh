#!/bin/bash

./mvnw clean install -DskipTests=false

# set profile

java -jar api/target/api-0.0.1-SNAPSHOT.jar
