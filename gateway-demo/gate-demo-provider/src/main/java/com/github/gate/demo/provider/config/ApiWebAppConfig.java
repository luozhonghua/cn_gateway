package com.github.gate.demo.provider.config;

import com.github.gate.agent.rest.ApiInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * luozhonghua on 2017/7/6.
 */
@Configuration
public class ApiWebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${gate.client.authHost}")
    private String authHost;
    @Value("${gate.client.authHeader}")
    private String authHeader;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiInterceptor(authHost,authHeader)).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
