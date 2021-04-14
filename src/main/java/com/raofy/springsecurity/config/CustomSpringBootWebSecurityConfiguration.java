package com.raofy.springsecurity.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author raofy
 * @date 2021-04-14 11:00
 * @desc 自定义spring boot web安全配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CustomSpringBootWebSecurityConfiguration {

    /**
     * 重写WebSecurityConfigurerAdapter里面configure方法实现自定义功能
     */
    @Configuration(proxyBeanMethods = false)
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    static class DefaultConfigurerAdapter extends WebSecurityConfigurerAdapter {

        /**
         * 认证管理器配置（UserDetail和passwordEncoder都管理）
         *
         * @param auth
         * @throws Exception
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            super.configure(auth);
        }

        /**
         * 核心过滤器配置（基于Servlet Filter 用来配置 springSecurityFilterChain 。而 springSecurityFilterChain 又
         * 被委托给了 Spring Security 核心过滤器 Bean DelegatingFilterProxy 。 相关逻辑你可以在
         * WebSecurityConfiguration 中找到。）
         *
         * @param web
         * @throws Exception
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
        }

        /**
         * 安全过滤器链配置（重点关注，用于构建一个安全过滤器链 SecurityFilterChain 。 SecurityFilterChain 最终
         * 被注入核心过滤器 。常用来灵活配置自定义访问策略）
         *
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .cors()
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/process")
                    .successForwardUrl("/login/success")
                    .failureForwardUrl("/login/failure");
        }
    }

}
