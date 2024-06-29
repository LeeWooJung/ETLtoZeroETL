# [Apache Flink](https://nightlies.apache.org/flink/flink-docs-release-1.16/)

* Apache Flink는 **분산 스트림 및 배치 데이터 처리를 위한 오픈 소스 플랫폼**임. 
* **실시간 데이터 스트리밍** 애플리케이션과 **배치 데이터 처리** 애플리케이션 모두를 지원하며, 높은 성능과 확장성을 제공함. 
* Flink는 데이터 스트림을 **실시간으로 처리할 수 있는 기능**과 함께, 대용량 데이터 배치 작업을 처리할 수 있는 기능을 제공함.

## Apache Flink를 사용하는 이유

### 실시간 데이터 처리

* Flink는 실시간 데이터 스트리밍 애플리케이션을 지원하여, **데이터가 생성되는 즉시 처리**할 수 있음.

* **고성능**  
Flink는 **낮은 지연 시간과 높은 처리량**을 제공하여 대규모 데이터 처리에 적합함.
* **확장성**  
Flink는 **클러스터 환경에서 실행**되며, 필요에 따라 쉽게 확장할 수 있음.
* **유연성**  
**스트림과 배치 데이터 처리를 모두 지원**하여 다양한 데이터 처리 요구사항에 대응할 수 있음.
* **상태 저장**  
Flink는 **상태 저장**(Stateful) 스트리밍을 지원하여, 복잡한 상태를 유지하면서 스트리밍 작업을 수행할 수 있음.

## Apache Flink의 작동 방식

Flink는 분산 데이터 스트림을 처리하는 **클러스터 기반의 시스템**임. 

### 주요 구성 요소

* **Job Manager**  
    * 클러스터의 **마스터 노드**로, 작업(Job)의 분산 실행을 조정하고, 작업의 상태를 관리함.
    * 실패된 작업에 대한 대응을 진행 함.
    * 작업의 상태를 관리하고, **Task Manager 간의 작업을 분배**함.
    * Resource Manager, Dispatcher, JobMaster로 구성됨.  
        * **Resource Manager** : Flink Cluster에서 리소스 할당 및 해제를 담당함. 리소스 예약 단위인 Task Slots를 관리함.
        * **Dispatcher** : Dispatcher는 실행을 위한 Flink App을 제출하고 Flink WebUI를 실행하여 작업 실행에 대한 정보를 제공함.
        * **Job Master** : 단일 Job Graph 실행 관리를 담당함.


* **Task Manager**  
    * 클러스터의 **워커 노드**로, 실제 데이터 처리를 수행함.  
    * JVM 프로세스이며 별도의 스레드에서 하나 이상의 작업(Task)을 실행할 수 있음.
    * 작업의 수를 제한하기 위해 task slots이 최소 하나 이상 존재.
    * 각 Task Manager는 하나 이상의 작업(Task)을 실행하고, Job Manager와 통신하여 작업 상태를 보고함.

* **Job**  
    * 사용자 정의 **데이터 처리 작업의 논리적 단위**로, 데이터 소스, 데이터 변환, 데이터 싱크 등의 데이터 처리 로직을 포함함.
    * **Job은 Job Manager에 의해 조정되고, Task Manager에 의해 실행**됨.

* **Task**  
    * Job이 실행되는 **물리적 단위**로, 각 Task는 Task Manager에 의해 실행됨.
    * Task는 **데이터 소스에서 데이터를 읽고, 데이터를 처리하고, 결과를 데이터 싱크**로 출력함.

## 연동되는 플랫폼/애플리케이션/API

* **Apache Kafka**  
실시간 데이터 스트리밍을 위한 메시징 플랫폼으로, Flink와 함께 자주 사용됨.
* **Amazon Kinesis**  
AWS의 스트리밍 데이터 플랫폼으로, Flink와 통합 가능함.
* **Apache Cassandra**  
NoSQL 데이터베이스로, Flink의 결과를 저장하는 데 사용될 수 있음.
* **HDFS** (Hadoop Distributed File System)  
Flink는 HDFS를 사용하여 대규모 배치 데이터 작업을 수행할 수 있음.
* **Elasticsearch**  
검색 및 분석 엔진으로, Flink의 처리 결과를 저장하고 검색하는 데 사용될 수 있음.
* **JDBC**  
관계형 데이터베이스와 통합하기 위한 표준 API로, Flink는 JDBC를 통해 다양한 데이터베이스와 연결 가능함.

## Apache Flink의 장단점

### 장점

* **실시간 처리**  
실시간 데이터 스트림 처리에 적합함.
* **고성능**  
낮은 지연 시간과 높은 처리량을 제공함.
* **상태 저장**  
상태 저장 스트리밍을 지원하여 복잡한 상태를 유지하면서 스트리밍 작업을 수행할 수 있음.
* **유연성**  
스트림과 배치 데이터 처리를 모두 지원함.
* **확장성**  
클러스터 환경에서 쉽게 확장 가능함.

### 단점

* **복잡성**  
초기 설정 및 구성이 복잡할 수 있음.
* **자원 요구량**  
대규모 데이터를 처리하는 경우 많은 자원이 필요할 수 있음.
* **학습 곡선**  
Flink의 고급 기능을 이해하고 사용하는 데 시간이 걸릴 수 있음.

## Apache Flink Architecture

### Basic Architecture

![Apache Flink architecture](https://github.com/LeeWooJung/AWS-SAA-C03/assets/31682438/505dc054-1dad-4996-83d1-887bbe9d4824)


### Cluster Simple Architecture

![Cluster simple architecture](https://github.com/LeeWooJung/AWS-SAA-C03/assets/31682438/60a38d72-a464-4f35-92da-dc320bb18233)

### Cluster Deep Architecture

![Cluster Deep Architecture](https://nightlies.apache.org/flink/flink-docs-release-1.16/fig/processes.svg)

### with Kakfa

![with kafka architecture](https://github.com/LeeWooJung/AWS-SAA-C03/assets/31682438/1e697cec-4365-4fc4-b158-a84b4e1a2e55)
