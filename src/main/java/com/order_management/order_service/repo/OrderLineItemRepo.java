package com.order_management.order_service.repo;

import com.order_management.order_service.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderLineItemRepo extends JpaRepository<OrderLineItem, UUID> {
}
