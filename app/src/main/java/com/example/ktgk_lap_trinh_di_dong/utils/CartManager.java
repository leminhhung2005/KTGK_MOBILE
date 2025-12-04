package com.example.ktgk_lap_trinh_di_dong.utils;

// utils/CartManager.java

import android.content.Context;
import android.content.SharedPreferences;
import com.example.ktgk_lap_trinh_di_dong.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "CartPreferences";
    private static final String KEY_CART_ITEMS = "cart_items";

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private static CartManager instance;

    private CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    // Lấy tất cả items trong giỏ hàng
    public List<CartItem> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, null);
        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // Lưu giỏ hàng
    private void saveCart(List<CartItem> cartItems) {
        String json = gson.toJson(cartItems);
        sharedPreferences.edit().putString(KEY_CART_ITEMS, json).apply();
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(CartItem newItem) {
        List<CartItem> cartItems = getCartItems();

        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProductId() == newItem.getProductId()) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            cartItems.add(newItem);
        }

        saveCart(cartItems);
    }

    // Cập nhật số lượng sản phẩm
    public void updateQuantity(int productId, int newQuantity) {
        List<CartItem> cartItems = getCartItems();

        if (newQuantity <= 0) {
            removeFromCart(productId);
            return;
        }

        for (CartItem item : cartItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(newQuantity);
                break;
            }
        }

        saveCart(cartItems);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(int productId) {
        List<CartItem> cartItems = getCartItems();
        cartItems.removeIf(item -> item.getProductId() == productId);
        saveCart(cartItems);
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart() {
        sharedPreferences.edit().remove(KEY_CART_ITEMS).apply();
    }

    // Tính tổng số lượng items
    public int getTotalItemCount() {
        List<CartItem> cartItems = getCartItems();
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    // Tính tổng tiền
    public double getTotalPrice() {
        List<CartItem> cartItems = getCartItems();
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
