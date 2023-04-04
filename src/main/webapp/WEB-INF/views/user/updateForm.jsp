<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>

<div class="container p-1 my-1 text-center">
    <h3 style="font-weight: bold">회원 정보</h3>
</div>
<div class="container">
    <br>
    <br>
    <div class="div_normal">
        <section class="section_normal">
            <p  class="p_subject">계정 관리</p>
            <p  class="p_explain">서비스에서 사용하는 내 계정 정보를 관리할 수 있습니다.</p>
            <ul class="ul_normal">
                <li id="li_name_chg" class="li_normal" style="cursor: pointer;">
                    <p class="p_label">이름</p>
                    <p id ="updateForm_name" class="p_value">${principal.user.name}</p>
                    <span class="span_normal">
                        <svg viewBox="0 0 8 14" class="svg_normal">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M0.969605 13.0303C1.2625 13.3232 1.73737 13.3232 2.03027 13.0303L7.53027 7.53033C7.82316 7.23744 7.82316 6.76257 7.53027 6.46967L2.03033 0.969673C1.73744 0.676778 1.26257 0.676775 0.969673 0.969667C0.676778 1.26256 0.676776 1.73743 0.969667 2.03033L5.93928 7L0.969605 11.9697C0.676712 12.2626 0.676712 12.7374 0.969605 13.0303Z"
                                  fill="#888;">
                            </path>
                        </svg>
                    </span>
                </li>
                <li id="li_email_chg" class="li_normal" style="cursor: pointer;">
                    <p class="p_label">이메일</p>
                    <p id ="updateForm_email" class="p_value">${principal.user.email}</p>
                    <span class="span_normal">
                        <svg viewBox="0 0 8 14" class="svg_normal">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M0.969605 13.0303C1.2625 13.3232 1.73737 13.3232 2.03027 13.0303L7.53027 7.53033C7.82316 7.23744 7.82316 6.76257 7.53027 6.46967L2.03033 0.969673C1.73744 0.676778 1.26257 0.676775 0.969673 0.969667C0.676778 1.26256 0.676776 1.73743 0.969667 2.03033L5.93928 7L0.969605 11.9697C0.676712 12.2626 0.676712 12.7374 0.969605 13.0303Z"
                                  fill="#888;">
                            </path>
                        </svg>
                    </span>
                </li>
<%--                <li tabindex="0" class="li_normal">
                    <p class="p_label">휴대폰 번호</p>
                    <p class="p_value"></p>
                    <span class="span_normal">
                        <svg viewBox="0 0 8 14" class="svg_normal">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M0.969605 13.0303C1.2625 13.3232 1.73737 13.3232 2.03027 13.0303L7.53027 7.53033C7.82316 7.23744 7.82316 6.76257 7.53027 6.46967L2.03033 0.969673C1.73744 0.676778 1.26257 0.676775 0.969673 0.969667C0.676778 1.26256 0.676776 1.73743 0.969667 2.03033L5.93928 7L0.969605 11.9697C0.676712 12.2626 0.676712 12.7374 0.969605 13.0303Z"
                                  fill="#888;">
                            </path>
                        </svg>
                    </span>
                </li>--%>
            </ul>
        </section>
        <section class="section_normal">
            <p  class="p_subject">계정 정보 보호</p>
            <p  class="p_explain">내 계정을 안전하게 보호하기 위한 정보를 관리할 수 있습니다.</p>
            <ul class="ul_normal">
<c:choose>
    <c:when test="${empty principal.user.provider}">
                <li id="li_pwd_chg" class="li_normal" style="cursor: pointer;">
                    <p class="p_label">비밀번호 변경</p>
                    <p class="p_value"></p>
                    <span class="span_normal">
                        <svg viewBox="0 0 8 14" class="svg_normal">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M0.969605 13.0303C1.2625 13.3232 1.73737 13.3232 2.03027 13.0303L7.53027 7.53033C7.82316 7.23744 7.82316 6.76257 7.53027 6.46967L2.03033 0.969673C1.73744 0.676778 1.26257 0.676775 0.969673 0.969667C0.676778 1.26256 0.676776 1.73743 0.969667 2.03033L5.93928 7L0.969605 11.9697C0.676712 12.2626 0.676712 12.7374 0.969605 13.0303Z"
                                  fill="#888;">
                            </path>
                        </svg>
                    </span>
                </li>
    </c:when>
