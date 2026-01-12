package com.carmechas.notification_service.events;


import com.carmechas.notification_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus status) {
}


