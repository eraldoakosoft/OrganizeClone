package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.helper.DateUtil;
import com.example.organizze.model.Movimentacao;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DespesasActivity extends AppCompatActivity {


    private TextInputEditText campoDescricaoD, campoCategoriaD;
    private TextView campoDataD;
    private EditText campoValorD;
    //private FloatingActionButton btnSalvarD;
    private Movimentacao movimentacao;

    //Configurações para o calendario
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_despesas);

        campoCategoriaD = findViewById(R.id.editTextInputDespesaCategoria);
        campoDataD = findViewById(R.id.editTextDespesaData);
        campoValorD = findViewById(R.id.editTextDespesaValor);
        campoDescricaoD = findViewById(R.id.editTextDespesaDescricao);


        //campoDataD.setText(DateUtil.dataAtual());
        campoDataD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new android.app.DatePickerDialog(DespesasActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        campoDataD.setText(formatarData(dayOfMonth,month, year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano,mes,dia);
                datePickerDialog.show();
            }
        });


    }


    public  void salvarrrr(View view){
        if (validarCampos()){
            movimentacao = new Movimentacao();
            String data = campoDataD.getText().toString();
            movimentacao.setValor(Double.parseDouble(campoValorD.getText().toString()));
            movimentacao.setCategoria(campoCategoriaD.getText().toString());
            movimentacao.setDescricao(campoDescricaoD.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");
            movimentacao.salvar(data);

            Toast.makeText(this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }


    public Boolean validarCampos(){

        String campoValor = campoValorD.getText().toString();
        String campoCategoria = campoCategoriaD.getText().toString();
        String campoData = campoDataD.getText().toString();
        String campoDescricao = campoDescricaoD.getText().toString();

        if ( !campoValor.isEmpty() ){
            if ( !campoCategoria.isEmpty() ){
                if ( !campoData.isEmpty() ){
                    if ( !campoDescricao.isEmpty() ){
                        return true;
                    }else {
                        Toast.makeText(DespesasActivity.this, "Digite Descriacao", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DespesasActivity.this, "Digite a Data!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(DespesasActivity.this, "Digite a Categoria!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(DespesasActivity.this, "Digite o Valor!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    //Exibir data no formato corrento
    public String formatarData(int dia, int mes, int ano){
        String data = "00/00/0000";
        if ( (mes+1) < 10 && dia < 10 ){
            data = "0" + dia + "/0" + (mes+1) + "/" + ano;
        }else if( (mes+1) < 10 ){
            data = dia + "/0" + (mes+1) + "/" + ano;
        }else if( dia < 10 ){
            data = "0" + dia + "/" + (mes+1) + "/" + ano;
        }else {
            data = dia + "/" + (mes+1) + "/" + ano;
        }

        return data;
    }
    /**Esconda o teclado*/
    public void esconderTeclado() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}