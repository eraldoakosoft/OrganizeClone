package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.organizze.R;
import com.example.organizze.helper.DateUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoDescricao, campoCategoria;
    private TextView campoData;
    private EditText campoValor;

    //Configurações para o calendario
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receitas);

        campoCategoria = findViewById(R.id.textInputReceitaDescricao);
        campoDescricao = findViewById(R.id.editTextReceitaCategoria);
        campoValor = findViewById(R.id.editTextReceitaValor);
        campoData = findViewById(R.id.textViewReceitaData);

        //campoData.setText(DateUtil.dataAtual());

        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                datePickerDialog = new android.app.DatePickerDialog(ReceitasActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        campoData.setText(formatarData(dayOfMonth,month, year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano,mes,dia);
                datePickerDialog.show();
            }
        });


    }

    public void salvarrrr(View view) {
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