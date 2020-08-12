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
import com.example.vendas.model.Venda;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterListaDasVendas extends BaseAdapter {//A classe BaseAdapter que permite implementar os metodos para operações no List View

    //Contem todos os metodos para adicionar, remover, atualizar na List View
    //Os Adapter eles exibem e recuperam os itens numa View

    private Context context;//pois usaremos views
    private List<Venda> vendaList;
    private SimpleDateFormat simpleDateFormat;

    public AdapterListaDasVendas(Context context, List<Venda> vendaList) {
        this.context = context;
        this.vendaList = vendaList;
        this.simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public int getCount() {
        //tamanho da lista
        //esse return está retornando erro
        return vendaList.size();
    }

    @Override
    public Object getItem(int posicao) {
        //pegar um item atraves da posicao i
        return this.vendaList.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public void removerVenda(int posicao){
        this.vendaList.remove(posicao);
        notifyDataSetChanged();//notificar a todos os interessados que a lista mudou de tamanho e atualizar na interface
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        //Esse metodo vai pegar os elementos, converter para o java e vai conseguir atraves do settext setar o nome de uma variavel para exibir ao usuario
        View v = View.inflate(this.context, R.layout.layout_minhas_vendas, null);//carregando o layout

        TextView tvDataVenda = v.findViewById(R.id.tvDataVenda);
        TextView tvPrecoTotal = v.findViewById(R.id.tvTotalVenda);
        TextView tvQuantidadeItens = v.findViewById(R.id.tvQuantidadeItens);
        TextView tvCliente = v.findViewById(R.id.tvCliente);

        //setando o texto para ser exibido
        tvDataVenda.setText(this.simpleDateFormat.format(this.vendaList.get(posicao).getDataDaVenda()));
        tvPrecoTotal.setText(String.valueOf(this.vendaList.get(posicao).getTotalVenda()));
        tvQuantidadeItens.setText(String.valueOf(this.vendaList.get(posicao).getQteItens()));
        tvCliente.setText(String.valueOf(this.vendaList.get(posicao).getNomeCliente()));

        return v; //retornando a view e o android resolve isso com ela
    }

    //atualiza a lista de produtos do adapter
    public void atualizar(List<Venda> pProdutos){
        this.vendaList.clear(); //limpando lista antiga
        this.vendaList = pProdutos; //entrando com a lista nova
        this.notifyDataSetChanged(); //avisar as interfaces que os dados tem que ser atualizados
    }


}
