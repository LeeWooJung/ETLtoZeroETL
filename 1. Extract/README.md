# Extract

* Extract(추출) 단계는 ETL 과정의 첫 번째 단계로, **다양한 소스 시스템에서 데이터를 추출**하여 데이터 웨어하우스나 데이터 마트로 이동시키는 작업을 포함.  
* 이 단계는 데이터를 가능한 **원시 형태로 추출하는 것이 목표**이며, 추출된 데이터는 이후 Transform(변환) 단계에서 필요에 따라 처리됨.

## Data Source

데이터는 다양한 소스에서 추출될 수 있음. 

### 주요 데이터 소스

* **관계형 데이터베이스**: MySQL, PostgreSQL, Oracle, SQL Server 등.
* **NoSQL 데이터베이스**: MongoDB, Cassandra, Redis 등.
* **파일 시스템**: CSV, JSON, XML, Parquet 파일 등.
* **웹 서비스 및 API**: RESTful API, SOAP API 등.
* **클라우드 저장소**: AWS S3, Google Cloud Storage, Azure Blob Storage 등.
* **메인프레임 시스템**: 레거시 시스템 데이터.
* **ERP, CRM 시스템**: SAP, Salesforce 등.

## 데이터 추출 방법

### 전체 추출 (Full Extraction)

* 소스 시스템에서 **전체 데이터를 추출**.
* 초기 데이터 로드나 비교적 작은 데이터셋에 적합.
* 데이터 변경 시점과 무관하게 전체 데이터를 가져오기 때문에 시간이 오래 걸리고 리소스를 많이 소모.

### 증분 추출 (Incremental Extraction)

* 마지막 추출 이후 변경된 데이터만 추출.
* 주로 변경 데이터 캡처(**Change Data Capture**, CDC) 기법을 사용.
* 데이터 처리 시간이 짧고 리소스를 적게 사용.

## 데이터 추출에 사용하는 도구

다양한 도구와 라이브러리가 데이터 추출을 지원.

* **Apache Nifi**  
데이터 흐름을 관리하는 도구로, 다양한 소스에서 데이터를 추출할 수 있음.
* **Talend**  
오픈 소스 데이터 통합 도구로, 시각적 인터페이스를 통해 다양한 데이터 소스에서 데이터를 추출.
* **Informatica PowerCenter**  
상용 ETL 도구로, 대규모 데이터 추출 및 변환 작업을 지원합니다.
* **Microsoft SQL Server Integration Services** (SSIS)  
SQL Server의 ETL 도구로, 다양한 데이터 소스에서 데이터를 추출.
* **Pentaho Data Integration** (PDI)  
오픈 소스 ETL 도구로, Kettle이라는 엔진을 통해 데이터 추출을 수행.

## Architecture

![Extract Architecture](https://github.com/LeeWooJung/ETLtoZeroETL/assets/31682438/72dfefb9-3597-4108-a65a-dd371ba64f5d)

### Source Systems

다양한 시스템에서 데이터를 추출하는 시작점. 각각의 데이터 소스는 고유한 형식과 구조를 가지며, ETL 도구는 이러한 다양한 형식의 데이터를 처리할 수 있어야 함.

* **RDBMS** (관계형 데이터베이스)  
MySQL, PostgreSQL, Oracle, SQL Server 등과 같은 관계형 데이터베이스 시스템에서 데이터를 추출.
* **NoSQL DB** (비관계형 데이터베이스)  
MongoDB, Cassandra, Redis 등과 같은 NoSQL 데이터베이스 시스템에서 데이터를 추출.
* **Files** (파일 시스템)  
로컬 또는 분산 파일 시스템에 저장된 **CSV, JSON, XML, Parquet** 등의 파일에서 데이터를 추출.
* **APIs** (웹 서비스 및 API)  
**RESTful API, SOAP API** 등에서 데이터를 추출.
* **Cloud Storage** (클라우드 저장소)  
**AWS S3, Google Cloud Storage, Azure Blob Storage** 등 클라우드 기반 스토리지에서 데이터를 추출.

### ETL Tool/Engine

추출 작업을 수행하는 핵심 컴포넌트. 데이터 소스와 연결하여 데이터를 추출하고, 스테이징 영역으로 데이터를 전송. ETL 도구는 데이터 커넥터를 통해 다양한 데이터 소스와 상호작용하며, 데이터를 효율적으로 추출.

* **Data Connect** (데이터 연결)  
    * ETL 도구는 다양한 데이터 소스와 연결할 수 있는 **커넥터**를 제공.  
    * 이를 통해 데이터베이스, 파일 시스템, 웹 서비스 등과 연결.
* **Data Extract** (데이터 추출)  
    * 데이터 소스에서 데이터를 추출하는 단계.  
    * ETL 도구는 추출 작업을 자동화하고, **데이터의 무결성을 유지하며, 데이터를 효율적으로 추출**합니다.

### Staging Area

추출된 데이터를 임시로 저장하는 공간. 데이터가 스테이징 영역에 저장되면, 변환 작업을 통해 필요한 형식으로 변환된 후 **데이터 웨어하우스나 데이터 마트**로 적재됨.

* **Raw Data** (원시 데이터)  
    * 추출된 데이터는 스테이징 영역에 저장됩.  
    * 이 영역은 임시 저장소로, 변환 및 적재 과정이 진행되기 전에 데이터를 보관.  
    * 스테이징 영역은 **데이터 웨어하우스의 일부분**이거나 별도의 저장소일 수 있음.

스테이징 영역의 주요 역할은 **추출된 데이터를 변환 및 적재하기 전에 임시로 저장하는 것**.  
이로 인해 원시 데이터가 손상되지 않으며, 변환 및 적재 작업이 중단되더라도 데이터를 다시 추출할 필요가 없음.

## Example

### MySQL에서 데이터를 추출하여 CSV 파일로 저장 (Python)

``` python
import mysql.connector
import csv

# MySQL 데이터베이스 연결 설정
config = {
    'user': 'your_username',
    'password': 'your_password',
    'host': 'your_host',
    'database': 'your_database',
}

# MySQL 연결
conn = mysql.connector.connect(**config)
cursor = conn.cursor()

# SQL 쿼리 실행
query = "SELECT * FROM your_table"
cursor.execute(query)

# 결과를 CSV 파일로 저장
with open('output.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow([i[0] for i in cursor.description])  # 컬럼 헤더 작성
    for row in cursor.fetchall():
        writer.writerow(row)

# 연결 종료
cursor.close()
conn.close()
```

### RESTful API에서 데이터를 추출하여 JSON 파일로 저장 (Python)

``` python
import requests
import json

# API 엔드포인트
api_url = "https://api.example.com/data"

# API 호출
response = requests.get(api_url)

# 응답 데이터를 JSON 파일로 저장
if response.status_code == 200:
    with open('output.json', 'w') as file:
        json.dump(response.json(), file)
else:
    print("Failed to retrieve data:", response.status_code)
```