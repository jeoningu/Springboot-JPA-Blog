package com.jig.blog.config.security;


import com.jig.blog.config.security.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.PostConstruct;

/**
 * 스프링 시큐리티 기본 설정
 */
@Configuration // 싱글톤 스프링 빈 등록
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근 하면 권한 및 인증을 미리 체크
@Order(1) // ??
public class SecurityConfig extends WebSecurityConfigurerAdapter {

// PwdConfig.java로 이전
//    /**
//     * 패스워드를 암호화 해주는 빈
//     * @return
//     */
//    @Bean
//    public BCryptPasswordEncoder encoderPWD() {
//        return new BCryptPasswordEncoder();
//    }
//
    @Autowired
    private PrincipalDetailService principalDetailService;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @PostConstruct
    private void init() {
        System.out.println("SecurityConfig.init");
    }

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

// 설정해주지 않아도 기본으로 BCryptPasswordEncoder로 설정되어있음. (WebSecurityConfigurerAdapter.java -  DefaultPasswordEncoderAuthenticationManagerBuilder() - - - -> PasswordEncoderFactories.java에서 기본 값을 bcrypt으로 함 )
//    /**
//     * 해싱(Hashing) :  단방향 암호화 기법
//     * 암호화(Encryption) : 양방향 암호화 기법
//     *
//     * 비밀번호를 저장할 때는 행여 탈취될 가능성을 있기 때문에 평문을 암호화하는 것은 가능하지만 다시 평문으로 복호화하는 것은 불가능한 단방향 암호화 방식을 사용한다.
//     * 같은 데이터를 같은 해시 알고리즘을 통해 암호활 경우 항상 같은 결과가 나오기 때문에 복호화가 불가능해도 사용자 인증은 가능하다.
//     *
//     * 로그인할 때 시큐리티가 가로채서 패스워드를 해싱하는데,
//     * 회원가입할 떄 사용한 해시 알고리즘과 같은 알고리즘을 사용하여 해싱 한다.
//     * 따라서 DB에 있는 해쉬데이터랑 비교하여 로그인 할 수 있다.
//     * @param auth
//     * @throws Exception
//     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(ajaxAuthenticationProvider());
        // 로그인 할 때 패스워드를 encoderPWD()로 인코드 하고 principalDetailService로 username, password 처리 (DB와 비교)를 한다.
        auth.userDetailsService(principalDetailService)/*.passwordEncoder(encoderPWD())*/;
    }

 /*   @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return ajaxLoginProcessingFilter;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()// csrf 토큰 비활성화 ( 테스트 시에 설정 필요 )
            .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/subscribe/*")  // 이 경로로 들어오면    ( "/auth/loginForm", "/auth/joinForm" )
                .permitAll()                         // 누구나 허용
                .anyRequest()    // 위 경로가 아닌 요청은
                .authenticated() // 인증이 필요하다
            .and()
                //.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()     // 위 경로가 아닌 요청은 로그인 페이지로 이동시키고
                .loginPage("/auth/loginForm") // 로그인 페이지는 이 경로로 하겠다.
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 이 요청을 가로채서 대신 로그인 해준다. UserDetails 상속 객체 필요(직접 controller 구현 필요없음)
                .defaultSuccessUrl("/") // 정상 로그인 후 이동되는 경로
                //.usernameParameter("id") // form의 name을 변경합니다. 이 부분은 없어도 되며, 그럼 default는 'username' 입니다.
            .and()
                .oauth2Login()
                .loginPage("/auth/loginForm")   // 로그인 페이지는 이 경로로 하겠다.
                .userInfoEndpoint()             // 로그인 성공 후 (토큰을 이용하여) 사용자 정보를 가져온다.
                .userService(principalOauth2UserService)
        ;
    }
}
