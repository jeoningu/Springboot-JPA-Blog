# 커뮤니티 

## 프로젝트 목표
* 백엔드 기술 중심으로 기능을 만들어보기 위한 프로젝트입니다.
* 화면에서도 기능을 동작시킬 수 있게 기본적인 형태로 구현하였습니다.

## 기술 스택
```
Back-end
* Java 1.8
* Spring Boot 2.7.4
* Spring Data JPA
* Spring Security
* MySQL 8.0
* Redis - redisson (분산 락 처리)

Front-end
* html
* css
* javascript
  * jQuery (DOM 처리)
  * ajax (비동기 통신 처리)
* jsp
```

## 이슈 기술적 해결 내용 정리
* JPA 이슈 - N+1, LazyInitializationException
  * https://1subi.tistory.com/entry/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-JPA-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0-LazyInitializationException-N1
  * https://github.com/jeoningu/Springboot-JPA-Blog/commit/b3cdeef432648be68dd08be3e732c896f0ec62a6

* 게시글 좋아요 기능 - redis 분산 락을 이용하여 동시성 제어하기
  * https://1subi.tistory.com/entry/%EC%A2%8B%EC%95%84%EC%9A%94%EA%B8%B0%EB%8A%A5%EB%8F%99%EC%8B%9C%EC%84%B1%EC%B2%98%EB%A6%AC
  * https://github.com/jeoningu/Springboot-JPA-Blog/commit/3726ea1ffb0ab119e0bff2566ba617572e9a37d5

## 기능 목록
[구현 완료]
* 세션 로그인, 네이버/카카오/구글 OAuth2.0 로그인, 로그아웃
* 회원 - 가입/수정/탈퇴
* 게시판 목록 - 페이징 조회
* 게시판 글 - 쓰기/상세 보기/수정/삭제
* 게시판 댓글 - 쓰기/수정/삭제
* 게시글 조회수 (중복 방지 기능 추가 필요)


[추가 예정]
* 회원/비밀번호 찾기
* 대댓글
* 게시판 목록에서 게시글 검색
* 페이지 기능 번호 형태로 변경
* 게시글 상세보기에 버튼 추가 - 이전글/다음글/목록
* 게시글 좋아요 (동시성 제어한 백단만 구현한 상태, 프론트 개발 필요)


## 이슈 목록
* oauth 가입시 name 입력 오류 확인 필요
* sse 관련 오류로 인해 알림 기능 수정 필요 : sse가 너무 불안정해서 다른 방법을 사용해야 될 것 같음
* 유효성 검사 기능 추가 필요
* 조회수 중복 방지 기능 추가 필요
* IllegalArgumentException exception handler 처리, 로그인이 안 돼서 처리 불가 에 대한 exception 처리
* 로그인에 사용 되는 username 대신 email로 로그인 할 수 있게 변경, 세션 방식 로그인에서 jwt 토큰 방식으로 변경
* 게시글 좋아요 redis 분산 락을 aop로 처리 / facade fattern 추가 해서 비관적 락, 낙관적 락 테스트 코드들 정리
* 분산 DB 구조


## 완료 이슈 목록
* 2023.4.4 - Excpeption 처리 기능 및 controller단 반환 형식 수정
* 2023.4.6 - Entityd의 id 타입을 Long으로 변경
* 2023.4.6 - createdDate, modifiedDate을 BaseEntity.java,DateTimeAttributeConverter.java 사용해서 자동 저장 되게 수정
* 2023.5.4 - maven 프로젝트에서 gradle로 변환
  * ~~querydsl 사용시 qClass 추가하면 qClass에 cannot find symbol 에러가 생겨서 해결하기 위함~~ 아니고..
  * QClass에 "cannot find symbol" 에러는 'intellij 설정에서 빌더의 java 버전'과 'build.gradle 파일의 java 버전' 불일치가 원인
  * 그렇다 해도 gradle에서 build.gradle에 설정이 간편하긴 하니까 gradle 계속해서 사용하는 걸로 결정

## 화면
![게시판 프로젝트 화면 모음_0001](https://user-images.githubusercontent.com/103714252/229777272-9f5b9341-51bd-4e87-b177-6b260e4a033b.png)
![게시판 프로젝트 화면 모음_0003](https://user-images.githubusercontent.com/103714252/229777568-139d3fd0-16ea-4d96-8e6c-e4641db9460b.png)
![게시판 프로젝트 화면 모음_0002](https://user-images.githubusercontent.com/103714252/229777573-8a0c2fa4-584b-413c-8d06-a5c4833ada91.png)
