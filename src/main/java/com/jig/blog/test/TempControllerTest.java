package com.jig.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("TempControllerTest.tempHome");
        /**
         * 클레스 레벨에 @Controller를 붙였으면 파일명을 리턴한다. 파일명 앞에는 /를 붙여준다.
         *
         *         // 파일 리턴 기본 경로 : src/main/resource/static
         *         // 리턴 명 : /home.html
         *         // 전체 경로 : src/main/resource/static/home.html
         */

        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        /**
         * webbapp은 스프링 부트 내 embedded tomcat의 기본 폴더 입니다.
         *
         * spring:
         *   mvc:
         *     view:
         *       prefix: /WEB-INF/views/
         *       suffix: .jsp
         *
         * 최종적으로 src/main/webapp/WEB-INF/views/text.jsp 파일을 찾게 된다.
         */
        return "test";
    }
}
