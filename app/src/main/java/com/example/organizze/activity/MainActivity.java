package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organizze.R;
import com.example.organizze.activity.CadastroActivity;
import com.example.organizze.activity.LoginActivity;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder().
                background(R.color.branco)
                .fragment(R.layout.intro_1).build()
                );

        addSlide(new FragmentSlide.Builder().
                background(R.color.branco)
                .fragment(R.layout.intro_2).build()
        );

        addSlide(new FragmentSlide.Builder().
                background(R.color.branco)
                .fragment(R.layout.intro_3).build()
        );

        addSlide(new FragmentSlide.Builder().
                background(R.color.branco)
                .fragment(R.layout.intro_4).build()
        );

        addSlide(new FragmentSlide.Builder().
                background(R.color.branco)
                .canGoForward(false)
                .fragment(R.layout.intro_cadastro).build()
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btnEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void verificarUsuarioLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //firebaseAuth.signOut();
        if ( firebaseAuth.getCurrentUser() != null ){
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
    }
}
