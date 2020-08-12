package com.example.vendas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;
import com.example.vendas.model.ItemDoCarrinho;
import com.example.vendas.model.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDAO {

    private final ConexaoSQLite conexaoSQLite;

    public VendaDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarVendaDAO(Venda pVenda) {
        SQLiteDatabase db =  conexaoSQLite.getWritableDatabase(); //Criando instancia da conexão SQLite, do banco, diferente do de cima

        try{
            ContentValues values = new ContentValues(); //criando lista com os dados
            Cliente cliente = new Cliente();
            values.put("data", pVenda.getDataDaVenda().getTime());
            values.put("total", pVenda.getTotalVenda());
            values.put("id_cliente", pVenda.getIdCliente());

            long idVendaInserido = db.insert("venda", null, values);
            return idVendaInserido;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null) {
                db.close();
            }
        }
        return 0; //quer dizer que não salvou
    }

    public boolean salvarItensDaVenda(Venda pVenda){
        SQLiteDatabase db =  conexaoSQLite.getWritableDatabase();

        try{
            ContentValues values = null;//criando lista com os dados
            for(ItemDoCarrinho itemDaVenda : pVenda.getItemDaVenda()) {
                values = new ContentValues();
                values.put("quantidade_vendida", itemDaVenda.getQuantidadeSelecionada());
                values.put("id_produto", itemDaVenda.getIdProduto());
                values.put("id_venda", pVenda.getId());
                //faltou precoitemdavenda,
                db.insert("item_da_venda", null, values);
            }
            long idVendaInserido = db.insert("venda", null, values);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<Venda> listarVendasDAO() {
        List<Venda> listaVendas = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor; //varrer registros

        String query =
                "SELECT" +
                        " venda.id," +
                        " venda.data," +
                        " venda.total," +
                        " SUM(item_da_venda.quantidade_vendida)," +
                        " cliente.nome" +
                        " FROM venda" +
                        " INNER JOIN item_da_venda ON (venda.id  = item_da_venda.id_venda)" +
                        " INNER JOIN produto ON (item_da_venda.id_produto = produto.id)" +
                        " INNER JOIN cliente ON (cliente.id = venda.id_cliente)" +
                        " GROUP BY venda.id;";
        try{
            db = this.conexaoSQLite.getReadableDatabase(); //somente ler o banco

            cursor = db.rawQuery(query, null); //faz uma consulta sql padrao

            if(cursor.moveToFirst()){//se o cursor conseguir mover para o elemento de primeira posicao
                Venda vendaTemp;
                do{
                    vendaTemp = new Venda();
                    vendaTemp.setId(cursor.getLong(0));
                    vendaTemp.setDataDaVenda(new Date(cursor.getLong(1)));
                    vendaTemp.setTotalVenda(cursor.getDouble(2));
                    vendaTemp.setQteItens(cursor.getInt(3));
                    vendaTemp.setNomeCliente(cursor.getString(4));

                    listaVendas.add(vendaTemp);

                }while(cursor.moveToNext()); //ele continua movendo para o proximo
            }
        }catch (Exception e){
            Log.d("ERRO VENDAS", "Erro ao retornar as vendas");
            return null;
        }finally {
            if(db != null) {
                db.close();
            }
        }
        return listaVendas;
    }
}
