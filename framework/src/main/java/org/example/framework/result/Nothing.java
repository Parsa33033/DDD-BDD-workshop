package org.example.framework.result;

import org.example.framework.dto.DataTransferObject;

public final class Nothing implements DataTransferObject {

  private static final Nothing instance = new Nothing();

  private Nothing() {}

  public static Nothing get() {return instance;}
}
