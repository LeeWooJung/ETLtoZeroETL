# Transform

* Transform(변환) 단계는 ETL 과정의 두 번째 단계로, **추출된 원시 데이터를 원하는 형태로 변환하는 작업**을 포함함.  
* 이 단계는 **데이터 정제, 중복 제거, 형식 변환, 집계 등의 작업**을 통해 데이터를 정리하고, 분석 및 보고에 적합한 형태로 변환함.

## Transform 작업

### 데이터 정제

* **누락된 값, 오류 값, 이상치를 처리하여 데이터의 품질을 향상시키는 작업**을 의미함.  
* 예를 들어, 누락된 값은 평균값이나 중위값으로 대체하고, 오류 값은 수작업으로 수정하거나 제거함.

### 중복 제거

* 데이터셋에서 **중복된 레코드를 찾아 제거하는 작업**을 포함함.
* 중복 제거를 통해 데이터의 일관성을 유지하고, 분석 결과의 신뢰성을 높임.

### 형식 변환

* 데이터 형식을 변환하여 **일관된 구조**를 만듦.
* 예를 들어, 날짜 형식을 표준화하거나 문자열을 숫자로 변환함.

### 집계

* 데이터를 요약하거나 집계하여 더 높은 수준의 정보를 도출함.
* 예를 들어, 매출 데이터를 월별, 분기별, 연도별로 집계함.

## 도구

변환 작업을 지원하는 다양한 도구와 라이브러리가 있음. 

* **Apache Spark**  
대규모 데이터 처리 및 변환 작업을 지원하는 **분산 컴퓨팅 시스템**임.
* **Talend**  
**시각적 인터페이스를 통해 변환 작업을 쉽게 설계하고 실행**할 수 있는 오픈 소스 데이터 통합 도구임.
* **Informatica PowerCenter**  
데이터 변환 및 통합을 위한 상용 ETL 도구임.
* **Microsoft SQL Server, Integration Services (SSIS)**  
SQL Server의 ETL 도구로, 다양한 변환 작업을 지원함.
* **Pentaho Data Integration** (PDI)  
Kettle이라는 엔진을 사용하여 데이터 변환 작업을 수행하는 오픈 소스 ETL 도구임.

## Architecture

![Transform architecture](https://github.com/LeeWooJung/ETLtoZeroETL/assets/31682438/e0f4783c-475e-4406-a701-da7a2fe85e80)

### Staging Area

* 추출된 원시 데이터를 임시로 저장하는 곳임.
* 변환 작업이 시작되기 전에 데이터를 보관함.

### ETL Tool/Engine

* **Data Cleanse** (데이터 정제)  
누락된 값, 오류 값, 이상치를 처리하여 데이터의 품질을 향상시키는 작업을 수행함.
* **Data Format** (형식 변환)  
데이터 형식을 변환하여 일관된 구조를 만듦.
* **Data Aggregate** (데이터 집계)  
데이터를 요약하거나 집계하여 더 높은 수준의 정보를 도출함.

### Transformed Data

* 정제, 변환, 집계된 데이터를 저장하는 곳임.
* 이 데이터는 이후 로드(Load) 단계에서 데이터 웨어하우스나 데이터 마트로 적재됨.

## Example Source code

### Python을 사용한 데이터 정제 및 변환

``` python
import pandas as pd

# 데이터 로드
data = pd.read_csv('raw_data.csv')

# 데이터 정제
data.dropna(subset=['important_column'], inplace=True)  # 중요한 열에서 누락된 값 제거
data['column_with_errors'] = data['column_with_errors'].apply(lambda x: correct_value(x))  # 오류 값 수정

# 형식 변환
data['date_column'] = pd.to_datetime(data['date_column'], format='%Y-%m-%d')  # 날짜 형식 변환

# 데이터 집계
aggregated_data = data.groupby('category_column').agg({'value_column': 'sum'}).reset_index()

# 결과 저장
aggregated_data.to_csv('transformed_data.csv', index=False)
```

### Apache Spark를 사용한 대규모 데이터 변환

``` python
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, sum

# Spark 세션 생성
spark = SparkSession.builder.appName("DataTransform").getOrCreate()

# 데이터 로드
df = spark.read.csv('raw_data.csv', header=True, inferSchema=True)

# 데이터 정제
df = df.na.drop(subset=["important_column"])  # 중요한 열에서 누락된 값 제거
df = df.withColumn("column_with_errors", correct_value_udf(col("column_with_errors")))  # 오류 값 수정

# 형식 변환
df = df.withColumn("date_column", col("date_column").cast("date"))  # 날짜 형식 변환

# 데이터 집계
aggregated_df = df.groupBy("category_column").agg(sum("value_column").alias("total_value"))

# 결과 저장
aggregated_df.write.csv('transformed_data.csv', header=True)

# 세션 종료
spark.stop()
```
