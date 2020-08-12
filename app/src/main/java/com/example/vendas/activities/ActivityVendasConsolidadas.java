package com.example.vendas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vendas.R;
import com.example.vendas.adapters.AdapterListaDasVendas;
import com.example.vendas.controller.VendaCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Venda;

import java.util.List;

public class ActivityVendasConsolidadas extends AppCompatActivity {

    private ListView lsvVendas;
    private List<Venda> listadeVendasFeitas;
    private AdapterListaDasVendas adapterListaDasVendas;
    private VendaCtrl vendaCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_consolidadas);

        this.vendaCtrl = new VendaCtrl(ConexaoSQLite.getInstancia(ActivityVendasConsolidadas.this));

        listadeVendasFeitas = this.vendaCtrl.listarVendasCtrl();

        this.lsvVendas = (ListView) findViewById(R.id.lsvMinhasVendas);

        this.adapterListaDasVendas = new AdapterListaDasVendas(ActivityVendasConsolidadas.this, listadeVendasFeitas);

        this.lsvVendas.setAdapter(this.adapterListaDasVendas); //lincando adapter com o listView
    }
}