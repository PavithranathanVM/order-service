package com.order_management.order_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemAttributeDTO {

    private String attributeKey;
    private String attributeValue;
}
