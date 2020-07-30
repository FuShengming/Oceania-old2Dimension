package com.old2dimension.OCEANIA.config;

import com.old2dimension.OCEANIA.security.JWTAuthenticationFilter;
import com.old2dimension.OCEANIA.security.JWTAuthorizationFilter;
import com.old2dimension.OCEANIA.security.JwtAccessDeniedHandler;
import com.old2dimension.OCEANIA.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userBLImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    // 定义授权规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll()
                .and().logout().logoutUrl("/logout").deleteCookies("token")
                .and().rememberMe().alwaysRemember(true);

        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 管理员访问控制
//                .antMatchers("/statistics/++")
//                .hasRole("ADMIN")
                // 用户访问控制
//                .antMatchers("/code/**", "/graph/++", "/label/**",
//                        "/upload/**", "/workSpace/++")
//                .hasRole("USER")
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login").permitAll().failureUrl("/error")
//                .and().logout().logoutSuccessUrl("/login").permitAll()
//                .and().rememberMe().alwaysRemember(true)
//                .and().authorizeRequests()
////                .antMatchers("/").hasRole("USER")
////                .antMatchers("/manager/**").hasRole("ROOT")
////                .antMatchers("/user/**").hasRole("USER")
//                .and().csrf().disable();
//    }

}
