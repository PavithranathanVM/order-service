package com.order_management.order_service.repo;

import com.order_management.order_service.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, UUID> {

    List<PurchaseOrder> findByCustomerId(UUID customerId);
}
