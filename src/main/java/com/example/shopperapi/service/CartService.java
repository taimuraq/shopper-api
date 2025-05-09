package com.example.shopperapi.service;

import com.example.shopperapi.dao.CartDao;
import com.example.shopperapi.model.Cart;
import com.example.shopperapi.model.CompanySettings;
import com.example.shopperapi.model.User;
import com.example.shopperapi.tracking.RequestContextTracker;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartDao cartDao;

  private final RestClientService restClientService;

  public CartService(CartDao cartDao, RestClientService restClientService) {
    this.restClientService = restClientService;
    this.cartDao = cartDao;
  }

  public Cart getCartById(String id) {
    RequestContextTracker.recordMethod(getCurrentMethod());
    Cart cart = cartDao.getCartById(id);
    if (cart != null) {
      User user = fetchUserById(cart.getUserId());
      cart.setUser(user);
    }
    return cart;
  }

  public Cart createCart(Cart cart) {
    RequestContextTracker.recordMethod(getCurrentMethod());

    //assume it needs to fetch company settings
    CompanySettings companySettings = fetchCompanySettingsByUnitId("1");
    //assumr we have userId
    User user = fetchUserById(cart.getUserId());
    return cartDao.createCart(cart);
  }

  private CompanySettings fetchCompanySettingsByUnitId(String unitId) {
    return restClientService.getCompanySettings(unitId);
  }

  public User fetchUserById(String userId) {
    return restClientService.getUser(userId);
  }

  private String getCurrentMethod() {
    return this.getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
  }
}
