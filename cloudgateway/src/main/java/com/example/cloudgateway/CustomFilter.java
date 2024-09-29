package com.example.cloudgateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("First pre filter" + exchange.getRequest());
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()

                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            //Custom Post Filter.Suppose we can call error response handler based on error code.
            return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> {
                System.out.println("First post filter");
            }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}
