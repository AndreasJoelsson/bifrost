#!/bin/bash

API_URL=http://localhost:8080/api/v1
ANY=/payload
TARGET=/target

echo "uploading json to any"
curl -vX 'POST' \
  "${API_URL}${ANY}${TARGET}" \
  -H 'Content-Type: application/json' \
  -d @./src/test/resources/test_json.json

echo "uploading xml to any"
curl -vX 'POST' \
  "${API_URL}${ANY}${TARGET}" \
  -H 'Content-Type: application/xml' \
  -d @./src/test/resources/test_xml.xml

echo "uploading yaml to any"
curl -vX 'POST' \
  "${API_URL}${ANY}${TARGET}" \
  -H 'Content-Type: application/yaml' \
  --data-binary @./src/test/resources/test_yaml.yml