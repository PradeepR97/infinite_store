package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.*;
import com.infinitevision.infinite_store.dto.DistributorDTO;
import com.infinitevision.infinite_store.dto.ProductDetailsDTO;
import com.infinitevision.infinite_store.exception.ProductNotFoundException;
import com.infinitevision.infinite_store.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductHighlightRepository productHighlightRepository;
    private final ProductCouponRepository productCouponRepository;
    private final ProductDistributorRepository productDistributorRepository;

    public ProductDetailsDTO getProductDetails(Long productId) {
        log.info("Fetching product details for ID: {}", productId);

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        log.info("Product found: {}", product.getProductName());

        List<String> images = productImageRepository.findByProductId(productId)
                .stream().map(ProductImage::getImageUrl).toList();

        List<String> highlights = productHighlightRepository.findByProductId(productId)
                .stream().map(ProductHighlight::getHighlightText).toList();

        List<String> coupons = productCouponRepository.findByProductId(productId)
                .stream().map(ProductCoupon::getCouponText).toList();

        var distributor = productDistributorRepository.findByProductId(productId);
        DistributorDTO distributorDTO = null;

        if (distributor != null) {
            distributorDTO = new DistributorDTO(
                    distributor.getSellerName(),
                    distributor.getSellerAddress(),
                    distributor.getLicenseNo(),
                    distributor.getCountryOfOrigin()
            );
        }

        log.info("Product details prepared for ID: {}", productId);

        return new ProductDetailsDTO(
                product.getId(),
                product.getProductName(),
                product.getMrp(),
                product.getPrice(),
                product.getRating(),
                product.getTotalReviews(),
                product.getUnitType(),
                images,
                highlights,
                coupons,
                distributorDTO
        );
    }
}
