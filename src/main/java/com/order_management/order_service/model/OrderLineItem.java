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
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID productId;

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    @ManyToOne
            (
                    cascade = CascadeType.ALL
            )
    @JoinColumn(
            name = "orderItemId"
    )
    private OrderItem orderItem;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "orderLineItem"
    )
    private List<OrderLineItemAttr> orderLineItemAttrList;
}
