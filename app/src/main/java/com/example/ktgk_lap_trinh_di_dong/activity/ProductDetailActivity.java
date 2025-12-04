package com.example.ktgk_lap_trinh_di_dong.activity;

// ProductDetailActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.ktgk_lap_trinh_di_dong.remote.RetrofitClient;
import com.example.ktgk_lap_trinh_di_dong.databinding.ActivityProductDetailBinding;
import com.example.ktgk_lap_trinh_di_dong.model.CartItem;
import com.example.ktgk_lap_trinh_di_dong.model.Product;
import com.example.ktgk_lap_trinh_di_dong.utils.CartManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private CartManager cartManager;
    private Product currentProduct;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartManager = CartManager.getInstance(this);

        setupClickListeners();
        loadProductDetail();
    }

    private void setupClickListeners() {
        binding.btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnPlus.setOnClickListener(v -> {
            quantity++;
            binding.tvQuantity.setText(String.valueOf(quantity));
        });

        binding.btnAddToCart.setOnClickListener(v -> addToCart());

        binding.btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadProductDetail() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Call<Product> call = RetrofitClient.getInstance().getApiService().getProductDetail();
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    currentProduct = response.body();
                    displayProduct(currentProduct);
                } else {
                    Toast.makeText(ProductDetailActivity.this,
                            "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductDetailActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProduct(Product product) {
        binding.tvProductName.setText(product.getName());
        binding.tvPrice.setText(String.format("$ %.2f", product.getPrice()));

        String description = product.getInstructions() != null ?
                product.getInstructions() : product.getDescription();
        binding.tvDescription.setText(description);

        Glide.with(this)
                .load(product.getImage())
                .into(binding.ivProduct);
    }

    private void addToCart() {
        if (currentProduct == null) {
            Toast.makeText(this, "Vui lòng đợi tải sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        CartItem cartItem = new CartItem(
                currentProduct.getId(),
                currentProduct.getName(),
                currentProduct.getPrice(),
                currentProduct.getImage(),
                currentProduct.getDescription(),
                quantity
        );

        cartManager.addToCart(cartItem);
        Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

        // Reset quantity
        quantity = 1;
        binding.tvQuantity.setText("1");
    }
}
