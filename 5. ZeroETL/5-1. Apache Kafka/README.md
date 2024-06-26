# Apache Kafka

* Apache Kafka는 **분산 스트리밍 플랫폼**으로, **실시간 데이터 파이프라인과 스트리밍 애플리케이션을 구축하기 위한 도구**임.  
* Zero ETL 구현을 위해 Apache Kafka는 **데이터 소스와 데이터 목적지 간의 실시간 데이터 스트리밍을 지원**함.

## Apache Kafka의 아키텍처

![Apache Kafka architecture](https://github.com/LeeWooJung/ETLtoZeroETL/assets/31682438/c7014fe7-858e-403d-8f33-0c857b089bad)

### Message

* Kafka에서 데이터의 기본 단위를 **메시지**라고 함.
* Byte 배열의 데이터로 간주 됨. 이 덕분에 어떠한 데이터 형태이든 저장이 가능, 변형 가능.
* Topic 내부의 Partition에 기록 됨.
* 파티션을 결정하기 위해 Message에 담긴 키 값을 해시 처리하고, 일치하는 파티션에 메시지가 기록 됨.

### Producer

* **데이터 소스에서 Kafka로 데이터를 전송**하는 역할을 함.
* 다양한 데이터 소스에서 데이터를 가져와 Kafka로 **스트림으로 보냄**.
* 메시지 전송 시 Batch 처리가 가능.
* 토픽을 지정하고 메시지를 게시 함.

### Broker

* Producer로부터 메시지를 수신하고, 오프셋을 지정한 후 해당 메시지를 디스크에 저장하는 역할.
* Consumer의 Partition 읽기 요청에 응답하고 디스크에 수록된 메시지를 전송.
* Kafka **클러스터를 구성하는 서버**로, **데이터를 저장하고 관리**함.
* 토픽을 통해 **데이터를 분산하고, 메시지를 저장**함.

### Topic

* 데이터가 **저장되는 논리적인 채널**임.
* 프로듀서가 데이터를 전송하면 **토픽에 메시지가 쌓임**.
* Consumer는 Topic의 메시지를 처리.
* 메시지를 목적에 맞게 구분할 때 사용함.
* 데이터 스트림이 어디에 publish될 지 정하는데 쓰임.
* Partition의 그룹이라고 할 수 있다. 즉, 하나의 Topic은 여러 개의 Partition으로 구성될 수 있음.

### Partition

* 토픽은 **파티션으로 분할되어 병렬 처리**를 가능하게 함.
* 각 파티션은 **여러 브로커에 분산 저장**됨.
* 파티션 내부의 각 메시지는 offset으로 구분 됨.
* 파티션이 여러개라면 Kafka 클러스터가 Round Robin 방식으로 분배해서 분산처리되기 때문에 순서가 보장되지 않음.
* 메시지의 처리 순서는 Topic이 아닌 Partition 별로 관리 됨.

#### 종류

* **Leader Partition**
    - Producer, Consumer와 직접 통신하는 파티션.
    -  읽기, 쓰기 연산 담당.
* **Follower Partition**
    - Producer로부터 리더 파티션으로 전달된 데이터를 복제(replication)하여 복제된 데이터 저장.
    - 리더 파티션이 속한 **브로커에 장애가 발생**하면, 팔로워 파티션에서 리더 파티션의 지위를 가짐.

### Consumer

* Kafka에서 **데이터를 소비하는 역할**을 함.
* 데이터를 처리하거나 다른 시스템으로 전달함.
* 메시지를 Batch 처리할 수 있음.
* 한 개의 Consumer는 여러 개의 토픽을 처리(하나 이상의 Topic을 구독)할 수 있음.
* Consumer 그룹에 속함. 이 때, Consumer 그룹은 하나의 Topic에 대한 책임을 지고 있음.
* 하나의 Topic을 정하여 여러 개의 Consumer 그룹이 메시지를 읽을 수 있음. 이 때, 각 Consumer 그룹은 상호 간섭 없이 각자의 Offset으로 메시지를 읽고 처리할 수 있음.
* 메시지를 읽을 때마다 Partition 단위로 Offset을 기억하므로 다음에 읽을 메시지의 위치를 알 수 있음.
* **Commit Offset**  
    - Consumer로부터 해당 Offset까지는 처리 했다는 것을 나타냄.
* **Current Offset**  
    - Consumer가 어디까지 메시지를 읽었는지 나타냄.

### [Zookeeper](https://github.com/LeeWooJung/ETLtoZeroETL/tree/main/5.%20ZeroETL/5-1.%20Apache%20Kafka/5-1-1.%20Zookeeper)

* Kafka 클러스터의 메타데이터를 관리하고, 브로커의 상태를 모니터링함.

## Apache Kafka의 장점

### 확장성

* Kafka는 **분산 시스템으로 설계되어 있어 확장성이 뛰어남**.
* 많은 양의 데이터를 처리할 수 있음.

### 내구성

* 데이터를 디스크에 저장하여 내구성을 보장함.
* **브로커 간의 복제**를 통해 데이터 손실을 방지함.

### 실시간 처리

* 실시간 **데이터 스트리밍을 지원하여 최신 데이터를 사용**할 수 있음.
* **낮은 지연 시간**으로 데이터를 처리할 수 있음.

### 유연성

* 다양한 데이터 소스와 목적지 간의 통합을 지원함.
* 여러 프로듀서와 컨슈머를 통해 다양한 형태로 데이터를 처리할 수 있음.

## Apache Kafka의 단점

### 복잡한 설정

* Kafka 클러스터의 설정과 관리가 복잡할 수 있음.
* Zookeeper를 포함한 여러 구성 요소를 관리해야 함.

### 데이터 중복

* **중복 데이터 처리를 위해 별도의 작업**이 필요함.
* 컨슈머 그룹을 사용하여 중복 처리를 방지할 수 있지만, 설정이 필요함.

### 학습

* Kafka의 개념과 작동 방식을 이해하는데 시간이 걸릴 수 있음.
* Kafka Streams와 같은 고급 기능을 사용하려면 추가 학습이 필요함.

## 게시-구독(Pub-Sub) 모델

* 게시-구독 모델에서는 각 메시지가 **토픽을 구독한 모든 소비자에게 전달**됨.  
* 이는 동일한 데이터를 여러 시스템이나 서비스에서 **동시에 사용해야 할 때 유용**함.

* **특징**
    * 각 메시지는 **모든 구독자**(소비자)에게 전달됨.
    * 각 소비자는 독립적으로 메시지를 처리함.
    * **메시지의 중복 처리가 있음**.

* **작동 방식**
    * 프로듀서가 Kafka의 특정 토픽에 메시지를 전송함.
    * **여러 소비자 그룹이 동일한 토픽을 구독**할 수 있음.
    * 토픽의 모든 파티션에 있는 메시지는 **모든 소비자 그룹에게 전달**됨.
    * 각 소비자 그룹 내의 소비자들은 대기열 모델처럼 메시지를 분배받아 처리함.

![pub-sub architecture](https://github.com/LeeWooJung/AWS-SAA-C03/assets/31682438/9ba99894-50a4-48c2-8819-f9c002efbe03)
