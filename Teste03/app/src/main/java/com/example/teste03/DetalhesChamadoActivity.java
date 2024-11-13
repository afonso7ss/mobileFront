package com.example.teste03;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teste03.models.Chamado;

public class DetalhesChamadoActivity extends AppCompatActivity {

    private TextView DetalhesDescricao;
    private Chamado chamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        DetalhesDescricao = findViewById(R.id.tvDetalhesDescricao);

        int index = getIntent().getIntExtra("index", -1);
        if (index != -1) {
            chamado = CadastroChamadoActivity.chamados.get(index);
            DetalhesDescricao.setText("Categoria: " + chamado.getCategoria() + "\nLocal: " + chamado.getLocal() + "\nDescrição: " + chamado.getDescricao() + "\nStatus: " + (chamado.isStatus() ? "Resolvido" : "Em Aberto"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza o status em caso de alteração
        int index = getIntent().getIntExtra("index", -1);
        if (index != -1) {
            chamado = CadastroChamadoActivity.chamados.get(index);
            DetalhesDescricao.setText("Categoria: " + chamado.getCategoria() + "\nLocal: " + chamado.getLocal() + "\nDescrição: " + chamado.getDescricao() + "\nStatus: " + (chamado.isStatus() ? "Resolvido" : "Em Aberto"));
        }
    }
}
