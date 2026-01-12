package com.carmechas.order_service.services;

import com.carmechas.order_service.events.OrderEvent;
import com.carmechas.order_service.model.dtos.*;
import com.carmechas.order_service.model.entities.Order;
import com.carmechas.order_service.model.entities.OrderItem;
import com.carmechas.order_service.model.enums.OrderStatus;
import com.carmechas.order_service.repositories.OrderRepository;
import com.carmechas.order_service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        //Check inventory
        BaseResponse result = this.webClient.build()
                .post()
                .uri("lb://inventory-service/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(result != null && !result.hasError()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());
            var savedOrder = this.orderRepository.save(order);

            this.kafkaTemplate.send("orders-topic",
                    JsonUtils.toJson(new OrderEvent(savedOrder.getOrderNumber(),savedOrder.getOrderItems().size(), OrderStatus.PLACED)));

            return mapToOrderResponse(savedOrder);
        }else {
            //TODO might use another queue for handling lack of products.
            throw new IllegalArgumentException("Some of products are not in stock");
        }
    }

    public List<OrderResponse> getAllOrders(){
        return this.orderRepository.findAll().stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber()
                , order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemResponse mapToOrderItemRequest(OrderItem orderItem) {
        return  new OrderItemResponse(orderItem.getId(), orderItem.getSku(), orderItem.getPrice(), orderItem.getQuantity());

    }

    private OrderItem mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItem.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}
