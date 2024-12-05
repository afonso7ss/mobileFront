package com.example.teste03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesChamadoActivity extends AppCompatActivity {

    private TextView tvDetalhesDescricao;
    private Button btnResolverChamado;
    private CadastroChamadoActivity.Chamado chamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        tvDetalhesDescricao = findViewById(R.id.tvDetalhesDescricao);
//        btnResolverChamado = findViewById(R.id.btnResolverChamado);

        // Pegar o index do chamado passado pelo intent
        int index = getIntent().getIntExtra("index", -1);
        if (index != -1) {
            chamado = CadastroChamadoActivity.chamados.get(index);
            tvDetalhesDescricao.setText("Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao + "\nStatus: " + (chamado.status.equals("pendente") ? "Em Aberto" : "Resolvido"));
        }

//        btnResolverChamado.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Lógica para marcar o chamado como resolvido
//                if (chamado != null && chamado.status.equals("pendente")) {
//                    chamado.status = "resolvido";
//                    tvDetalhesDescricao.setText("Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao + "\nStatus: Resolvido");
//                    Toast.makeText(DetalhesChamadoActivity.this, "Chamado marcado como resolvido", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(DetalhesChamadoActivity.this, "Chamado já está resolvido", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
