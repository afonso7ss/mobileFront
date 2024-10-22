package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class HistoricoChamadoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutHistorico;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_chamado);

        linearLayoutHistorico = findViewById(R.id.linearLayoutHistorico);
        btnVoltar = findViewById(R.id.btnVoltar);

        for (int i = 0; i < CadastroChamadoActivity.chamados.size(); i++) {
            CadastroChamadoActivity.Chamado chamado = CadastroChamadoActivity.chamados.get(i);

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

            if (chamado.status.equals("pendente")) {
                statusView.setBackgroundColor(Color.RED);
            } else {
                statusView.setBackgroundColor(Color.GREEN);
            }

            TextView textView = new TextView(this);
            textView.setText("Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao);
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

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
