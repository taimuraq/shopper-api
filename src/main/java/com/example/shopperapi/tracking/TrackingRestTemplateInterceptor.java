package com.example.shopperapi.tracking;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class TrackingRestTemplateInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request,
      byte[] body,
      ClientHttpRequestExecution execution) throws IOException {

    ClientHttpResponse response = execution.execute(request, body);

    String method = request.getMethod().name();
    URI uri = request.getURI();
    String path = normalizePath(uri.getPath());
    String service = extractServiceName(uri);

    // Optional placeholder for fields - can be enhanced with response introspection
    List<String> fieldsUsed = List.of("id", "email");

    DependencyTracker.recordExternalCall(service, method, path, null, fieldsUsed);

    return response;
  }

  private String extractServiceName(URI uri) {
    String host = uri.getHost() + ":" + uri.getPort();
    switch (host) {
      case "localhost:8080": return "userdataapi";
      case "localhost:8090": return "inventoryapi";
      default: return "unknown";
    }
  }

  private String normalizePath(String path) {
    return path.replaceAll("/\\d+", "/{id}");
  }
}
