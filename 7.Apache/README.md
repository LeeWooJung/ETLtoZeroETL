# Linux Docker 설치

[링크](https://velog.io/@osk3856/Docker-Ubuntu-22.04-Docker-Installation)

## 1. System package update

``` bash
sudo apt-get update
```

## 2. Download Package

``` bash
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
```

## 3. 공식 GPG 키 추가

``` bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

## 4. Docker 공식 apt 저장소 추가

``` bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```

## 5. System package update

``` bash
sudo apt-get update
```

## 6. Docker 설치

``` bash
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

## 7. Docker 설치 확인

``` bash
sudo systemctl status docker
```

## Error

``` bash
docker: permission denied while trying to connect to the Docker daemon socket
```

* **sudo**로 실행  
* 혹은 아래와 같이 권한 변경[링크](https://github.com/occidere/TIL/issues/116)  
	``` bash
	sudo chmod 666 /var/run/docker.sock # 파일 권한 변경  
	sudo chown root:docker /var/run/docker.sock # group ownership 변경
	```

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

