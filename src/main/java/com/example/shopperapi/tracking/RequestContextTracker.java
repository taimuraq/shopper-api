package com.example.shopperapi.tracking;

import java.util.ArrayList;
import java.util.List;

public class RequestContextTracker {

  private static final ThreadLocal<String> currentPublicApi = new ThreadLocal<>();
  private static final ThreadLocal<List<String>> internalTrace = ThreadLocal.withInitial(ArrayList::new);

  public static void setCurrentPublicApi(String api) {
    currentPublicApi.set(api);
  }

  public static String getCurrentPublicApi() {
    return currentPublicApi.get();
  }

  public static void clear() {
    currentPublicApi.remove();
    internalTrace.remove();
  }

  public static void recordMethod(String methodName) {
    internalTrace.get().add(methodName);
  }

  public static List<String> getCurrentInternalTrace() {
    return new ArrayList<>(internalTrace.get());
  }
}

