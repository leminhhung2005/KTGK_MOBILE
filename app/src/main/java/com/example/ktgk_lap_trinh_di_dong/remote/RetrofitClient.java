package com.example.ktgk_lap_trinh_di_dong.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // ⚠️ ĐỔI BASE_URL theo môi trường:

    // Nếu chạy trên Android Emulator:
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    // Nếu chạy trên thiết bị thật (điện thoại):
    // private static final String BASE_URL = "http://192.168.1.X:8080/";
    // (Thay X bằng IP máy tính chạy Spring Boot)

    private static RetrofitClient instance;
    private ApiService apiService;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
