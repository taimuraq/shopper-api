package com.example.shopperapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "external-services")
@Validated
public class ExternalServicesConfig {
  private Map<String, ServiceConfig> services;

  @Getter
  @Setter
  public static class ServiceConfig {
    private String baseUrl;
  }

  public String getServiceUrl(String serviceName) {
    if (services != null && services.containsKey(serviceName)) {
      return services.get(serviceName).getBaseUrl();
    }
    return null;
  }

}

