let index ={
    init: function () {
        $("#btn-save").on("click", () => {      // function(){} 대신 ()=>{} 사용하는 이유 : this를 바인딩하기 위함. function으로 하면 this는 window를 가리킴
            this.save();
        });
    },

    save: function () {
        //alert('user의 save함수 호출');

        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val()
        };
        //console.log(data);

        /**
         * ajax를 사용하는 이유
         *  1) 요청에 대한 응답을 html이 아닌 Json Data로 받기 위함
         *  2) 비동기 통신
         *
         *  ajax는 default가 비동기 요청
         */
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
        $.ajax({
            type : "POST",
            url : "/blog/api/user",
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json" // 서버로부터의 ajax 응답 데이터를 javascript object 형태로 변환해서 응답해줌 (생략가능)

        }).done(function(resp){
            //console.log(resp);
            alert("회원가입이 완료되었습니다");
            location.href ="/blog";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
index.init();