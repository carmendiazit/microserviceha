package com.carmechas.order_service.model.dtos;

import com.carmechas.order_service.model.entities.Order;
import jakarta.persistence.*;

import java.util.List;

public record OrderItemResponse(Long id, String sku,Double price, Long quantity) {


}
