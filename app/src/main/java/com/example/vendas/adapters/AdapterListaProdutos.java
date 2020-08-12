package com.example.vendas.adapters;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.vendas.R;
import com.example.vendas.model.Produto;

import java.util.List;

public class AdapterListaProdutos extends BaseAdapter {//A classe BaseAdapter que permite implementar os metodos para operações no List View

    //Contem todos os metodos para adicionar, remover, atualizar na List View
    //Os Adapter eles exibem e recuperam os itens numa View

    private Context context;//pois usaremos views
    private List<Produto> produtosList;

    public AdapterListaProdutos(Context context, List<Produto> produtosList) {
        this.context = context;
        this.produtosList = produtosList;
    }

    @Override
    public int getCount() {
        //tamanho da lista
        return this.produtosList.size();
    }

    @Override
    public Object getItem(int posicao) {
        //pegar um item atraves da posicao i
        return this.produtosList.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public void removerProduto(int posicao){
        this.produtosList.remove(posicao);
        notifyDataSetChanged();//notificar a todos os interessados que a lista mudou de tamanho e atualizar na interface
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        //Esse metodo vai pegar os elementos, converter para o java e vai conseguir atraves do settext setar o nome de uma variavel para exibir ao usuario
        View v = View.inflate(this.context, R.layout.layout_produto, null);//carregando o layout

        TextView tvNomeProduto = v.findViewById(R.id.tvNomeProduto);
        TextView tvCodigoDeBarras = v.findViewById(R.id.tvCodigoDeBarras);
        TextView tvPrecoProduto = v.findViewById(R.id.tvPrecoProduto);
        TextView tvEstoqueProduto = v.findViewById(R.id.tvEstoqueProduto);

        //setando o texto para ser exibido
        tvNomeProduto.setText(this.produtosList.get(posicao).getNome());
        tvCodigoDeBarras.setText(String.valueOf(this.produtosList.get(posicao).getId()));
        tvPrecoProduto.setText(String.valueOf(this.produtosList.get(posicao).getPreco()));
        tvEstoqueProduto.setText(String.valueOf(this.produtosList.get(posicao).getQuantidadeEmEstoque()));

        return v; //retornando a view e o android resolve isso com ela
    }

    //atualiza a lista de produtos do adapter
//    public void atualizar(List<Produto> pProdutos){
//        this.produtosList.clear(); //limpando lista antiga
//        this.produtosList = pProdutos; //entrando com a lista nova
//        this.notifyDataSetChanged(); //avisar as interfaces que os dados tem que ser atualizados
//    }


}
