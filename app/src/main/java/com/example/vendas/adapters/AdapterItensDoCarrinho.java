package com.example.vendas.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.vendas.R;
import com.example.vendas.activities.ActivityVenda;
import com.example.vendas.model.ItemDoCarrinho;
import com.example.vendas.model.Produto;

import java.util.Iterator;
import java.util.List;

public class AdapterItensDoCarrinho extends BaseAdapter {//A classe BaseAdapter que permite implementar os metodos para operações no List View

    //Contem todos os metodos para adicionar, remover, atualizar na List View
    //Os Adapter eles exibem e recuperam os itens numa View

    private Context context;//pois usaremos views
    private List<ItemDoCarrinho> itensDoCarrinho;

    public AdapterItensDoCarrinho(Context context, List<ItemDoCarrinho> itensDoCarrinho) {
        this.context = context;
        this.itensDoCarrinho= itensDoCarrinho;
    }

    @Override
    public int getCount() {
        //tamanho da lista
        return this.itensDoCarrinho.size();
    }

    @Override
    public Object getItem(int posicao) {
        //pegar um item atraves da posicao i
        return this.itensDoCarrinho.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public boolean removerItemDoCarrinho(int posicao){
        this.itensDoCarrinho.remove(posicao);
        notifyDataSetChanged();//notificar a todos os interessados que a lista mudou de tamanho e atualizar na interface
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        //Esse metodo vai pegar os elementos, converter para o java e vai conseguir atraves do settext setar o nome de uma variavel para exibir ao usuario
        View v = View.inflate(this.context, R.layout.layout_carrinho_produtos, null);//carregando o layout

        TextView tvNomeProduto = v.findViewById(R.id.tvNomeProduto);
        TextView tvPrecoProduto = v.findViewById(R.id.tvPrecoProduto);
        TextView tvQuantidadeSelecionada = v.findViewById(R.id.tvQuantidadeProduto);
        TextView tvValorItem = v.findViewById(R.id.tvValorTotalItem);

        //setando o texto para ser exibido
        tvNomeProduto.setText(this.itensDoCarrinho.get(posicao).getNome());
        tvPrecoProduto.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getPreco()));
        tvQuantidadeSelecionada.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getQuantidadeSelecionada()));
        tvValorItem.setText(String.valueOf(this.itensDoCarrinho.get(posicao).getPrecoDoItemDaVenda()));

        return v; //retornando a view e o android resolve isso com ela
    }

    public void VerificarItemAdicionado(ItemDoCarrinho pItemDoCarrinho) {
        int cont = 0;
        for(int i = 0; i < itensDoCarrinho.size(); i++){
            if (itensDoCarrinho.get(i).getIdProduto() == pItemDoCarrinho.getIdProduto()) {
                cont += 1;
                if(cont == 2) {
                    Toast.makeText(context.getApplicationContext(), "Item já adicionado", Toast.LENGTH_SHORT).show();
                    removerItemDoCarrinho(i);
                }
            }
        }
    }

    public void AdicionarItemAoCarrinho(ItemDoCarrinho pItemDoCarrinho){
        this.itensDoCarrinho.add(pItemDoCarrinho);
        VerificarItemAdicionado(pItemDoCarrinho);
        this.notifyDataSetChanged();


    }

    //atualiza a lista de produtos do adapter
   public void atualizar(List<ItemDoCarrinho> pItensDoCarrinho){
       this.itensDoCarrinho.clear(); //limpando lista antiga
       this.itensDoCarrinho = pItensDoCarrinho; //entrando com a lista nova
       this.notifyDataSetChanged(); //avisar as interfaces que os dados tem que ser atualizados
    }


}
