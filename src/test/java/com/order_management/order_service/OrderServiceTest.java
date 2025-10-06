package com.order_management.order_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order_management.order_service.DTOs.KafkaDTOs.OrderCreatedEvent;
import com.order_management.order_service.DTOs.OrderItemDTO;
import com.order_management.order_service.DTOs.OrderLineItemAttributeDTO;
import com.order_management.order_service.DTOs.OrderLineItemDTO;
import com.order_management.order_service.DTOs.PurchaseOrderRequest;
import com.order_management.order_service.Enums.OrderStatus;
import com.order_management.order_service.Mappers.PurchaseOrderMapper;
import com.order_management.order_service.Service.PurchaseOrderService;
import com.order_management.order_service.Service.PurchaseOrderServiceImpl;
import com.order_management.order_service.model.OrderItem;
import com.order_management.order_service.model.OrderLineItem;
import com.order_management.order_service.model.OrderLineItemAttr;
import com.order_management.order_service.model.PurchaseOrder;
import com.order_management.order_service.repo.PurchaseOrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private PurchaseOrderRepo purchaseOrderRepo;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private PurchaseOrderMapper mapper;

    private PurchaseOrderService orderService;

    @BeforeEach
    void setup() {
        purchaseOrderRepo = mock(PurchaseOrderRepo.class);
        kafkaTemplate = mock(KafkaTemplate.class);
        mapper = mock(PurchaseOrderMapper.class);

        orderService = new PurchaseOrderServiceImpl(purchaseOrderRepo, kafkaTemplate, mapper);
    }

    @Test
    void placeOrder_shouldSaveOrderAndPublishEvent() throws JsonProcessingException {
        // given
        UUID customerId = UUID.randomUUID();


        OrderItemDTO itemDto = new OrderItemDTO(
                "DummyProduct",
                "Test ProductName",
                List.of(OrderLineItemDTO.builder().price(BigDecimal.valueOf(21))
                        .productId(UUID.randomUUID())
                        .quantity(12)
                        .attributes(List.of(OrderLineItemAttributeDTO.builder()
                                        .attributeKey("1")
                                .attributeValue("21").build())).build()),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(2)

        );

        PurchaseOrderRequest request = new PurchaseOrderRequest(customerId, List.of(itemDto));

        OrderItem orderItem = OrderItem.builder().id(UUID.randomUUID())
                .productCategory("EntityProduct")
                .productName("ProdName1")
                .totalPrice(BigDecimal.valueOf(12))
                .totalQuantity(BigDecimal.valueOf(21))
                .orderLineItem(
                        List.of( OrderLineItem.builder()
                                .quantity(2)
                                .productName("prod")
                                .productId(UUID.randomUUID())
                                .price(BigDecimal.valueOf(2))
                                .orderItem(null)
                                .orderLineItemAttrList(
                                        List.of(OrderLineItemAttr.builder()
                                        .attributeKey("key1")
                                        .attributeValue("value1")
                                        .orderLineItem(null).build())).build()
                        )
                       ).purchaseOrder(null).build();

        PurchaseOrder orderEntity = new PurchaseOrder();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setCustomerId(customerId);
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setOrderItems(List.of(orderItem));

        // stub mapper and repo
        when(mapper.toEntity(request)).thenReturn(orderEntity);
        when(purchaseOrderRepo.save(orderEntity)).thenReturn(orderEntity);

        // when
        orderService.placeOrder(request);

        // then → verify save
        verify(purchaseOrderRepo,times(2)).save(orderEntity);

        // then → verify Kafka publish
        ArgumentCaptor<OrderCreatedEvent> eventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(kafkaTemplate).send(eq("order-created"), eventCaptor.capture());

        OrderCreatedEvent publishedEvent = eventCaptor.getValue();
        assertThat(publishedEvent.getCustomerId()).isEqualTo(customerId);
        assertThat(publishedEvent.getStatus()).isEqualTo(OrderStatus.CREATED.name());
    }
}
