package com.dispatchwindows.web.error;

public class SlotSoldOutException extends RuntimeException {
  public SlotSoldOutException() { super("Time slot sold out"); }
}
