package com.example.vendas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vendas.activities.ActivityClientes;
import com.example.vendas.activities.ActivityListarCliente;
import com.example.vendas.activities.ActivityListarProdutos;
import com.example.vendas.activities.ActivityProduto;
import com.example.vendas.activities.ActivityVenda;
import com.example.vendas.activities.ActivityVendasConsolidadas;
import com.example.vendas.dbHelper.ConexaoSQLite;

public class MainActivity extends AppCompatActivity {

    Button btnCadastroProduto;
    Button btnListarProdutos;
    Button btnCadastroClientes;
    Button btnListarClientes;
    Button btnVender;
    Button btnMinhasVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando conexão
        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(this); //Chama esse metodo no lugar do new, pq esse metodo verifica se a classe já foi instanciada ou não

        this.btnCadastroProduto = findViewById(R.id.btnCadastroProduto);
        this.btnCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityProduto.class);
                startActivity(intent);
            }
        });

        this.btnListarProdutos = findViewById(R.id.btnListarProdutos);
        this.btnListarProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityListarProdutos.class);
                startActivity(intent);
            }
        });

        this.btnCadastroClientes = findViewById(R.id.btnCadastroClientes);
        this.btnCadastroClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityClientes.class);
                startActivity(intent);
            }
        });

        this.btnListarClientes = findViewById(R.id.btnListarClientes);
        this.btnListarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityListarCliente.class);
                startActivity(intent);
            }
        });

        this.btnVender = findViewById(R.id.btnVender);
        this.btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityVenda.class);
                startActivity(intent);
            }
        });

        this.btnMinhasVendas = findViewById(R.id.btnMinhasVendas);
        this.btnMinhasVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityVendasConsolidadas.class);
                startActivity(intent);
            }
        });
    }
}