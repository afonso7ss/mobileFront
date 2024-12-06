package com.example.teste03;

import com.example.teste03.CadastroChamadoActivity.Chamado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;


public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("users")
    Call<List<User>> users();

    @POST("chamados/{userId}")
    Call<Chamado> cadastrarChamado(@Path("userId") int userId, @Body Chamado chamado);

    @GET("chamados")
    Call<List<CadastroChamadoActivity.Chamado>> getChamados();

    @PUT("chamados/{chamadoId}")
    Call<CadastroChamadoActivity.Chamado> atualizarStatusChamado(@Path("chamadoId") int chamadoId, @Body CadastroChamadoActivity.Chamado chamado);
    @GET("users/{userId}/chamados")
    Call<List<CadastroChamadoActivity.Chamado>> getChamadosDoUsuario(@Path("userId") int userId);

}
