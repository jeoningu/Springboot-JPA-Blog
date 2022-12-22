<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" class="form-control" placeholder="Enter email" id="email">
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">가입하기</button>
</div>

<%-- 절대 경로 / 다음은 'main/resources/static' 에서 찾는다. 그런데, application.yml에서 context-path를 /blog로 설정 했기 때문에
  /blog가 'main/resource/static'을 가리킨다.--%>
<script src="/blog/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>