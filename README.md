# Docker 설정

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

 * Redis (redis.conf 설정)
  
```
mkdir -p /redis/conf
vi /redis/conf/redis/conf
```
```
# By default, if no "bind" configuration directive is specified, Redis listens
# for connections from all the network interfaces available on the server.
#
# Examples:
# bind 192.168.1.100 10.0.0.1
# bind 127.0.0.1 ::1

# Accept connections on the specified port, default is 6379 (IANA #815344).
port 6379

# TCP keepalive
tcp-keepalive 300

# Password
requirepass shinwoo123!
```

 * Redis 설치
```
docker pull redis

docker run \
 --name myRedis \
 -d \
 -p 6379:6379 \
 -v /redis/conf:/usr/local/etc/redis \
 --restart always \
 redis \
 redis-server /usr/local/etc/redis/redis.conf
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

 * ipm-manager
```
docker run \
 --name=ipm-manager \
 -itd \
 -p 8000:8000 \
 -v /logs/ipm-manager:/logs \
 --restart always \
 ipm-base:0.1 \
 /bin/bash
```

* ipm-insight
```
docker run \
 --name=ipm-insight \
 -itd \
 -p 514:514/udp \
 -p 8001:8001 \
 -v /logs/ipm-insight:/logs \
 --restart always \
 ipm-base:0.1 \
 /bin/bash
```

* Timezone 설정
```
rm /etc/localtime

ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
```

## ipm-base 이미지 생성

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
 
## Container 기본 설정

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

## ipm-base 이미지 Build

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


# SinwooTNS - Github Repository

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
