package com.project.lacuccina;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.lacuccina.adapter.Ad_Menu;
import com.project.lacuccina.model.Food;
import com.project.lacuccina.WebService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private RelativeLayout checkout;
    RecyclerView recyclerview;
    GridLayoutManager gridLayoutManager;
    Ad_Menu ad_menu;
    String[][] menuArray;
    String orderId;
    Global clsGlobal;
    Context cntMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta contexto:
        cntMain = getApplicationContext();

        // Abre classe global:
        clsGlobal = (Global) cntMain;

        Intent intent = getIntent();
        setContentView(R.layout.activity_menu);
        orderId = intent.getStringExtra("orderId");

        //Busca da quantidade de itens no pedido para atualizar o ícone do carrinho
        if(!orderId.equals("")){
            SearchQtd searchQtd = new SearchQtd();
            searchQtd.execute(clsGlobal.getIdEndere()+"pedido/"+orderId);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Visualizar carrinho
        checkout = findViewById(R.id.id_checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });

        recyclerview = findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        //Requisição de itens do menu
        SearchMenu searchMenu = new SearchMenu();
        searchMenu.execute(clsGlobal.getIdEndere()+"menu");

    }

    //Seta a quantidade do ícone do carrinho
    public void setQtd(String pedido) {
        TextView txtQtdPed = findViewById(R.id.id_order_count);

        try {
            JSONObject jsonObject = new JSONObject(pedido);
            txtQtdPed.setText(jsonObject.getString("qtdItens"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Recebe o retorno do GET de itens do menu
    public void setItensMenu(String menu) {
        try {
            JSONArray jsonArray = new JSONArray(menu);
            menuArray = new String[jsonArray.length()][5];
            // Percorrer o JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                menuArray[i][0] = String.valueOf(jsonObject.getInt("id"));
                menuArray[i][1] = jsonObject.getString("description");
                menuArray[i][2] = jsonObject.getString("title");
                menuArray[i][3] = jsonObject.getString("url");
                menuArray[i][4] = String.valueOf(jsonObject.getInt("price"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ad_menu = new Ad_Menu(this, getData(menuArray), orderId);
        recyclerview.setAdapter(ad_menu);
    }

    //Requisição assíncrona GET de itens do menu
    private class SearchMenu extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String retorno = WebService.buscaWS(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            setItensMenu(s);
        }
    }

    //Monta o array para a visualização dos itens do menu
    private ArrayList<Food> getData(String[][] menuArray) {

        ArrayList<Food> arrayList = new ArrayList<>();

        // Percorrer o JSONArray
        for (int i = 0; i < menuArray.length; i++) {

            int url;

            if (Objects.equals(menuArray[i][3], "R.drawable.fetuccine")) {
                url = R.drawable.fetuccine;
            } else if (menuArray[i][3].trim().equals("R.drawable.molho_sugo")) {
                url = R.drawable.molho_sugo;
            } else if (Objects.equals(menuArray[i][3], "R.drawable.nhoque_4_queijos")) {
                url = R.drawable.nhoque_4_queijos;
            } else if (Objects.equals(menuArray[i][3], "R.drawable.carbonara")) {
                url = R.drawable.carbonara;
            } else if (Objects.equals(menuArray[i][3], "R.drawable.nhoque_fughi")) {
                url = R.drawable.nhoque_fughi;
            }else{
                url = R.drawable.bolonhesa;
            }

            arrayList.add(new Food(url, menuArray[i][2], menuArray[i][1], Integer.parseInt(menuArray[i][4]), menuArray[i][0]));
        }

        return arrayList;
    }

    //Requisiçõ GET da quntidade de itens do pedido
    private class SearchQtd extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String retorno = WebService.buscaWS(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            setQtd(s);
        }
    }
}
