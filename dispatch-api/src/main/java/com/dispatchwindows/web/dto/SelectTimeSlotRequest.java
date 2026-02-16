package com.dispatchwindows.web.dto;

import jakarta.validation.constraints.NotBlank;

public record SelectTimeSlotRequest(

        @NotBlank String orderId) {
}
