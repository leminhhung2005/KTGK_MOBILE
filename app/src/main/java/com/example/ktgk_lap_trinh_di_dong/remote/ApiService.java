package com.example.ktgk_lap_trinh_di_dong.remote;

import com.example.ktgk_lap_trinh_di_dong.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // API lấy chi tiết sản phẩm đầu tiên (giống API gốc)
    @GET("api/products/detail")
    Call<Product> getProductDetail();

    // API lấy tất cả sản phẩm
    @GET("api/products")
    Call<List<Product>> getAllProducts();

    // API lấy sản phẩm theo ID
    @GET("api/products/{id}")
    Call<Product> getProductById(@Path("id") Long id);
}


