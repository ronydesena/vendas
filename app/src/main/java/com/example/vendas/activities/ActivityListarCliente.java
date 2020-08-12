package com.example.vendas.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vendas.R;
import com.example.vendas.adapters.AdapterListaClientes;
import com.example.vendas.adapters.AdapterListaProdutos;
import com.example.vendas.controller.ClienteCtrl;
import com.example.vendas.controller.ProdutoCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;
import com.example.vendas.model.Produto;

import java.util.Date;
import java.util.List;

public class ActivityListarCliente extends AppCompatActivity {

    private ListView lsvCliente;
    private List<Cliente> clienteList;
    private AdapterListaClientes adapterListaClientes; //patra fazer a ligacao entre todos os elementos
    private ClienteCtrl clienteCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        //buscar os produtos do banco
        this.clienteCtrl = new ClienteCtrl(ConexaoSQLite.getInstancia(ActivityListarCliente.this));

        clienteList = clienteCtrl.listarClienteCtrl();

        this.lsvCliente = (ListView) findViewById(R.id.listClientes); //ligando o listView

        this.adapterListaClientes = new AdapterListaClientes(ActivityListarCliente.this, this.clienteList);

        this.lsvCliente.setAdapter(this.adapterListaClientes);


        this.lsvCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int posicao, long id) {
                final Cliente clienteSelecionado = (Cliente) adapterListaClientes.getItem(posicao);

                AlertDialog.Builder janelaDeEscolha = new AlertDialog.Builder(ActivityListarCliente.this);
                janelaDeEscolha.setTitle("Escolha: ");
                janelaDeEscolha.setMessage("O que deseja fazer com o cliente selecionado?");
                janelaDeEscolha.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });

                janelaDeEscolha.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        boolean excluiu = clienteCtrl.excluirClienteCtrl((int) clienteSelecionado.getId());
                        dialogInterface.cancel(); //fechando dialogo

                        if(excluiu == true){
                            Toast.makeText(ActivityListarCliente.this, "Cliente excluído com sucesso!", Toast.LENGTH_LONG).show();
                            adapterListaClientes.removeCliente(posicao);
                        }else{
                            Toast.makeText(ActivityListarCliente.this, "Erro ao excluir produto!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEscolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundleDadosCliente = new Bundle();//é um pacote que contem os dados e consegue viajar entre as interfaces do android
                        bundleDadosCliente.putLong("id", clienteSelecionado.getId());
                        bundleDadosCliente.putString("nome", clienteSelecionado.getNome());
                        bundleDadosCliente.putString("sexo", clienteSelecionado.getSexo());
                        bundleDadosCliente.putString("dataNascimento", (clienteSelecionado.getDataDeNascimento()));
                        bundleDadosCliente.putLong("rg", clienteSelecionado.getRg());
                        bundleDadosCliente.putLong("cpf", clienteSelecionado.getCpf());
                        bundleDadosCliente.putString("endereco", clienteSelecionado.getEndereco());
                        bundleDadosCliente.putInt("numero", clienteSelecionado.getNumero());
                        bundleDadosCliente.putString("bairro", clienteSelecionado.getBairro());
                        bundleDadosCliente.putString("cidade", clienteSelecionado.getCidade());
                        bundleDadosCliente.putInt("cep", clienteSelecionado.getCep());
                        bundleDadosCliente.putString("estado", clienteSelecionado.getEstado());
                        bundleDadosCliente.putLong("telefone", clienteSelecionado.getTelefone());

                        Intent intent = new Intent(ActivityListarCliente.this, ActivityEditarCliente.class);
                        intent.putExtras(bundleDadosCliente);
                        startActivity(intent);
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