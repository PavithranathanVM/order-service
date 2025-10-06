package com.order_management.order_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order_management.order_service.DTOs.PurchaseOrderRequest;
import com.order_management.order_service.DTOs.PurchaseOrderResponse;
import com.order_management.order_service.Service.PurchaseOrderService;
import com.order_management.order_service.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class OrderController {

    PurchaseOrderService purchaseOrderService;

    OrderController(PurchaseOrderService purchaseOrderService){
        this.purchaseOrderService = purchaseOrderService;

    }

    @PostMapping("/order")
    public ResponseEntity<PurchaseOrderResponse>placeOrder(@RequestBody PurchaseOrderRequest purchaseOrderRequest) {
       return new ResponseEntity<>(purchaseOrderService.placeOrder(purchaseOrderRequest), HttpStatus.CREATED);

    }

    @GetMapping("/order")
    public ResponseEntity<PurchaseOrderResponse>getOrder(@RequestParam(name = "orderId") UUID orderId)
    {

        PurchaseOrderResponse response = purchaseOrderService.getOrderById(orderId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all-order")
    public ResponseEntity<List<PurchaseOrderResponse>>getAllOrder(@RequestParam(name = "custId") UUID customerId)
    {
        List<PurchaseOrderResponse> response = purchaseOrderService.getAllOrders(customerId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
