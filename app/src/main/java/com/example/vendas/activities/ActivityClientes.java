package com.example.vendas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class ActivityClientes extends AppCompatActivity {

    EditText editNome;
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
    Button btnFinalizarCadastro;

    private Cliente cliente;
    SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
    ClienteCtrl clienteCtrl = new ClienteCtrl(ConexaoSQLite.getInstancia(this));

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        this.editNome = findViewById(R.id.editNome);
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
        this.btnFinalizarCadastro = findViewById(R.id.btnFinalizarCadastro);

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

        btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cliente clienteACadastrar = getClienteFormulario();
                    if(clienteACadastrar != null){
                        salvarCliente(clienteACadastrar);
                    }
                    } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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
            Toast.makeText(ActivityClientes.this, "Campo Sexo é obrigatório!", Toast.LENGTH_LONG).show();
            return null;
        }
        if (this.editDataNascimento.getText().toString().isEmpty() == true){
            this.editDataNascimento.setError("Este campo é obrigatório!");
            this.editDataNascimento.requestFocus();
            return null;
        } else {

            this.cliente.setNome(this.editNome.getText().toString().trim());

            if (rbmasc.isChecked()) {
                this.cliente.setSexo("Masculino");
            } else if (rbfem.isChecked()) {
                this.cliente.setSexo("Feminino");
            }

            if(!this.editDataNascimento.getText().equals("")){
                String data = (this.editDataNascimento.getText().toString());
                this.cliente.setDataDeNascimento(data);
            }else{
                this.cliente.setDataDeNascimento(null);
            }

            if(!this.editRg.getText().toString().equals("")) {
                this.cliente.setRg(Long.parseLong(this.editRg.getText().toString()));
            }

            if(!this.editCpf.getText().toString().equals("")) {
                String cpf;
                cpf = this.editCpf.getText().toString().replace("-", "").replace(".", "");

                this.cliente.setCpf(Long.parseLong(cpf));
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


                this.cliente.setEstado(this.editEstado.getText().toString().trim());


            if(!this.editTelefone.getText().toString().toString().equals("")) {
                this.cliente.setTelefone(Long.parseLong(this.editTelefone.getText().toString().replace("-", "").replace("(", "").replace(")", "")));
            }
            return cliente;
        }
    }

    private boolean salvarCliente(Cliente pCliente){

        long idCliente = clienteCtrl.salvarClienteCtrl(pCliente);

        if(idCliente > 0){
            pCliente.setId(idCliente);
            Toast.makeText(ActivityClientes.this, "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ActivityClientes.this, MainActivity.class);
            startActivity(intent);
            return true;
        }else {
            Toast.makeText(ActivityClientes.this, "Erro!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}