package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.Product;
import com.infinitevision.infinite_store.dto.ProductCardDTO;

import com.infinitevision.infinite_store.dto.ProductDetailsDTO;
import com.infinitevision.infinite_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDetailsDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(p -> new ProductDetailsDTO(
                        p.getId(),
                        p.getProductName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getMrp(),
                        p.getDiscountPercentage(),
                        p.getCategoryId(),
                        p.getSubcategoryId(),
                        p.getBrand(),
                        p.getWeight(),
                        p.getUnitType(),
                        p.getImageUrl(),
                        p.getThumbnailUrl(),
                        p.getStock(),
                        p.getIsAvailable(),
                        p.getRating(),
                        p.getTotalReviews()
                ))
                .toList();
    }


}

