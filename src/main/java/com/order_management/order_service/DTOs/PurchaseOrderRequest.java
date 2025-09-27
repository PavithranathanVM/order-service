package com.order_management.order_service.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderRequest {

    @NotNull(message = "CustomerId is required")
    private UUID customerId;

    @NotNull(message = "At least one item in the order is expected")
    @Size(min = 1,message = "One item is needed at the least")
    private List<OrderItemDTO> items;

}
