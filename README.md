[![Build Status](https://travis-ci.org/klindziukp/evbx-resource.svg?branch=master)](https://travis-ci.org/github/klindziukp/evbx-resource)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-resources&metric=alert_status)](https://sonarcloud.io/dashboard?id=klindziukp_evbx-resources)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-resources&metric=coverage)](https://sonarcloud.io/component_measures?id=klindziukp_evbx-resources&metric=coverage)

# evbx-resources server
Used to store resources for '[evbx-product](https://github.com/klindziukp/evbx-product)' server 

## OpenAPI Specification
* Open [swagger-editor](http://editor.swagger.io/)
* Import file from `contract/evbx-resource-contract.yaml`
## Set up database
#### Using docker MySQL image
* If you already MySQL container installed and running - just create database(schema) `evbx_resource`
* Configure MySQL database in the `src/main/resources/docker-compose.yml`
* Execute command from __project root__ directory `docker-compose -f src/main/resources/docker-compose.yml up -d`
* Verify that MySQL container is started with command `docker ps`
* Create database(schema) `evbx_resource`
#### Using MySqL instead of docker image
* Install MySQL database
* Create database(schema) `evbx_resource`

## Database migrations
* Update database configuration in `src/main/resources/application.yml` according to MySQL db configuration
```
datasource:
    url: jdbc:mysql://localhost:3306/evbx_resource
    username: root
    password: root
```
* Update data base configuration in `build.gradle` according to MySQL db configuration
```
flyway {
	url = 'jdbc:mysql://localhost:3306/evbx_resource'
	user = 'root'
	password = 'root'
}
```
* Execute command from __project root__ directory `./gradlew flywayMigrate`
* If some errors appeared - execute command from __project root__ directory `./gradlew flywayRepair`
## Application start
* Execute command from __project root__ directory `./gradlew clean build bootRun -x test` 
