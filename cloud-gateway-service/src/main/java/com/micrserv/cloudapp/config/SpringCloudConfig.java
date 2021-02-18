package com.micrserv.cloudapp.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/consume/**")
                		.filters(f -> f.hystrix(h -> h.setName("Hystrix")
                				.setFallbackUri("forward:/fallback/message")))
                        .uri("lb://SpringBootAppConsumer")
                        .id("SpringBootAppConsumerModule"))

              /*  .route(r -> r.path("/consumer/**")
                		.filters(f -> f.hystrix(h -> h.setName("Hystrix")
                				.setFallbackUri("forward:/fallback/message")))
                        .uri("lb://SECOND-SERVICE")
                        .id("consumerModule"))*/
                .build();
    }

}
