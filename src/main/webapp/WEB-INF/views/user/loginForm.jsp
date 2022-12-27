<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <%-- 스프링 시큐리티에 의한 로그인 요청 --%>
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>
<%--        <div class="form-group form-check">
            <label class="form-check-label">
                <input name="remember" class="form-check-input" type="checkbox"> Remember me
            </label>
        </div>--%>
        <%-- form 태그 안의 button은 form 요청을 한다.--%>
    <button id="btn-login" class="btn btn-primary">로그인</button>
    </form>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>