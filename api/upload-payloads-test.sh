#!/bin/bash

API_URL=http://localhost:8080/api/v1
JSON=/json
YAML=/yml
XML=/xml
TARGET=/target

echo "uploading json"
curl -vX 'POST' \
  "${API_URL}${JSON}${TARGET}" \
  -H 'Content-Type: application/json' \
  -d @./src/test/resources/test_json.json

echo "uploading xml"
curl -vX 'POST' \
  "${API_URL}${XML}${TARGET}" \
  -H 'Content-Type: application/xml' \
  -d @./src/test/resources/test_xml.xml

echo "uploading yaml"
curl -vX 'POST' \
  "${API_URL}${YAML}${TARGET}" \
  -H 'Content-Type: application/yaml' \
  --data-binary @./src/test/resources/test_yaml.yml
