package com.example.ktgk_lap_trinh_di_dong.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ktgk_lap_trinh_di_dong.adapters.CartAdapter;
import com.example.ktgk_lap_trinh_di_dong.databinding.ActivityCartBinding;
import com.example.ktgk_lap_trinh_di_dong.model.CartItem;
import com.example.ktgk_lap_trinh_di_dong.utils.CartManager;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemListener {

    private ActivityCartBinding binding;
    private CartManager cartManager;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartManager = CartManager.getInstance(this);

        setupRecyclerView();
        setupClickListeners();
        loadCartItems();
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(this);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(cartAdapter);
    }

    private void setupClickListeners() {
        binding.btnCheckout.setOnClickListener(v -> {
            // Handle checkout
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void loadCartItems() {
        List<CartItem> cartItems = cartManager.getCartItems();

        if (cartItems.isEmpty()) {
            binding.tvEmptyCart.setVisibility(View.VISIBLE);
            binding.rvCart.setVisibility(View.GONE);
        } else {
            binding.tvEmptyCart.setVisibility(View.GONE);
            binding.rvCart.setVisibility(View.VISIBLE);
        }

        cartAdapter.setCartItems(cartItems);
        updateTotals();
    }

    private void updateTotals() {
        int totalItems = cartManager.getTotalItemCount();
        double totalPrice = cartManager.getTotalPrice();

        binding.tvItemCount.setText(String.valueOf(totalItems));
        binding.tvTotal.setText(String.format("$ %.2f", totalPrice));
    }

    @Override
    public void onQuantityChanged(CartItem item, int newQuantity) {
        cartManager.updateQuantity(item.getProductId(), newQuantity);
        loadCartItems();
    }

    @Override
    public void onItemRemoved(CartItem item) {
        cartManager.removeFromCart(item.getProductId());
        loadCartItems();
    }
}
