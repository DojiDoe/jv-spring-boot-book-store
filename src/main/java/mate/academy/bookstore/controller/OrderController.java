package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.order.OrderDto;
import mate.academy.bookstore.dto.order.OrderItemDto;
import mate.academy.bookstore.dto.order.PlaceOrderRequestDto;
import mate.academy.bookstore.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}")
    @Operation(summary = "Update order status")
    public OrderDto placeOrder(@RequestBody @Valid UpdateOrderStatusRequestDto requestDto,
                               @PathVariable Long id) {
        return orderService.update(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Place an Order",
            description = "Place an Order and clean shopping cart of registered client")
    public OrderDto placeOrder(@RequestBody @Valid PlaceOrderRequestDto requestDto,
                               Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.save(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get Order history",
            description = "Get all orders of registered client")
    public List<OrderDto> getOrderHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAll(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{orderId}/items")
    @Operation(summary = "Get order details",
            description = "Get all order items from specific order of registered client")
    public List<OrderItemDto> getOrderDetails(@PathVariable Long orderId,
                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllOrderItemsByOrderId(orderId, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{orderId}/items/{id}")
    @Operation(summary = "Get order item",
            description = "Get order item from specific order of registered client")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long id,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItem(id, orderId, user.getId());
    }
}
