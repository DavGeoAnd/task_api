#!/bin/bash

#live
#Deployment Directory - /home/davega/Apps/Task/task_api
#cp /mnt/c/Users/DaveG/Desktop/Apps/Task/task_api/service.sh .;dos2unix service.sh;chmod 755 service.sh;

command=$1
env=$2
jar="task_api-2021.11.0.jar"
appPath="/home/davega/Apps/Task/task_api"

install() {
  if [[ "${env}" == "test" ]]; then
    cp /mnt/c/Users/DaveG/Desktop/Apps/Task/task_api/output/${jar} ${appPath}
  fi

  if [[ "${env}" == "live" ]]; then
    cp /home/davega/IdeaProjects/task_data/output/${jar} ${appPath}
  fi
}

start() {
  if [[ "${env}" == "test" ]]; then
      nohup java -jar -Djava.net.preferIPv4Stack=true -Dspring.profiles.active="${env}" ${appPath}/${jar} &
      echo "Started Application"
    fi
  if [[ "${env}" == "live" ]]; then
    nohup java -jar -Djava.net.preferIPv4Stack=true -Dspring.profiles.active="${env}" -DenableKafka=false ${appPath}/${jar} &
    echo "Started Application"
  fi
}

stop() {
  ps axo user:15,pid,args | grep ${jar} | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "Stopped Application"
}

status() {
  ps axo user:15,pid,args | grep ${jar} | grep -v grep
}

if [[ "${command}" == "install" ]]; then
  install
elif [[ "${command}" == "start" ]]; then
  start
elif [[ "${command}" == "stop" ]]; then
  stop
elif [[ "${command}" == "restart" ]]; then
  stop
  start
elif [[ "${command}" == "status" ]]; then
  status
fi
