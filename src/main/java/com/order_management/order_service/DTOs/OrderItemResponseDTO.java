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
public class OrderItemResponseDTO {

    private UUID id;
    private String productCategory;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private List<OrderLineItemResponseDTO> lineItems;
}
