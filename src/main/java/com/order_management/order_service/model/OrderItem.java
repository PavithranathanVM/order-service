package com.order_management.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String productCategory;

    private BigDecimal price;

    private BigDecimal quantity;

    @ManyToOne
            (
                    cascade = CascadeType.ALL
            )
    @JoinColumn(name = "orderId")
    private PurchaseOrder purchaseOrder;

    @OneToMany
            (
                    cascade = CascadeType.ALL,
                    mappedBy = "orderItem"
            )
    private List<OrderLineItem> orderLineItem;
}
