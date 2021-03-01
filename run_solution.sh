#!/bin/sh
echo "Building the solution"

if mvn -v ; then
   echo ok
else
   echo This solution needs maven to be installed.
   exit 1
fi

echo "=============================================="
echo "Building the Blob Store"
echo "=============================================="
cd blob-store
if mvn clean install 2> /dev/null; then
   echo "Done"
fi
echo "=============================================="
cd ..

echo "=============================================="
echo "Building the Worker Service"
echo "=============================================="
cd worker-service
if mvn clean install 2> /dev/null; then
   echo "Done"
fi

cd ..
echo "=============================================="
echo "Building the Image Processing Service"
echo "=============================================="
cd image-processing
if mvn clean install 2> /dev/null; then
   echo "Done"
fi


echo "Starting the containers"
docker-compose up --build
