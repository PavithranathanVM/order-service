package com.order_management.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
