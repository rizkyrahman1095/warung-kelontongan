package com.enigma.tokonyadia.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "m_customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String address;
    private String gender;
    private String phoneNumber;
    @CreatedDate
    @Column(columnDefinition="TIMESTAMP")
    private Timestamp createDate;
    @LastModifiedDate
    @Column(columnDefinition="TIMESTAMP")
    private Timestamp updateDate;
    @OneToOne
    private UserCredential userCredential;

}
