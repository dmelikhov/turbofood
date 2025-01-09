package turbofood.delivery.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import turbofood.delivery.dto.AssignCourierRequest;
import turbofood.delivery.dto.DeliveryAssignmentDto;
import turbofood.delivery.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
@SecurityRequirement(name = "openid-connect")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public List<DeliveryAssignmentDto> findAllUnassigned() {
        return deliveryService.findAllUnassigned();
    }

    @PutMapping("/{id}/assign")
    public DeliveryAssignmentDto assign(@PathVariable UUID id, @RequestBody AssignCourierRequest request) {
        return deliveryService.assign(id, request);
    }

    @PutMapping("/{id}/arrive")
    public DeliveryAssignmentDto arrive(@PathVariable UUID id) {
        return deliveryService.arrive(id);
    }

}
