<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container p-1 my-1 text-center">
    <h5>회원 정보</h5>
</div>
<div class="container">
    <br>
    <br>
    <div style="width: 350px; float:none; margin:0 auto" >
        <h3 style="text-align: center;">회원 수정</h3>
        <form>
            <input type="hidden" id="id" value="${principal.user.id}"/>
            <div class="form-group">
                <label for="id">아이디:</label>
                <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
            </div>
            <c:choose>
                <c:when test="${empty principal.user.provider}">
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" placeholder="Enter password" id="password">
                    </div>
                    <div class="form-group">
                        <label for="email">이름</label>
                        <input type="text" value="${principal.user.name}" class="form-control" placeholder="이름" id="name">
                    </div>
                    <div class="form-group">
                        <label for="email">Email :</label>
                        <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label for="email">이름</label>
                        <input type="text" class="form-control" placeholder="이름" id="name">
                    </div>
                    <div class="form-group">
                        <label for="email">Email :</label>
                        <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
        <button style="width: 100%; margin-bottom: 10px" id="btn-update" class="btn btn-primary">회원수정완료</button>
    </div>
</div>

<%-- 절대 경로 / 다음은 'main/resources/static' 에서 찾는다. 그런데, application.yml에서 context-path를 /blog로 설정 했기 때문에
  /blog가 'main/resource/static'을 가리킨다.--%>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>