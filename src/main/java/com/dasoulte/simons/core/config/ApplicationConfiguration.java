package com.dasoulte.simons.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by NHNEnt on 2016-11-08.
 */
@Configuration
@PropertySource("classpath:properties/value.properties")
public class ApplicationConfiguration {

    @Value("${payco.billing.api.host}")
    private String paycoApiHost;

    public String getPaycoApiHost() {
        return paycoApiHost;
    }
}
