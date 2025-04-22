package com.example.shopperapi.tracking;

import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class DependencyTracker {

  public static class DependencyRecord {
    public String api;
    public String path;
    public List<String> internalTrace;

    public DependencyRecord(String api, String path, List<String> trace) {
      this.api = api;
      this.path = path;
      this.internalTrace = trace;
    }
  }

  public static class ExternalCallKey {
    public String service;
    public String method;
    public String path;

    public ExternalCallKey(String service, String method, String path) {
      this.service = service;
      this.method = method;
      this.path = path;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ExternalCallKey)) return false;
      ExternalCallKey key = (ExternalCallKey) o;
      return Objects.equals(service, key.service)
          && Objects.equals(method, key.method)
          && Objects.equals(path, key.path);
    }

    @Override
    public int hashCode() {
      return Objects.hash(service, method, path);
    }
  }

  private static final Map<ExternalCallKey, List<DependencyRecord>> dependencyMap = new HashMap<>();

  public static void recordExternalCall(String service, String method, String path, String api, List<String> trace) {
    ExternalCallKey key = new ExternalCallKey(service, method, path);

    if (api == null) {
      api = RequestContextTracker.getCurrentPublicApi();
    }

    if (trace == null) {
      trace = RequestContextTracker.getCurrentInternalTrace();
    }

    if (api == null) {
      api = "UNKNOWN";
    }

    if (trace == null) {
      trace = new ArrayList<>();
    }

    DependencyRecord record = new DependencyRecord(api, trace.isEmpty() ? "UNKNOWN" : trace.get(0), trace);
    dependencyMap.computeIfAbsent(key, k -> new ArrayList<>()).add(record);
  }

  public static void printDependencyMap() {
    System.out.println("=== Dependency Map ===");
    for (Map.Entry<ExternalCallKey, List<DependencyRecord>> entry : dependencyMap.entrySet()) {
      ExternalCallKey key = entry.getKey();
      System.out.printf("\nExternal Call: [%s] %s (%s)\n", key.method, key.path, key.service);

      for (DependencyRecord record : entry.getValue()) {
        System.out.printf("→ Origin API: %s\n", record.api);
        System.out.printf("  Origin Path: %s\n", record.path);
        System.out.printf("  Internal Trace: %s\n", String.join(" → ", record.internalTrace));
      }
    }
  }

  public static Map<ExternalCallKey, List<DependencyRecord>> getDependencyMap() {
    return dependencyMap;
  }
}
