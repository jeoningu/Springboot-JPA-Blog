package com.jig.blog.test;

import org.springframework.web.bind.annotation.*;

// @Controller 사용 : 사용자가 요청 -> 응답(HTML 파일)
// @RestController 사용 : 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        //Member member = new Member(1,"jig","1234","jigEmail");

        Member member = Member.builder().username("jig").password("1234").email("jigEmail").build();
        System.out.println(TAG + "getter : " + member.getId());
        member.setId(5000);
        System.out.println(TAG + "getter : " + member.getId());

        return "lombok test 완료";
    }

    // 브라우저에서 요청은 get요청만 가능하다. (브라우저에서 post,put,delete 요청하면 405 error 발생 -> 브라우저에서 테스트 불가하니, postman 활용해서 테스트)
    // http://localhost:8080/http/get ( selete )
    @GetMapping("/http/get")
    public String getTest(Member member) {   //  url에 쿼리스트링으로 요청을 하면 스프링의 message converter가 Member 클래스의 변수에 맞게 맵핑해준다.

        return "get 요청 id = " + member.getId() + " username = " + member.getUsername();
    }

    // http://localhost:8080/http/post ( insert )
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member member ) {
        return "post 요청 id = " + member.getId() + " username = " + member.getUsername();
    }

    // http://localhost:8080/http/put ( update )
    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    // http://localhost:8080/http/delete ( delete )
    @DeleteMapping("http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
