package com.project.lacuccina;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.lacuccina.adapter.Ad_Order_View;

import com.project.lacuccina.model.ViewOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderViewActivity extends AppCompatActivity {

    Ad_Order_View ad_order_view;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button  backButton;
    String orderId;
    String cartArray[][];
    TextView titulo;
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
        setContentView(R.layout.activity_order_view);

        //Chama a requisição dos itens do pedido
        SearchCart searchCart = new SearchCart();
        searchCart.execute(clsGlobal.getIdEndere()+"pedido/items/"+orderId);

        recyclerView = findViewById(R.id.id_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        titulo = findViewById(R.id.id_Title_tx);
        titulo.setText("Pedido #"+orderId);

        //Volta para a listagem de pedidos
        backButton = findViewById(R.id.id_backIni);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderViewActivity.this, OrdersActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
    }

    //Seta os itens do pedido
    public void setItensMenu(String menu) {
        try {
            JSONArray jsonArray = new JSONArray(menu);
            cartArray = new String[jsonArray.length()][7];
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
                    cartArray[i][6] = jsonPedido.getString("obsitem");
                }

                ad_order_view = new Ad_Order_View(this, getData(cartArray), orderId);
                recyclerView.setAdapter(ad_order_view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Requisição GET dos itens
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

    //Seta o array pra preencher a listagem
    private ArrayList<ViewOrder> getData(String[][] cartArray) {

        ArrayList<ViewOrder> arrayList = new ArrayList<>();
        // Percorrer o JSONArray
        for (int i = 0; i < cartArray.length; i++) {

            int url;

            if (cartArray[i][6].equals("null")) {
                cartArray[i][6] = "*Sem Observação*";
            }

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

            arrayList.add(new ViewOrder(url, cartArray[i][4], cartArray[i][3], Integer.parseInt(cartArray[i][5]), cartArray[i][0], Integer.parseInt(cartArray[i][1]), orderId, cartArray[i][6]));
        }

        return arrayList;
    }
}
