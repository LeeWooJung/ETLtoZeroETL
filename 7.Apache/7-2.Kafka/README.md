# Docker Kafka 설치

[링크](https://wefree.tistory.com/85)  

## Kafka-docker clone

```bash

git clone https://github.com/wurstmeister/kafka-docker.git

```  

## Edit Docker File 
[Release](https://github.com/wurstmeister/kafka-docker#tags-and-releases)

```vim
ARG kafka_version=2.8.1
ARG scala_version=2.13

# VOLUME["/kafka"] # Docker-compose.yml에서 처리할 예정이라 주석처리함.
```

**Clone해온 Kafka, scala version 그대로 사용**  
**Volume만 수정**

## Edit docker-compose.yml

``` vim
version: '2.4'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
    restart: unless-stopped

  kafka:
    build: .
    ports:
        - "9092:9092" # 9092 -> 9092
    environment:
      DOCKER_API_VERSION: 1.46 # docker version
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1 # local host
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_MESSAGE_MAX_BYTES: 10000000
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
      KAFKA_DELETE_TOPIC_ENABLE: 'true'

    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - /kafka/data:/kafka # store data

    #restart: unless-stopped
```

## Docker-compose 실행

```bash
docker-compose up -d
```

### Error

* **Client version Error**  

``` bash
ERROR: client version 1.22 is too old. Minimum supported API version is 1.24, please upgrade your client to a newer version
``` 

위와 같은 Error가 발생하면 **docker-compose**의 **Version**을 2.4로 수정[링크](https://github.com/wurstmeister/kafka-docker/issues/461#issuecomment-517688464).

* **Docker-compose Up/Down Error** 

``` bash
Couldn't connect to Docker daemon at http+docker://localhost - is it running?
```

위와 같은 Error가 발생하면 **sudo**를 앞에 붙여주면 된다. 혹은 그에 앞서 docker 서비스를 시작/재시작 해주면 된다.

``` bash
sudo service docker start  
sudo service docker restart  
```

``` bash
sudo docker-compose down  
sudo docker-compose up  
```

## Kafka Download

### Docker File

```vim
ARG kafka_version=2.4.1
ARG scala_version=2.12

# VOLUME["/kafka"] # Docker-compose.yml에서 처리할 예정이라 주석처리함.
```
  
**Docker File에 정의된 버전에 부합하는 kafka 다운도르** [링크](https://archive.apache.org/dist/kafka/)  

* Kafka version : 2.8.1

```bash
wget https://archive.apache.org/dist/kafka/2.8.1/kafka_2.12-2.8.1.tgz  
tar xvfz kafka_2.12-2.8.1.tgz
```

## Kafka 실행

### TEST topic 생성

* **Terminal 1**

	``` bash
	cd kafka_2.12-2.8.1.tgz
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic TEST
	```
	
### Consumer 실행

* **Terminal 2**

	``` bash
	bin/kafka-console-consumer.sh --topic TEST --bootstrap-server localhost:9092 --from-beginning
	```
	
### Producer 실행

* **Terminal 3**

	``` bash
	bin/kafka-console-producer.sh --topic TEST --broker-list localhost:9092
	```
	
### 결과 확인 및 Topic 삭제

**Producer** 터미널에서 **TEXT** 입력하면, **Consumer**에서 출력되는 것을 확인할 수 있음.

``` bash
bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic TEST
```

#### Error

```bash
classpath is empty. please build the project first e.g. by running 'gradlew jarall'
```
위와 같은 Error가 발생하면 "폴더명"에 띄어쓰기가 있는지 확인 후, "폴더명"에서 띄어쓰기를 제거[링크](https://stackoverflow.com/questions/34081336/classpath-is-empty-please-build-the-project-first). 
