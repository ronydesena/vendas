package com.example.vendas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vendas.MainActivity;
import com.example.vendas.R;
import com.example.vendas.controller.ProdutoCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Produto;
import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ActivityProduto extends AppCompatActivity {

    private EditText editIdProduto; //codigo de barra
    private EditText edtNomeProduto;
    private EditText edtQuantidadeProduto;
    private EditText edtPrecoProduto;
    private Button btnSalvarProduto;

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        //View é qualquer componente visual do Android

        editIdProduto = findViewById(R.id.editIdProduto);
        edtNomeProduto = findViewById(R.id.edtNomeProduto);
        edtQuantidadeProduto = findViewById(R.id.edtQuantidadeProduto);
        edtPrecoProduto = findViewById(R.id.edtPrecoProduto);
        btnSalvarProduto = findViewById(R.id.btnSalvarProduto);
        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Produto produtoACadastrar = getDadosProdutoFormulario();
                if(produtoACadastrar != null){
                    ProdutoCtrl produtoCtrl = new ProdutoCtrl(ConexaoSQLite.getInstancia(ActivityProduto.this));
                    long idProduto = produtoCtrl.salvarProdutoCtrl(produtoACadastrar);

                    if(idProduto > 0){
                        Toast.makeText(ActivityProduto.this, "Produto salvo com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityProduto.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ActivityProduto.this, "Erro!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ActivityProduto.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                }
            }
        });

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