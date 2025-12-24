package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.*;
import com.infinitevision.infinite_store.dto.AddAddressRequestDTO;
import com.infinitevision.infinite_store.dto.OrderResponse;
import com.infinitevision.infinite_store.dto.PlaceOrderRequest;
import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.repository.*;
import com.infinitevision.infinite_store.util.OrderIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ApiResponse<OrderResponse> placeOrder(PlaceOrderRequest request) {
        log.info("Placing order for userId={}", request.getUserId());

        // -------- CREATE ADDRESS --------
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with id={}", request.getUserId());
                    return new RuntimeException("User not found");
                });

        AddAddressRequestDTO addrReq = request.getAddress();

        var address = new Address();
        address.setUser(user);
        address.setAddressLine1(addrReq.getAddressLine1());
        address.setAddressLine2(addrReq.getAddressLine2());
        address.setLandmark(addrReq.getLandmark());
        address.setCity(addrReq.getCity());
        address.setState(addrReq.getState());
        address.setPincode(addrReq.getPincode());
        address.setAddressType(addrReq.getAddressType());

        Address savedAddress = addressRepository.save(address);
        log.info("Address saved with id={}", savedAddress.getId());

        // -------- CREATE ORDER --------
        var order = new Order();
        order.setUserId(request.getUserId());
        order.setAddressId(savedAddress.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PACKING);
        order.setPayment(request.getPayment());
        order.setOnTime(true);

        Order savedOrder = orderRepository.save(order);
        savedOrder.setOrderId(OrderIdGenerator.generate(savedOrder.getId()));
        orderRepository.save(savedOrder);
        log.info("Order saved with orderId={}", savedOrder.getOrderId());

        // -------- CREATE ORDER ITEMS --------
        for (PlaceOrderRequest.Item reqItem : request.getItems()) {
            Product product = productRepository.findById(reqItem.getProductId())
                    .orElseThrow(() -> {
                        log.error("Product not found with id={}", reqItem.getProductId());
                        return new RuntimeException("Product not found");
                    });

            OrderItem item = new OrderItem();
            item.setOrderId(savedOrder.getOrderId());
            item.setProductId(product.getId());
            item.setQuantity(reqItem.getQuantity());
            item.setPrice(product.getPrice());

            orderItemRepository.save(item);
            log.info("OrderItem saved: productId={}, quantity={}", product.getId(), reqItem.getQuantity());
        }

        // -------- BUILD RESPONSE --------
        OrderResponse orderResponse = buildResponse(savedOrder);
        log.info("Order response built for orderId={}", savedOrder.getOrderId());

        return ApiResponse.success("Order placed successfully", orderResponse);
    }

    private OrderResponse buildResponse(Order order) {
        OrderStatus status = calculateStatus(order.getCreatedAt());

        Address address = addressRepository.findById(order.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());

        List<OrderResponse.Item> responseItems = items.stream().map(i -> {
            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            var itemResp = new OrderResponse.Item();
            itemResp.setProductId(product.getId());
            itemResp.setProductName(product.getProductName());
            itemResp.setQuantity(i.getQuantity());
            itemResp.setPrice(i.getPrice());
            itemResp.setTotalPrice(i.getPrice() * i.getQuantity());
            return itemResp;
        }).toList();

        double itemTotal = responseItems.stream()
                .mapToDouble(OrderResponse.Item::getTotalPrice)
                .sum();

        var bill = new OrderResponse.BillSummary();
        bill.setItemTotal(itemTotal);
        bill.setDeliveryFee(40.00);
        bill.setDiscount(268.00);
        bill.setGrandTotal(itemTotal + 40.00 - 268.00);

        var payment = new OrderResponse.Payment();
        payment.setPaymentMethod(order.getPayment().name());
        payment.setPaymentStatus("PENDING");
        payment.setMessage("Pay cash when order is delivered");

        var addr = new OrderResponse.Address();
        addr.setAddressLine1(address.getAddressLine1());
        addr.setAddressLine2(address.getAddressLine2());
        addr.setLandmark(address.getLandmark());
        addr.setCity(address.getCity());
        addr.setState(address.getState());
        addr.setPincode(address.getPincode());
        addr.setAddressType(address.getAddressType().name());

        var tracking = new OrderResponse.Tracking();
        tracking.setEnabled(true);

        var response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setOrderStatus(status);
        response.setArrivalTimeInSeconds(30);
        response.setDeliveryStatusText(getStatusText(status));
        response.setOnTime(true);
        response.setBillSummary(bill);
        response.setPayment(payment);
        response.setDeliveryAddress(addr);
        response.setTracking(tracking);
        response.setItems(responseItems);

        return response;
    }

    private OrderStatus calculateStatus(LocalDateTime createdAt) {
        long seconds = Duration.between(createdAt, LocalDateTime.now()).getSeconds();
        if (seconds < 30) return OrderStatus.PACKING;
        if (seconds < 60) return OrderStatus.DELIVERY_PARTNER_PICKED;
        if (seconds < 120) return OrderStatus.ON_THE_WAY;
        return OrderStatus.DELIVERED;
    }

    private String getStatusText(OrderStatus status) {
        return switch (status) {
            case PACKING -> "Your order is getting packed";
            case DELIVERY_PARTNER_PICKED -> "Delivery partner picked your order";
            case ON_THE_WAY -> "Your order is on the way";
            case DELIVERED -> "Order delivered successfully";
        };
    }
}
