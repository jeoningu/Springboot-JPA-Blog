<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@include file="layout/header.jsp"%>

<div class="container">
    <%-- m-2 : bootstrap merge --%>

    <c:forEach var="board" items="${boards}">
    <div class="card m-2" style="">
        <div class="card-body">
            <h4 class="card-title">${board.title}</h4>
            <a href="#" class="btn btn-primary">상세 보기</a>
        </div>
    </div>
    </c:forEach>

</div>

<%@include file="layout/footer.jsp"%>


