package com.example.vendas.model;

import java.util.Date;
import java.util.List;

public class Venda {

    private long id;
    private Date dataDaVenda;
    private List<ItemDoCarrinho> itemDaVenda;
    private Long idCliente;
    private String nomeCliente;
    private double totalVenda;
    private int QteItens;


    public Venda() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemDoCarrinho> getItemDaVenda() {
        return itemDaVenda;
    }

    public void setItemDaVenda(List<ItemDoCarrinho> itemDaVenda) {
        this.itemDaVenda = itemDaVenda;
    }

    public Date getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(Date dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public int getQteItens() {
        return QteItens;
    }

    public void setQteItens(int qteItens) {
        QteItens = qteItens;
    }

}
