#!/bin/bash

DEFAULT_BITSIZE=512

read -p "RSA Key Name: " KEY_NAME
read -p "Size (512,1024,2048... default ${DEFAULT_BITSIZE})" INPUT_BITSIZE

BITSIZE=${INPUT_BITSIZE:-${DEFAULT_BITSIZE}}

DATE=$(date +'%Y%m%d')

PRIVATE_KEY="${KEY_NAME}-${DATE}-priv.pem"
PUBLIC_KEY="${KEY_NAME}-${DATE}-pub.pem"
PKCS8_KEY="${KEY_NAME}-${DATE}-pkcs8.pem"

echo "Executing the following commands the following keys: "
echo "openssl genrsa -out ${PRIVATE_KEY} ${BITSIZE}"
echo "openssl rsa -in ${PRIVATE_KEY} -out ${PUBLIC_KEY} -pubout"
echo "openssl pkcs8 -in ${PRIVATE_KEY} -topk8 -nocrypt -out ${PKCS8_KEY}"

read -p "Do you want to continue? [Yy] " -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    exit 1
fi

openssl genrsa -out ${PRIVATE_KEY} ${BITSIZE}
openssl rsa -in ${PRIVATE_KEY} -out ${PUBLIC_KEY} -pubout
openssl pkcs8 -in ${PRIVATE_KEY} -topk8 -nocrypt -out ${PKCS8_KEY}

