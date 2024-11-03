#!/bin/bash

sudo docker pull collinsefe/dotnet-app-image
sudo docker run -d -p 8085:80 collinsefe/dotnet-app-image

