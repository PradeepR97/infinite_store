package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_distributor_info")
public class ProductDistributorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_address")
    private String sellerAddress;

    @Column(name = "license_no")
    private String licenseNo;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;
}

