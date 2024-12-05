package com.example.teste03;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesChamadoActivity extends AppCompatActivity {

    private TextView tvDetalhesDescricao;
    // Se quiser permitir resolver o chamado, descomente o botão e a lógica
    // private Button btnResolverChamado;

    private CadastroChamadoActivity.Chamado chamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        tvDetalhesDescricao = findViewById(R.id.tvDetalhesDescricao);
        // btnResolverChamado = findViewById(R.id.btnResolverChamado);

        // Pegar o index do chamado passado pelo intent
        int index = getIntent().getIntExtra("index", -1);

        if (index != -1 && index < CadastroChamadoActivity.chamados.size()) {
            chamado = CadastroChamadoActivity.chamados.get(index);

            String statusText = chamado.status ? "Resolvido" : "Em Aberto";

            tvDetalhesDescricao.setText("Categoria: " + chamado.categoria +
                    "\nLocal: " + chamado.local +
                    "\nDescrição: " + chamado.descricao +
                    "\nStatus: " + statusText);
        }

        // Caso deseje um botão para resolver o chamado, descomente o código abaixo:
        /*
        btnResolverChamado.setOnClickListener(v -> {
            if (chamado != null) {
                // Se o chamado está em aberto (status = false)
                if (!chamado.status) {
                    chamado.status = true; // marcar como resolvido
                    String statusText = "Resolvido";
                    tvDetalhesDescricao.setText("Categoria: " + chamado.categoria +
                                                "\nLocal: " + chamado.local +
                                                "\nDescrição: " + chamado.descricao +
                                                "\nStatus: " + statusText);
                    Toast.makeText(DetalhesChamadoActivity.this, "Chamado marcado como resolvido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetalhesChamadoActivity.this, "Chamado já está resolvido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
    }
}
