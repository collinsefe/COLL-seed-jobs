#!/bin/bash

sudo docker pull collinsefe/dotnet-app-image
sudo docker run -d -p 8082:80 collinsefe/dotnet-app-image

