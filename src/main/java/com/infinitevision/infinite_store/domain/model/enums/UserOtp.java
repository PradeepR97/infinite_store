package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_otp")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String otp;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by")
    private String createdBy;
}


