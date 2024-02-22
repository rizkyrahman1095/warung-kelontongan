package com.enigma.tokonyadia.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_purchase")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date purchaseDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "purchase")
    private List<PurchaseDetail> purchaseDetails = new ArrayList<>();
}
