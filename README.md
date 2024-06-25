# ETL 개요

* ETL(Extract, Transform, Load)은 데이터 웨어하우징과 데이터 통합의 핵심 과정으로, 데이터를 다양한 소스에서 **추출**(Extract), **변환**(Transform)한 후, **데이터 웨어하우스 또는 데이터 저장소로 로드**(Load)하는 프로세스를 의미.  
* 이 과정은 데이터 분석 및 보고를 위한 기초 작업으로, **데이터의 일관성과 정확성을 보장하기 위해 중요**.

## ETL Process

### [Extract](https://github.com/LeeWooJung/ETLtoZeroETL/tree/main/1.%20Extract) (추출)

* 다양한 데이터 소스(데이터베이스, 파일, 웹 서비스 등)에서 **데이터를 추출**하는 단계.  
* 이 단계에서는 **원시 데이터**를 있는 그대로 가져옴.

### [Transform](https://github.com/LeeWooJung/ETLtoZeroETL/tree/main/2.%20Transform) (변환)

* 추출된 데이터를 **정리, 정규화, 집계**등의 작업을 통해 원하는 형태로 변환하는 단계.  
* 데이터 정제, 중복 제거, 형식 변환, 계산 및 집계 등의 작업이 포함.

### [Load](https://github.com/LeeWooJung/ETLtoZeroETL/tree/main/3.%20Load) (적재)

* 변환된 데이터를 **데이터 웨어하우스나 데이터 마트** 등 목적지로 적재하는 단계.  
* 이 단계에서는 **데이터의 무결성과 일관성**을 유지하면서 적재합니다.