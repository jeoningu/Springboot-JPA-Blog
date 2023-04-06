<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@include file="layout/header.jsp"%>

<div class="container">
    <%-- m-2 : bootstrap merge --%>
    <div class="container">
        <table class="table table-striped">
            <colgroup>
                <col style="width:80px">
                <col style="width:410px">
                <col style="width:150px">
                <col style="width:200px">
                <col style="width:80px">
            </colgroup>
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty boards.content}">
                    <tr>
                        <td colspan="6" style="background-color: #deeaf5">
                            <div class="nodata">등록된 게시글이 없습니다.</div>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="board" items="${boards.content}">
                        <tr>
                        <td>${board.id}</td>
                        <td><a href="/board/${board.id}" >${board.title}</a></td>
                        <td>${board.user.name}</td>
                        <td>${board.createdDate}</td>
                        <td>${board.viewCount}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
<%--    <div class="card m-2" style="">--%>
<%--        <c:forEach var="board" items="${boards.content}">--%>
<%--            <div class="card-body">--%>
<%--                <h4 class="card-title">${board.title}</h4>--%>
<%--                <a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>

    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${boards.first}">
                <li class="page-item disabled"><a class="page-link" href="/?page=${boards.number-1}">Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="/?page=${boards.number-1}">Previous</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled"><a class="page-link" href="/?page=${boards.number+1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="/?page=${boards.number+1}">Next</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

<%@include file="layout/footer.jsp"%>


