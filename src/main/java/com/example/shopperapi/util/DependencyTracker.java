package com.example.shopperapi.util;

import java.util.HashSet;
import java.util.Set;

public class DependencyTracker {

  private static final Set<DependencyCall> calls = new HashSet<>();

  public static void track(String service, String method, String endpoint) {
    calls.add(new DependencyCall(service, method, endpoint));
  }

  public static Set<DependencyCall> getCalls() {
    return calls;
  }

  public record DependencyCall(String service, String method, String endpoint) {}
}

