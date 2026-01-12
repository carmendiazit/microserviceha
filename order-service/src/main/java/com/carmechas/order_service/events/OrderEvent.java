package com.carmechas.order_service.events;

import com.carmechas.order_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus status) {
}
