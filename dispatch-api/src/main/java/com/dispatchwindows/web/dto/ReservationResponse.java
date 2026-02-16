package com.dispatchwindows.web.dto;

public record ReservationResponse(
        Long id,
        String orderId,
        String status) {
}
