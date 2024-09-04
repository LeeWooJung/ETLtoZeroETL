# Flink


## DockerFile

* **Flink**: 1.17.1 버전
* **Telnet** 설치

## Docker-compose.yml

* **Jobmanager**  
* **TaskManager**

## Network

Kafka와 연결을 위해 **kafka-flink-network**에 연결

## 실행

``` bash
docker exec -it <Jobmanager Name> /bin/bash
```

JobManager 접속

``` bash
telnet kafka 9092
```

연결 성공시, Flink가 Kafka 브로커에 접근할 수 있다는 것을 의미.
