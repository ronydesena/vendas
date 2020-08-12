package com.example.vendas.adapters;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.vendas.R;
import com.example.vendas.model.Cliente;
import com.example.vendas.model.Produto;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterListaClientes extends BaseAdapter {//A classe BaseAdapter que permite implementar os metodos para operações no List View

    //Contem todos os metodos para adicionar, remover, atualizar na List View
    //Os Adapter eles exibem e recuperam os itens numa View

    private Context context;//pois usaremos views
    private List<Cliente> clientesList;
    private SimpleMaskFormatter smfTelefone;
    private SimpleMaskFormatter smfData;

    public AdapterListaClientes(Context context, List<Cliente> clientesList) {
        this.context = context;
        this.clientesList = clientesList;
        this.smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        this.smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");

    }

    @Override
    public int getCount() {
        //tamanho da lista
        return this.clientesList.size();
    }

    @Override
    public Object getItem(int posicao) {
        //pegar um item atraves da posicao i
        return this.clientesList.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public void removerCliente(int posicao){
        this.clientesList.remove(posicao);
        notifyDataSetChanged();//notificar a todos os interessados que a lista mudou de tamanho e atualizar na interface
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        //Esse metodo vai pegar os elementos, converter para o java e vai conseguir atraves do settext setar o nome de uma variavel para exibir ao usuario
        View v = View.inflate(this.context, R.layout.layout_cliente, null);//carregando o layout

        TextView tvNomeCliente = v.findViewById(R.id.tvNomeCliente);
        TextView tvSexo = v.findViewById(R.id.tvSexo);
        TextView tvDataNascimento = v.findViewById(R.id.tvDataNascimento);
        TextView tvTelefone = v.findViewById(R.id.tvTelefone);

        //setando o texto para ser exibido
        tvNomeCliente.setText(this.clientesList.get(posicao).getNome());
        tvSexo.setText(this.clientesList.get(posicao).getSexo());
        tvDataNascimento.setText(smfData.format(this.clientesList.get(posicao).getDataDeNascimento()));
        tvTelefone.setText(smfTelefone.format(String.valueOf(this.clientesList.get(posicao).getTelefone())));

        return v; //retornando a view e o android resolve isso com ela
    }

    //atualiza a lista de produtos do adapter
    public void atualizar(List<Cliente> pCliente){
        this.clientesList.clear(); //limpando lista antiga
        this.clientesList = pCliente; //entrando com a lista nova
       this.notifyDataSetChanged(); //avisar as interfaces que os dados tem que ser atualizados
   }

    public boolean removeCliente(int posicao){
        this.clientesList.remove(posicao);
        notifyDataSetChanged();//notificar a todos os interessados que a lista mudou de tamanho e atualizar na interface
        return true;
    }


}
