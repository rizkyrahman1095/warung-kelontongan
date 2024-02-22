package com.enigma.tokonyadia.entity;



import com.enigma.tokonyadia.utils.constant.Eroll;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
@Entity
@Table(name = "m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated (EnumType.STRING)
    private Eroll eroll;
}
