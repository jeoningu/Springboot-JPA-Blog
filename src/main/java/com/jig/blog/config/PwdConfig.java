package com.jig.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PwdConfig {

    /**
     * 패스워드를 암호화 해주는 빈
     * @return
     */
    @Bean
    public BCryptPasswordEncoder encoderPWD() {
        return new BCryptPasswordEncoder();
    }
}
