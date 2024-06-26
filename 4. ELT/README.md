# ELT 개요

* **ELT**는 데이터 처리의 패러다임으로, 데이터를 추출(Extract)하여 **원시 데이터 형식 그대로 데이터 웨어하우스나 데이터 레이크에 적재**(Load)한 후, 필요한 분석 작업을 위해 그곳에서 변환(Transform)하는 방식을 의미함.  
* 전통적인 [ETL](https://github.com/LeeWooJung/ETLtoZeroETL)(Extract, Transform, Load) 방식과는 데이터 변환의 위치가 다름.

## ETL과 ELT의 차이점

### ETL  

* 데이터를 추출(Extract)한 후, **중간 단계에서 데이터를 변환**(Transform)하고, 최종적으로 **데이터 웨어하우스**나 **데이터 마트**에 적재(Load)함.

### ELT  

* 데이터를 추출(Extract)한 후, **중간 단계에서 변환 없이 원시 데이터 형식 그대로 데이터 웨어하우스나 데이터 레이크에 적재**(Load)한 다음, 그곳에서 데이터를 변환(Transform)함.

## ELT로의 전환 이유

### 빅데이터 처리

* 현대의 **데이터 레이크**와 **데이터 웨어하우스**는 대용량의 데이터를 효율적으로 처리할 수 있는 성능과 확장성을 갖추고 있음.
* ELT는 대량의 데이터를 빠르게 적재하고 **나중에 필요한 시점에 변환함으로써 빅데이터 처리에 적합**함.

### 클라우드 데이터 웨어하우스의 등장

* AWS Redshift, Google BigQuery, Snowflake와 같은 클라우드 데이터 웨어하우스는 **높은 성능의 데이터 처리와 쿼리 기능을 제공**함.
* 클라우드 환경에서 데이터를 빠르게 적재하고, **강력한 쿼리 기능을 활용하여 변환 작업을 수행**할 수 있음.

### 실시간 데이터 분석 필요성

* 실시간 데이터 분석 요구가 증가함에 따라, **데이터를 빠르게 적재하고 필요에 따라 변환하는 ELT 방식이 유리**함.

## ELT의 장점

### 처리 속도와 성능

* 데이터 웨어하우스의 강력한 처리 성능을 활용하여 **데이터 변환 작업을 빠르게 수행**할 수 있음.
* 데이터 적재 속도가 빨라짐.

### 확장성

* 데이터 웨어하우스와 데이터 레이크는 **높은 확장성을 제공하여 대량의 데이터를 처리**할 수 있음.
* 클라우드 기반 솔루션은 필요에 따라 확장할 수 있어 유연함.

### 비용 효율성

* 클라우드 **데이터 웨어하우스를 사용하면 초기 인프라 구축 비용이 줄어들고**, 사용량 기반 과금 모델을 통해 비용을 효율적으로 관리할 수 있음.

### 유연성

* 데이터 변환을 **필요에 따라 수행할 수 있어 유연한 데이터 처리**가 가능함.
* 데이터 분석 요구 사항이 변경되더라도 **데이터 재적재 없이 변환 작업만 조정**하면 됨.

## ELT의 단점

### 데이터 적재 후 변환의 복잡성

* 원시 데이터 그대로 적재하기 때문에 변환 과정이 복잡해질 수 있음.
* 데이터 변환 작업이 **데이터 웨어하우스의 성능에 의존**함.

### 데이터 품질 관리

* 적재된 원시 데이터의 **품질을 보장하기 어려움**.
* 데이터 **변환 단계에서 데이터 품질을 검증하고 보정**해야 함.

### 데이터 보안 및 프라이버시

* 원시 데이터가 데이터 웨어하우스에 그대로 적재되므로 **보안 및 프라이버시 문제가 발생**할 수 있음.
* 민감한 데이터는 적재 전에 적절한 보호 조치가 필요함.

## ELT Architecture

![ELT](https://github.com/LeeWooJung/ETLtoZeroETL/assets/31682438/30115ade-d0e5-4fff-b185-16f7780773b2)

### Data Sources (데이터 소스)

* 다양한 원본 시스템에서 데이터를 추출함.
* RDBMS, NoSQL 데이터베이스, API, 파일 등 다양한 소스가 포함됨.

### Extract

* 원본 시스템에서 데이터를 추출함.
* 데이터 수집 및 **데이터 풀링** 작업을 수행함.

### Load

* 추출된 데이터를 원시 데이터 형식 그대로 데이터 웨어하우스나 데이터 레이크에 적재함.
* 데이터 저장소와 **데이터 레이크, 데이터 웨어하우스를 포함**함.

### Transform

* 적재된 데이터에서 필요한 변환 작업을 수행함.
* 데이터 정제, 집계, 형식 변환 등을 수행하여 **데이터 분석에 적합한 형태**로 변환함.

### Data Analysis and Reporting

* 변환된 데이터를 사용하여 분석 및 보고를 수행함.
* **BI 도구와 데이터 분석 도구를 활용하여 인사이트를 도출**함.

## Source code Example

### Python을 사용한 데이터 적재 (Google BigQuery)

``` python
from google.cloud import bigquery
import pandas as pd

# Google BigQuery 클라이언트 설정
client = bigquery.Client()

# 데이터 적재
data = pd.read_csv('raw_data.csv')
table_id = 'your_project.your_dataset.your_table'

job = client.load_table_from_dataframe(data, table_id)

# 작업 완료 대기
job.result()

print(f"Loaded {job.output_rows} rows to {table_id}.")
```

### SQL을 사용한 데이터 변환 (Google BigQuery)

``` sql
-- 원시 데이터 테이블에서 변환된 데이터 테이블로 데이터 변환
CREATE OR REPLACE TABLE your_project.your_dataset.transformed_table AS
SELECT
    column1,
    column2,
    column3,
    SUM(column4) AS aggregated_column4
FROM
    your_project.your_dataset.raw_table
GROUP BY
    column1, column2, column3;
```