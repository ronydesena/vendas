package com.example.vendas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vendas.MainActivity;
import com.example.vendas.R;
import com.example.vendas.controller.ProdutoCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Produto;

public class ActivityEditarProdutos extends AppCompatActivity {

    private EditText editIdProduto;
    private EditText edtNomeProduto;
    private EditText edtQuantidadeProduto;
    private EditText edtPrecoProduto;
    private Button btnSalvarAlteracoesProduto;

    Produto produto = new Produto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produtos);

        editIdProduto = findViewById(R.id.editIdProduto);
        edtNomeProduto = findViewById(R.id.edtNomeProduto);
        edtQuantidadeProduto = findViewById(R.id.edtQuantidadeProduto);
        edtPrecoProduto = findViewById(R.id.edtPrecoProduto);

        Bundle bundleDadosProdutos = getIntent().getExtras(); //recuperando dados do bundle

        Produto produto = new Produto();
        produto.setId(bundleDadosProdutos.getInt("id_produto"));
        produto.setNome(bundleDadosProdutos.getString("nome_produto"));
        produto.setPreco(bundleDadosProdutos.getDouble("preco_produto"));
        produto.setQuantidadeEmEstoque(bundleDadosProdutos.getInt("estoque_produto"));

        setDadosProduto(produto);

        btnSalvarAlteracoesProduto = findViewById(R.id.btnSalvarProduto);
        btnSalvarAlteracoesProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Produto produtoAAtualizar = getDadosProdutoFormulario();

                if(produtoAAtualizar != null){
                    ProdutoCtrl produtoCtrl = new ProdutoCtrl(ConexaoSQLite.getInstancia(ActivityEditarProdutos.this));
                    boolean idProduto = produtoCtrl.atualizarProdutoDAO(produtoAAtualizar);

                    if(idProduto){
                        Toast.makeText(ActivityEditarProdutos.this, "Produto atualizado com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityEditarProdutos.this, ActivityListarProdutos.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ActivityEditarProdutos.this, "Erro!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ActivityEditarProdutos.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setDadosProduto(Produto produto){
        this.editIdProduto.setText(String.valueOf(produto.getId()));
        this.edtNomeProduto.setText(String.valueOf(produto.getNome()));
        this.edtPrecoProduto.setText(String.valueOf(produto.getPreco()));
        this.edtQuantidadeProduto.setText(String.valueOf(produto.getQuantidadeEmEstoque()));
    }

    private Produto getDadosProdutoFormulario() {
        this.produto = new Produto();

        if (this.editIdProduto.getText().toString().isEmpty() == false) { //isEmpty () da string java verifica se esta string está vazia ou não
            this.produto.setId(Integer.parseInt(this.editIdProduto.getText().toString()));
        }else{
            return null;
        }

        if (this.edtNomeProduto.getText().toString().isEmpty() == false) { //isEmpty () da string java verifica se esta string está vazia ou não
            this.produto.setNome(this.edtNomeProduto.getText().toString());
        }else{
            return null;
        }

        if (this.edtQuantidadeProduto.getText().toString().isEmpty() == false) { //isEmpty () da string java verifica se esta string está vazia ou não. Retorna true , se o comprimento da string for 0
            int quantidadeProduto = Integer.parseInt(this.edtQuantidadeProduto.getText().toString());
            this.produto.setQuantidadeEmEstoque(quantidadeProduto);
        }else{
            return null;
        }

        if(this.edtPrecoProduto.getText().toString().isEmpty() == false){
            double preco = Double.parseDouble(this.edtPrecoProduto.getText().toString());
            this.produto.setPreco(preco);
        }else{
            return null;
        }

        return produto;
    }
}