<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <%-- 스프링 시큐리티에 의한 로그인 요청 --%>
    <form>
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" class="form-control" placeholder="Enter title" id="title">
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea class="form-control summernote" rows="5" id="content"></textarea>
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>
</div>

<script>
    $('.summernote').summernote({
        /*placeholder: 'Hello Bootstrap 4',*/
        tabsize: 2,
        height: 300
    });
</script>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>