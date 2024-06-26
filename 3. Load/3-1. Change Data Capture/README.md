# Change Data Capture

* **Change Data Capture** (CDC)는 데이터베이스에서 발생하는 변경 사항을 추적하고 캡처하여, 이러한 **변경 사항을 다른 시스템으로 실시간으로 전송하는 기술**임.  
* CDC는 주로 Incremental Load(증분 적재) 과정에서 사용되어, 데이터베이스의 **변경된 부분만을 적재함으로써 효율성을 높이고, 데이터의 최신 상태를 유지**하는 데 도움을 줌.

## CDC 사용 목적

### 효율성

* 전체 데이터셋을 재적재하지 않고, **변경된 부분만 적재**하여 리소스 사용을 최소화함.

### 실시간 데이터 통합

* 실시간 또는 거의 실시간으로 데이터 변경 사항을 타겟 시스템에 반영하여 최신 데이터를 유지함.

### 데이터 무결성 및 일관성 유지

* 데이터 변경 사항을 추적하고 검증하여 데이터의 **무결성과 일관성을 유지**함.

## CDC 작동 원리

CDC는 다양한 방법으로 데이터 변경 사항을 추적하고 캡처할 수 있음

### 테이블 트리거(Trigger)

* 데이터베이스 테이블에 **트리거를 설정**하여 INSERT, UPDATE, DELETE 작업을 모니터링하고, 변경 사항을 별도의 **로그 테이블에 기록**함.

### 로그 스캐닝

* 데이터베이스의 **트랜잭션 로그**를 읽어 변경 사항을 추출함.
* 대부분의 상용 데이터베이스 관리 시스템(DBMS)에서 지원함.

### 타임스탬프 기반

* 레코드에 **타임스탬프를 추가**하여 마지막 변경 시간 기준으로 변경 사항을 추적함.

### 변경 테이블

* 변경 사항을 기록하는 별도의 테이블을 사용하여 데이터 변경 내역을 저장함.

## CDC 도구
여러 상용 및 오픈 소스 도구가 CDC를 지원함.

### Debezium
* 오픈 소스 CDC 도구로, 다양한 데이터베이스의 **트랜잭션 로그를 모니터링**하고 변경 사항을 Kafka, Kinesis 등으로 스트리밍함.

### Oracle GoldenGate
* Oracle에서 제공하는 CDC 도구로, 다양한 데이터 소스 간의 실시간 데이터 통합을 지원함.

### Microsoft SQL Server CDC
SQL Server의 내장 기능으로, 테이블의 변경 사항을 자동으로 추적하고 기록함.

### AWS Database Migration Service (DMS)
* AWS에서 제공하는 데이터 마이그레이션 도구로, CDC를 통해 실시간으로 데이터 변경 사항을 캡처하고 이동함.

## Example

### Debezium을 사용한 MySQL CDC

Debezium을 사용하여 MySQL 데이터베이스의 변경 사항을 캡처하고 Kafka로 스트리밍하는 예제.

* Kafka 및 Debezium 설정
    ``` bash
    # Kafka와 Zookeeper 시작
    bin/zookeeper-server-start.sh config/zookeeper.properties
    bin/kafka-server-start.sh config/server.properties

    # Debezium 커넥터 설정
    curl -X POST -H "Content-Type: application/json" \
    --data '{
        "name": "mysql-connector",
        "config": {
            "connector.class": "io.debezium.connector.mysql.MySqlConnector",
            "tasks.max": "1",
            "database.hostname": "mysql",
            "database.port": "3306",
            "database.user": "debezium",
            "database.password": "dbz",
            "database.server.id": "184054",
            "database.server.name": "dbserver1",
            "database.include.list": "inventory",
            "database.history.kafka.bootstrap.servers": "kafka:9092",
            "database.history.kafka.topic": "schema-changes.inventory"
        }
    }' http://localhost:8083/connectors
    ```
    Debezium 커넥터가 MySQL 데이터베이스의 변경 사항을 캡처하여 Kafka 토픽으로 스트리밍함.

### Microsoft SQL Server CDC

SQL Server에서 CDC를 사용하여 테이블의 변경 사항을 캡처하는 예제

* CDC 활성화
    ``` sql
    -- 데이터베이스에서 CDC 활성화
    EXEC sys.sp_cdc_enable_db;

    -- 테이블에서 CDC 활성화
    EXEC sys.sp_cdc_enable_table
        @source_schema = N'dbo',
        @source_name = N'YourTable',
        @role_name = NULL;
    ```

* 변경 사항 조회
    ``` sql
    -- 변경 사항을 조회하는 쿼리
    SELECT * FROM cdc.fn_cdc_get_all_changes_dbo_YourTable (
        from_lsn, 
        to_lsn, 
        'all'
    );
    ```