package com.lyr.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 禁用 Spring Security 的鉴权功能
        exchange.getRequest().mutate().headers(h -> h.remove(HttpHeaders.AUTHORIZATION)); // 移除 Authorization 头部
        return chain.filter(exchange);
    }


}