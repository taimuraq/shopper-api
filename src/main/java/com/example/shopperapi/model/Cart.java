package com.example.shopperapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
  private String id;
  private String userId;
  private String contents; // Simplified field for cart items
  private User user;       // Populated via internal API call
}

