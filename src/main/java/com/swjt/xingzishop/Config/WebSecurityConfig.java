package com.swjt.xingzishop.Config;
import com.swjt.xingzishop.sercurity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //未认证处理器
    @Autowired
    private JSONAuthenticationPoint jsonAuthenticationPoint;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> MyWebAuthenticationDetailsSource;

    //未授权处理器
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    //登录错误处理器
    @Autowired
    private AuthFailHandler authFailHandler;

    //用户注销处理器
    @Autowired
    private MyLogoutSuccessHandler logoutSuccessHandler;

    //用户登录成功处理器
    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    //未认证处理器
    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/user/api/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/admin/api/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jsonAuthenticationPoint)
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .authenticationDetailsSource(MyWebAuthenticationDetailsSource)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailHandler)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();

    }

    private CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String encode = bCryptPasswordEncoder.encode("12345678");
        System.out.println(encode);
    }
}
