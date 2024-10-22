package com.example.teste03;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ChamadosAbertosActivity extends AppCompatActivity {

    private ListView listChamadosAbertos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamados_abertos);

        listChamadosAbertos = findViewById(R.id.listChamadosAbertos);

        // Obtendo os chamados cadastrados da classe CadastroChamadoActivity
        ArrayList<CadastroChamadoActivity.Chamado> chamados = CadastroChamadoActivity.chamados;
        ArrayList<String> chamadosStringList = new ArrayList<>();

        // Convertendo a lista de objetos Chamado para uma lista de strings
        for (CadastroChamadoActivity.Chamado chamado : chamados) {
            String chamadoString = "Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao + "\nStatus: " + chamado.status;
            chamadosStringList.add(chamadoString);
        }

        // Configurando o adapter para exibir a lista de chamados
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chamadosStringList);
        listChamadosAbertos.setAdapter(adapter);
    }
}
