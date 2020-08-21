#!/bin/bash
docker build ./ -f case1 -t case1:1-alpine
docker build ./ -f case2 -t case2:1-alpine
docker build ./ -f case3 -t case3:1-alpine
docker build ./ -f case4 -t case4:1-alpine
docker build ./ -f case5 -t case5:1-alpine
docker build ./ -f case-base -t case-base:1-alpine
docker build ./ -f case6 -t case6:1-alpine

docker images | grep alpine