package com.dispatchwindows.service;

public interface TimeSlotLockService {

    void lock(Long timeSlotId, String orderId);

    void validateLock(Long timeSlotId, String orderId);

    void release(Long timeSlotId, String orderId);
}
