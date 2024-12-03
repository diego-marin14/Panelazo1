package com.proyecto.panelazo.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor

@AllArgsConstructor // Constructor completo para Lombok Builder
@Builder
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Relación muchos a 1
    @ToString.Exclude
    private Usuario usuario; // Relación con Usuario

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean used = false; // Indica si el código ya fue usado
}
