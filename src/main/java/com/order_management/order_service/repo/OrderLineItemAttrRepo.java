package com.order_management.order_service.repo;

import com.order_management.order_service.model.OrderLineItemAttr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderLineItemAttrRepo extends JpaRepository<OrderLineItemAttr, UUID> {
}
