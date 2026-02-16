package com.dispatchwindows.web.controller;

import com.dispatchwindows.service.TimeSlotLockService;
import com.dispatchwindows.web.dto.SelectTimeSlotRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotLockService lockService;

    public TimeSlotController(TimeSlotLockService lockService) {
        this.lockService = lockService;
    }

    /**
     *
     * POST /api/timeslots/{id}/select
     */
    @PostMapping("/{id}/select")
    @ResponseStatus(HttpStatus.OK)
    public void select(
            @PathVariable Long id,
            @RequestBody @Valid SelectTimeSlotRequest request) {
        lockService.lock(id, request.orderId());
    }
}
