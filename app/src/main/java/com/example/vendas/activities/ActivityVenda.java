package com.example.vendas.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vendas.MainActivity;
import com.example.vendas.R;
import com.example.vendas.adapters.AdapterItensDoCarrinho;
import com.example.vendas.controller.ClienteCtrl;
import com.example.vendas.controller.ProdutoCtrl;
import com.example.vendas.controller.VendaCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;
import com.example.vendas.model.ItemDoCarrinho;
import com.example.vendas.model.Produto;
import com.example.vendas.model.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityVenda extends AppCompatActivity {

    private Spinner spnProdutos;
    private Spinner spnClientes;
    List<Produto> listaProduto;
    List<Cliente> listaCliente;
    private ProdutoCtrl produtoCtrl;
    private ClienteCtrl clienteCtrl;
    private EditText quantidadeItem;
    private TextView tvTotalVenda;

    //Carinho de comprars
    private ListView lsvCarrinhoCompras;
    private List<ItemDoCarrinho> listaItemDoCarrinhos;
    private AdapterItensDoCarrinho adapterItensDoCarrinho; //faz o o link entre a list e o listView

    //Controllers
    private VendaCtrl vendaCtrl;

    private long idCliente;
    Cliente cliente = new Cliente();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        this.vendaCtrl = new VendaCtrl(ConexaoSQLite.getInstancia(this));

        //spinner produto
        this.produtoCtrl = new ProdutoCtrl(ConexaoSQLite.getInstancia(this));
        this.listaProduto = this.produtoCtrl.getListaProdutosCtrl();
        this.spnProdutos = this.findViewById(R.id.spnProduto);
        //Não precisaremos fazer um Adapter para o spinner, pq ele é bem simples
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_spinner_item, this.listaProduto);
        this.spnProdutos.setAdapter(spnProdutoAdapter);

        //spinner cliente
        this.clienteCtrl = new ClienteCtrl(ConexaoSQLite.getInstancia(this));
        this.listaCliente = this.clienteCtrl.listarClienteCtrl();
        this.spnClientes = this.findViewById(R.id.spnCliente);
        final ArrayAdapter<Cliente> spnClienteAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, this.listaCliente);
        this.spnClientes.setAdapter(spnClienteAdapter);

        this.spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCliente = spnClienteAdapter.getItem(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.quantidadeItem = this.findViewById(R.id.edtQuantidadeProduto);
        this.tvTotalVenda = findViewById(R.id.tvTotalVenda);

        //Variaveis e objetos do carrinho de compras
        this.lsvCarrinhoCompras = this.findViewById(R.id.lsvProdutos);
        this.listaItemDoCarrinhos = new ArrayList<>();
        this.adapterItensDoCarrinho = new AdapterItensDoCarrinho(ActivityVenda.this, this.listaItemDoCarrinhos); // passei o contexto que é a activity e a lista de itens
        this.lsvCarrinhoCompras.setAdapter(this.adapterItensDoCarrinho); //link do listview com o adapter

        this.lsvCarrinhoCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int posicao, long id) {

                final ItemDoCarrinho itemDoCarrinho = (ItemDoCarrinho) adapterItensDoCarrinho.getItem(posicao);

                AlertDialog.Builder janelaEscolha = new AlertDialog.Builder(ActivityVenda.this);
                janelaEscolha.setTitle("Escolha:");
                janelaEscolha.setMessage("Deseja remover o item " + itemDoCarrinho.getNome());
                janelaEscolha.setNegativeButton("Não", null);
                janelaEscolha.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        boolean excluiu = true;

                        adapterItensDoCarrinho.removerItemDoCarrinho(posicao);

                        //Como estamos dentro da chamada de interface, não pode usar o this
                        double totalVenda = calcularTotalVendas(listaItemDoCarrinhos);
                        atualizarValorTotalVenda(totalVenda);

                        if(!excluiu){
                            Toast.makeText(ActivityVenda.this, "Erro ao exluir item " + itemDoCarrinho.getNome() + "?", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                janelaEscolha.create().show();//exibir janela
            }
        });
    }

    public void eventFecharVenda(View view) {
        Venda vendaFechada = this.criarVenda();

        if(this.salvarVenda(vendaFechada)){
            Toast.makeText(this, "Venda realizada com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Erro ao realizar venda!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(ActivityVenda.this, MainActivity.class);
        startActivity(intent);
    }

    public void eventAddProduto(View view) {

        ItemDoCarrinho itemDoCarrinho = new ItemDoCarrinho();
        Produto produtoSelecionado = (Produto) this.spnProdutos.getSelectedItem();

        int quantidadeProduto = 0;

        if (this.quantidadeItem.getText().toString().equals("")) {
            quantidadeProduto = 1;
        }else{
            quantidadeProduto = Integer.parseInt(this.quantidadeItem.getText().toString());
        }

        itemDoCarrinho.setIdProduto(produtoSelecionado.getId());
        itemDoCarrinho.setNome(produtoSelecionado.getNome());
        itemDoCarrinho.setQuantidadeSelecionada(quantidadeProduto);
        itemDoCarrinho.setPreco(produtoSelecionado.getPreco());
        itemDoCarrinho.setPrecoDoItemDaVenda(quantidadeProduto*itemDoCarrinho.getPreco());

        this.adapterItensDoCarrinho.AdicionarItemAoCarrinho(itemDoCarrinho);
        this.quantidadeItem.setText("");

        double totalVenda = this.calcularTotalVendas(this.listaItemDoCarrinhos);
        this.atualizarValorTotalVenda(totalVenda);
    }

    private double calcularTotalVendas(List<ItemDoCarrinho> pitemDoCarrinho){
        double totalVenda = 0.00d;

        for(ItemDoCarrinho itemDoCarrinho : pitemDoCarrinho){
            totalVenda += itemDoCarrinho.getPrecoDoItemDaVenda();
        }
        return totalVenda;
    }

    private void atualizarValorTotalVenda(double pValorTotal){
        this.tvTotalVenda.setText(String.valueOf(pValorTotal));
    }

    private Venda criarVenda(){
        Venda venda = new Venda();
        venda.setDataDaVenda(new Date());
        venda.setItemDaVenda(this.listaItemDoCarrinhos);
        venda.setTotalVenda(calcularTotalVendas(this.listaItemDoCarrinhos));
        venda.setIdCliente(idCliente);
        return venda;
    }

    private boolean salvarVenda(Venda pVenda){

        long idVenda = vendaCtrl.salvarVendaCtrl(pVenda);

        if(idVenda > 0){
            pVenda.setId(idVenda);
            vendaCtrl.salvarItensDaVenda(pVenda);
            return true;
        }else{
            return false;
        }
    }
}