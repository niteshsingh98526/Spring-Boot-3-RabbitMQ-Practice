package com.order.service.controller;

import com.order.service.dto.Order;
import com.order.service.dto.OrderEvent;
import com.order.service.publisher.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/oredrs")
    public ResponseEntity<String> placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent event = new OrderEvent();
        event.setStatus("Pending");
        event.setMessage("Order is in pending status");
        event.setOrder(order);

        producer.sendMessage(event);
        return ResponseEntity.ok("Order sent to the RabbitMQ...");
    }
}
