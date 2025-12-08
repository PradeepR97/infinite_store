package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUser {

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;


}




