package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.DateUtil;
import com.example.organizze.model.Movimentacao;
import com.example.organizze.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoDescricaoR, campoCategoriaR;
    private TextView campoDataR;
    private EditText campoValorR;
    private Movimentacao movimentacao;
    private Double receitaTotal;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();

    //Configurações para o calendario
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receitas);

        campoCategoriaR = findViewById(R.id.textInputReceitaDescricao);
        campoDescricaoR = findViewById(R.id.editTextReceitaCategoria);
        campoValorR = findViewById(R.id.editTextReceitaValor);
        campoDataR = findViewById(R.id.textViewReceitaData);

        //campoData.setText(DateUtil.dataAtual());

        campoDataR.setOnClickListener(new View.OnClickListener() {
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
                        campoDataR.setText(formatarData(dayOfMonth,month, year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano,mes,dia);
                datePickerDialog.show();
            }
        });

        recuperarReceitaTotal();


    }

    public void salvar(View view) {

        if(validarCampos()){
            movimentacao = new Movimentacao();
            String data = campoDataR.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValorR.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoriaR.getText().toString());
            movimentacao.setDescricao(campoDescricaoR.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("r");
            movimentacao.salvar(data);

            Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);

            Toast.makeText(this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    public Boolean validarCampos(){

        String campoValor = campoValorR.getText().toString();
        String campoCategoria = campoCategoriaR.getText().toString();
        String campoData = campoDataR.getText().toString();
        String campoDescricao = campoDescricaoR.getText().toString();

        if ( !campoValor.isEmpty() ){
            if ( !campoCategoria.isEmpty() ){
                if ( !campoData.isEmpty() ){
                    if ( !campoDescricao.isEmpty() ){
                        return true;
                    }else {
                        Toast.makeText(ReceitasActivity.this, "Digite Descriacao", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ReceitasActivity.this, "Digite a Data!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ReceitasActivity.this, "Digite a Categoria!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ReceitasActivity.this, "Digite o Valor!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    public void recuperarReceitaTotal(){

        String emailUsuario = firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = databaseReference.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizarReceita(Double receita){

        String emailUsuario = firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = databaseReference.child("usuarios").child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);
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