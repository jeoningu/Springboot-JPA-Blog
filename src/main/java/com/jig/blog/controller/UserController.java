package com.jig.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jig.blog.model.KakaoToken;
import com.jig.blog.model.KakaoUserInfoResponse;
import com.jig.blog.model.User;
import com.jig.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


/**
 * 접속 허용 경로
 * - 인증 안된 사용자들이 출입할 수 잇는 경로로서 /auth/** 허용
 * - 기본 경로 ( / ) 면 index.jsp 허용
 * - static 이하에 있는 /js/**. /css/**, /image/** 허용
 *
 */
@Controller
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }


// 카카오 로그인도 아래 로직 사용 안 하고 OAuth2 Client 라이브러리 사용하도록 변경함
//
//    @GetMapping("/auth/kakao/callback")
//    public  String kakaoCallback(@RequestParam String code  // 반환 자료형 앞에 @ResponseBody를 붙이면 Data를 리턴해주는 컨트롤러함수가 된다.
//            , @Value("${kakao.springboot_blog_project.REST_API_KEY}") String REST_API_KEY
//            , @Value("${kakao.springboot_blog_project.REDIRECT_URI}") String REDIRECT_URI
//            ,@Value("${kakao.springboot_blog_project.PUBLIC_PASSWORD}") String PUBLIC_PASSWORD
//    ) {
//        // TODO: 아래 과정을 service단으로 옮길 필요는 없는 건지? (@Transactional 처리)
//
//        // 카카오 토큰 요청
//        KakaoToken KakaoToken = getKakaoTokenRequest(code, REST_API_KEY, REDIRECT_URI);
//        // 카카오 사용자 정보 요청
//        KakaoUserInfoResponse kakaoUserInfo = getKakaoUserInfoRequest(KakaoToken);
//
//        // TODO : Oauth2 Client로 사용안하게 될 메서드라서 providerId 추가 안함.
//        User user = User.builder()
//                .username(kakaoUserInfo.getKakao_account().getEmail() + "_" + kakaoUserInfo.getId())
//                .password(PUBLIC_PASSWORD)
//                .email(kakaoUserInfo.getKakao_account().getEmail())
//                .provider("kakao")
//                .build();
//
//        // 가입자 인지 확인
//        User findUser = userService.getUser(user.getUsername());
//        // 기존 회원이 아니면 회원 가입
//        if( null == findUser) {
//            userService.joinUser(user);
//        }
//
//        // 로그인 처리
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), PUBLIC_PASSWORD));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // 홈으로 리다이렉트
//        return "redirect:/";
//    }
//
//    /**
//     *  카카오 사용자 정보 요청
//     *
//     * @param KakaoTokenResponse
//     * @return
//     */
//    private KakaoUserInfoResponse getKakaoUserInfoRequest(KakaoToken KakaoTokenResponse) {
//        // HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//        headers.add("Authorization", "Bearer " + KakaoTokenResponse.getAccess_token());
//
//        // HttpBody  오브젝트 생성
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        //  property_keys 포함시 400error(파싱 문제인듯?) 반환됨.
//        // params.add("property_keys", new String[]{"kakao_account.profile", "kakao_account.name","kakao_account.email"});
//        // 카카오에 문의 결과 : 'String[]'가 아니라 String 형식으로 '[]모양의 문자열' 형태로 보내야 함
//        params.add("property_keys", "[\"kakao_account.profile\",\"kakao_account.name\",\"kakao_account.email\"]");
//        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기 ( 이유 : exchange에서 HttpEntity<?>를 받기 때문)
//        HttpEntity<MultiValueMap<String, Object>> kakaoUserInfoRequest =
//                new HttpEntity<>(params, headers);
//
//        // Http 요청 후 response에 응답 받음
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        // Json데이터 파싱 라이브러리 사용 ( 예시)Gson, Json Simple, ObjectMapper)
//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoUserInfoResponse kakaoUserInfoResponse = null;
//        try { // readValue(파싱) 과정에서 데이터 받는 클래스의 변수명이 다르거나 setter가 없는 경우 error 발생! try-catch문으로 처리
//            kakaoUserInfoResponse = objectMapper.readValue(response.getBody(), KakaoUserInfoResponse.class);
//        } catch (JsonProcessingException e) {
//            logger.error("{}", e);
//        }
//
//        return kakaoUserInfoResponse;
//    }
//
//    /**
//     * 카카오 토큰 요청
//     *
//     * ( java POST요청은 보통 Retorfit2, OkHttp, RestTemplate 방식들 중 한개를 사용함)
//     *
//     * @param code
//     * @param REST_API_KEY
//     * @param REDIRECT_URI
//     * @return
//     */
//    private KakaoToken getKakaoTokenRequest(String code, String REST_API_KEY, String REDIRECT_URI) {
//        // HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HttpBody  오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code"); // authorization_code로 고정
//        params.add("client_id", REST_API_KEY); // 앱 REST API 키
//        params.add("redirect_uri", REDIRECT_URI); // 인가 코드가 리다이렉트된 URI
//        params.add("code", code); // 인가 코드 받기 요청으로 얻은 인가 코드
//
//        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기 ( 이유 : exchange에서 HttpEntity<?>를 받기 때문)
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        // Http 요청 후 response에 응답받음
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // Json데이터 파싱 라이브러리 사용 ( 예시)Gson, Json Simple, ObjectMapper)
//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoToken kakaoToken = null;
//        try { // readValue(파싱) 과정에서 데이터 받는 클래스의 변수명이 다르거나 setter가 없는 경우 error 발생! try-catch문으로 처리
//            kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);
//        } catch (JsonProcessingException e) {
//            logger.error("{}", e);
//        }
//        logger.info("oauthToken.getAccess_token : {}", kakaoToken.getAccess_token());
//
//        return kakaoToken;
//    }
}
