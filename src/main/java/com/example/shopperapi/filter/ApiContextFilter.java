package com.example.shopperapi.filter;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import com.example.shopperapi.tracking.RequestContextTracker;

import java.io.IOException;

@Component
@WebFilter("/*")
public class ApiContextFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      if (request instanceof HttpServletRequest) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String path = httpRequest.getRequestURI();

        // Combine method and path as API identifier
        String api = method + " " + path;
        RequestContextTracker.setCurrentPublicApi(api);
      }

      chain.doFilter(request, response); // proceed with the request
    } finally {
      // Clear context only after request is fully processed
      RequestContextTracker.clear();
    }
  }
}

