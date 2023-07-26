#!/bin/bash

API_URL=http://localhost:8080/api/v1/object
BUCKET=/mybucket
PATH_AND_SUBPATH=/mypath/subpath
echo "uploading image"
curl -vX 'POST' \
  "${API_URL}${BUCKET}${PATH_AND_SUBPATH}" \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  --form 'file=@./src/test/resources/bilde.jpg'


echo "dowloading same image and opens preview"
curl -vX 'GET' \
  "${API_URL}${BUCKET}${PATH_AND_SUBPATH}/bilde.jpg" \
  -H 'accept: multipart/form-data' --output testimage.jpg

eog testimage.jpg &

read -p "press any key to clean up"
curl -vX 'DELETE' \
  "${API_URL}${BUCKET}${PATH_AND_SUBPATH}/bilde.jpg" \
  -H 'accept: application/json'

rm testimage.jpg