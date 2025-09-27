package com.order_management.order_service.DTOs.KafkaDTOs;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemEvent {

    private UUID productId;
    private String productName;
    private String productCategory;
    private BigDecimal quantity;
    private BigDecimal price;

}