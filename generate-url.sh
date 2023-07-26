#!/bin/bash

function join_by {
    local IFS="$1";
    shift;
    echo "$*";
}

DEFAULT_URL="http://s3vegbilder.utv.atlas.vegvesen.no"
read -p "URL (default ${DEFAULT_URL}): " INPUT_URL
URL=${INPUT_URL:-${DEFAULT_URL}}

DEFAULT_BUCKET="test-bucket"
read -p "BUCKET (default ${DEFAULT_BUCKET}): " INPUT_BUCKET
BUCKET=${INPUT_BUCKET:-${DEFAULT_BUCKET}}

DEFAULT_PATH="sunset.jpg"
read -p "PATH (default ${DEFAULT_PATH}): " INPUT_PATH
PATH=${INPUT_PATH:-${DEFAULT_PATH}}

DEFAULT_FROM=$(/bin/date -u +'%FT%T.%2NZ')
read -p "FROM (default ${DEFAULT_FROM}): " INPUT_FROM
FROM=${INPUT_FROM:-${DEFAULT_FROM}}

DEFAULT_TO=`/bin/date -d '1 hour' -u +'%FT%T.%2NZ'`
read -p "TO (default ${DEFAULT_TO}): " INPUT_TO
TO=${INPUT_TO:-${DEFAULT_TO}}

DEFAULT_KEY="VF1"
read -p "KEY (default ${DEFAULT_KEY}): " INPUT_KEY
KEY=${INPUT_KEY:-${DEFAULT_KEY}}

DEFAULT_SECRET="secret"
read -p "SECRET (default ${DEFAULT_SECRET}): " INPUT_SECRET
SECRET=${INPUT_SECRET:-${DEFAULT_SECRET}}

SP="mrjf" # m = multi (u = unique), r = read

# test-bucket,sunset.jpg,2021-01-12T12:03:45Z,ur,2021-01-12T11:58:45Z!
JOINED=$(join_by , $BUCKET $PATH $TO $SP $FROM)

# echo "$JOINED"

SIGNATURE=$(echo -n "${JOINED}" | /bin/openssl dgst -sha256 -hmac ${SECRET} | /bin/cut -d " " -f 2 | /bin/xxd -r -p | /bin/base64)

# echo "$SIGNATURE"

# http://s3vegbilder.utv.atlas.vegvesen.no/test-bucket/sunset.jpg
# ?st=2021-01-12T11:58:45Z
# &se=2021-01-12T12:03:45Z
# &sp=mr
# &sv=VF2
# &sig=pAM09WDQM1v+JNJdL+HF9Fh1Z+t1tPNhBDe26kmobO8=

echo "${URL}/${BUCKET}/${PATH}?st=${FROM}&se=${TO}&sp=${SP}&sv=${KEY}&sig=${SIGNATURE}"
