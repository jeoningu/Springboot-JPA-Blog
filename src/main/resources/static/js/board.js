let index ={
    init: function () {
        $("#btn-save").on("click", () => {      // function(){} 대신 ()=>{} 사용하는 이유 : this를 바인딩하기 위함. function으로 하면 this는 window를 가리킴
            this.save();
        });
        $("#btn-delete").on("click", () => {
            this.deleteById();
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
            dataType: "json"

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
            dataType: "json"

        }).done(function(resp){
            alert("글 삭제가 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};

index.init();