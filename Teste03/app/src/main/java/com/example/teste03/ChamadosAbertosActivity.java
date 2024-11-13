package com.example.teste03;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.teste03.models.Chamado;

public class ChamadosAbertosActivity extends AppCompatActivity {

    private ListView listChamadosAbertos;
    private ArrayAdapter<String> adapter;
    private ArrayList<Chamado> chamados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamados_abertos);

        listChamadosAbertos = findViewById(R.id.listChamadosAbertos);

        chamados = CadastroChamadoActivity.chamados;
        updateListView();

        // Define um listener para marcar o chamado como resolvido
        listChamadosAbertos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Atualiza o status do chamado selecionado para resolvido
                Chamado chamado = chamados.get(position);
                chamado.setStatus(true); // Define como "resolvido"

                // Atualiza a lista para refletir a mudança de status
                updateListView();
            }
        });
    }

    private void updateListView() {
        ArrayList<String> chamadosStringList = new ArrayList<>();

        for (Chamado chamado : chamados) {
            String status = chamado.isStatus() ? "resolvido" : "pendente"; // Define o status como "resolvido" ou "pendente"
            String chamadoString = "Categoria: " + chamado.getCategoria() + "\nLocal: " + chamado.getLocal() + "\nDescrição: " + chamado.getDescricao() + "\nStatus: " + status;
            chamadosStringList.add(chamadoString);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chamadosStringList);
        listChamadosAbertos.setAdapter(adapter);
    }
}
