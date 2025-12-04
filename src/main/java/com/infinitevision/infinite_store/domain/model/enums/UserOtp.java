package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String otp;
}

