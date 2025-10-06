package com.order_management.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String productCategory;

    private BigDecimal totalPrice;

    private String productName;

    private BigDecimal totalQuantity;

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
