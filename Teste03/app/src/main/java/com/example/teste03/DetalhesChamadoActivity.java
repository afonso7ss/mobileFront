package com.example.teste03;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesChamadoActivity extends AppCompatActivity {

    private TextView tvDetalhesDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        tvDetalhesDescricao = findViewById(R.id.tvDetalhesDescricao);

        // Pegar os dados do chamado passado pelo intent
        int id = getIntent().getIntExtra("id", -1);
        String categoria = getIntent().getStringExtra("categoria");
        String local = getIntent().getStringExtra("local");
        String descricao = getIntent().getStringExtra("descricao");
        boolean status = getIntent().getBooleanExtra("status", false);

        String statusText = status ? "Resolvido" : "Em Aberto";

        tvDetalhesDescricao.setText("ID: " + id +
                "\nCategoria: " + categoria +
                "\nLocal: " + local +
                "\nDescrição: " + descricao +
                "\nStatus: " + statusText);
    }
}
