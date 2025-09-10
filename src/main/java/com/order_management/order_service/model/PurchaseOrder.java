package com.order_management.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID customerId;

    private String status;
    private BigDecimal totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "purchaseOrder"
    )
    private List<OrderItem>  orderItem;

}
