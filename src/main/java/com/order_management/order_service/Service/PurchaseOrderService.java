package com.order_management.order_service.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order_management.order_service.DTOs.PurchaseOrderRequest;
import com.order_management.order_service.DTOs.PurchaseOrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PurchaseOrderService {
    PurchaseOrderResponse placeOrder(PurchaseOrderRequest request);
    PurchaseOrderResponse getOrderById(UUID orderId);
    List<PurchaseOrderResponse> getAllOrders();
    PurchaseOrderResponse updateOrderStatus(UUID orderId, String status);
    void cancelOrder(UUID orderId);
}
