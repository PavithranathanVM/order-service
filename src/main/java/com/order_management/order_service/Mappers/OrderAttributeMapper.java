package com.order_management.order_service.Mappers;

import com.order_management.order_service.DTOs.OrderLineItemAttributeDTO;
import com.order_management.order_service.DTOs.OrderLineItemAttributeResponseDTO;
import com.order_management.order_service.DTOs.OrderLineItemResponseDTO;
import com.order_management.order_service.model.OrderLineItem;
import com.order_management.order_service.model.OrderLineItemAttr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderAttributeMapper
{
    @Mapping(target = "key",source = "attributeKey")
    @Mapping(target = "value",source = "attributeValue")
    OrderLineItemAttributeResponseDTO toDto(OrderLineItemAttr orderAttribute);

    @Mapping(target = "attributeKey",source = "attributeKey")
    @Mapping(target = "attributeValue",source = "attributeValue")
    OrderLineItemAttr toEntity(OrderLineItemAttributeDTO orderAttributeRequest);

    List<OrderLineItemResponseDTO> toDtoList(List<OrderLineItemAttr> orderAttributes);

    List<OrderLineItemAttr> toEntityList(List<OrderLineItemAttributeDTO> orderAttributeRequests);

}

