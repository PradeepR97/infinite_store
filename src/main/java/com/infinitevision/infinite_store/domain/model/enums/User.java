package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;
}



