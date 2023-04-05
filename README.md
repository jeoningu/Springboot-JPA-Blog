# 게시판 개인 프로젝트
* 백엔드 기술 중심으로 기능을 만들어보기 위한 프로젝트입니다.
* 화면에서도 기능을 동작시킬 수 있게 기본적인 형태로 구현하였습니다.

## 기술 스택
Back-end
* JAVA
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL

Front-end
* html
* css
* javascript
  * jQuery (DOM 처리)
  * ajax (비동기 통신 처리)
* jsp

## 기능
[구현 완료]
* 세션 로그인, OAuth 로그인, 로그아웃
* 회원가입, 회원수정, 회원탈퇴
* 게시판 목록, 게시판 글 쓰기/보기/수정/삭제
* 댓글 쓰기/수정/삭제


[구현 중]
* 회원/비밀번호 찾기
* 대댓글
* 게시글 조회수


## 핵심 이슈 해결 내용
* JPA 이슈 - N+1, LazyInitializationException
  * https://1subi.tistory.com/entry/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-JPA-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0-LazyInitializationException-N1
  * https://github.com/jeoningu/Springboot-JPA-Blog/commit/b3cdeef432648be68dd08be3e732c896f0ec62a6


## 이슈 목록
* oauth 가입시 name 입력 오류 확인 필요
* sse 관련 오류로 인해 알림 기능 수정 필요 : sse가 너무 불안정해서 다른 방법을 사용해야 될 것 같음
* 유효성 검사 기능 추가 필요

## 화면
![게시판 프로젝트 화면 모음_0001](https://user-images.githubusercontent.com/103714252/229777272-9f5b9341-51bd-4e87-b177-6b260e4a033b.png)
![게시판 프로젝트 화면 모음_0003](https://user-images.githubusercontent.com/103714252/229777568-139d3fd0-16ea-4d96-8e6c-e4641db9460b.png)
![게시판 프로젝트 화면 모음_0002](https://user-images.githubusercontent.com/103714252/229777573-8a0c2fa4-584b-413c-8d06-a5c4833ada91.png)
