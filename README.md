![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=250&section=header&text=BackMin%20&fontSize=90&animation=blinking&fontAlignY=35&desc=백둥이%20민족%20(현호와%20형제들)&descAlignY=51&descAlign=62)

# BackMin
__Woo-ah! 함을 뒤이을 Hyeon-ho! 함의 등장!__

## 🛠 Tech Stack
</br>
<div align="center">
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=ffffff&logoWidth=25"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/-JPA-orange?style=flat-square&logoColor=ffffff"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=ffffff&logoWidth=20"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=ffffff&logoWidth=25"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Lombok-CC071E?style=flat-square"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Junit-609540?style=flat-square"/>&nbsp;&nbsp;
<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=ffffff&logoWidth=20"/>
</div>
<div align="center">
<img src="https://img.shields.io/badge/pull%20request-92%20open-lightgrey">&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/issues-91%20open-lightgrey">
</div>
</br></br>

* Programming Language : Java 14
* Framework & Libraries : 
    * Spring Boot
    * Gradle
    * Lombok
    * Junit
* IDE : Intellij
* DataBase : H2 database
* SCM : Git Hub

</br>

---
</br>

## 👨🏻‍💻 Contributors & Team Conventions
</br>

|Project Owner|Scrum Master|Dveloper|Mentor|
|:---:|:---:|:---:|:---:|
|[DevRunner21](https://github.com/DevRunner21)|[Young-BLUE](https://github.com/Young-BLUE)|[yhh1056](https://github.com/yhh1056)|[WooSungHwan](https://github.com/WooSungHwan)|
|<img src="https://user-images.githubusercontent.com/81351244/140481423-6a286f38-3047-43b4-ab43-8fd39620fdba.JPG" width="400" />|<img src="https://user-images.githubusercontent.com/81351244/140481418-842f52b3-7a6f-44e7-97c6-f1add14cde85.jpeg" width="400" />|<img src="https://user-images.githubusercontent.com/81351244/140481488-5309a3df-8632-4d97-a335-838fc6e43aa5.JPG" width="400" />|<img src="https://user-images.githubusercontent.com/81351244/140481500-3f7bf7d9-993b-4867-a7d7-8c4235cc941c.png" width="400" />|


</br>

 * 프로젝트 관리용 협업툴 - [Notion](https://www.notion.so/backend-devcourse/9a49c9bcb2ee4ad29fb7651bf403653c)
 * 문서 공유용 협업툴 - [Notion](https://www.notion.so/backend-devcourse/9a49c9bcb2ee4ad29fb7651bf403653c)
 * 메신저 - Slack
 * 온라인 화이트보드 - 게더타운
 * 이슈관리 - Git Hub

</br>

---
</br>

## 💻 Project Description

* 목표 : 회원가입을 통해 원하는 카테고리의 가게를 검색하여 메뉴와 옵션을 선택해 주문을 할 수 있습니다.

* 선정 이유 : 실생활에서 익숙하지만 많은 데이터를 처리하는 배민 서비스를 분석해보고 싶었습니다.

* 구현된 주요기능 :
    - 회원가입, 수정
    - 카테고리 검색
    - 카테고리를 통한 가게 검색
    - 가게 상세조회
    - 메뉴 및 옵션 선택 후 주문 요청
    - 리뷰 작성 및 조회
    - 이메일, 닉네임 중복 검증
    - 주문상태 변경
    - 회원 주문내역 조회

</br>

<details>
<summary style="font-size: 18px">📁 디렉토리 구조보기</summary>

```
├── README.md
└── src
    └── docs
    │   └── index.adoc
    └── main
        ├── docs
        │   └── asciidoc
        │       └── index.adoc
        ├── java
        │   └── com
        │       └── backmin
        │           ├── Application.java
        │           ├── DataSettingRunner.java
        │           ├── config
        │           │   ├── exception
        │           │   │   ├── BusinessException.java
        │           │   │   ├── GlobalExceptionHandler.java
        │           │   │   └── StoreNotFoundException.java
        │           │   └── util
        │           │       └── AssertThrow.java
        │           └── domains
        │               ├── common
        │               │   ├── BaseEntity.java
        │               │   ├── dto
        │               │   │   ├── ApiError.java
        │               │   │   ├── ApiResult.java
        │               │   │   └── PageResult.java
        │               │   └── enums
        │               │       └── ErrorInfo.java
        │               ├── member
        │               │   ├── controller
        │               │   │   └── MemberController.java
        │               │   ├── converter
        │               │   │   └── MemberConverter.java
        │               │   ├── domain
        │               │   │   ├── Member.java
        │               │   │   └── MemberRepository.java
        │               │   ├── dto
        │               │   │   ├── request
        │               │   │   │   ├── EmailCheckParam.java
        │               │   │   │   ├── MemberCreateParam.java
        │               │   │   │   ├── MemberUpdateParam.java
        │               │   │   │   └── NicknameCheckParam.java
        │               │   │   └── response
        │               │   │       └── MemberOrderPageResult.java
        │               │   └── service
        │               │       └── MemberService.java
        │               ├── menu
        │               │   ├── converter
        │               │   │   ├── MenuConverter.java
        │               │   │   └── MenuOptionConverter.java
        │               │   ├── domain
        │               │   │   ├── Menu.java
        │               │   │   ├── MenuOption.java
        │               │   │   ├── MenuOptionRepository.java
        │               │   │   └── MenuRepository.java
        │               │   └── dto
        │               │       ├── request
        │               │       │   ├── MenuOptionReadParam.java
        │               │       │   └── MenuReadParam.java
        │               │       └── response
        │               │           ├── MenuAtStoreDetailResult.java
        │               │           ├── MenuAtStoreListResult.java
        │               │           ├── MenuOptionAtStoreDetailResult.java
        │               │           ├── MenuOptionAtStoreListResult.java
        │               │           └── MenuReadResult.java
        │               ├── order
        │               │   ├── controller
        │               │   │   └── OrderController.java
        │               │   ├── converter
        │               │   │   └── OrderConverter.java
        │               │   ├── domain
        │               │   │   ├── Order.java
        │               │   │   ├── OrderMenu.java
        │               │   │   ├── OrderMenuOption.java
        │               │   │   ├── OrderRepository.java
        │               │   │   ├── OrderStatus.java
        │               │   │   └── Payment.java
        │               │   ├── dto
        │               │   │   └── request
        │               │   │       ├── CreateOrderParam.java
        │               │   │       ├── OrderCreateRequest.java
        │               │   │       ├── UpdateOrderStatusParam.java
        │               │   │       └── UpdateOrderStatusRequest.java
        │               │   └── service
        │               │       └── OrderService.java
        │               ├── review
        │               │   ├── controller
        │               │   │   └── ReviewController.java
        │               │   ├── converter
        │               │   │   └── ReviewConverter.java
        │               │   ├── domain
        │               │   │   ├── Review.java
        │               │   │   ├── ReviewRepository.java
        │               │   │   └── dto
        │               │   │       ├── request
        │               │   │       │   └── ReviewCreateParam.java
        │               │   │       └── response
        │               │   │           └── ReviewResult.java
        │               │   └── service
        │               │       └── ReviewService.java
        │               └── store
        │                   ├── controller
        │                   │   ├── CategoryController.java
        │                   │   └── StoreController.java
        │                   ├── converter
        │                   │   ├── CategoryConverter.java
        │                   │   └── StoreConverter.java
        │                   ├── domain
        │                   │   ├── Category.java
        │                   │   ├── CategoryRepository.java
        │                   │   ├── Store.java
        │                   │   └── StoreRepository.java
        │                   ├── dto
        │                   │   └── response
        │                   │       ├── CategoriesReadResult.java
        │                   │       ├── CategoryAtListResult.java
        │                   │       ├── DetailStoreReadResult.java
        │                   │       ├── StoreAtDetailResult.java
        │                   │       └── StoreAtListResult.java
        │                   └── service
        │                       ├── CategoryService.java
        │                       └── StoreService.java
        └── resources
            └── application.yml
```
</details>

</br>

<details>
<summary style="font-size: 18px">🗄 ERD</summary>
<img src="https://user-images.githubusercontent.com/81351244/140477261-1e41c678-824a-4939-8214-66dd4c8c1af8.jpeg">
</details>

