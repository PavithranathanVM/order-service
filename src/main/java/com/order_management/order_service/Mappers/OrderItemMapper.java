package com.order_management.order_service.Mappers;

import com.order_management.order_service.DTOs.OrderItemDTO;
import com.order_management.order_service.DTOs.OrderItemResponseDTO;
import com.order_management.order_service.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {OrderLineItemMapper.class})
public interface OrderItemMapper {

    @Mapping(target = "lineItems", source = "orderLineItem")
    OrderItemResponseDTO toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderLineItem", source = "lineItem")
    @Mapping(target = "totalPrice", source = "price")
    @Mapping(target = "totalQuantity", source = "quantity")
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    List<OrderItemResponseDTO> toDtoList(List<OrderItem> orderItems);

    List<OrderItem> toEntityList(List<OrderItemDTO> orderItemRequests);

}
