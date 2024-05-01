package org.example.test.data.config;

import org.example.dto.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataInMemory implements TestData {

    private final Set<CustomerData> customers;
    private final Set<OrderData> orders;
    private final Set<CartData> carts;
    private final Set<ProductData> products;

    public TestDataInMemory() {
        customers = new HashSet<>();
        orders = new HashSet<>();
        carts = new HashSet<>();
        products = new HashSet<>();
    }

    @Override
    public Optional<CustomerData> getCustomerById(final UUID identifier) {
        return this.customers
                .stream()
                .filter(customer -> customer.identifier().equals(identifier))
                .findAny();
    }

    @Override
    public Set<CustomerData> getCustomers() {
        return this.customers;
    }

    @Override
    public void addCustomer(final CustomerData customerData) {
        this.customers.add(customerData);
    }

    @Override
    public Optional<OrderData> getOrderById(final UUID identifier) {
        return this.orders.stream().filter(order -> order.identifier().equals(identifier)).findAny();
    }

    @Override
    public Set<OrderData> getOrders() {
        return this.orders;
    }

    @Override
    public void addOrder(final OrderData orderData) {
        this.orders.add(orderData);
    }

    @Override
    public Optional<CartData> getCartById(UUID identifier) {
        return this.carts.stream().filter(c -> c.identifier() == identifier).findAny();
    }

    @Override
    public Optional<CartData> getCartByCustomerId(UUID identifier) {
        return this.carts.stream().filter(c -> c.customerIdentifier().equals(identifier)).findAny();
    }

    @Override
    public Set<CartData> getCarts() {
        return this.carts;
    }

    @Override
    public void addCart(CartData cartData) {
        this.carts.add(cartData);
    }

    @Override
    public Optional<ProductData> getProductById(UUID identifier) {
        return this.products.stream().filter(p -> p.identifier() == identifier).findAny();
    }

    @Override
    public Set<ProductData> getProducts() {
        return this.products;
    }

    @Override
    public void addProduct(ProductData productData) {
        this.products.add(productData);
    }

    @Override
    public void updateProduct(ProductData productData) {
        ProductData old = this.products.stream().filter(p -> p.identifier().equals(productData.identifier())).findFirst().get();
        this.products.remove(old);
        this.products.add(productData);
        Set<CartData> cartUpdate = this.carts.stream().filter(cart -> cart.products().stream().anyMatch(p -> p.identifier().equals(productData.identifier())))
                .map(cart -> {
                    Set<ProductData> productsWithoutUpdatedProduct = cart.products().stream().filter(p -> !p.identifier().equals(productData.identifier())).collect(Collectors.toSet());
                    productsWithoutUpdatedProduct.add(productData);
                    return ImmutableCartData.copyOf(cart).withProducts(productsWithoutUpdatedProduct);
                }).collect(Collectors.toSet());
        carts.removeAll(this.carts.stream().filter(
                cart -> cart.products().stream().anyMatch(p -> p.identifier().equals(productData.identifier()))).collect(Collectors.toSet()));
        this.carts.addAll(cartUpdate);
    }
}
