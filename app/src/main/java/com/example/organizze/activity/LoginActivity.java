package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editTextLoginEmail);
        campoSenha = findViewById(R.id.editTextLoginSenha);
        btnEntrar = findViewById(R.id.buttonLoginEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if ( !textoEmail.isEmpty() ){
                    if ( !textoSenha.isEmpty() ){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();
                    }else {
                        Toast.makeText(LoginActivity.this, "Digite sua Senha!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Digite seu Email!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void validarLogin(){

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            abrirTelaPrincipal();
                        }else{

                            String excecao = "";
                            try {
                                throw  task.getException();
                            }catch ( FirebaseAuthInvalidUserException e ){
                                excecao = "Usuário não Cadastrado!";
                            }catch ( FirebaseAuthInvalidCredentialsException e){
                                excecao = "Email e senha não corresponde a um Usuário Cadastrado!";
                            }catch ( Exception e ){
                                excecao = "Erro ao cadastrar usuario: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(LoginActivity.this, excecao,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
        finish();
    }

}
