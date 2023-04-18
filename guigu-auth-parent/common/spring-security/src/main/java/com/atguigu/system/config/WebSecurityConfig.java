package com.atguigu.system.config;

import com.atguigu.system.custom.CustomMd5Password;
import com.atguigu.system.filter.TokenAuthenticationFilter;
import com.atguigu.system.filter.TokenLoginFilter;
import com.atguigu.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RedisTemplate redisTemplate;

    private final UserDetailsService userDetailsService;


    private final CustomMd5Password customMd5Password;

    private final SysLoginLogService sysLoginLogService;
    @Autowired
    public WebSecurityConfig(RedisTemplate redisTemplate,
                             UserDetailsService userDetailsService,
                             CustomMd5Password customMd5Password,
                             SysLoginLogService sysLoginLogService) {
        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
        this.customMd5Password = customMd5Password;
        this.sysLoginLogService = sysLoginLogService;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // determine what is allowed
        http
                //Cross-Site Request Forgery
                //csrf is a way of attacking by send malicious requests
                .csrf().disable()
                // 开启跨域以便前端调用接口
                .cors().and()
                .authorizeRequests()
                // To specify certain APIs that do not require authentication
                .antMatchers("/admin/system/index/login").permitAll()
                // all other Apis need to be authenticated
                .anyRequest().authenticated()
                .and()
                //use token to do authentication
                .addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate, sysLoginLogService));

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // To specify a UserDetailService and an encryption method.
        auth.userDetailsService(userDetailsService).passwordEncoder(customMd5Password);
    }

    /**
     * allow swagger to access
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }

}