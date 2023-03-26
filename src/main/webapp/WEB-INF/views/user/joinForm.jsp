<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <br>
    <br>
    <div style="width: 350px; float:none; margin:0 auto" >
        <h3 style="text-align: center;">회원가입</h3>
        <form>
                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" placeholder="아이디" id="username">
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" placeholder="비밀번호" id="password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" class="form-control" placeholder="이름" id="name">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" placeholder="이메일" id="email">
                </div>
        </form>
        <button style="width: 100%; margin-bottom: 10px" id="btn-save" class="btn btn-primary">가입하기</button>
    </div>
</div>

<%-- 절대 경로 / 다음은 'main/resources/static' 에서 찾는다. 그런데, application.yml에서 context-path를 /blog로 설정 했기 때문에
  /blog가 'main/resource/static'을 가리킨다.--%>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>