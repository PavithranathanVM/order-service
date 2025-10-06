package com.order_management.order_service.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_management.order_service.DTOs.OrderItemDTO;
import com.order_management.order_service.DTOs.PurchaseOrderRequest;
import com.order_management.order_service.DTOs.PurchaseOrderResponse;
import com.order_management.order_service.Enums.OrderStatus;
import com.order_management.order_service.Mappers.OrderLineItemMapper;
import com.order_management.order_service.Mappers.PurchaseOrderMapper;
import com.order_management.order_service.model.OrderItem;
import com.order_management.order_service.model.OrderLineItem;
import com.order_management.order_service.model.OrderLineItemAttr;
import com.order_management.order_service.model.PurchaseOrder;
import com.order_management.order_service.repo.PurchaseOrderRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.common.kafka.common_kafka.KafkaDTOs.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private final PurchaseOrderRepo purchaseOrderRepository;
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private final PurchaseOrderMapper orderMapper;

    @Transactional
    public PurchaseOrderResponse placeOrder(PurchaseOrderRequest request) {

        PurchaseOrder purchaseOrder = orderMapper.toEntity(request);

        purchaseOrder.setCreatedAt(LocalDateTime.now());
        purchaseOrder.setUpdatedAt(LocalDateTime.now());
        purchaseOrder.setStatus(OrderStatus.PENDING);

//        BigDecimal totalAmount = BigDecimal.valueOf(0);

        BigDecimal totalAmount = purchaseOrder.getOrderItems().stream().peek(
                orderItem ->{
                         orderItem.setPurchaseOrder(purchaseOrder);

                         if(orderItem.getOrderLineItem() != null)
                         {
                             orderItem.getOrderLineItem().forEach(orderLineItem -> {
                                 orderLineItem.setOrderItem(orderItem);

                                 if(orderLineItem.getOrderLineItemAttrList() != null)
                                 {
                                     orderLineItem.getOrderLineItemAttrList().forEach(
                                             orderLineItemAttr -> {
                                                 orderLineItemAttr.setOrderLineItem(orderLineItem);
                                             }
                                     );
                                 }
                             });
                         }
                }
        )
                .map(OrderItem::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO,BigDecimal::add);



        //System.out.println(purchaseOrder.getOrderItems().toString());

//        if(purchaseOrder.getOrderItems() != null)
//        {
//            purchaseOrder.getOrderItems().forEach(orderItem ->
//            {
//                orderItem.setPurchaseOrder(purchaseOrder);
//                if(orderItem.getOrderLineItem() != null)
//                {
//                    orderItem.getOrderLineItem().forEach(orderLineItem -> {
//                        orderLineItem.setOrderItem(orderItem);
//
//                        if(orderLineItem.getOrderLineItemAttrList() != null)
//                        {
//                            orderLineItem.getOrderLineItemAttrList().forEach(
//                                    orderLineItemAttr -> {
//                                        orderLineItemAttr.setOrderLineItem(orderLineItem);
//                                    }
//                            );
//                        }
//                    });
//                }
//
//            });
//
//            for(OrderItem orderItem : purchaseOrder.getOrderItems())
//            {
//                orderItem.setPurchaseOrder(purchaseOrder);
//                totalAmount = totalAmount.add(orderItem.getTotalPrice());
//                if(orderItem.getOrderLineItem() != null)
//                {
//                    for(OrderLineItem orderLineItem : orderItem.getOrderLineItem())
//                    {
//                        orderLineItem.setOrderItem(orderItem);
//
//                        if (orderLineItem.getOrderLineItemAttrList() != null)
//                        {
//                            for (OrderLineItemAttr orderLineItemAttr : orderLineItem.getOrderLineItemAttrList())
//                            {
//                                orderLineItemAttr.setOrderLineItem(orderLineItem);
//                            }
//                        }
//                    }
//
//                }
//            }
//        }

        purchaseOrder.setTotalAmount(totalAmount);

        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

        try {
            savedOrder.setStatus(OrderStatus.CREATED);
            savedOrder = purchaseOrderRepository.save(savedOrder);

            OrderCreatedEvent event = OrderCreatedEvent.builder()
                    .orderId(savedOrder.getId())
                    .customerId(savedOrder.getCustomerId())
                    .createdAt(savedOrder.getCreatedAt())
                    .status(savedOrder.getStatus().name())
                    .orderItems(savedOrder.getOrderItems().stream()
                            .map(item -> OrderItemEvent.builder()
                                    .productId(item.getId())
                                    .productName(item.getProductName())
                                    .productCategory(item.getProductCategory())
                                    .quantity(item.getTotalQuantity())
                                    .price(item.getTotalPrice())
                                    .build())
                            .toList())
                    .build();

//            ObjectMapper mapper = new ObjectMapper();
//            String eventJson = mapper.writeValueAsString(event);

            kafkaTemplate.send("order-created",event);
        }
        catch (Exception e)
        {
            savedOrder.setStatus(OrderStatus.CANCELLED);
            throw e;
        }

        return orderMapper.toDTO(savedOrder);
    }

    public PurchaseOrderResponse getOrderById(UUID orderId) {
        PurchaseOrderResponse response = null;
        if(orderId != null)
        {
                PurchaseOrder order = purchaseOrderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));

                response= orderMapper.toDTO(order);
        }
        return response;
    }

    public List<PurchaseOrderResponse> getAllOrders(UUID customerId) {
        List<PurchaseOrder> order = List.of();

        if(customerId != null) {
            order = purchaseOrderRepository.findByCustomerId(customerId);

            if(order.isEmpty())
            {
                throw new EntityNotFoundException("No orders for customer " + customerId);
            }
        }
        return orderMapper.toDTOList(order);
    }

    public PurchaseOrderResponse updateOrderStatus(UUID orderId, String status) {
        return null;
    }

    public void cancelOrder(UUID orderId) {

    }
}
