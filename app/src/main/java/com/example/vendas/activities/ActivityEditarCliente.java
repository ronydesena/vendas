package com.example.vendas.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendas.MainActivity;
import com.example.vendas.R;
import com.example.vendas.controller.ClienteCtrl;
import com.example.vendas.dbHelper.ConexaoSQLite;
import com.example.vendas.model.Cliente;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityEditarCliente extends AppCompatActivity {

    EditText editNome;
    EditText editId;
    EditText editDataNascimento;
    RadioGroup rgSexo;
    RadioButton rbmasc;
    RadioButton rbfem;
    EditText editRg;
    EditText editCpf;
    EditText editEndereco;
    EditText editNumero;
    EditText editBairro;
    EditText editCidade;
    EditText editCep;
    EditText editEstado;
    EditText editTelefone;
    Button btnSalvarAlteracoes;

    private Cliente cliente;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_clientes);

        this.editId = findViewById(R.id.editId);
        this.editId.setKeyListener(null);
        this.editNome = findViewById(R.id.editNome);
        this.editNome.requestFocus();
        this.editDataNascimento = findViewById(R.id.editDataNascimento);
        this.rgSexo = findViewById(R.id.rgSexo);
        this.rbmasc = findViewById(R.id.rbmasc);
        this.rbfem = findViewById(R.id.rbfem);
        this.editRg = findViewById(R.id.editRg);
        this.editCpf = findViewById(R.id.editCpf);
        this.editEndereco = findViewById(R.id.editEndereco);
        this.editNumero = findViewById(R.id.editNumero);
        this.editBairro = findViewById(R.id.editBairro);
        this.editCidade = findViewById(R.id.editCidade);
        this.editCep = findViewById(R.id.editCep);
        this.editEstado = findViewById(R.id.editEstado);
        this.editTelefone = findViewById(R.id.editTelefone);
        this.btnSalvarAlteracoes = findViewById(R.id.btnSalvarAlteracoes);

        SimpleMaskFormatter smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwData = new MaskTextWatcher(editDataNascimento, smfData);
        editDataNascimento.addTextChangedListener(mtwData);

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(editCpf, smfCpf);
        editCpf.addTextChangedListener(mtwCpf);

        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(editCep, smfCep);
        editCep.addTextChangedListener(mtwCep);

        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(editTelefone, smfTelefone);
        editTelefone.addTextChangedListener(mtwTelefone);

        Bundle bundleDadosCliente = getIntent().getExtras(); //recuperando dados do bundle

        Cliente cliente = new Cliente();
        cliente.setId(bundleDadosCliente.getLong("id"));
        cliente.setNome(bundleDadosCliente.getString("nome"));
        cliente.setDataDeNascimento((bundleDadosCliente.getString("dataNascimento")));
        cliente.setSexo(bundleDadosCliente.getString("sexo"));
        cliente.setRg(bundleDadosCliente.getLong("rg"));
        cliente.setCpf(bundleDadosCliente.getLong("cpf"));
        cliente.setEndereco(bundleDadosCliente.getString("endereco"));
        cliente.setNumero(bundleDadosCliente.getInt("numero"));
        cliente.setBairro(bundleDadosCliente.getString("bairro"));
        cliente.setCidade(bundleDadosCliente.getString("cidade"));
        cliente.setCep(bundleDadosCliente.getInt("cep"));
        cliente.setEstado(bundleDadosCliente.getString("estado"));
        cliente.setTelefone(bundleDadosCliente.getLong("telefone"));

        setDadosCliente(cliente);

        btnSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cliente clienteACadastrar = getClienteFormulario();
                    ClienteCtrl clienteCtrl = new ClienteCtrl(ConexaoSQLite.getInstancia(ActivityEditarCliente.this));
                    boolean idCliente = clienteCtrl.atualizarClienteCtrl(clienteACadastrar);

                    if (idCliente == true) {
                        Toast.makeText(ActivityEditarCliente.this, "Cliente atualizado com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityEditarCliente.this, ActivityListarCliente.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ActivityEditarCliente.this, "Erro!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDadosCliente(Cliente cliente){

        this.editId.setText(String.valueOf(cliente.getId()));
        this.editNome.setText(String.valueOf(cliente.getNome()));
        this.editDataNascimento.setText(String.valueOf(cliente.getDataDeNascimento()));

        if (cliente.getSexo().equals("Feminino")) {
            this.rbfem.setChecked(true);
        }
        else if(cliente.getSexo().equals("Masculino")){
            this.rbmasc.setChecked(true);
        }

        this.editRg.setText(String.valueOf(cliente.getRg()));
        this.editCpf.setText(String.valueOf(cliente.getCpf()));
        this.editEndereco.setText(String.valueOf(cliente.getEndereco()));
        this.editNumero.setText(String.valueOf(cliente.getNumero()));
        this.editBairro.setText(String.valueOf(cliente.getBairro()));
        this.editCidade.setText(String.valueOf(cliente.getCidade()));
        this.editCep.setText(String.valueOf(cliente.getCep()));
        this.editEstado.setText(String.valueOf(cliente.getEstado()));
        this.editTelefone.setText(String.valueOf(cliente.getTelefone()));
    }

    private Cliente getClienteFormulario() throws ParseException {
        this.cliente = new Cliente();
        int selectedItemID = rgSexo.getCheckedRadioButtonId();

        if (this.editNome.getText().toString().isEmpty() == true) {
            this.editNome.setError("Este campo é obrigatório!");
            this.editNome.requestFocus();
            return null;
        }
        if (selectedItemID < 0) {
            Toast.makeText(ActivityEditarCliente.this, "Campo Sexo é obrigatório!", Toast.LENGTH_LONG).show();
            return null;
        }
        if (this.editDataNascimento.getText().toString().isEmpty() == true){
            this.editDataNascimento.setError("Este campo é obrigatório!");
            this.editDataNascimento.requestFocus();
            return null;
        } else {

            this.cliente.setId(Long.parseLong(this.editId.getText().toString()));
            this.cliente.setNome(this.editNome.getText().toString());

            if (rbmasc.isChecked()) {
                this.cliente.setSexo("Masculino");
            } else if (rbfem.isChecked()) {
                this.cliente.setSexo("Feminino");
            }

            if(!this.editDataNascimento.getText().equals("")){
                this.cliente.setDataDeNascimento(this.editDataNascimento.getText().toString().replace("/", ""));
            }else{
                this.cliente.setDataDeNascimento(null);
            }

            if(!this.editRg.getText().toString().equals("")) {
                this.cliente.setRg(Long.parseLong(this.editRg.getText().toString()));
            }

            if(!this.editCpf.getText().toString().equals("")) {
                this.cliente.setCpf(Long.parseLong(this.editCpf.getText().toString().replace("-", "").replace(".", "")));
            }

            if(!this.editEndereco.getText().toString().equals("")) {
                this.cliente.setEndereco(this.editEndereco.getText().toString());
            }else{
                this.cliente.setEndereco(null);
            }

            if(!this.editNumero.getText().toString().equals("")) {
                this.cliente.setNumero(Integer.parseInt(this.editNumero.getText().toString()));
            }

            if(!this.editBairro.getText().toString().equals("")) {
                this.cliente.setBairro(editBairro.getText().toString());
            }else{
                this.cliente.setBairro(null);
            }

            if(!editCidade.getText().toString().equals("")){
            this.cliente.setCidade(editCidade.getText().toString());
            }else{
                this.cliente.setBairro(null);
            }

            if(!this.editCep.getText().toString().equals("")) {
                this.cliente.setCep(Integer.parseInt(editCep.getText().toString().replace("-", "").replace(".", "")));
            }

            if(!this.editEstado.getText().toString().equals("")) {
                this.cliente.setEstado(this.editEstado.getText().toString());
            }else{
                this.cliente.setEstado(null);
            }

            if(!this.editTelefone.getText().toString().toString().equals("")) {
                this.cliente.setTelefone(Long.parseLong(this.editTelefone.getText().toString().replace("-", "").replace("(", "").replace(")", "")));
            }
            return cliente;
        }
    }
}