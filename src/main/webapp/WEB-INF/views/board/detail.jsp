<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
<%--    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>--%>
    <%-- TODO : 돌아가기 대신 목록 버튼 구현 필요 ( 상세보기 클릭 전 보고 있던 목록으로 돌아가야 함)
    https://lifere.tistory.com/167
    https://www.google.com/search?q=%EA%B2%8C%EC%8B%9C%ED%8C%90+%EC%9D%B4%EC%A0%84+%EB%B3%B4%EB%8D%98+%EB%AA%A9%EB%A1%9D+%EB%B2%84%ED%8A%BC&ei=dgmsY9ekKs7T-QaLvaaYDQ&ved=0ahUKEwjXt52b_Zv8AhXOad4KHYueCdMQ4dUDCA8&uact=5&oq=%EA%B2%8C%EC%8B%9C%ED%8C%90+%EC%9D%B4%EC%A0%84+%EB%B3%B4%EB%8D%98+%EB%AA%A9%EB%A1%9D+%EB%B2%84%ED%8A%BC&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIHCAAQHhCiBDIKCAAQ8QQQHhCiBDIFCAAQogQyBQgAEKIEOgoIABBHENYEELADOgQIABAeOgYIABAFEB46CAghEMMEEKABOgwIIRDDBBAKEKABECo6BggAEB4QDToICAAQBRAeEA1KBAhBGABKBAhGGABQqCxYgHJglHNoCnABeAKAAaUBiAHXFpIBBDAuMjOYAQCgAQHIAQrAAQE&sclient=gws-wiz-serp
    <button class="btn btn-secondary" onclick="location.href='/'">목록</button>--%>
    <c:if test="${board.user.id == principal.user.id}">
        <a href="/board/${board.id}/updateForm/" class="btn btn-light">수정</a>
        <button id="btn-delete" class="btn btn-light">삭제</button>
    </c:if>
    <br/>
    <br/>
    <div>
        글 번호 : <span id="id">${board.id}</span>
        작성자 : <span>${board.user.name}</span>
    </div>
    <br/>
    <div class="form-group">
        <h3>${board.title}</h3>
    </div>
    <hr/>
    <div class="form-group">
        <div>${board.content}</div>
    </div>
    <hr/>

    <br/>
    <div class="card">
        <div class="card-header">댓글 리스트</div>
        <ul id="reply-box" class="list-group">
            <c:forEach var="reply" items="${board.replys}" >
                <li  class="list-group-item ">
                    <div id="reply-${reply.id}">
                        <div class="d-flex justify-content-between">
                            <div>${reply.content}</div>
                            <div class="d-flex">
                                <div class="font-italic">작성자 : ${reply.user.name}&nbsp;</div>
                                <c:if test="${reply.user.id == principal.user.id}">
                                    &nbsp;
                                    <button onclick="index.replyUpdate(${board.id}, ${reply.id})" class="badge badge-dark">수정</button>
                                    &nbsp;
                                    <button onclick="index.replyDelete(${board.id}, ${reply.id})" class="badge badge-dark">삭제</button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <c:if test="${reply.user.id == principal.user.id}">
                        <div id="modifyReply-${reply.id}" class="card"  style="display: none" >
                            <form>
                                <input type="hidden" id="modifyReply-id" value="${reply.id}">
                                <div class="card-body"><textarea id="modifyReply-content" class="form-control" rows="1">${reply.content}</textarea> </div>
                                <div class="card-footer"> <button id="btn-modifyReply-save" class="btn btn-primary">등록</button></div>
                            </form>
                        </div>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
    <br/>
    <div class="card">
        <div class="card-body"><textarea id="reply-content" class="form-control" rows="1"></textarea> </div>
        <div class="card-footer"> <button id="btn-reply-save" class="btn btn-primary">등록</button></div>
    </div>
</div>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>