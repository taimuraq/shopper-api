package com.example.shopperapi.service;

import com.example.shopperapi.config.ExternalServicesConfig;
import com.example.shopperapi.model.User;
import com.example.shopperapi.tracking.DependencyTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestClientService {

  private final RestTemplate restTemplate;
  private final ExternalServicesConfig externalServicesConfig;

  private final DependencyTracker dependencyTracker;

  public RestClientService(RestTemplate restTemplate, DependencyTracker dependencyTracker, ExternalServicesConfig externalServicesConfig) {
    this.dependencyTracker = dependencyTracker;
    this.restTemplate = restTemplate;
    this.externalServicesConfig = externalServicesConfig;
  }

  public User getUser(String userId) {
//    String originApi = RequestContextTracker.getCurrentApi();
    String target = externalServicesConfig.getServices().get("userdataapi").getBaseUrl() + "/users/" + userId;
//    dependencyTracker.track("UserdataAPI", );
//    log.info("External Call → GET {} | Called by → {}", target, originApi);

    return restTemplate.getForObject(target, User.class);
  }
}


