#!/bin/bash
cd IdentityService
./mvnw clean package -DskipTests
java -jar target/IdentityService-0.0.1-SNAPSHOT.jar