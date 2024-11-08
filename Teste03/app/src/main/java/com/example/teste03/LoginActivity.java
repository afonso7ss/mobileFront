package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                boolean isFuncionario = switchRole.isChecked();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (isFuncionario) {
                        // Redireciona o funcionário para a tela de "Chamados em Aberto"
                        Intent intent = new Intent(LoginActivity.this, ChamadosAbertosActivity.class);
                        startActivity(intent);
                    } else {
                        // Redireciona o aluno para a tela principal de aluno (exemplo)
                        Intent intent = new Intent(LoginActivity.this, CadastroChamadoActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
