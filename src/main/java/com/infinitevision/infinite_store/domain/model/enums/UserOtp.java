package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOtp extends BaseUser {

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String otp;
}




