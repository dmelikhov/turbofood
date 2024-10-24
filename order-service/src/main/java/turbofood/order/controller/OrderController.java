package turbofood.order.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.dto.OrderDto;
import turbofood.order.service.OrderService;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "openid-connect")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto create(CreateOrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @PutMapping("/{id}/confirm")
    public OrderDto confirm(@PathVariable UUID id) {
        return orderService.confirm(id);
    }

    @PutMapping("/{id}/reject")
    public OrderDto reject(@PathVariable UUID id) {
        return orderService.reject(id);
    }

    @PutMapping("/{id}/prepare")
    public OrderDto prepare(@PathVariable UUID id) {
        return orderService.prepare(id);
    }

}
