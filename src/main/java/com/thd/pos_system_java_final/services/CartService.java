package com.thd.pos_system_java_final.services;

import com.thd.pos_system_java_final.models.Cart.Item;
import com.thd.pos_system_java_final.models.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thd.pos_system_java_final.models.Product.Product;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepository;
    private List<Item> cartItems = new ArrayList<>();

    public void addItemToCart(Item item) {
        Product product = productRepository.findByProductId(item.getProduct().getProductId());
        item.setProduct(product);

        for (Item cartItem : cartItems) {
            if (cartItem.getProduct().equals(item.getProduct())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return;
            }
        }

        cartItems.add(item);
    }

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void printCart() {
        System.out.println("Cart Contents:");
        for (Item item : cartItems) {
            System.out.println("Product ID: " + item.getProduct().getProductId() +
                    ", Quantity: " + item.getQuantity());
        }
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Item item : cartItems) {
            totalAmount += item.getProduct().getRetailPrice() * item.getQuantity();
        }
        return totalAmount;
    }

    public int calculateTotalQuantity() {
        int totalQuantity = 0;
        for (Item item : cartItems) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    public void removeItemFromCart(int productId) {
        Iterator<Item> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getProduct().getProductId() == productId) {
                iterator.remove();
                return;
            }
        }
        throw new NoSuchElementException("Item not found in cart");
    }

    public void updateCartItem(int productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }

        for (Item item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                item.setQuantity(newQuantity);
                return;
            }
        }

        throw new NoSuchElementException("Item not found in cart");
    }

    public void resetCart() {
        cartItems.clear();
    }
}


