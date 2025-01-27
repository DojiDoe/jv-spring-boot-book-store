package mate.academy.bookstore.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.UpdateShoppingCartItemRequestDto;
import mate.academy.bookstore.dto.shoppingcart.ShoppingCartDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.CartItemMapper;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.CartItemRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto create(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartDto addCartItem(CreateCartItemRequestDto requestDto, Long userId) {
        ShoppingCart shoppingFromDB = getShoppingCartFromDB(userId);
        Optional<CartItem> cartItemFromDb = checkIfCartItemAlreadyExist(requestDto, shoppingFromDB);
        CartItem itemToAdd = cartItemMapper.toModel(requestDto);
        itemToAdd.setShoppingCart(shoppingFromDB);
        Set<CartItem> cartItems = shoppingFromDB.getCartItems();
        if (cartItemFromDb.isPresent()) {
            CartItem cartItem = cartItemFromDb.get();
            cartItems.remove(cartItem);
            itemToAdd.setId(cartItem.getId());
            itemToAdd.setQuantity(cartItem.getQuantity() + itemToAdd.getQuantity());
        }
        cartItems.add(itemToAdd);
        shoppingFromDB.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingFromDB));
    }

    @Override
    @Transactional
    public ShoppingCartDto updateShoppingCart(Long cartItemId,
                                              UpdateShoppingCartItemRequestDto requestDto,
                                              Long userId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()
                -> new EntityNotFoundException("Can't find cart item with id " + cartItemId));
        cartItem.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItem);
        return getShoppingCartByUserId(userId);
    }

    @Override
    public ShoppingCartDto deleteCartItem(Long cartItemId, Long userId) {
        cartItemRepository.deleteById(cartItemId);
        return getShoppingCartByUserId(userId);
    }

    @Override
    public ShoppingCartDto getShoppingCartByUserId(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartFromDB(userId));
    }

    private static Optional<CartItem> checkIfCartItemAlreadyExist(
            CreateCartItemRequestDto requestDto,
            ShoppingCart shoppingFromDB) {
        return shoppingFromDB.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.bookId()))
                .findFirst();
    }

    private ShoppingCart getShoppingCartFromDB(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }
}
