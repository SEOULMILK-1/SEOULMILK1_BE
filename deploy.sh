#!/bin/bash

if docker ps | grep -q blue; then
  IS_BLUE_RUNNING=true
else
  IS_BLUE_RUNNING=false
fi

docker pull m020202/seoulmilk

# blue가 실행 중이면 green을 up
if [ $IS_BLUE_RUNNING = true ];then
  docker run -d -p 8081:8080 --name green m020202/seoulmilk:latest
  sleep 2
  sudo sh -c 'echo "set \$service_url green;" > /etc/nginx/conf.d/service-env.inc'
  sudo nginx -s reload
  docker rm -f blue

# green이 실행 중이면 blue를 up
else
  docker run -d -p 8080:8080 --name blue m020202/seoulmilk:latest
  sleep 2
  sudo sh -c 'echo "set \$service_url blue;" > /etc/nginx/conf.d/service-env.inc'
  sudo nginx -s reload
  docker rm -f green
fi

docker rmi $(docker images --format "{{.Repository}}:{{.Tag}} {{.ID}}" | grep "m020202/seoulmilk:<none>" | awk '{print $2}')



