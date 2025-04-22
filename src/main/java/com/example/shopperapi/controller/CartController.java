package com.example.shopperapi.controller;

import com.example.shopperapi.model.Cart;
import com.example.shopperapi.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public Cart createCart(@RequestBody Cart cart) {
    return cartService.createCart(cart);
  }

  @GetMapping("/{id}")
  public Cart getCart(@PathVariable String id) {
    return cartService.getCartById(id);
  }
}

