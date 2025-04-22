package com.example.shopperapi.controller;

import com.example.shopperapi.util.DependencyTracker;
import com.example.shopperapi.util.DependencyTracker.DependencyCall;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugController {

  @GetMapping("/dependencies")
  public Set<DependencyCall> getTrackedCalls() {
    return DependencyTracker.getCalls();
  }
}

