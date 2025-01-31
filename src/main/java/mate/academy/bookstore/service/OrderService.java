package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.order.OrderDto;
import mate.academy.bookstore.dto.order.OrderItemDto;
import mate.academy.bookstore.dto.order.PlaceOrderRequestDto;
import mate.academy.bookstore.dto.order.UpdateOrderStatusRequestDto;

public interface OrderService {
    OrderDto save(PlaceOrderRequestDto requestDto, Long userId);

    List<OrderDto> findAll(Long userId);

    List<OrderItemDto> findAllOrderItemsByOrderId(Long orderId, Long userId);

    OrderItemDto getOrderItem(Long orderItemId, Long orderId, Long userId);

    OrderDto update(UpdateOrderStatusRequestDto requestDto, Long orderId);
}
