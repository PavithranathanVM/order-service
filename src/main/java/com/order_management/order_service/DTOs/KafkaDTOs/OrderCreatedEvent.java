package com.order_management.order_service.DTOs.KafkaDTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private UUID orderId;
    private UUID customerId;
    private String status;
    List<OrderItemEvent> orderItems;
    LocalDateTime createdAt;

}
