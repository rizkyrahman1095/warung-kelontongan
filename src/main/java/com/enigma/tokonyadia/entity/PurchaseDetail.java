package com.enigma.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_purchase_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer quantity;
    private Integer priceSell;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
