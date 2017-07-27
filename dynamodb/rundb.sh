#!/bin/bash

# terminate on unchecked errors
set -e

# start local DynamoDB
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb

exit 0
