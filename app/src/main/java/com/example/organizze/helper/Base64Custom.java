package com.example.organizze.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto){

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

    }

    public static String decodificarBase64(String textoCodificado){
        return new String( Base64.decode(textoCodificado, Base64.DEFAULT) );
    }

    public static String mesAnoDataEscolhida(String data){

        String dataRetorno[] = data.split("/");
        String mes = dataRetorno[1];
        String ano = dataRetorno[2];
        String mesAno = mes+ano;
        return mesAno;

    }
}