</c:choose>
                <li id="li_user_del" class="li_normal" style="cursor: pointer;">
                    <p class="p_label">회원 탈퇴</p>
                    <p class="p_value"></p>
                    <span class="span_normal">
                        <svg viewBox="0 0 8 14" class="svg_normal">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M0.969605 13.0303C1.2625 13.3232 1.73737 13.3232 2.03027 13.0303L7.53027 7.53033C7.82316 7.23744 7.82316 6.76257 7.53027 6.46967L2.03033 0.969673C1.73744 0.676778 1.26257 0.676775 0.969673 0.969667C0.676778 1.26256 0.676776 1.73743 0.969667 2.03033L5.93928 7L0.969605 11.9697C0.676712 12.2626 0.676712 12.7374 0.969605 13.0303Z"
                                  fill="#888;">
                            </path>
                        </svg>
                    </span>
                </li>
            </ul>
        </section>
    </div>
    <%--<a href="#modal_pwd_chg" id="btn_pwd_chg_open" >비밀번호 변경</a>--%>
    <div id="modal_name_chg" class="modal_wrap" style="display:none;">
        <div class="modal_inner">
            <div style="text-align: center">
                <h4>이름</h4>
            </div>
            <br>
            <br>
            <form id="form_name_chg" method="put" action="/me">
                <input class ="input_normal" type="input" name="name" placeholder="이름을 입력해주세요." value="${principal.user.name}">
                <br>
                <br>
                <div style="text-align: right">
                    <button type="button" id="btn_name_chg_cancel" class="btn_normal">취소</button>
                    <button type="submit" id="btn_name_chg_save" class="btn_normal">저장</button>
                </div>
            </form>
        </div>
    </div>
    <div id="modal_email_chg" class="modal_wrap" style="display:none;">
        <div class="modal_inner">
            <div style="text-align: center">
                <h4>이메일</h4>
            </div>
            <br>
            <br>
            <form id="form_email_chg" method="put" action="/me">
                <input class ="input_normal" type="input" name="email" placeholder="이메일을 입력해주세요." value="${principal.user.email}">
                <br>
                <br>
                <div style="text-align: right">
                    <button type="button" id="btn_email_chg_cancel" class="btn_normal">취소</button>
                    <button type="submit" id="btn_email_chg_save" class="btn_normal">저장</button>
                </div>
            </form>
        </div>
    </div>
    <div id="modal_pwd_chg" class="modal_wrap" style="display:none;">
        <div class="modal_inner">
            <div style="text-align: center">
            <h4>비밀번호 변경</h4>
            </div>
            <br>
            <br>
            <form id="form_pwd_chg">
                <div>
                    <label>현재 비밀번호</label>
                </div>
                <input class ="input_normal" type="password" name="currentPassword" placeholder="비밀번호를 입력해주세요.">
                <p style="font-size: small; color: red;" id="curPwdErrorMsg"><%-- 올바르지 않은 비밀번호입니다. or 비밀번호가 일치하지 않습니다.--%></p>
                <br>
                <div>
                    <label>새 비밀번호</label>
                </div>
                <input class ="input_normal" type="password" name="newPassword" placeholder="새 비밀번호를 입력해주세요.">
                <p style="font-size: small; color: red;" id="newPwdErrorMsg" style="font-size: small"><%-- 올바르지 않은 비밀번호입니다. --%></p>
                <input class ="input_normal" type="password" name="newPasswordConfirm" placeholder="새 비밀번호를 다시 한번 입력해주세요.">
                <p style="font-size: small; color: red;" id="newPwdCheckErrorMsg"><%-- 비밀번호가 서로 일치하지 않습니다. --%></p>
                <div style="font-size: small;">
                    <p>영문 대소문자, 숫자, 특수문자를 3가지 이상으로 조합해 8자 이상 16자 이하로 입력해주세요.</p>
                </div>
                <br>
                <div style="text-align: right">
                    <button type="button" id="btn_pwd_chg_cancel" class="btn_normal">취소</button>
                    <button type="submit" id="btn_pwd_chg_save" class="btn_normal" disabled >저장</button>
                </div>
            </form>
        </div>
    </div>
    <div id="modal_user_del" class="modal_wrap" style="display:none;">
        <div class="modal_inner">
            <div style="text-align: center">
                <h4>회원 탈퇴</h4>
            </div>
            <br>
            <br>
            <form id="form_user_del">
                <div>
                    <p  class="p_explain">탈퇴 시 등록한 서비스의 모든 정보가 영구적으로 삭제되며, 다시는 복구할 수 없습니다.</p>
                </div>
                <br>
                <c:choose>
                    <c:when test="${empty principal.user.provider}">
                        <div id="div_userDelPwd">
                            <label>비밀번호</label>
                            <input class ="input_normal" type="password" name="userDelReqCurrentPassword" placeholder="비밀번호를 입력해주세요.">
                            <p style="font-size: small; color: red;" id="userDelReqCurPwdErrMsg"><%-- 비밀번호가 일치하지 않습니다.--%></p>
                        </div>
                    </c:when>
                </c:choose>
                <br>
                <div>
                    <input type="checkbox" name="userDelCheck" class="css-input_normal">
                    <p color="#888;" class="p_inputCheckBox">회원 탈퇴를 진행하여 해당 계정에 귀속된 모든 정보를 삭제하는 데 동의합니다.</p>
                </div>
                <br>
                <div style="text-align: right">
                    <button type="button" id="btn_user_del_cancel" class="btn_normal">취소</button>
                    <button type="submit" id="btn_user_del_submit" class="btn_normal" disabled >회원 탈퇴</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- 절대 경로 / 다음은 'main/resources/static' 에서 찾는다. 그런데, application.yml에서 context-path를 /blog로 설정 했기 때문에
  /blog가 'main/resource/static'을 가리킨다.--%>
<script src="/js/user.js"></script>
<script type="module" src="/js/updateUser.js?v=<%=System.currentTimeMillis() %>"></script>
<%@ include file="../layout/footer.jsp"%>