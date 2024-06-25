# Load

* Load(적재) 단계는 ETL 과정의 마지막 단계로, 변환된 데이터를 **데이터 웨어하우스나 데이터 마트로 이동시키는 작업**을 포함함.  
* 이 단계는 데이터의 **무결성과 일관성을 유지**하면서 데이터를 적재하여, 최종 사용자가 분석과 보고를 위해 데이터를 사용할 수 있도록 함.

## Load 방법

### 전체 적재 (Full Load)

* 데이터 웨어하우스 또는 데이터 마트에 **전체 데이터를 한 번에 적재하는 방법**임.
* 초기 데이터 로드나 주기적인 전체 데이터 갱신 시 사용됨.
* **적재 시간이 길어질 수 있으며, 시스템 리소스를 많이 소모**할 수 있음.

### 증분 적재 (Incremental Load)

* 마지막 적재 이후 **변경된 데이터만 적재**하는 방법임.
* 변경 데이터 캡처(Change Data Capture, **CDC**) 기법을 사용하여 변경된 데이터만 추적하여 적재함.
* 적재 시간이 짧고, 시스템 리소스를 효율적으로 사용할 수 있음.

## 데이터 무결성 및 일관성 유지 방법

### 트랜잭션 관리

* 데이터베이스 **트랜잭션을 사용하여 데이터 적재 과정에서의 일관성을 보장**함.
* 모든 데이터 적재 작업이 성공적으로 완료되거나, 실패 시 모든 작업이 롤백되도록 함.

### 데이터 검증

* 데이터 적재 전후에 **데이터 검증**을 통해 데이터 무결성을 유지함.
* 체크섬, 데이터 양 검증, 데이터 패턴 검증 등을 사용함.

### 이중화

* 데이터 적재 중 **데이터 손실을 방지하기 위해 이중화된 시스템**을 사용함.
* 백업 및 복구 전략을 마련하여 데이터 손실 시 신속하게 복구할 수 있도록 함.

## 데이터 적재에 사용하는 도구

다양한 도구와 라이브러리가 데이터 적재를 지원함.

* **Apache Sqoop**  
관계형 데이터베이스와 하둡 간의 데이터 전송을 지원하는 도구임.
* **Talend**  
시각적 인터페이스를 통해 데이터 적재 작업을 쉽게 설계하고 실행할 수 있는 오픈 소스 데이터 통합 도구임.
* **Informatica PowerCenter**  
데이터 적재 및 통합을 위한 상용 ETL 도구임.
* **Microsoft SQL Server Integration Services (SSIS)**  
SQL Server의 ETL 도구로, 다양한 데이터 적재 작업을 지원함.
* **Pentaho Data Integration (PDI)**  
Kettle이라는 엔진을 사용하여 데이터 적재 작업을 수행하는 오픈 소스 ETL 도구임.

## Architecture

![Load architecture](https://github.com/LeeWooJung/ETLtoZeroETL/assets/31682438/8dc1245a-bb8a-4833-9bfe-0dd2d57f4c3c)

### Transformed Data (변환된 데이터)

* 정제되고 변환된 데이터를 저장하는 곳임.
* 이 데이터는 이후 로드 단계에서 데이터 웨어하우스나 데이터 마트로 적재됨.

### ETL Tool/Engine (ETL 도구/엔진)

* **Data Validation** (데이터 검증)  
적재 전에 데이터 무결성과 일관성을 검증함.
* **Data Load** (데이터 적재)  
데이터를 데이터 웨어하우스나 데이터 마트로 이동함.
* **Transaction Management** (트랜잭션 관리)  
데이터 적재 작업의 일관성과 무결성을 보장하기 위해 트랜잭션을 관리함.

### Data Warehouse/Mart (데이터 웨어하우스/마트)

* 최종적으로 데이터를 저장하는 곳임.
* **Fact Tables** (사실 테이블)  
수치 데이터를 저장하며, 주요 분석 대상이 됨.
* **Dimension Tables** (차원 테이블)  
사실 테이블과 연결된 설명 데이터를 저장함.

## Example Source

### Python을 사용한 데이터 적재 (PostgreSQL)
``` python
import psycopg2
import pandas as pd

# PostgreSQL 연결 설정
conn = psycopg2.connect(
    dbname="your_database",
    user="your_username",
    password="your_password",
    host="your_host",
    port="your_port"
)
cursor = conn.cursor()

# 변환된 데이터 로드
data = pd.read_csv('transformed_data.csv')

# 데이터 적재
for index, row in data.iterrows():
    cursor.execute("""
        INSERT INTO your_table (column1, column2, column3)
        VALUES (%s, %s, %s)
    """, (row['column1'], row['column2'], row['column3']))

# 트랜잭션 커밋
conn.commit()

# 연결 종료
cursor.close()
conn.close()
```