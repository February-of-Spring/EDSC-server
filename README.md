# DSC Ewha board
<img width="882" alt="스크린샷 2021-01-30 오후 3 30 50" src="https://user-images.githubusercontent.com/45806836/106349281-25383800-6310-11eb-9265-1906ec554501.png">

## 공개 주소 ✨ 

배포 URL:

&nbsp;

## 소개

<img width="870" alt="스크린샷 2021-01-30 오후 3 29 28" src="https://user-images.githubusercontent.com/45806836/106349253-f326d600-630f-11eb-8ce0-61884bac5229.png">

🌱 DSC Ewha 멤버분들을 위해 만든 게시판 프로젝트 입니다! 🌱 

&nbsp;

## 기능

### Member

- 회원가입
- 전체 회원 목록 조회

### Post

- 게시판, 카테고리 선택 후 글 작성
- 게시물에 사진과 파일 첨부
- 닉네임, 제목 기반 게시글 검색
- 내가 작성한 게시글 모아보기
- 게시글 좋아요
- 내가 좋아요한 게시글 모아보기

### Comment

- 비밀댓글
- 답글
- 댓글 공감

&nbsp;

## Domain

<img width="975" alt="스크린샷 2021-01-30 오후 3 34 25" src="https://user-images.githubusercontent.com/45806836/106349345-a4c60700-6310-11eb-9f3c-2ead5163d396.png">

&nbsp;

## Git Convension

### Commit message
```
[Server/Client] {add/update/fix/remove} {기능 }
```

`add() :` 새로운 파일 추가  
`update() :` 기존 파일 수정  
`feat() :` 새로운 기능 추가할 때  
`fix() :` 버그 수정할 때  
`docs() :` 문서 추가 및 변경할 때  
`style() :` 코드 포맷팅, 로직의 변화는 없이 띄어쓰기나 탭 문자 등의 사소한 변화가 있을 때  
`refactor() :` 리팩토링할 때  
`test() :` 테스트 코드 수정 및 변경할 때  
`chore() :` 빌드 및 패키지 매니저 수정 등 maintain할 때  

- `()`괄호 안에는 클래스명등 어디를 고쳤는지 정도를 적고, 뒤에 무엇이 달라졌고 왜 수정했는지 현재형으로 적는다.

### example

`feat(Location): add location class`  
`fix(Location): fix bug for addLocation method`  
`docs(README.md) : check plans`

### Branches

- `develop-server` & `develop-client` 에서 개발
- 각 브랜치에서 pull (git에서 merge 시 충돌 안 나도록)

```bash
$ git remote update
$ git pull origin {current-branch}
```

- pull한 후 push

```bash
$ git push origin {current-branch}
```

- 기능 개발 완료 시 origin `develop` 브랜치에 pull request
- 다른 한 명이 검사 후 merge

### Server Test DisplayName

```java
@DisplayName("{테스트기능} | {Success/Fail} : {Reason}")
```

### Log

```java
log.info([Request] {get/add/update/remove}-{기능});
```

ex) [Request] get-user-history

&nbsp;

## About us 👨‍👩‍👧‍👧 

### Developers 🖥 

|이름|담당|github|
|--|--|--|
|이정원|Back-end|[@jeongwon-iee](https://github.com/jeongwon-iee)|
|장다솜|Back-end||
|박주은|Back-end|[@hoit1302](https://github.com/hoit1302)|
