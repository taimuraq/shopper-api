package com.example.shopperapi.controller;

import com.example.shopperapi.tracking.DependencyTracker;
import com.example.shopperapi.tracking.DependencyTracker.DependencyRecord;
import com.example.shopperapi.tracking.DependencyTracker.ExternalCallKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class DebugController {

  @GetMapping("/debug/dependencies")
  public List<Map<String, Object>> getDependencies() {
    Map<ExternalCallKey, List<DependencyRecord>> rawMap = DependencyTracker.getDependencyMap();
    List<Map<String, Object>> formatted = new ArrayList<>();

    for (Map.Entry<ExternalCallKey, List<DependencyRecord>> entry : rawMap.entrySet()) {
      ExternalCallKey key = entry.getKey();
      List<DependencyRecord> records = entry.getValue();

      Map<String, Object> externalCall = new HashMap<>();
      externalCall.put("service", key.service);
      externalCall.put("method", key.method);
      externalCall.put("path", key.path);

      List<Map<String, Object>> originatingEndpoints = new ArrayList<>();
      for (DependencyRecord record : records) {
        Map<String, Object> ep = new HashMap<>();
        ep.put("api", record.api);
        ep.put("path", record.path);
        ep.put("internalTrace", record.internalTrace);
        originatingEndpoints.add(ep);
      }

      Map<String, Object> result = new HashMap<>();
      result.put("externalCall", externalCall);
      result.put("originatingEndpoints", originatingEndpoints);

      formatted.add(result);
    }

    return formatted;
  }
}
