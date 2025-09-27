package com.order_management.order_service.DTOs.KafkaDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEvent {

    private UUID orderId;
    private UUID customerId;
    private String reason;
    private LocalDateTime cancelledAt;

}
