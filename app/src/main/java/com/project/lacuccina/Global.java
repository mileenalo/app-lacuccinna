package com.project.lacuccina;

import android.app.Application;
import android.util.Log;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Classe responsável por manipular as variáveis globais:
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class Global extends Application {
    // Controle do idPedido para uso na visualização do item do menu
    public String idPedido = "";
    public String cIpEndere = "http://10.0.2.2:8081/";

    public void setIdPedido(String id){
        idPedido = id;
    }

    public String getIdPedido(){
        return idPedido;
    }

    public String getIdEndere(){
        return cIpEndere;
    }
}
