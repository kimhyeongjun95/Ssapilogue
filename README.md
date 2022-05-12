<div align="center">
	![ssapilogue](https://user-images.githubusercontent.com/78924207/167992034-573173c0-daf4-470a-bb0e-04808e5c8695.png)
</div>

> **SSAFY + Epilogue**
>
> SSAFY에서 프로젝트를 완성하고 난 뒤, 어떻게 홍보하고 피드백을 받으시나요?
>
> 싸피인들을 위한 프로젝트 공유 플랫폼, 싸필로그에 여러분을 초대합니다.
>
> 편하게 프로젝트를 홍보하고 효과적으로 피드백을 받아보세요!

<br/>

##  프로젝트 목차

1. [팀소개](#1.-팀소개)
2. [프로젝트 소개](#2.-프로젝트-소개)
   - [기술 스택](#🛠-기술-스택)
   - [아키텍처](#💻-아키텍처)
   - [ERD](#📊-ERD)
   - [UI 프로토타입](#🎨-UI-프로토타입)
3. [프로젝트 파일 구조](#3.-프로젝트-파일-구조)
	- [Backend](#Backend)
	- [Frontend](#Frontend)
4. [프로젝트 산출물](#4.-프로젝트-산출물)
5. [프로젝트 결과](#5.-프로젝트-결과)

<br/>

## 1. 팀소개

🐝 프로폴리스

> **Pro**ject + **Police** = 프로젝트를 지키는 사람들

| <a href="https://github.com/hyunse0"><img src="https://avatars.githubusercontent.com/u/78924207?v=4" alt="" style="zoom:33%;" /></a> | <a href="https://github.com/eunseo130"><img src="https://avatars.githubusercontent.com/u/84255977?v=4" alt="" style="zoom:33%;" /></a> | <a href="https://github.com/kimhyeongjun95"><img src="https://avatars.githubusercontent.com/u/86656921?v=4" alt="" style="zoom:33%;" /></a> | <a href="https://github.com/DongKyunJung"><img src="https://avatars.githubusercontent.com/u/87457171?v=4" alt="" style="zoom:33%;" /></a> | <a href="https://github.com/khyunchoi"><img src="https://avatars.githubusercontent.com/u/77478732?v=4" alt="" style="zoom:33%;" /></a> |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                            하현서                            |                            김은서                            |                            김형준                            |                            정동균                            |                            최강현                            |
|                        팀장, Backend                         |                        Backend, Infra                        |                           Frontend                           |                           Frontend                           |                        Backend, Infra                        |

<br/>

## 2. 프로젝트 소개

🗓 일정 : 2022.04.12 ~ 2022.05.20 (총 6주)

<br/>

### 🛠 기술 스택
| 역할         | Tool                                                         |
| ------------ | ------------------------------------------------------------ |
| 이슈관리     | Jira                                                         |
| 형상관리     | Gitlab                                                       |
| 커뮤니케이션 | Mattermost, Notion                                           |
| 디자인       | Figma                                                        |
| 개발 환경    | - OS : Windows 10<br />- DB : MySQL 8.0.29, Mongo DB 4.4.13<br />- Server : AWS EC2, Ubuntu 20.04 LTS, Docker 20.10.14, Nginx 1.18.0, Jenkins 2.332.2 |
| Backend      | - Java openjdk 11.0.15<br />- SpringBoot 2.6.6               |
| Frontend     | - React                                                      |
| IDE          | - Intellij 21.3.2<br />- Visual Studio Code 1.67             |

<br/>

### 💻 아키텍처

<img src="https://user-images.githubusercontent.com/78924207/168020378-f77c454e-54b0-4cc2-9c9b-cfc8beb639a6.png" width="700" />

<br/>

### 📊 ERD

<img src="https://user-images.githubusercontent.com/78924207/168007467-e5d24011-32b8-47ac-86dc-fbde8b667180.png" width="1000" />

<br/>

### 🎨 UI 프로토타입

<img src="https://user-images.githubusercontent.com/78924207/168004633-f539a1ea-e922-41cd-9abe-7eda898ec6af.png" width="1000" />
<img src="https://user-images.githubusercontent.com/78924207/168005065-8af286fb-11ad-4dc8-bad8-d6d836593fda.png" width="1000" />
<img src="https://user-images.githubusercontent.com/78924207/168005411-f99c4529-a66b-4439-9fea-e2d2d4b72920.png" width="1000" />
<img src="https://user-images.githubusercontent.com/78924207/168005503-8b4e82af-bbc2-4486-b1be-8594fd6be8f6.png" width="1000" />

<br/>

## 3. 프로젝트 파일 구조

### Backend

```markdown
java/com/ssafy/ssapilogue
├── api
│	├── config
│	│
│	├── controller
│	│
│	├── dto
│	│	├── request
│	│	└── response
│	│
│	├── exception
│	│
│	├── service
│	│
│	└── util
│
└── core
    ├── config
    │
    ├── queryrepository
    │
    └── repository
```

<br/>

### Frontend

```markdown
frontend
└── src
	├── api
	│
	├── assets
	│
	├── components
	│	├── Footer
	│	├── Input
	│	└── Navbar
	│
	├── pages
	│
	├── utils
	│	└── store
	│
	├── App.jsx
	├── App.scss
	├── index.css
	└── index.jsx
```

<br/>

## 4. 프로젝트 산출물

- [Ssapilogue Notion 바로가기](https://satisfying-starfish-993.notion.site/dd785428616e47d69512de7cf90003c4)
- [프로젝트 계획서](https://www.notion.so/b4f5ea786b76435a816b9adf03546a94)
- [요구사항 명세서](https://www.notion.so/71bd6686764e49fe901976b3ea7d88f0?v=abbf74a7c33a4f24ae685412af26f6ac)
- [API 명세서](https://www.notion.so/fa7103c8651c4d4c8af473a954487430?v=e4401d7ba0ca4de2a0867522214d4469)

- [최종 발표 자료]()
- [프로젝트 서비스 UCC]()

<br/>

## 5. 프로젝트 결과

짱짱최고1등❤
