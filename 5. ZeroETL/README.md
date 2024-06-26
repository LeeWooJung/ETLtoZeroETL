# Zero ETL

## 개요

* Zero ETL은 전통적인 **ETL(Extract, Transform, Load) 프로세스를 제거**하고, 데이터 소스와 데이터 목적지 간의 직접적이고 실시간 데이터 통합을 목표로 하는 접근 방식임.  
* 이 방식은 **데이터를 추출, 변환, 적재하는 중간 단계를 생략**하고, **데이터 소스에서 데이터 웨어하우스나 데이터 레이크로 실시간으로 데이터를 전달**함으로써 효율성을 극대화함.

## 주요 개념

### 데이터 추출(Extract) 제거

* 데이터 소스에서 데이터를 별도로 추출하지 않음.
* 데이터는 **소스 시스템에서 직접 목적지로 이동**됨.

### 데이터 변환(Transform) 제거

* 데이터 **변환 작업을 중간 단계에서 수행하지 않음**.
* 데이터 소스와 목적지 간에 직접적으로 전달됨.

### 데이터 적재(Load) 제거

* 별도의 데이터 적재 과정 없이 **실시간으로 데이터가 전달**됨.
* 데이터 웨어하우스나 데이터 레이크에 직접 통합됨.

## 장점

### 실시간 데이터 통합

* 데이터를 실시간으로 통합하여 **최신 상태를 유지**함.
* 지연 시간 없이 데이터를 사용할 수 있음.

### 간소화된 데이터 파이프라인

* 중간 단계가 제거되어 **데이터 파이프라인이 단순**해짐.
* 관리 및 유지보수가 쉬워짐.

### 비용 절감

* 중간 데이터 저장소와 변환 작업이 필요 없으므로 비용이 절감됨.
* 데이터 이동과 관련된 리소스 사용이 줄어듦.

### 데이터 일관성 향상

* 데이터를 소스에서 직접 목적지로 전달함으로써 **데이터 일관성이 향상**됨.
* 중간 변환 과정에서 발생할 수 있는 오류를 줄임.

## 단점

### 복잡한 데이터 변환

* 데이터 변환 작업을 **소스 또는 목적지에서 직접 처리**해야 함.
* 복잡한 변환 작업이 필요할 경우, 이 작업을 **효율적으로 처리하기 어려울 수 있음**.

### 데이터 보안 및 프라이버시

* 실시간 데이터 전송 과정에서 **보안 문제가 발생**할 수 있음.
* 민감한 데이터를 보호하기 위한 추가 조치가 필요함.

### 기술적 한계

* 모든 시스템이 **실시간 데이터 전송을 지원하지 않을 수 있음**.
* **기존 시스템과의 통합이 어려울 수 있음**.

## 구현 Example

Zero ETL을 구현하기 위해서는 **데이터 소스와 목적지 간의 직접적인 데이터 스트리밍을 지원하는 기술**이 필요.

### Kafka를 사용한 Zero ETL

Kafka를 사용하여 데이터 소스에서 데이터 웨어하우스로 실시간 데이터 스트리밍을 구현하는 예시.

1. **Kafka 설정**

    ``` bash
    # Kafka와 Zookeeper 시작
    bin/zookeeper-server-start.sh config/zookeeper.properties
    bin/kafka-server-start.sh config/server.properties
    ```

2. **데이터 소스에서 Kafka로 데이터 전송**

    ``` python
    from kafka import KafkaProducer
    import json

    producer = KafkaProducer(bootstrap_servers='localhost:9092',
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

    data = {"id": 1, "name": "Alice", "age": 30}
    producer.send('my_topic', value=data)
    producer.flush()
    ```

3. **Kafka에서 데이터 웨어하우스로 데이터 수신**

    ``` python
    from kafka import KafkaConsumer
    import json
    from google.cloud import bigquery

    consumer = KafkaConsumer('my_topic',
                         bootstrap_servers='localhost:9092',
                         value_serializer=lambda v: json.loads(v.decode('utf-8')))

    client = bigquery.Client()
    table_id = 'your_project.your_dataset.your_table'

    for message in consumer:
        row_to_insert = [message.value]
        errors = client.insert_rows_json(table_id, row_to_insert)
        if errors == []:
            print("New row has been added.")
        else:
            print(f"Encountered errors: {errors}")
    ```