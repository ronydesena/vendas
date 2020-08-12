package com.example.vendas.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vendas.R;
import com.example.vendas.adapters.AdapterListaProdutos;
import com.example.vendas.controller.ProdutoCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ActivityListarProdutos extends AppCompatActivity {

    private ListView lsvProdutos;
    private List<Produto> produtoList;
    private AdapterListaProdutos adapterListaProdutos; //patra fazer a ligacao entre todos os elementos
    private ProdutoCtrl produtoCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);

        //buscar os produtos do banco
        this.produtoCtrl = new ProdutoCtrl(ConexaoSQLite.getInstancia(ActivityListarProdutos.this));

        produtoList = produtoCtrl.getListaProdutosCtrl();

        this.lsvProdutos = (ListView) findViewById(R.id.listProdutos); //ligando o listView

        this.adapterListaProdutos = new AdapterListaProdutos(ActivityListarProdutos.this, this.produtoList);

        this.lsvProdutos.setAdapter(this.adapterListaProdutos);

        this.lsvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int posicao, long id) {
                final Produto produtoSelecionado = (Produto) adapterListaProdutos.getItem(posicao);

                AlertDialog.Builder janelaDeEscolha = new AlertDialog.Builder(ActivityListarProdutos.this);
                janelaDeEscolha.setTitle("Escolha: ");
                janelaDeEscolha.setMessage("O que deseja fazer com o produto selecionado?");
                janelaDeEscolha.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });

                janelaDeEscolha.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        boolean excluiu = produtoCtrl.excluirProdutoCtrl(produtoSelecionado.getId());
                        dialogInterface.cancel(); //fechando dialogo

                        if(excluiu == true){
                            Toast.makeText(ActivityListarProdutos.this, "Produto excluído com sucesso!", Toast.LENGTH_LONG).show();
                            adapterListaProdutos.removerProduto(posicao);
                        }else{
                            Toast.makeText(ActivityListarProdutos.this, "Erro ao excluir produto!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEscolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundleDadosProduto = new Bundle();//é um pacote que contem os dados e consegue viajar entre as interfaces do android
                        bundleDadosProduto.putInt("id_produto", produtoSelecionado.getId());
                        bundleDadosProduto.putString("nome_produto", produtoSelecionado.getNome());
                        bundleDadosProduto.putDouble("preco_produto", produtoSelecionado.getPreco());
                        bundleDadosProduto.putInt("estoque_produto", produtoSelecionado.getQuantidadeEmEstoque());

                        Intent intentEditarProdutos = new Intent(ActivityListarProdutos.this, ActivityEditarProdutos.class);
                        intentEditarProdutos.putExtras(bundleDadosProduto);
                        startActivity(intentEditarProdutos);
                    }
                });
                janelaDeEscolha.create().show();
            }
        });
    }

    //Executa evento de click do botaoAtualizar
//    public void eventAtualizarProdutos(View view){
//        this.adapterListaProdutos.atualizar(this.produtoCtrl.getListaProdutosCtrl());
//    }
}