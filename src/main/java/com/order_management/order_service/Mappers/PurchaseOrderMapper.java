package com.order_management.order_service.Mappers;

import com.order_management.order_service.DTOs.PurchaseOrderRequest;
import com.order_management.order_service.DTOs.PurchaseOrderResponse;
import com.order_management.order_service.model.OrderItem;
import com.order_management.order_service.model.OrderLineItem;
import com.order_management.order_service.model.OrderLineItemAttr;
import com.order_management.order_service.model.PurchaseOrder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class})
public interface PurchaseOrderMapper {

    @Mapping(target = "items",source = "orderItems")
    PurchaseOrderResponse toDTO (PurchaseOrder purchaseOrder);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", source = "items")
    PurchaseOrder toEntity (PurchaseOrderRequest purchaseOrderRequest);

    List<PurchaseOrderResponse> toDTOList (List<PurchaseOrder> purchaseOrders);

    List<PurchaseOrder> toEntityList(List<PurchaseOrderRequest> purchaseOrderRequests);

//    @AfterMapping
//    default void setParents(PurchaseOrderRequest dto, @MappingTarget PurchaseOrder order) {
//        if (order.getOrderItems() != null) {
//            for (OrderItem item : order.getOrderItems())
//            {
//                item.setPurchaseOrder(order);
//
//                if (item.getOrderLineItem() != null) {
//                    for (OrderLineItem lineItem : item.getOrderLineItem()) {
//                        lineItem.setOrderItem(item);
//
//                        if (lineItem.getOrderLineItemAttrList() != null) {
//                            for (OrderLineItemAttr attr : lineItem.getOrderLineItemAttrList()) {
//                                attr.setOrderLineItem(lineItem);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

}
