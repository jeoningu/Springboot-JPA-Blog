<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval var="REST_API_KEY" expression="@environment.getProperty('kakao.springboot_blog_project.REST_API_KEY')" />
<spring:eval var="REDIRECT_URI" expression="@environment.getProperty('kakao.springboot_blog_project.REDIRECT_URI')" />

<div class="container-sm">
    <%-- 스프링 시큐리티에 의한 로그인 요청 --%>
    <form c action="/auth/loginProc" method="post">
        <div style="width: 350px; float:none; margin:0 auto" >
            <input style="margin-bottom: 10px" type="text" name="username" class="form-control" placeholder="아이디" id="username">
            <input style="margin-bottom: 10px" type="password" name="password" class="form-control" placeholder="비밀번호" id="password">
                <%--        <div class="form-group form-check">
                <label class="form-check-label">
                    <input name="remember" class="form-check-input" type="checkbox"> Remember me
                </label>
                </div>--%>
            <%-- form 태그 안의 button은 form 요청을 한다.--%>
            <button style="width: 100%; margin-bottom: 10px" id="btn-login" class="btn btn-primary btn-lg">로그인</button>
            <%--    <a href="/oauth2/authorization/facebook">페이스북 로그인</a>  facebook은 잘 사용 안하는 거라 주석처리함--%>
            <br/>
            <div style="text-align: center;">
                <p style="font-family: Malgun Gothic,dotum,gulim,sans-serif;">──── 소셜 계정으로 간편 로그인 ────</p>
                <a href="/oauth2/authorization/naver"><img src="/image/naver_login_btn_원형.png"/></a>
                <a href="/oauth2/authorization/kakao"><img src="/image/kakao_login_btn_원형.png"/></a>
                <a href="/oauth2/authorization/google"><img src="/image/google_login_btn_원형.png"/></a>
            </div>
            <%-- oauth2 Client 라이브러리 사용하지 않고 직접 토큰 받아서 사용자 정보 요청하는 방식 썼을 때 사용한 버튼이라 주석처리
            <a href="https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code"><img height="38px" src="/image/kakao_login_btn_원형.png"/></a>--%>
        </div>
    </form>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>