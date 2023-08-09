package com.datn.meou.services;

import com.datn.meou.entity.Cart;
import com.datn.meou.entity.CartItem;
import com.datn.meou.model.CartItemDTO;
import com.datn.meou.repository.CartItemRepository;
import com.datn.meou.repository.CartRepository;
import com.datn.meou.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public List<CartItemDTO> saveCarts(List<CartItemDTO> cartItemDTOS) {
        Cart cart = this.cartRepository.save(Cart.builder().accountId(1).build());
        List<CartItem> cartItems = MapperUtil.mapList(cartItemDTOS, CartItem.class);
        cartItems.forEach(x -> {
            x.setCartId(cart.getId());
            this.cartItemRepository.save(x);
        });
        return cartItemDTOS;
    }

    public Object paymentOrder() {
        return null;
    }
}
