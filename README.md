# SinwooTNS - Github Repository


## Docker 설정

 * Rabbitmq 설치
```
docker pull rabbitmq

docker run \
 --name=myRabbitmq \
 -d \
 -p 5672:5672 \
 -p 15672:15672 \
 -e RABBITMQ_DEFAULT_USER=admin \
 -e RABBITMQ_DEFAULT_PASS=shinwoo123! \
 --restart always \
 rabbitmq:3-management
```

 * Redis 설치
```
docker pull redis

docker run \
 --name myRedis \
 -d \
 -p 6379:6379 \
 -e REDIS_PASS=shinwoo123! \
 --restart always \
 redis
```

 * PostgreSQL 설치
```
docker pull postgres

docker run \
 --name=myPostgres \
 -d \
 -p 5432:5432 \
 -e POSTGRES_PASSWORD=shinwoo123! \
 -v /db_data/pgdata:/pgdata \
 --restart always \
 postgres
```

#### ipm-base 이미지 생성

 * 기본 container 생성
```
docker pull centos

docker run \
 --name=ipm-temp \
 -d \
 centos \
 /bin/bash
```

 * container로 jdk 설치 파일 복사 (rpm)
```
 docker cp jdk-8u91-linux-x64.rpm ipm-temp:/
```
 
#### Container 기본 설정

  - container 접속
```
$ docker exec -it ipm-temp /bin/bash
```
  
  - jdk 설치
```
$ yum install jdk-8u91-linux-x64.rpm
```

  - docker-entrypoint.sh 생성

```
$ vi /docker-entrypoint.sh
```
```
#!/bin/bash

java -jar /ipm/swt-manager-0.0.1.jar --spring.config.location=/ipm/app.properties
```
  - 실행 권한 설정
```
$ chmod 755 /docker-entrypoint.sh
```

 * ipm-base 기본 이미지 생성 (ipm-base)

```
$ docker commit ipm-temp ipm-base
```

##### ipm-base 이미지 Build

  - Dockerfile 생성
```
vi Dockerfile
```
```
FROM ipm-base
MAINTAINER songagi <songagi@gmail.com>

RUN yum update

ENTRYPOINT ["/docker-entrypoint.sh", "-D", "FOREGROUD"]
```

 * 이미지 Build
```
docker build --tag ipm-base:1.0 .
```


### swt-common 연동 방법(Maven)

- ***pom.xml***에 코드 추가

```xml
...
	
<repositories>
	<repository>
		<id>songagi-mvn-repo</id>
		<url>https://github.com/songagi/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>

...
	
<dependencies>
	...
	<dependency>
		<groupId>com.shinwootns</groupId>
		<artifactId>swt-common</artifactId>
		<version>0.1.6</version>
	</dependency>
	...
</dependencies>
```

===============================
...
