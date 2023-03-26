// import {serverSentEvents} from "./module/ux/ToastrMessageQueue.js";

let index = {
    init: function () {
        $("#btn-save").on("click", () => {      // function(){} 대신 ()=>{} 사용하는 이유 : this를 바인딩하기 위함. function으로 하면 this는 window를 가리킴
            this.save();
        });
/*        $("#btn-login").on("click", () => {
            this.login();
        });
        */
        $("#btn-update").on("click", () => {
            this.update();
        });
/*        $("#oauth2-naver-btn").on("click", ()=>{
            this.oauth2Login();
        });
        */
/*        $("#username").on("focusout", ()=> {
            this.usernameValidate();
        })*/
    },

    save: function () {
        //alert('user의 save함수 호출');

        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            name : $("#name").val(),
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
            url : "/auth/joinProc",
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json" // 서버로부터의 ajax 응답 데이터를 javascript object 형태로 변환해서 응답해줌 (생략가능)

        }).done(function(resp){
            //console.log(resp);
            alert("회원가입이 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
/*
    serverSentEvents를 로그인 후 한번만 구독하기 위해
    로그인 요청 콜백 함수에서 하려고 ajax로 변경하려 했다가 중단함
     - header에서 로그인 되어있는지 체크하고 localStorage에서 구독했는지 확인 하는 걸로 처리함.

    login: function () {
        let data = {
            username : $("#username").val(),
            password : $("#password").val()
        };

        $.ajax({
            type : "POST",
            url : "/auth/joinProc",
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType: "json",
            success : function (){
                alert("로그인이 완료되었습니다");
            },
            error : function () {
                alert("로그인이 실패하였습니다.");
            }
        });
    },
    /!*done(function(resp){
           alert("로그인이 완료되었습니다");
           //serverSentEvents(resp.id);
           location.href ="/";

       }).fail(function(xhr, status, error){
           let err = eval("(" + xhr.responseText + ")");
           alert(err.message);
       });*!/
    */
    update: function () {
        let data = {
            id : $("#id").val(),
            //username : $("#username").val(), // session update 에 사용됨 ( authentication을 만들어서 session의 SecurityContextHolder의 Context에 넣어주는 방식일 때 사용. 다른 방식으로 userApiController에서 처리)
            password : $("#password").val(),
            name : $("#name").val(),
            email : $("#email").val()
        };

        $.ajax({
            type : "PUT",
            url : "/user",
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json" // 서버로부터의 ajax 응답 데이터를 javascript object 형태로 변환해서 응답해줌 (생략가능)

        }).done(function(resp){
            alert("회원수정이 완료되었습니다");
            location.href ="/";

        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

/*
    serverSentEvents를 로그인 후 한번만 구독하기 위해
    로그인 요청 콜백 함수에서 하려고 ajax로 변경하려 했다가 중단함
     - header에서 로그인 되어있는지 체크하고 localStorage에서 구독했는지 확인 하는 걸로 처리함.
    oauth2Login: function () {

        $.ajax({
            type : "GET",
            url : "/oauth2/authorization/naver",
            //contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            // dataType: "json" // 서버로부터의 ajax 응답 데이터를 javascript object 형태로 변환해서 응답해줌 (생략가능)

        }).done(function(resp){
            console.log(1);
            //alert("로그인이 완료되었습니다");
            //serverSentEvents(resp.id);
            //location.href ="/";

        }).fail(function(error,a,b,c){
            alert(JSON.stringify(error));
        });
    },*/

/*  유효성 검사 로직 추가 필요
usernameValidate : function() {
        let id = $("#id").val();

        // https://devmoony.tistory.com/162
        if () {

        }
    }*/
};
index.init();