package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Switch switchRole;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        switchRole = findViewById(R.id.switchRole);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String matricula = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (matricula.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                Log.v("logger", "deve mostrar o log");
//                    getUsers(v);
                loginUser(matricula, password);
            }
        });
    }



    public void getUsers(View v) {
        try {
            Log.v("logger", "entrou no test");
            ApiService apiService = RetrofitClient.getApiService();
            Call call = apiService.users();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        List<User> users = response.body();
                        Log.v("logger", "login efetuado com sucesso");
                        Log.v("response", users.toString());
                    } else {
                        Log.v("logger", "Erro: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("logger", "falhou o login", t);
                }
            });
        } catch (Exception e) {
            Log.e("logger", "Erro ao executar a requisição", e);
        }
    }

    public void loginUser(String matricula, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(matricula, password);

        Log.v("API_REQUEST", "URL: " + apiService.login(loginRequest).request().url());
        Log.v("API_REQUEST", "Corpo da Requisição: " + loginRequest);

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (response.code() == 200) {
                        Log.v("isSucess", "sucesso na requisição");
                        Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        if (switchRole.isChecked()) {
                            intent = new Intent(LoginActivity.this, ChamadosAbertosActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, CadastroChamadoActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        String message = loginResponse.getMessage();
                        int code = response.code();
                        if (message == null) {
                            message = "Mensagem de erro não disponível" + ", code:" + code;
                        }
                        Log.v("API_RESPONSE", "Erro de login: " + message);
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro de login: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.v("API", response.message());
                        Log.v("API", response.toString());
                        Log.v("API", String.valueOf(response.raw()));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("API", "cai na falha de botão");
            }
        });
    }
}