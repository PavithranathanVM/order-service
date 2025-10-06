package com.order_management.order_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemResponseDTO {

    private UUID id;
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private List<OrderLineItemAttributeResponseDTO> attributes;
}
