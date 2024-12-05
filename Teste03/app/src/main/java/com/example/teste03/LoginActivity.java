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
                loginUser(matricula, password);
            }
        });
    }

    public void getUsers(View v) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<User>> call = apiService.users();
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
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("logger", "falhou o login", t);
            }
        });
    }

    public void loginUser(String matricula, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(matricula, password);

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    int userId = loginResponse.getId();
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    if (switchRole.isChecked()) {
                        // Funcionário ou outra função
                        intent = new Intent(LoginActivity.this, ChamadosAbertosActivity.class);
                    } else {
                        // Usuário comum
                        intent = new Intent(LoginActivity.this, CadastroChamadoActivity.class);
                    }
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro de login: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.v("API", response.message());
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
