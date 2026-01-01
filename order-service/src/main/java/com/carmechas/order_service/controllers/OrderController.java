package com.carmechas.order_service.controllers;


import com.carmechas.order_service.model.dtos.OrderRequest;
import com.carmechas.order_service.model.dtos.OrderResponse;
import com.carmechas.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return ("Order placed successfully {}");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderResponse> getOrders(){
        return  orderService.getAllOrders();
    }
}
