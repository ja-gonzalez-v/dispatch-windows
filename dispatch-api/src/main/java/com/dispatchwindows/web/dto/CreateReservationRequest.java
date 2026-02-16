package com.dispatchwindows.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(

        @NotBlank String orderId,

        @NotBlank String addressLine,

        @NotBlank String postalCode,

        @NotNull Long timeSlotId) {

}
