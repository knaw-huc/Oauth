#!/bin/sh
mvn clean package
JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
export JAVA_OPTS
./target/appassembler/bin/authapp server hello-world.yml

