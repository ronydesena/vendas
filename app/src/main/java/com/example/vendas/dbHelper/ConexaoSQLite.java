package com.example.vendas.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoSQLite extends SQLiteOpenHelper { //SQLiteOpenHelper: Uma classe auxiliar para gerenciar a criação do banco de dados e o gerenciamento de versões.

    private static ConexaoSQLite INSTANCIA_CONEXAO;
    private static final int VERSAO_BD = 1;
    private static final String NOME_BD = "bl_produtos_app";

    public ConexaoSQLite(@Nullable Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    //Metodo que garante a unica instancia rodando
    public static ConexaoSQLite getInstancia(Context context){
        if(INSTANCIA_CONEXAO == null){
            INSTANCIA_CONEXAO = new ConexaoSQLite(context);
        }
        return INSTANCIA_CONEXAO;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Cria as tabelas
        String sqlTabelaProduto =
                "CREATE TABLE IF NOT EXISTS produto" +
                        "(" +
                        "id INTEGER PRIMARY KEY," +
                        "nome TEXT," +
                        "quantidade_em_estoque INTEGER," +
                        "preco REAL" +
                        ")";
        db.execSQL(sqlTabelaProduto);

        String sqlTabelaVenda =
                "CREATE TABLE IF NOT EXISTS venda" +
                        "(" +
                        "id INTEGER PRIMARY KEY," +
                        "data INTEGER," +
                        "total REAL," +
                        "id_cliente INTEGER" +
                        ")";
        db.execSQL(sqlTabelaVenda);

        String sqlTabelaItemDaVenda =
                "CREATE TABLE IF NOT EXISTS item_da_venda" +
                        "(" +
                        "id INTEGER PRIMARY KEY," +
                        "quantidade_vendida INTEGER," +
                        "id_produto INTEGER," +
                        "id_venda INTEGER" +
                        ")";
        db.execSQL(sqlTabelaItemDaVenda);

        String sqlTabelaCliente =
                "CREATE TABLE IF NOT EXISTS cliente" +
                        "(" +
                        "id INTEGER PRIMARY KEY," +
                        "nome TEXT," +
                        "dataNascimento String," +
                        "sexo TEXT," +
                        "rg INTEGER," +
                        "cpf INTEGER," +
                        "endereco TEXT," +
                        "numero INTEGER," +
                        "bairro TEXT," +
                        "cidade TEXT," +
                        "cep INTEGER," +
                        "estado TEXT," +
                        "telefone INTEGER" +
                        ")";
        db.execSQL(sqlTabelaCliente);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
