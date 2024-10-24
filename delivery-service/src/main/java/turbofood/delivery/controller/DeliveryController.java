package turbofood.delivery.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import turbofood.delivery.dto.AssignCourierRequest;
import turbofood.delivery.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
@SecurityRequirement(name = "openid-connect")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public void assign(@RequestBody AssignCourierRequest request) {
        deliveryService.assign(request);
    }

    @PutMapping("/{id}/arrive")
    public void arrive(@PathVariable UUID id) {
        deliveryService.arrive(id);
    }

}
