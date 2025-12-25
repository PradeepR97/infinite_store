package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.Cart;
import com.infinitevision.infinite_store.domain.model.enums.CartItem;
import com.infinitevision.infinite_store.domain.model.enums.Product;
import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.dto.ProductCardDTO;
import com.infinitevision.infinite_store.repository.CartItemRepository;
import com.infinitevision.infinite_store.repository.CartRepository;
import com.infinitevision.infinite_store.repository.ProductRepository;
import com.infinitevision.infinite_store.repository.UserRepository;
import com.infinitevision.infinite_store.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final JwtService JwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public void addToCart(String token, Long productId) {

        Long userId = JwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setPrice(product.getPrice());
            cartItem.setMrp(product.getMrp());

        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        cartItemRepository.save(cartItem);
    }


    public List<ProductCardDTO> getCart(String token) {

        Long userId = JwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCart(cart)
                .stream()
                .map(item -> {
                    Product product = item.getProduct();

                    double discount = 0.0;
                    if (product.getMrp() != null && product.getPrice() != null) {
                        discount = ((product.getMrp() - product.getPrice())
                                / product.getMrp()) * 100;
                    }

                    return new ProductCardDTO(
                            userId,
                            product.getId(),
                            product.getProductName(),
                            product.getMrp(),
                            product.getPrice(),
                            product.getThumbnailUrl(),
                            product.getRating(),
                            product.getUnitType(),
                            discount,
                            product.getTotalReviews()
                    );
                })
                .toList();
    }
    public void removeFromCart(String token, Long productId) {

        Long userId = JwtService.extractUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        cartItemRepository.delete(cartItem);
    }

}