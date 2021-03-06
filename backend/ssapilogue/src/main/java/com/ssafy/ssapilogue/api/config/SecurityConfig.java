package com.ssafy.ssapilogue.api.config;

import com.ssafy.ssapilogue.api.service.CustomUserDetailService;
import com.ssafy.ssapilogue.api.service.JwtTokenProvider;
import com.ssafy.ssapilogue.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-resources/**", "/swagger-ui", "/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().httpBasic().disable();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll();

        // jwt
        http.headers().frameOptions().disable();
        http
                .httpBasic().disable() // rest api ?????? ???????????? ?????? ?????? ??????
                .csrf().disable() // csrf ?????? ?????? disable ??????
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()// ????????? ?????? ???????????? ??????
                .antMatchers("/user/login").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll() // ?????? ????????? ????????? ????????? ?????? ??????
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, authenticationManager(), userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtTokenProvider))
                .authorizeRequests(); // ?????? ?????? ?????? ?????? ?????????
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
