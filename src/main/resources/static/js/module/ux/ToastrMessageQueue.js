/**
 * toast 알림창 띄우기
 * @param _id
 * @param message
 * @param option
 */
function toast(_id, message, option) {
    let mother = document.getElementById(_id);
    mother.removeChild(mother.firstChild);  //초기화
    let doc = document.createElement('div');
    mother.appendChild(doc);
    // doc.innerText = message;
    doc.outerHTML = message;

    //부모노드 입니다.
    let motherStyle = {
        //display: 'flex',
        position: 'fixed',
        bottom: '4rem',
        left: '75%',
        width: '320px',
        height:'80px',
        'line-height': '80px',
        //'z-index': 99999999,
        background: 'rgb(227 219 220)',
        border : '2px',
        'border-color': 'red',
        'border-style' : 'inset',
        'border-radius': '1rem',
        'text-align' : 'center'
    };
    for (key in motherStyle) {  //부모테그에 스타일을 적용 합니다.
        mother.style[key] = motherStyle[key];
    }

    //자식노드 입니다.
    let style = {
        opacity: 0,
        //background: '#595959',
        //color: 'white',
        padding: '1rem 3.5rem 1rem 3.5rem',
        'border-radius': '1rem'
    };
    if (option) style = {...style, option};

    //자식노드에 스타일 적용 합니다.
    for (key in style) {
        doc.style[key] = style[key];
    }

    let max = false;  //동작 완료여부 판별 변수 입니다.
    let inter = setInterval(() => {  //이벤트
        let number = parseFloat(doc.style.opacity)+0.02;
        if(max) number = parseFloat(doc.style.opacity)-0.02;
        if(number >= 1){
            setTimeout(() => {
                max = true;
                setTimeout(() => {
                    mother.style.display = 'none';  //none처리를 통해서 다른 영역이 선택가능하게 합니다.
                    clearInterval(inter);  //이벤트 끝!
                }, 15000);  // 알람 떠 있는 시간
            }, 500);
        }
        doc.style.opacity = number;
    }, 10)
};

/**
 * 로그인 됐으면 SSE로 구독 요청하기
 * - localStorage에서 userId로 값 가져와서 false면 구독 중이지 않은 상태
 * - 구독중이지 않으면 구독 요청 후 localStorage에 저장  key : userId, value : "ture"
 */
function serverSentEvents() {
    //  principal이 없으면 로그인 안 된걸로 판단
    if( !$("#principalUserId")[0] ) return;

    let userId = $("#principalUserId").val();

    /**
     * Server-sent-events 구현
     * // 이벤트 이름을 설정해주면 클라이언트에서 해당 이름으로 이벤트를 받을 수 있습니다.
     * // 클라이언트에서는 `count`라는 이름의 이벤트가 발생할 때 콘솔에 변경된 데이터를 출력하도록 이벤트 리스너를 등록해둡니다.
     * // 이제 만약 다른 브라우저에서 count를 호출한다면 내 브라우저에 서버에서 변경된 값이 찍히게 됩니다.
     */
    if (localStorage.getItem(userId)) {
        // 이미 구독 중인 경우
    } else {
        // 구독 중이 아닌 경우
        // EventSource라는 인터페이스로 서버에 SSE 연결 요청
        let sse = new EventSource(`http://localhost:8080/subscribe/${userId}`);
        // localStorage에 저장
        localStorage.setItem(userId, "isSse-true");

        // "sse" 이름으로 구독 요청
        sse.addEventListener('sse', (event) => {

            if (typeof(event.data) == 'object') {
                const data = JSON.parse(event.data); //const { data: receivedConnectData } = event;
                if (data) {
                    toast('toast', `<a href="/board/${data.boardId}" >댓글이 추가된 게시글로 이동합니다.</br>data.boardTitle</a>`, {});
                }
            }
        });
    }
};

serverSentEvents();





