#!/bin/bash

API_URL=http://localhost:8080/api/v1/object
TARGET=/target

echo "uploading json"
curl -vX 'POST' \
  "${API_URL}${TARGET}" \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  --form 'file=@./src/test/resources/test_json.json'

echo "uploading xml"
curl -vX 'POST' \
  "${API_URL}${TARGET}" \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  --form 'file=@./src/test/resources/test_xml.xml'

echo "uploading yaml"
curl -vX 'POST' \
  "${API_URL}${TARGET}" \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  --form 'file=@./src/test/resources/test_yaml.yml'

echo "uploading multiple files"
curl -vX 'POST' \
  "${API_URL}${TARGET}" \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  --form 'file=@./src/test/resources/test_json.json' \
  --form 'file=@./src/test/resources/test_xml.xml' \
  --form 'file=@./src/test/resources/test_yaml.yml'
