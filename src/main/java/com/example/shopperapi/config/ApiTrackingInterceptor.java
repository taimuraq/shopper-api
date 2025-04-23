package com.example.shopperapi.config;

import com.example.shopperapi.tracking.DependencyTracker;
import com.example.shopperapi.tracking.RequestContextTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ApiTrackingInterceptor implements HandlerInterceptor {

  private final DependencyTracker dependencyTracker;

  public ApiTrackingInterceptor(DependencyTracker dependencyTracker) {
    this.dependencyTracker = dependencyTracker;
  }
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//    String apiPath = request.getMethod() + " " + request.getRequestURI();
    String method = request.getMethod();
    String uri = request.getRequestURI();
    RequestContextTracker.setCurrentPublicApi(method + " " + uri);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//    RequestContextTracker.clear();
  }
}

