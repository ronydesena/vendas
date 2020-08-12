package com.example.vendas.controller;

import com.example.vendas.DAO.ProdutoDAO;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Produto;

import java.util.List;

//Interface entre view e dao
public class ProdutoCtrl {

    private final ProdutoDAO produtoDAO;

    public ProdutoCtrl(ConexaoSQLite pConexaoSQLite) {
        produtoDAO = new ProdutoDAO(pConexaoSQLite);
    }

    //como instanciamos o produto, podemos chamar qualquer metodo da camada de baixo
    public long salvarProdutoCtrl(Produto pProduto){
        return this.produtoDAO.salvarProduto(pProduto);
    }

    public List<Produto> getListaProdutosCtrl(){
        return this.produtoDAO.getListaProdutosDAO();
    }

    public boolean excluirProdutoCtrl(long pIdProduto){
        return this.produtoDAO.excluirProdutoDAO(pIdProduto);
    }

    public boolean atualizarProdutoDAO(Produto pProduto){
        return this.produtoDAO.atualizarProdutoDAO(pProduto);
    }

}
