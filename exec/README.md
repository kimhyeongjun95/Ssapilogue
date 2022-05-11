# 포팅 메뉴얼

## 1. 빌드 및 배포

### 기술 스택 및 버전

| 설치 목록    | version   |
| ------------ | --------- |
| Ubuntu       | 20.04 LTS |
| Docker       | 20.10.14  |
| Nginx        | 1.18.0    |
| Jenkins      | 2.332.2   |
| Java openjdk | 11.0.15   |
| SpringBoot   | 2.6.6     |
| MySQL        | 8.0.29    |
| MongoDB      | 4.4.13    |
|              |           |
|              |           |
|              |           |
|              |           |
|              |           |
|              | -         |



### 빌드 및 배포 과정

#### 프론트엔드

깃 클론

```
git clone https://lab.ssafy.com/s06-final/S06P31C104.git
```



빌드

```
npm install
```



#### 백엔드

깃 클론

```
git clone https://lab.ssafy.com/s06-final/S06P31C104.git
```



ssapilogue 폴더의 gradle 빌드

```
cd backend/ssapilogue
gradle build
```



Nginx 설정

```
upstream api {
    server backend:8080;
}

server {
    listen 80;
    listen [::]:80;

    server_name k6c104.p.ssafy.io;

    location / {
        root /frontend/build;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://api;
        proxy_set_header Host $http_host;
    }

    location /api/v4/ {
        proxy_pass https://meeting.ssafy.com;
    }

    location /images/ {
        root /;
        try_files $uri $uri/ 404;
    }
}

```



docker-compose 설정

```
version: '3'
services:
  frontend:
    build:
      context: ./frontend
    volumes:
      - ./frontend/build:/frontend/build
  backend:
    build:
      context: ./backend
    volumes:
      - ../images:/images
    ports:
      - "8080:8080"
  nginx:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./nginx/default.conf:/etc/nginx/conf.d/default.conf
      - ./frontend/build:/frontend/build
      - ../images:/images
    depends_on:
      - backend
      - frontend
```



Jenkins 설정

```
SSH Server
Name : AWS EC2

Transfers
Source files: **/*
Remote directory : S06P31C104/
Exec command :
    cd /home/ubuntu/S06P31C104/
    docker-compose down
    docker rmi s06p31c104_frontend:latest
    docker rmi s06p31c104_backend:latest
    docker-compose up -d
```



### Port 및 DB 정보

- SpringBoot 기본 포트
  - 8080
- React 기본 포트
  - 3000
- Jenkins 기본 포트
  - 9090
- MySQL 데이터베이스명
  - ssapilogue
- MySQL 계정이름
  - ssapilogue
- MongoDB 데이터베이스명
  - ssapilogue

- MongoDB 계정이름
  - ssapilogue




## 2. 외부 서비스 정보

- mattermost API
- github API



## 3. DB 덤프 파일





## 4. 시연 시나리오

