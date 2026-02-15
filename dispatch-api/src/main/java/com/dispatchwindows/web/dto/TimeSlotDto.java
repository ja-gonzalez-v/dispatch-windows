package com.dispatchwindows.web.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record TimeSlotDto(Long id,LocalTime startTime,LocalTime endTime,BigDecimal price,int remainingCapacity){}
