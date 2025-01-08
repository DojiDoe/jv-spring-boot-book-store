package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.UpdateShoppingCartItemRequestDto;
import mate.academy.bookstore.dto.shoppingcart.ShoppingCartDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto create(User user);

    ShoppingCartDto addCartItem(CreateCartItemRequestDto requestDto, Long id);

    ShoppingCartDto getShoppingCartByUserId(Long userId);

    ShoppingCartDto updateShoppingCart(Long cartItemId,
                                       UpdateShoppingCartItemRequestDto requestDto,
                                       Long id);

    ShoppingCartDto deleteCartItem(Long cartItemId, Long userId);
}
