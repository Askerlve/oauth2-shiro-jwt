package com.askerlve.ums.web.config.oauth2;

import com.askerlve.ums.web.oauth2.entrypoint.AppAuthenticationEntryPoint;
import com.askerlve.ums.web.oauth2.handler.AppLoginFailureHandler;
import com.askerlve.ums.web.oauth2.handler.AppLogoutSuccessHandler;
import com.askerlve.ums.web.oauth2.handler.AppOAuth2AccessDeniedHandler;
import com.askerlve.ums.web.oauth2.renderer.AppOAuth2ExceptionRenderer;
import com.askerlve.ums.web.oauth2.translator.AppWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Askerlve
 * @Description: ResourceServerConfig
 * @date 2018/4/28下午5:00
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler appLoginSuccessHandler;

    @Autowired
    private AppLogoutSuccessHandler appLogoutSuccessHandler;

    @Autowired
    private AppLoginFailureHandler appLoginFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/oauth/login").successHandler(this.appLoginSuccessHandler).failureHandler(this.appLoginFailureHandler)
                .and()
                .logout().logoutUrl("/oauth/logout").logoutSuccessHandler(this.appLogoutSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/login").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/oauth/auth/refresh").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                //shiro方式请求全部放过
                .antMatchers("/shiro/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();//允许跨域
    }


    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }


    @Bean
    public WebResponseExceptionTranslator exceptionTranslator() {
        return new AppWebResponseExceptionTranslator();
    }

    @Bean
    public OAuth2ExceptionRenderer oAuth2ExceptionRenderer() {
        return new AppOAuth2ExceptionRenderer();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final AppAuthenticationEntryPoint entryPoint = new AppAuthenticationEntryPoint();
        entryPoint.setExceptionTranslator(exceptionTranslator());
        entryPoint.setExceptionRenderer(oAuth2ExceptionRenderer());
        return entryPoint;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final AppOAuth2AccessDeniedHandler handler = new AppOAuth2AccessDeniedHandler();
        handler.setExceptionTranslator(exceptionTranslator());
        return handler;
    }
}
