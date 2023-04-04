let index ={
    init: function () {
        $("#btn-save").on("click", () => {      // function(){} 대신 ()=>{} 사용하는 이유 : this를 바인딩하기 위함. function으로 하면 this는 window를 가리킴
            this.save();
        });
        $("#btn-delete").on("click", () => {
            this.deleteById();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        });
        $("#btn-modifyReply-save").on("click", () => {
            this.modifyReplySave();
        });
    },

    save: function () {

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
        };

        $.ajax({
            type : "POST",
            url : "/api/board",
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            //dataType: "json"

        }).done(function(resp){
            alert("글쓰기가 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteById: function () {

        let id = $("#id").text();

        $.ajax({
            type : "DELETE",
            url : "/api/board/"+id,
            contentType : "application/json; charset=utf-8",
            //dataType: "json"

        }).done(function(resp){
            alert("글 삭제가 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update: function () {

        let id = $("#id").val();

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
        };

        $.ajax({
            type : "PUT",
            url : "/api/board/"+id,
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            //dataType: "json"

        }).done(function(resp){
            alert("글 수정이 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replySave: function () {
        let boardId = $("#id").text()
        let data = {
            content : $("#reply-content").val(),
        };

        // TODO: content 빈칸 validate 로직 추가 필요

        $.ajax({
            type : "POST",
            url : `/api/board/${boardId}/reply`,    // ` 백틱 사용 // boardId를 data에 담지 않고 path방식을 사용하는 이유: id는 path방식으로 주소에 담고 데이터는 body에 담는 방식을 지키기 위함
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            //dataType: "json"
        }).done(function(resp){
            //alert("댓글이 등록 되었습니다.");
            location.href = `/board/${boardId}`;

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replyDelete: function (boardId, replyId) {

        $.ajax({
            type : "DELETE",
            url : `/api/board/${boardId}/reply/${replyId}`,    // ` 백틱 사용 // boardId를 data에 담지 않고 path방식을 사용하는 이유: id는 path방식으로 주소에 담고 데이터는 body에 담는 방식을 지키기 위함
            contentType : "application/json; charset=utf-8",
            //dataType: "json"

        }).done(function(resp){
            //alert("댓글이 삭제 되었습니다.");
            location.href ="/board/"+$("#id").text();

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    replyUpdate: function (boardId, replyId) {
        $(`#reply-${replyId}`).css('display', 'none');
        $(`#modifyReply-${replyId}`).css('display', ' block');
    },

    modifyReplySave: function () {
        let boardId = $("#id").text();
        let replyId = $("#modifyReply-id").val();
        let data = {
            content : $("#modifyReply-content").val(),
        };

        $.ajax({
            type : "PUT",
            url : `/api/board/${boardId}/reply/${replyId}`,    // ` 백틱 사용 // boardId를 data에 담지 않고 path방식을 사용하는 이유: id는 path방식으로 주소에 담고 데이터는 body에 담는 방식을 지키기 위함
            contentType : "application/json; charset=utf-8",
            data : JSON.stringify(data),
            //dataType: "json"
        }).done(function(resp){
            //alert("댓글이 수정 되었습니다.");
            location.href ="/board/"+$("#id").text();

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};

index.init();