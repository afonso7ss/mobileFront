package com.example.teste03;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("users")
    Call<List<User>> users();

    @GET("chamados")
    Call<List<CadastroChamadoActivity.Chamado>> chamados();

    @POST("chamados/1")
    Call<CadastroChamadoActivity.Chamado> cadastrarChamado(@Body CadastroChamadoActivity.Chamado chamado);
}
