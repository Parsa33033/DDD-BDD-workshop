package org.example.framework;

import java.util.HashMap;
import java.util.Map;

public class DomainConfig {

  public static Map<String, String> LIST_OF_VALUES = new HashMap<>();

  private static DomainConfig instance;

  private DomainConfig() {};

  public static DomainConfig instance() {
    if (instance != null) {
      return instance;
    }
    instance = new DomainConfig();
    return instance;
  }

  public Map<String, String> getLOV() {
    return LIST_OF_VALUES;
  }
}
