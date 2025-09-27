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
}
