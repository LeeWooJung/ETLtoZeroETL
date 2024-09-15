# Linux Docker 설치

[링크](https://velog.io/@osk3856/Docker-Ubuntu-22.04-Docker-Installation)

## 1. System package update

``` bash
sudo apt-get update
```

## 2. Download Package

``` bash
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
```

## 3. 공식 GPG 키 추가

``` bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

## 4. Docker 공식 apt 저장소 추가

``` bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```

## 5. System package update

``` bash
sudo apt-get update
```

## 6. Docker 설치

``` bash
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

## 7. Docker 상태 확인

``` bash
sudo systemctl status docker
```

## 8. Docker 실행

``` bash
sudo service docker start
```

## 9. Docker 중지

``` bash
sudo service docker stop
```

## Error

``` bash
docker: permission denied while trying to connect to the Docker daemon socket
```

* **sudo**로 실행  
* 혹은 아래와 같이 권한 변경[링크](https://github.com/occidere/TIL/issues/116)  
	``` bash
	sudo chmod 666 /var/run/docker.sock # 파일 권한 변경  
	sudo chown root:docker /var/run/docker.sock # group ownership 변경
	```


