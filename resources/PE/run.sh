#!/bin/bash
# hello


npm install -y
rm -rf prisma
npm install prisma -y
npx prisma init --datasource-provider sqlite
echo resources/schema.txt >> prisma/schema.prisma
#Run Migration: 
npx prisma migrate dev --name first migration
npm run dev


# 4. npx prisma studio - to see the database in the UI
# npx prisma generate -y   # if you can't find your model
# prisma format
# 6. npx prisma db push

# TODO:
# add a technique to copy .env file to ec2
# run prisma jon in detached mode