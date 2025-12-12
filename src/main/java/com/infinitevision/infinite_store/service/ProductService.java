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


    public List<ProductCardDTO> getAllProductCards() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(p -> new ProductCardDTO(
                        p.getId(),
                        p.getProductName(),
                        p.getMrp(),
                        p.getPrice(),
                        p.getThumbnailUrl(),
                        p.getRating(),
                        p.getUnitType(),
                        p.getDiscountPercentage(),
                        p.getTotalReviews()
                ))
                .toList();
    }



}

