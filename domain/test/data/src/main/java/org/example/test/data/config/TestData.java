package org.example.test.data.config;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.example.dto.CartData;
import org.example.dto.CustomerData;
import org.example.dto.OrderData;
import org.example.dto.ProductData;

public interface TestData {

  Optional<CustomerData> getCustomerById(UUID identifier);
  Set<CustomerData> getCustomers();
  void addCustomer(CustomerData customerData);

  Optional<OrderData> getOrderById(UUID identifier);
  Set<OrderData> getOrders();
  void addOrder(OrderData orderData);

  Optional<CartData> getCartById(UUID identifier);
  Optional<CartData> getCartByCustomerId(UUID identifier);
  Set<CartData> getCarts();
  void addCart(CartData cartData);

  Optional<ProductData> getProductById(UUID identifier);
  Set<ProductData> getProducts();
  void addProduct(ProductData productData);
  void updateProduct(ProductData productData);
}
