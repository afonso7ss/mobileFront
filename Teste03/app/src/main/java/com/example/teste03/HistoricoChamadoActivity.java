package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teste03.models.Chamado;

public class HistoricoChamadoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutHistorico;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_chamado);

        linearLayoutHistorico = findViewById(R.id.linearLayoutHistorico);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        populateHistorico();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateHistorico();
    }

    private void populateHistorico() {
        linearLayoutHistorico.removeAllViews(); // Limpa as views existentes

        for (int i = 0; i < CadastroChamadoActivity.chamados.size(); i++) {
            Chamado chamado = CadastroChamadoActivity.chamados.get(i);

            LinearLayout layoutChamado = new LinearLayout(this);
            layoutChamado.setOrientation(LinearLayout.HORIZONTAL);
            layoutChamado.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            layoutChamado.setPadding(16, 16, 16, 16);

            View statusView = new View(this);
            LinearLayout.LayoutParams paramsStatus = new LinearLayout.LayoutParams(50, 50);
            paramsStatus.setMargins(0, 0, 16, 0);
            statusView.setLayoutParams(paramsStatus);

            // Define a cor do status com base no booleano
            if (!chamado.isStatus()) {
                statusView.setBackgroundColor(Color.RED); // Pendente
            } else {
                statusView.setBackgroundColor(Color.GREEN); // Resolvido
            }

            TextView textView = new TextView(this);
            String status = chamado.isStatus() ? "Resolvido" : "Em Aberto";
            textView.setText("Categoria: " + chamado.getCategoria() + "\nLocal: " + chamado.getLocal() + "\nDescrição: " + chamado.getDescricao() + "\nStatus: " + status);
            textView.setTextColor(getResources().getColor(android.R.color.black));
            textView.setTextSize(16f);

            layoutChamado.addView(statusView);
            layoutChamado.addView(textView);

            final int index = i;
            layoutChamado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoricoChamadoActivity.this, DetalhesChamadoActivity.class);
                    intent.putExtra("index", index);
                    startActivity(intent);
                }
            });

            linearLayoutHistorico.addView(layoutChamado);
        }
    }
}
