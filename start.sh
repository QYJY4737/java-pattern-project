#!/bin/bash
kill -9 `ps -ef|grep 'java-pattern-project.jar'|awk '{print $2}'`
kill -9 `ps -ef|grep 'pattern.log'|awk '{print $2}'`

java -Xms512m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:ParallelGCThreads=4 -jar java-pattern-project.jar --spring.application.name=java-pattern-project --spring.profiles.active=dev --server.port=2333 > pattern.log &

