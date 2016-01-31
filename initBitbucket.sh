#!/bin/bash

wget https://raw.githubusercontent.com/tecris/backup/v16.01.29/configureBitbucket.sh
chmod 744 configureBitbucket.sh
./configureBitbucket.sh
rm configureBitbucket.sh
