package com.example.vendas.controller;

import com.example.vendas.DAO.ClienteDAO;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;

import java.util.List;

public class ClienteCtrl {

    private final ClienteDAO clienteDAO;

    public ClienteCtrl(ConexaoSQLite pConexaoSQLite) {
        this.clienteDAO = new ClienteDAO(pConexaoSQLite);
    }

    public long salvarClienteCtrl(Cliente pCliente){
        return clienteDAO.salvarCliente(pCliente);
    }

    public List<Cliente> listarClienteCtrl(){
        return clienteDAO.getListaClientesDAO();
    }

    public boolean excluirClienteCtrl(int pIdCliente){
        return clienteDAO.excluirCliente(pIdCliente);
    }

    public boolean atualizarClienteCtrl(Cliente pCliente){
        return clienteDAO.atualizarCliente(pCliente);
    }
}
