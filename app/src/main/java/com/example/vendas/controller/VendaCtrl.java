package com.example.vendas.controller;

import com.example.vendas.DAO.VendaDAO;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Venda;

import java.util.List;

public class VendaCtrl {
    private VendaDAO vendaDAO;

    public VendaCtrl(ConexaoSQLite pConexaoSQLite){
        vendaDAO = new VendaDAO(pConexaoSQLite);
    }

    public long salvarVendaCtrl(Venda pVenda){
        return vendaDAO.salvarVendaDAO(pVenda);
    }

    public boolean salvarItensDaVenda(Venda pVenda){
        return vendaDAO.salvarItensDaVenda(pVenda);
    }

    public List<Venda> listarVendasCtrl(){
        return vendaDAO.listarVendasDAO();
    }
}
