package com.dispatchwindows.service;

import com.dispatchwindows.web.error.SlotLockedException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TimeSlotLockServiceImpl implements TimeSlotLockService {

    private static final Duration LOCK_TTL = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    public TimeSlotLockServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void lock(Long timeSlotId, String orderId) {
        String key = key(timeSlotId);

        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(key, orderId, LOCK_TTL);

        if (Boolean.FALSE.equals(acquired)) {
            throw new SlotLockedException();
        }
    }

    @Override
    public void validateLock(Long timeSlotId, String orderId) {
        String key = key(timeSlotId);
        String currentOwner = redisTemplate.opsForValue().get(key);

        if (currentOwner == null || !currentOwner.equals(orderId)) {
            throw new SlotLockedException();
        }
    }

    @Override
    public void release(Long timeSlotId, String orderId) {
        String key = key(timeSlotId);
        String currentOwner = redisTemplate.opsForValue().get(key);

        if (orderId.equals(currentOwner)) {
            redisTemplate.delete(key);
        }
    }

    private String key(Long timeSlotId) {
        return "timeslot:" + timeSlotId + ":lock";
    }
}
