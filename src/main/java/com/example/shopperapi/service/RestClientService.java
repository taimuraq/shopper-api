package com.example.shopperapi.service;

import com.example.shopperapi.config.ExternalServicesConfig;
import com.example.shopperapi.model.CompanySettings;
import com.example.shopperapi.model.User;
import com.example.shopperapi.tracking.DependencyTracker;
import com.example.shopperapi.tracking.RequestContextTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestClientService {

  private final RestTemplate restTemplate;
  private final ExternalServicesConfig externalServicesConfig;


  public RestClientService(RestTemplate restTemplate, DependencyTracker dependencyTracker, ExternalServicesConfig externalServicesConfig) {
    this.restTemplate = restTemplate;
    this.externalServicesConfig = externalServicesConfig;
  }

  public User getUser(String userId) {
    RequestContextTracker.recordMethod(getCurrentMethod());
    String target = externalServicesConfig.getServices().get("userdataapi").getBaseUrl() + "/users/" + userId;
    return restTemplate.getForObject(target, User.class);
  }

  public CompanySettings getCompanySettings(String unitId) {
    RequestContextTracker.recordMethod(getCurrentMethod());
    String target = externalServicesConfig.getServices().get("userdataapi").getBaseUrl() + "/companysettings/unit-id/" + unitId;
    return restTemplate.getForObject(target, CompanySettings.class);
  }


  private String getCurrentMethod() {
    return this.getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
  }

}


