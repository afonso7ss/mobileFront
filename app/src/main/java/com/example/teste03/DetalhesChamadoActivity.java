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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        tvDetalhesDescricao = findViewById(R.id.tvDetalhesDescricao);
        btnResolverChamado = findViewById(R.id.btnResolverChamado);

        // Pegar os detalhes do chamado (exemplo estático)
        tvDetalhesDescricao.setText("Computador não liga, verificado cabo de energia.");

        btnResolverChamado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para marcar o chamado como resolvido (dados estáticos)
                Toast.makeText(DetalhesChamadoActivity.this, "Chamado marcado como resolvido", Toast.LENGTH_SHORT).show();
                // Aqui você pode redirecionar para a tela de chamados abertos ou o histórico de chamados
                finish();
            }
        });
    }
}