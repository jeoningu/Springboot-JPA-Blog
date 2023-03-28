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

## 핵심 이슈 해결
* JPA N+1
* JPA LazyInitializationException
* SSE 기능 구현
