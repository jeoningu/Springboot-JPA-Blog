package com.jig.blog.config.security.oauth;

import com.jig.blog.config.security.PrincipalDetail;
import com.jig.blog.config.security.oauth.provider.*;
import com.jig.blog.model.RoleType;
import com.jig.blog.model.User;
import com.jig.blog.repository.UserRepository;
import com.jig.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     *  BCryptPasswordEncoder-loadUser, PrincipleService-loadUserByUsername
     *  가 없어도 내부적으로 동작하는데, PrincipalDetails반환해주기 위해 overrride 함.
     *
     * [구글로부터 받은 userRequest 데이터에 대한 후처리를 하는 함수]
     *
     *   구글 로그인 동작 흐름
     *    1. 구글 로그인 버튼 클릭
     *    2. 구글 로그인 창
     *    3. 로그인 완료
     *    4. code를 반환 받는데, OAuth2 Client 라이브러리가 code를 이용해서 AccessToken을 요청
     *    5. AccessToken과 userRequest 정보를 반환받는다. loadUser 함수에서 받환 받는다. ( userRequest에는 회원프로필 정보가 있다.)
     *
     * @param userRequest
     * @return OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // oauth 로그인 사용자 정보 데이터. 개발용
        logger.info(" =================== loadUser에서 데이터 확인 =================== ");
        logger.info("userRequest = {}", userRequest);
        logger.info("userRequest.getClientRegistration() = {}", userRequest.getClientRegistration());
        logger.info("userRequest.getAccessToken() = {}", userRequest.getAccessToken());
        logger.info("userRequest.getAccessToken().getTokenValue() = {}", userRequest.getAccessToken().getTokenValue());
        logger.info("super.loadUser(userRequest).getAttributes() = {}", super.loadUser(userRequest).getAttributes());

        // OAuth2 종류에 따른 데이터 맵핑
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        // TODO: SWITCH-CASE문으로 수정하면 좋을듯, provider는 아래에서 정의하고.
        if ("google".equals(provider)) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2UserAttributes);
            logger.info("google OAuth2 로그인 요청 입니다.");
        } else if ("facebook".equals(provider)) {
            oAuth2UserInfo = new FacebookUserInfo(oAuth2UserAttributes);
            logger.info("facebook OAuth2 로그인 요청 입니다.");
        } else if ("naver".equals(provider)) {
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2UserAttributes.get("response"));
            logger.info("naver OAuth2 로그인 요청 입니다.");
        } else if ("kakao".equals(provider)) {
            oAuth2UserInfo = new KakaoUserInfo(String.valueOf( oAuth2UserAttributes.get("id")), (Map)oAuth2UserAttributes.get("kakao_account"));
            logger.info("kakao OAuth2 로그인 요청 입니다.");
        } else {
            logger.error("지원하지 않는 OAuth2입니다.");
            throw new IllegalArgumentException("지원하지 않는 OAuth2입니다.");
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        // TODO: OAuth2 회원가입인 경우 패스워드에 특정값을 넣어줘서 노출되는 경우 모든 OAuth 회원이 노출되게 되는 문제가 있음. 해결 필요함
        String password = bCryptPasswordEncoder.encode("특정값");
        String email = oAuth2UserInfo.getEmail();
        String name =  oAuth2UserInfo.getName();
        String role = "ROLE_USER";

        // 회원등록이 안됐으면 회원가입
        User userEntity = userService.getUser(username);
        if (null == userEntity) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role(RoleType.USER)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
            logger.info("존재하지 않는 OAuth2회원입니다. 자동으로 회원가입됩니다.");
        } else {
            logger.info("이미 가입된 OAuth2 회원입니다.");
        }

        return new PrincipalDetail(userEntity, oAuth2UserAttributes);
    }
}
