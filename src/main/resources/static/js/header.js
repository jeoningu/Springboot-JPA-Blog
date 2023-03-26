function funcLogout () {
    let userId = $("#principalUserId").val()
    if (userId) {
        // 로컬스토리지에서 sse를 꺼내서 구독 종료 후 로컬스토리지에서도 지운다.
        let sse = localStorage.getItem(userId);
        if (sse) {
            /*
            sse를 로컬스토리지에 담을 수 없어서 종료 부분 잠시 보류 -> indexDB 사용가능한지 확인해봐야 함
            // sse를 종료
            // sse.close();
             */
            localStorage.removeItem(userId);
        }
    } else {
        console.log("principal error")
    }

    location.href ="/logout";
}
