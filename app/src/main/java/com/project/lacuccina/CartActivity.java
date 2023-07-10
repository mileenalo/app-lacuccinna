package com.project.lacuccina;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.lacuccina.adapter.Ad_Cart_Order;

import com.project.lacuccina.model.CartOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    Ad_Cart_Order ad_card_order;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button  continueButton;
    Button  backButton;
    String orderId;
    String cartArray[][];
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
        orderId = intent.getStringExtra("orderId");

        setContentView(R.layout.activity_cart);

        SearchCart searchCart = new SearchCart();

        CloseOrder closeOrder = new CloseOrder();

        //Chamada da tarefa de busca dos itens do pedido
        searchCart.execute(clsGlobal.getIdEndere()+"pedido/items/"+orderId);

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

        recyclerView = findViewById(R.id.id_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Finalizar o pedido
        continueButton = findViewById(R.id.id_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
                closeOrder.execute(clsGlobal.getIdEndere()+"pedido/finish", orderId);
                startActivity(intent);
            }
        });

        //Voltar ao menu
        backButton = findViewById(R.id.id_backMenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
    }

    //Função que seta os itens retornados do GET de Itens do Pedido
    public void setItensMenu(String menu) {
        try {
            JSONArray jsonArray = new JSONArray(menu);
            cartArray = new String[jsonArray.length()][6];
            if(jsonArray.length() == 0){
                TextView msgCart = findViewById(R.id.id_cart);
                msgCart.setText("Carrinho Vazio!");
            }else{
                // Percorrer o JSONArray
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Extrair os valores dos campos do objeto 'pedido'
                    JSONObject jsonPedido = jsonObject.getJSONObject("pedido");

                    // Extrair os valores dos campos do objeto 'produto'
                    JSONObject jsonProduto = jsonObject.getJSONObject("produto");

                    cartArray[i][0] = String.valueOf(jsonPedido.getInt("idmenu"));
                    cartArray[i][1] = String.valueOf(jsonPedido.getInt("qtditem"));
                    cartArray[i][2] = jsonProduto.getString("url");
                    cartArray[i][3] = jsonProduto.getString("description");
                    cartArray[i][4] = jsonProduto.getString("title");
                    cartArray[i][5] = String.valueOf(jsonProduto.getInt("price"));
                }

                //Inclui na visualização dinâmica de itens do pedido
                ad_card_order = new Ad_Cart_Order(this, getData(cartArray), orderId);
                recyclerView.setAdapter(ad_card_order);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Função para requisitar o POST de finalização do pedido
    private class CloseOrder extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String idPedido = strings[1];
            String requestUrl = strings[0];

            // Para adicionar ao pedido
            JSONObject postData = new JSONObject();

            try {
                postData.put("orderId", idPedido);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String retorno = WebService.postWS(requestUrl, String.valueOf(postData));
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            setItensMenu(s);
        }
    }

    //Função para requisitar o GET de itens do pedido
    private class SearchCart extends AsyncTask<String, String, String> {

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

    //Função que monta o Array de itens que serão incluído na listagem
    private ArrayList<CartOrder> getData(String[][] cartArray) {

        ArrayList<CartOrder> arrayList = new ArrayList<>();
        // Percorrer o JSONArray
        for (int i = 0; i < cartArray.length; i++) {

            int url;

            if (cartArray[i][2].equals("R.drawable.fetuccine")) {
                url = R.drawable.fetuccine;
            } else if (cartArray[i][2].equals("R.drawable.molho_sugo")) {
                url = R.drawable.molho_sugo;
            } else if (cartArray[i][2].equals("R.drawable.nhoque_4_queijos")) {
                url = R.drawable.nhoque_4_queijos;
            } else if (cartArray[i][2].equals("R.drawable.carbonara")) {
                url = R.drawable.carbonara;
            } else if (cartArray[i][2].equals("R.drawable.nhoque_fughi")) {
                url = R.drawable.nhoque_fughi;
            }else{
                url = R.drawable.bolonhesa;
            }

            arrayList.add(new CartOrder(url, cartArray[i][4], cartArray[i][3], (Integer.parseInt(cartArray[i][5]) * Integer.parseInt(cartArray[i][1])), cartArray[i][0], Integer.parseInt(cartArray[i][1]), orderId));
        }

        return arrayList;
    }
}
