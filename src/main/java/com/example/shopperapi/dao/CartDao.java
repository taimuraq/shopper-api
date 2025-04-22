package com.example.shopperapi.dao;

import com.example.shopperapi.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CartDao {
  private final Map<String, Cart> carts = new ConcurrentHashMap<>();

  public CartDao() {
    // Mock cart
    carts.put("c1", new Cart("c1", "1", "item1,item2", null));
  }

  public Cart getCartById(String id) {
    return carts.get(id);
  }

  public Cart createCart(Cart cart) {
    carts.put(cart.getId(), cart);
    return cart;
  }
}

