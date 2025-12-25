package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.*;
import com.infinitevision.infinite_store.dto.*;
import com.infinitevision.infinite_store.repository.*;
import com.infinitevision.infinite_store.security.JwtService;
import com.infinitevision.infinite_store.util.OrderIdGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final JwtService jwtService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public ApiResponse<OrderResponse> placeOrder(
            String authorizationHeader,
            PlaceOrderRequest request
    ) {

        // ---------- AUTH ----------
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization token missing");
        }

        String token = authorizationHeader.substring(7);
        Long tokenUserId = jwtService.extractUserId(token);

        if (!tokenUserId.equals(request.getUserId())) {
            throw new RuntimeException("Unauthorized access");
        }

        User user = userRepository.findById(tokenUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ---------- ADDRESS ----------
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(tokenUserId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        // ---------- ORDER ----------
        Order order = new Order();
        order.setUserId(tokenUserId);
        order.setAddressId(address.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PACKING);
        order.setPayment(request.getPayment());
        order.setOnTime(true);

        Order savedOrder = orderRepository.save(order);
        savedOrder.setOrderId(OrderIdGenerator.generate(savedOrder.getId()));
        orderRepository.save(savedOrder);

        // ---------- ORDER ITEMS ----------
        for (PlaceOrderRequest.Item reqItem : request.getItems()) {

            Product product = productRepository.findById(reqItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrderId(savedOrder.getOrderId());
            item.setProductId(product.getId());
            item.setQuantity(reqItem.getQuantity());
            item.setPrice(product.getPrice());

            orderItemRepository.save(item);
        }

        // ---------- RESPONSE ----------
        OrderResponse response = buildResponse(savedOrder);
        return ApiResponse.success("Order placed successfully", response);
    }

    private OrderResponse buildResponse(Order order) {

        Address address = addressRepository.findById(order.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());

        List<OrderResponse.Item> responseItems = items.stream().map(i -> {
            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderResponse.Item item = new OrderResponse.Item();
            item.setProductId(product.getId());
            item.setProductName(product.getProductName());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            item.setTotalPrice(i.getPrice() * i.getQuantity());
            return item;
        }).toList();

        // ---------- BILL ----------
        double itemTotal = responseItems.stream()
                .mapToDouble(OrderResponse.Item::getTotalPrice)
                .sum();
        double deliveryFee = 40.0;
        double discount = itemTotal * 0.10;
        double grandTotal = itemTotal + deliveryFee - discount;

        // **STORE BILL SUMMARY TO ORDER ENTITY**
        order.setItemTotal(itemTotal);
        order.setDeliveryFee(deliveryFee);
        order.setDiscount(discount);
        order.setGrandTotal(grandTotal);
        orderRepository.save(order); // save updated totals

        // ---------- BILL SUMMARY FOR RESPONSE ----------
        OrderResponse.BillSummary bill = new OrderResponse.BillSummary();
        bill.setItemTotal(itemTotal);
        bill.setDeliveryFee(deliveryFee);
        bill.setDiscount(discount);
        bill.setGrandTotal(grandTotal);

        // ---------- PAYMENT ----------
        OrderResponse.Payment payment = new OrderResponse.Payment();
        payment.setPaymentMethod(order.getPayment().name());
        payment.setPaymentStatus("PENDING");
        payment.setMessage("Pay cash when order is delivered");

        // ---------- DELIVERY ADDRESS ----------
        OrderResponse.Address addr = new OrderResponse.Address();
        addr.setAddressLine1(address.getAddressLine1());
        addr.setAddressLine2(address.getAddressLine2());
        addr.setLandmark(address.getLandmark());
        addr.setCity(address.getCity());
        addr.setState(address.getState());
        addr.setPincode(address.getPincode());
        addr.setAddressType(address.getAddressType().name());

        // ---------- TRACKING ----------
        OrderResponse.Tracking tracking = new OrderResponse.Tracking();
        tracking.setEnabled(true);

        // ---------- FINAL ----------
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setOrderStatus(order.getOrderStatus());
        response.setArrivalTimeInSeconds(30);
        response.setDeliveryStatusText(getStatusText(order.getOrderStatus()));
        response.setOnTime(true);
        response.setBillSummary(bill);
        response.setPayment(payment);
        response.setDeliveryAddress(addr);
        response.setTracking(tracking);
        response.setItems(responseItems);

        return response;
    }


    // ================= STATUS =================
    private String getStatusText(OrderStatus status) {
        return switch (status) {
            case PACKING -> "Your order is getting packed";
            case DELIVERY_PARTNER_PICKED -> "Delivery partner picked your order";
            case ON_THE_WAY -> "Your order is on the way";
            case DELIVERED -> "Order delivered successfully";
        };
    }
}
