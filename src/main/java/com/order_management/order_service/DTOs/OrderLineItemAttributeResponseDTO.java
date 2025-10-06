package com.order_management.order_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemAttributeResponseDTO {
    private UUID id;
    private String key;
    private String value;
}
