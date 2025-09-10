package com.order_management.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineItemAttr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String attributeKey;
    private String attributeValue;

    @ManyToOne
            (
                    cascade = CascadeType.ALL
            )
    @JoinColumn(
             name = "orderLineItemId"
    )
    private OrderLineItem orderLineItem;
}
