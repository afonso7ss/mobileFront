package com.example.teste03;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiService {
    @POST("auth/login")  // substitua pelo endpoint correto
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/users")
    Call test();
}