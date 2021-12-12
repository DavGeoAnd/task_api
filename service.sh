#!/bin/bash

command=$1
env=$2
jar="task_api-2021.12.0.jar"
appPath="/home/davega/Apps/Task/task_api"

install() {
  if [[ "${env}" == "test" ]]; then
    wget https://github.com/DavGeoAnd/task_api/blob/master/output/${jar} -P ${appPath}
  fi
}

start() {
  if [[ "${env}" == "test" ]]; then
    nohup java -jar -Djava.net.preferIPv4Stack=true -Dspring.profiles.active="${env}" -Xms128m -Xmx256m -Denv="${env}" ${appPath}/${jar} &
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
