
let principalUserProvider = $("#principalUserProvider");

let nameInput = $("input[name=name]");
let principalName = $("#updateForm_name").text();
let emailInput = $("input[name=email]");
let principalEmail = $("#updateForm_email").text();

let currentPasswordInput = $("input[name=currentPassword]");
let newPasswordInput = $("input[name=newPassword]");
let newPasswordConfirmInput = $("input[name=newPasswordConfirm]");
let userDelReqCurPwdInput = $("input[name=userDelReqCurrentPassword]");
let userDelCheckInput = $("input[name=userDelCheck]");

let passwordChangeSaveButton = $("#btn_pwd_chg_save");
let userDelButton = $("#btn_user_del_submit");

let index = {
    init: function () {
        // todo: oauth 계정은 패스워드 변경 필요 없으니. oauth 계정인지 체크해서 disable 처리해야 할 듯

        // 팝업 열기
        $("#li_name_chg").click(function(){
            nameInput.val(principalName);
            $("#modal_name_chg").fadeIn();
        });
        $("#li_email_chg").click(function(){
            emailInput.val(principalEmail);
            $("#modal_email_chg").fadeIn();
        });
        $("#li_pwd_chg").click(function(){
            $("#modal_pwd_chg").fadeIn();
        });
        $("#li_user_del").click(function(){
            $("#modal_user_del").fadeIn();
        });

        // 팝업 닫기
        $("#btn_name_chg_cancel").click(this.closeModalNameChg);
        $("#btn_email_chg_cancel").click(this.closeModalEmailChg);
        $("#btn_pwd_chg_cancel").click(this.closeModalPwdChg);
        $("#btn_user_del_cancel").click(this.closeUserDel);

        // 저장 요청
        $("#form_name_chg").submit(this.nameChangeSubmit);
        $("#form_email_chg").submit(this.emailChangeSubmit);
        $("#form_pwd_chg").submit(this.passwordChangeSubmit);
        $("#form_user_del").submit(this.userDelSubmit);

        // 패스워드 입력 시 유효성 검사
        currentPasswordInput.on('input', this.checkChangePasswordButton);
        newPasswordInput.on('input', this.checkChangePasswordButton);
        newPasswordConfirmInput.on('input', this.checkChangePasswordButton);
        // 회원 탈퇴 - 패스워드 입력 시 저장 버튼 활성화
        userDelReqCurPwdInput.on('input', this.checkPwdUserDelButton);
        userDelCheckInput.on('input', this.checkPwdUserDelButton);

    },

    /**
     * modal 닫기
     */
    closeModalNameChg : function(){
        // 닫기
        $("#modal_name_chg").fadeOut();
    },
    closeModalEmailChg : function(){
        // 닫기
        $("#modal_email_chg").fadeOut();
    },
    closeModalPwdChg : function(){
        // 입력 초기화
        currentPasswordInput.val(null);
        newPasswordInput.val(null);
        newPasswordConfirmInput.val(null);
        // 메시지 초기화
        $("#curPwdErrorMsg").text("");
        $("#newPwdErrorMsg").text("");
        $("#newPwdCheckErrorMsg").text("");

        // 닫기
        $("#modal_pwd_chg").fadeOut();
    },
    closeUserDel : function() {
        // 닫기
        $("#modal_user_del").fadeOut();
    },

    /**
     * 이름 변경 요청
     */
    nameChangeSubmit: function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const data = {
            name: nameInput.val()
        };
        $.ajax({
            url: '/me',
            type: 'PUT',
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            success: function (result) {
                $("#updateForm_name").text(result.name)

                // 변경 성공 시 modal 닫기
                index.closeModalNameChg();
                alert("성공적으로 업데이트 했습니다.");
            },
            error: function (xhr, status, error) {
                console.log("xhr = " + xhr);
                console.log("status = " + status);
                console.log("error = " + error);
            }
        });
    },

    /**
     * 이메일 변경 요청
     */
    emailChangeSubmit: function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const data = {
            email: emailInput.val()
        };
        $.ajax({
            url: '/me',
            type: 'PUT',
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            success: function (result) {
                $("#updateForm_email").text(result.email)

                // 변경 성공 시 modal 닫기
                index.closeModalEmailChg();
                alert("성공적으로 업데이트 했습니다.");
            },
            error: function (xhr, status, error) {
                console.log("xhr = " + xhr);
                console.log("status = " + status);
                console.log("error = " + error);
            }
        });
    },

    /**
     * 비밀번호 변경 요청
     */
    passwordChangeSubmit: function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const data = {
            currentPassword: currentPasswordInput.val(),
            newPassword: newPasswordInput.val()
        };

        $.ajax({
            url: '/me',
            type: 'PUT',
            data : JSON.stringify(data),  // http body 데이터
            contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            success: function (result) {
                // 패스워드 변경 성공 시 modal 닫기
                index.closeModalPwdChg();
                alert("성공적으로 업데이트 했습니다.");
            },
            error: function (xhr, status, error) {
                // 에러 처리
                if (xhr && xhr.responseJSON && xhr.responseJSON.code === "passwordMismatch") {
                    $("#curPwdErrorMsg").text("비밀번호가 일치하지 않습니다.");
                } else {
                    console.log("status = " + status);
                    console.log("error = " + error);
                }
            }
        });
    },

    /**
     * 회원 탈퇴 요청
     */
    userDelSubmit: function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const data = {
            currentPassword: userDelReqCurPwdInput.val()
        };

        $.ajax({
            url: '/me',
            type: 'DELETE',
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            data : JSON.stringify(data),  // http body 데이터
            success: function (result) {
                // 홈페이지로 리다이렉트
                alert("회원탈퇴 하였습니다.");
                location.replace("/");
            },
            error: function (xhr, status, error) {
                // 에러 처리
                if (xhr && xhr.responseJSON && xhr.responseJSON.code === "passwordMismatch") {
                    $("#userDelReqCurPwdErrMsg").text("비밀번호가 일치하지 않습니다.");
                } else {
                    console.log("status = " + status);
                    console.log("error = " + error);
                }
            }
        });
    },

    /**
     * 비밀번호 유효성 검사 함수
     */
    checkChangePasswordButton : function() {
        const currentPassword = currentPasswordInput.val();
        const newPassword = newPasswordInput.val();
        const newPasswordConfirm = newPasswordConfirmInput.val();
        let isValidateCurrentPassword = true;
        let isValidateNewPassword = true;
        let isValidateNewConfirmPassword = true;

        // todo: 회원가입 유효성 검사 로직 추가시 주석 풀자
        // if (!currentPassword) {
        //     $("#curPwdErrorMsg").text("");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateCurrentPassword = false;
        // } else if (currentPassword && !index.validatePassword(currentPassword)) {
        //     $("#curPwdErrorMsg").text("올바르지 않은 비밀번호입니다.");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateCurrentPassword = false;
        // } else {
        //     $("#curPwdErrorMsg").text("");
        // }
        //
        // if (!newPassword) {
        //     $("#newPwdErrorMsg").text("");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateNewPassword = false;
        // } else if (newPassword && !index.validatePassword(newPassword)) {
        //     $("#newPwdErrorMsg").text("올바르지 않은 비밀번호입니다.");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateNewPassword = false;
        // } else {
        //     $("#newPwdErrorMsg").text("");
        // }
        //
        // if (!newPasswordConfirm) {
        //     $("#newPwdCheckErrorMsg").text("");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateNewConfirmPassword = false;
        // } else if (newPasswordConfirm && newPassword !== newPasswordConfirm) {
        //     $("#newPwdCheckErrorMsg").text("비밀번호가 서로 일치하지 않습니다.");
        //     passwordChangeSaveButton.prop('disabled', true);
        //     isValidateNewConfirmPassword = false;
        // } else {
        //     $("#newPwdCheckErrorMsg").text("");
        // }

        // 모든 조건을 만족하면 변경 버튼 활성화
        if (isValidateCurrentPassword && isValidateNewPassword && isValidateNewConfirmPassword) {
            passwordChangeSaveButton.prop('disabled', false);
        }
    },

    /**
     * 회원 탈퇴 비밀번호 입력시 저장 버튼 활성화
     */
    checkPwdUserDelButton : function() {
        const userDelReqCurPwd = userDelReqCurPwdInput.val();

        // oAuth 회원이 아닐 때만 패스워드 체크
        if (!principalUserProvider.val()) {
            if (!userDelReqCurPwd) {
                $("#userDelReqCurPwdErrMsg").text("");
                userDelButton.prop('disabled', true);
                return;
            }
        }

        // 회원 삭제 동의 체크
        if (userDelCheckInput.is(":checked")){
            userDelButton.prop('disabled', false);
        } else {
            userDelButton.prop('disabled', true);
        }
    },


    /**
    비밀번호 형식 검사

    영문 대소문자, 숫자, 특수문자를 3가지 이상으로 조합해 8자 이상 16자 이하

     ^ : 문자열의 시작
    (?=.*?[A-Z]) : 영문 대문자가 최소한 1개 이상 포함되어야 함
    (?=.*?[a-z]) : 영문 소문자가 최소한 1개 이상 포함되어야 함
    (?=.*?[0-9]) : 숫자가 최소한 1개 이상 포함되어야 함
    (?=.*?[!@#$%^&*()_+~-={}[]:;"'<>,.?\/])` : 특수문자가 최소한 1개 이상 포함되어야 함
    .{8,16} : 총 길이가 8자 이상 16자 이하여야 함
    $ : 문자열의 끝
    */
     validatePassword : function (password) {
         const regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()_+~`\-={}[\]:;"'<>,.?\\/]).{8,16}$/;
         return regex.test(password);
     }

};

index.init();
