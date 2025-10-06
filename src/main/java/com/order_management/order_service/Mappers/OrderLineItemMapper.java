package com.order_management.order_service.Mappers;

import com.order_management.order_service.DTOs.OrderLineItemDTO;
import com.order_management.order_service.DTOs.OrderLineItemResponseDTO;
import com.order_management.order_service.model.OrderLineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {OrderAttributeMapper.class})
public interface OrderLineItemMapper {


    @Mapping(target = "attributes", source = "orderLineItemAttrList")
    OrderLineItemResponseDTO toDto(OrderLineItem orderLineItem);

    @Mapping(target = "orderLineItemAttrList", source = "attributes")
    OrderLineItem toEntity(OrderLineItemDTO orderLineItemRequest);

    List<OrderLineItemResponseDTO> toDtoList(List<OrderLineItem> orderLineItems);

    List<OrderLineItem> toEntityList(List<OrderLineItemDTO> orderLineItemRequests);

}
