package com.example.vendas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final ConexaoSQLite conexaoSQLite;


    public ClienteDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarCliente(Cliente pCliente){
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put("nome ", pCliente.getNome());
            contentValues.put("sexo", pCliente.getSexo());
            contentValues.put("dataNascimento", pCliente.getDataDeNascimento());
            contentValues.put("rg", pCliente.getRg());
            contentValues.put("cpf", pCliente.getCpf());
            contentValues.put("endereco", pCliente.getEndereco());
            contentValues.put("numero", pCliente.getNumero());
            contentValues.put("bairro", pCliente.getBairro());
            contentValues.put("cidade", pCliente.getCidade());
            contentValues.put("cep", pCliente.getCep());
            contentValues.put("estado", pCliente.getEstado());
            contentValues.put("telefone", pCliente.getTelefone());

            long idClienteInserido = db.insert("cliente", null, contentValues);
            return idClienteInserido;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null){
                db.close();
            }
        }
        return 0;
    }

    public List<Cliente> getListaClientesDAO(){
        List<Cliente> listClientes = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor;

        String query = "SELECT * FROM cliente;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();
            cursor = db.rawQuery(query, null);

                if(cursor.moveToFirst()) {
                Cliente clienteTemporario;
                do {
                    clienteTemporario = new Cliente();
                    clienteTemporario.setId(cursor.getInt(0));
                    clienteTemporario.setNome(cursor.getString(1));
                    clienteTemporario.setDataDeNascimento(cursor.getString(2));
                    clienteTemporario.setSexo(cursor.getString(3));
                    clienteTemporario.setRg(cursor.getLong(4));
                    clienteTemporario.setCpf(cursor.getLong(5));
                    clienteTemporario.setEndereco(cursor.getString(6));
                    clienteTemporario.setNumero(cursor.getInt(7));
                    clienteTemporario.setBairro(cursor.getString(8));
                    clienteTemporario.setCidade(cursor.getString(9));
                    clienteTemporario.setCep(cursor.getInt(10));
                    clienteTemporario.setEstado(cursor.getString(11));
                    clienteTemporario.setTelefone(cursor.getLong(12));

                    listClientes.add(clienteTemporario);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTAR CLIENTES", "Erro ao listar clientes!");
        }finally {
            if(db != null){
                db.close();
            }
        }
        return listClientes;
    }

    public boolean excluirCliente(int pIdCliente){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getWritableDatabase(); // instancia de leitura
            db.delete("cliente", "id = ?", new String[]{String.valueOf(pIdCliente)});

        }catch (Exception e){
            Log.d("ERRO EXLUIR CLIENTE", "Erro ao excluir clientes!");
            return false;
        }finally {
            if(db != null){
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarCliente(Cliente pCliente){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", pCliente.getId());
            contentValues.put("nome", pCliente.getNome());
            contentValues.put("sexo", pCliente.getSexo());
            contentValues.put("dataNascimento", pCliente.getDataDeNascimento());
            contentValues.put("rg", pCliente.getRg());
            contentValues.put("cpf", pCliente.getCpf());
            contentValues.put("endereco", pCliente.getEndereco());
            contentValues.put("numero", pCliente.getNumero());
            contentValues.put("bairro", pCliente.getBairro());
            contentValues.put("cidade", pCliente.getCidade());
            contentValues.put("cep", pCliente.getCep());
            contentValues.put("estado", pCliente.getEstado());
            contentValues.put("telefone", pCliente.getTelefone());

            int atualizou = db.update("cliente", contentValues, "id = ?", new String[]{String.valueOf(pCliente.getId())});

            if(atualizou > 0){
                return true;
            }

        }catch (Exception e){
            Log.d("ERRO ATUALIZAR CLIENTE", "Erro ao atualizar dados do cliente!");
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
