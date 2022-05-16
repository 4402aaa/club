package org.zerock.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//WebSecurityConfigurerAdapter : 시큐리티 관련 기능을 쉽게 설정하기 위해서, 주로 override를 통해서 여러 설정을 저정하게 된다
//모든 시큐리티 관련 설정이 추가되는 부분 핵심적인 역할을 하는 클래스
@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*AuthenticationManager의 설정을 쉽게 처리할 수 있게 도와주는 configure를 overide해서 처리 */
    /*AuthenticationManagerBuilder : 말 그대로 코드를 통해서 직접 인증 매니저를 설정할 때 사용 */
    /*inMemoryAuthentication() : 내부에는 최소한의 코드로 로그인을 확인할 수 있는 메서드를 사용해서 리턴 객체를 이용해서 한명의 사용자를 생성*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1") //사용자 계정은 user1
                .password("$2a$10$4cyUV0wSIowbr8Py7iV1GOuOrN0ovV8ZWoElQ8/VLWeMNmW3RJxU6") //1111 패스워드 인코딩
                .roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");
        http.formLogin(); //인가/인증에 문제시 로그인 화면
        http.csrf().disable();//CSRF 토큰을 발행하지 않도록 설정하기 위해서, 외부에서 REST 방식으로 이용할 수 있는 보안 설정을 다루기 위해서 발행하지 않는 방식으로 설정
        http.logout();//CSRF 토큰을 이용하는 경우에는 반드시 POST방식으로만 로그아웃을 처리
    }
}
