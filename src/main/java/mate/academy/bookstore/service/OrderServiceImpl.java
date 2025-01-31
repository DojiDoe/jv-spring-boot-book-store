package mate.academy.bookstore.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.order.OrderDto;
import mate.academy.bookstore.dto.order.OrderItemDto;
import mate.academy.bookstore.dto.order.PlaceOrderRequestDto;
import mate.academy.bookstore.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.OrderItemMapper;
import mate.academy.bookstore.mapper.OrderMapper;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.OrderItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.repository.OrderRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import mate.academy.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDto save(PlaceOrderRequestDto requestDto, Long userId) {
        Order order = new Order();
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);

        order.setUser(userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Can't find a user with id: " + userId)));

        order.setOrderItems(cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet()));

        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        cart.getCartItems().clear();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> findAll(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toDto).toList();
    }

    @Override
    public List<OrderItemDto> findAllOrderItemsByOrderId(Long orderId, Long userId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long orderItemId, Long orderId, Long userId) {
        OrderItem orderItemFromDB = orderRepository.findByIdAndUserId(orderId, userId)
                .getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(()
                        -> new EntityNotFoundException("Can't find order item with id: "
                        + orderItemId));
        return orderItemMapper.toDto(orderItemFromDB);
    }

    @Override
    public OrderDto update(UpdateOrderStatusRequestDto requestDto, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new EntityNotFoundException("Can't find order item with id: " + orderId));
        order.setStatus(requestDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }
}
