# 게시판 개인 프로젝트
* 백엔드 기술 중심으로 기능을 만들어보기 위한 프로젝트입니다.
* 화면에서도 기능을 동작시킬 수 있게 기본적인 형태로 구현하였습니다.

## 기술 스택
Back-end
* JAVA
* Spring Boot
* Spring Data JPA
* Spring Security
* MariaDB

Front-end
* html
* css
* javascript
  * jQuery (DOM 처리)
  * ajax (비동기 통신 처리)
* jsp

## 기능
구현완료
* 세션 로그인, OAuth 로그인, 로그아웃
* 회원가입, 회원수정
* 게시판 목록, 게시판 글 쓰기/보기/수정/삭제
* 댓글 쓰기/수정/삭제
* 게시판 글에 댓글 추가 시 게시글 저자에게 알림기능

구현 중
* 회원/비밀번호 찾기
* 회원탈퇴
* 대댓글
* 게시글 조회수
* 회원수정 버그

## 핵심 이슈 해결 내용
* JPA 이슈 - N+1, LazyInitializationException
  * https://1subi.tistory.com/entry/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-JPA-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0-LazyInitializationException-N1
  * https://github.com/jeoningu/Springboot-JPA-Blog/commit/b3cdeef432648be68dd08be3e732c896f0ec62a6
* SSE 기능 구현
