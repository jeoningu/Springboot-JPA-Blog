<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <%-- TODO : 돌아가기 대신 목록 버튼 구현 필요 ( 상세보기 클릭 전 보고 있던 목록으로 돌아가야 함)
    https://lifere.tistory.com/167
    https://www.google.com/search?q=%EA%B2%8C%EC%8B%9C%ED%8C%90+%EC%9D%B4%EC%A0%84+%EB%B3%B4%EB%8D%98+%EB%AA%A9%EB%A1%9D+%EB%B2%84%ED%8A%BC&ei=dgmsY9ekKs7T-QaLvaaYDQ&ved=0ahUKEwjXt52b_Zv8AhXOad4KHYueCdMQ4dUDCA8&uact=5&oq=%EA%B2%8C%EC%8B%9C%ED%8C%90+%EC%9D%B4%EC%A0%84+%EB%B3%B4%EB%8D%98+%EB%AA%A9%EB%A1%9D+%EB%B2%84%ED%8A%BC&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIHCAAQHhCiBDIKCAAQ8QQQHhCiBDIFCAAQogQyBQgAEKIEOgoIABBHENYEELADOgQIABAeOgYIABAFEB46CAghEMMEEKABOgwIIRDDBBAKEKABECo6BggAEB4QDToICAAQBRAeEA1KBAhBGABKBAhGGABQqCxYgHJglHNoCnABeAKAAaUBiAHXFpIBBDAuMjOYAQCgAQHIAQrAAQE&sclient=gws-wiz-serp
    <button class="btn btn-secondary" onclick="location.href='/'">목록</button>--%>
    <button id="btn-update" class="btn btn-warning">수정</button>
    <button id="btn-delete" class="btn btn-danger">삭제</button>
    <br/>
    <br/>
    <div class="form-group">
        <h3>${board.title}</h3>
    </div>
    <hr/>
    <div class="form-group">
        <div>${board.content}</div>
    </div>
    <hr/>
</div>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>