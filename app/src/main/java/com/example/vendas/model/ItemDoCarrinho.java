package com.example.vendas.model;

public class ItemDoCarrinho extends Produto {
    //Essa classe foi criada para poder armanezar os itens da venda para poder jogar no Adapter
    private double precoDoItemDaVenda;
    private int quantidadeSelecionada;
    private long idProduto;

    public double getPrecoDoItemDaVenda() {
        return precoDoItemDaVenda;
    }

    public void setPrecoDoItemDaVenda(double precoUnitario) {
        this.precoDoItemDaVenda = precoUnitario;
    }

    public int getQuantidadeSelecionada() {
        return quantidadeSelecionada;
    }

    public void setQuantidadeSelecionada(int quantidadeSelecionada) {
        this.quantidadeSelecionada = quantidadeSelecionada;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }
}
