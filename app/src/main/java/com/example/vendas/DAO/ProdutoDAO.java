package com.example.vendas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final ConexaoSQLite conexaoSQLite;

    public ProdutoDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    //long pq ele vai retornar o id do produto
    public long salvarProduto(Produto pProduto){
        //pegue  a conexão e faca uma conexão que eu possa escrever
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try{
            //objetos que armazenam chaves e valores(campo, valor que queremos inserir)
            ContentValues values = new ContentValues();
            values.put("id", pProduto.getId());
            values.put("nome", pProduto.getNome());
            values.put("quantidade_em_estoque", pProduto.getQuantidadeEmEstoque());
            values.put("preco", pProduto.getPreco());

            long idProdutoInserido = db.insert("produto", null, values);
            return idProdutoInserido;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null) { //se estiver aberto
                db.close();
            }
        }
        return 0;//caso der algum erro, não vai inserir nenhum registro

    }

    public List<Produto> getListaProdutosDAO(){
        List<Produto> listProdutos = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor; //apontador que procura os registros

        String query = "SELECT * FROM produto;";

        try{
            db = this.conexaoSQLite.getReadableDatabase(); //instancia de leitura
            cursor = db.rawQuery(query, null); //passando a query

            //percorrendo o cursor
            if(cursor.moveToFirst()){//cursor movendo para o primeiro registro encontrado
                Produto produtoTemporario;
                do{//se tiver registro, vai ficar iterando
                    produtoTemporario = new Produto();
                    produtoTemporario.setId(cursor.getInt(0)); //passando numero do campo de acordo com que foi criado na tabela
                    produtoTemporario.setNome(cursor.getString(1));
                    produtoTemporario.setQuantidadeEmEstoque(cursor.getInt(2));
                    produtoTemporario.setPreco(cursor.getDouble(3));

                    listProdutos.add(produtoTemporario);//passando o produto a lista
                }while(cursor.moveToNext());//para se não encontrar registro
            }
        }catch (Exception e){
            Log.d("ERRO LISTAR PRODUTOS", "Erro ao listar os produtos!");
            return null; //retorna lista vaziz
        }finally {
            if(db != null){
                db.close();
            }
        }
        return listProdutos;
    }

    public boolean excluirProdutoDAO(long pIdProduto){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getWritableDatabase(); // instancia de leitura

            db.delete("produto", "id = ?", new String[]{String.valueOf(pIdProduto)});
        }catch (Exception ex){
            Log.d("PRODUTODAO", "Não foi possível deletar o produto!");
            return false;
        }finally {
            if(db != null){
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarProdutoDAO(Produto pProduto){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            ContentValues produtosAtrivutos = new ContentValues();
            produtosAtrivutos.put("nome", pProduto.getNome());
            produtosAtrivutos.put("quantidade_em_estoque", pProduto.getQuantidadeEmEstoque());
            produtosAtrivutos.put("preco", pProduto.getPreco());

           int atualizou = db.update("produto", produtosAtrivutos, "id = ?", new String[]{String.valueOf(pProduto.getId())});
            //Vai atualizar onde o id foi igual ao parametro passado em formato de vetor de strings e retorna um inteiro
            //Se for maior que 0, atualizou

            if(atualizou > 0){
                return true;
            }
        }catch (Exception e){
            Log.d("ProdutoDAO", "Não foi possível atualizar produto");
            e.printStackTrace();
            return false;
        }finally {
            if(db != null){
                db.close();
            }
        }
        return false;
    }
}
