package com.order_management.order_service.DTOs;

import com.order_management.order_service.model.OrderLineItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {

    private String productCategory;
    private String productName;
    @NotNull(message = "At least one lineitem is required")
    private List<OrderLineItemDTO> lineItem;
    private BigDecimal price;

    private BigDecimal quantity;
}
