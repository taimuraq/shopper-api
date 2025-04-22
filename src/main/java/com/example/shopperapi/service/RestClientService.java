package com.example.shopperapi.service;

import com.example.shopperapi.config.ExternalServicesConfig;
import com.example.shopperapi.util.DependencyTracker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientService {

  private final ExternalServicesConfig config;
  private final RestTemplate restTemplate;

  public RestClientService(@Qualifier("externalServicesConfig")ExternalServicesConfig config) {
    this.config = config;
    this.restTemplate = new RestTemplate();
  }

  public <T> T get(String serviceName, String endpoint, Class<T> responseType) {
    String baseUrl = config.getServiceUrl(serviceName);
    String url = baseUrl + endpoint;

    // Track the dependency
    DependencyTracker.track(serviceName, "GET", endpoint);

    return restTemplate.getForObject(url, responseType);
  }

  // You can add post/put/etc. later here
}

