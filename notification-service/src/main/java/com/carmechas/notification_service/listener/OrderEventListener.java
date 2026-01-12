package com.carmechas.notification_service.listener;


import com.carmechas.notification_service.events.OrderEvent;

import com.carmechas.notification_service.utils.JsonUtils;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orders-topic", groupId = "notification-service")
    @Retry(name = "notification-service", fallbackMethod = "fallbackNotification")
    public void handleOrdersNotifications(String message) {
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        log.info("Trying to send an order email: {}", orderEvent.orderNumber());


            // Some external API

        log.info("Mail was sent successfully through external API.");
    }
    public void fallbackNotification(String message, Throwable t) {
        Thread.ofVirtual().start(() -> {
            kafkaTemplate.send("orders-topic-failed", message);
            log.error("Sending the failed order to DLQ" );
        });
    }
    // --- CONSUMER (DLQ) ---
    @KafkaListener(topics = "orders-topic-failed", groupId = "notification-audit-service")
    public void handleFailedOrders(String message) {
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        log.error("FAILURE AUDIT: Unable to notify order {} after several attempts.",
                orderEvent.orderNumber());

        //TODO: Retry notification or persist order to another handler for processing.

        log.info("The failed message requires awaiting manual revision: {}", message);
    }
}