package com.dispatchwindows.web.error;

public class SlotLockedException extends RuntimeException {
  public SlotLockedException() { super("Time slot is temporarily locked by another customer"); }
}
