package com.project.lacuccina;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ProductCard extends AppCompatActivity {

    private RelativeLayout checkout;
    Global clsGlobal;
    Context cntMain;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta contexto:
        cntMain = getApplicationContext();

        // Abre classe global:
        clsGlobal = (Global) cntMain;

        Intent intent = getIntent();
        setContentView(R.layout.activity_product_card);
        String idProduct = intent.getStringExtra("id_product");
        orderId = intent.getStringExtra("orderId");

        //Busca vizualização do item
        SearchItem searchMenu = new SearchItem();
        searchMenu.execute(clsGlobal.getIdEndere()+"menu/food/"+idProduct);

        //Busca quantidade no carrinho pro ícone
        if(!orderId.equals("")) {
            SearchQtd searchQtd = new SearchQtd();
            searchQtd.execute(clsGlobal.getIdEndere()+"pedido/"+orderId);
        }

        EditText obsItem = findViewById(R.id.id_obs_item);
        TextView addButton;
        addButton = findViewById(R.id.id_add_to_cart);
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

        //Abre o carrinho
        checkout = findViewById(R.id.id_checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductCard.this, CartActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });

        //Adiciona item no carrinho/pedido
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obs = obsItem.getText().toString();
                SendItemOrder sendItemOrder = new SendItemOrder();

                sendItemOrder.execute(clsGlobal.getIdEndere()+"pedido", idProduct, obs);
            }
        });

    }

    //Recebe o retorno do GET de item
    public void setItensMenu(String menu) {
        ImageView imageView = findViewById(R.id.id_img_item);
        TextView txtTitulo = findViewById(R.id.id_Product_Name);
        TextView txtDesc = findViewById(R.id.id_Desc_Item);
        TextView txtPrice = findViewById(R.id.id_product_price);

        try {

            JSONObject jsonObject = new JSONObject(menu);
            int url;

            if (Objects.equals(jsonObject.getString("url"), "R.drawable.fetuccine")) {
                url = R.drawable.fetuccine;
            } else if (jsonObject.getString("url").trim().equals("R.drawable.molho_sugo")) {
                url = R.drawable.molho_sugo;
            } else if (Objects.equals(jsonObject.getString("url"), "R.drawable.nhoque_4_queijos")) {
                url = R.drawable.nhoque_4_queijos;
            } else if (Objects.equals(jsonObject.getString("url"), "R.drawable.carbonara")) {
                url = R.drawable.carbonara;
            } else if (Objects.equals(jsonObject.getString("url"), "R.drawable.nhoque_fughi")) {
                url = R.drawable.nhoque_fughi;
            }else{
                url = R.drawable.bolonhesa;
            }

            imageView.setImageResource(url);
            txtTitulo.setText(jsonObject.getString("title"));
            txtDesc.setText(jsonObject.getString("description"));
            txtPrice.setText("R$ " + jsonObject.getInt("price"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Seta a quantidade no carrinho
    public void setQtd(String pedido) {
        TextView txtQtdPed = findViewById(R.id.id_order_count);

        try {
            JSONObject jsonObject = new JSONObject(pedido);
            txtQtdPed.setText(jsonObject.getString("qtdItens"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Requisição GET infos do item
    private class SearchItem extends AsyncTask<String, String, String> {

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

    //Inclui item no pedido/carrinho
    private class SendItemOrder extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String idProduct = strings[1];
            String requestUrl = strings[0];
            String obsItem = strings[2];

            // Para adicionar ao pedido
            JSONObject postData = new JSONObject();

            try {
                if(orderId.equals("")){
                    postData.put("orderId", JSONObject.NULL);
                }else{
                    postData.put("orderId", clsGlobal.getIdPedido());
                }
                postData.put("menuId", Integer.parseInt(idProduct));
                postData.put("qtd", 1);
                postData.put("obsItem", obsItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String retorno = WebService.postWS(requestUrl, postData.toString());

            return retorno;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                clsGlobal.setIdPedido(response);
                Intent intent = new Intent(ProductCard.this,CartActivity.class);

                intent.putExtra("orderId", clsGlobal.getIdPedido());
                startActivity(intent);
            }
        }
    }

    //Requisição GET da quantidade
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
