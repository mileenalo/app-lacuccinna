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
import com.project.lacuccina.adapter.Ad_Orders;
import com.project.lacuccina.model.CartOrder;
import com.project.lacuccina.model.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Ad_Orders ad_orders;
    Button buttonContinue;
    String[][] ordersArray;
    Global clsGlobal;
    Context cntMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seta contexto:
        cntMain = getApplicationContext();

        // Abre classe global:
        clsGlobal = (Global) cntMain;

        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.id_recycler_orders);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        buttonContinue = findViewById(R.id.id_continue);

        //Chama o menu
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, MenuActivity.class);
                intent.putExtra("orderId", "");
                startActivity(intent);
            }
        });

        //Chamada da função de busca de pedidos
        SearchOrders searchOrders = new SearchOrders();
        searchOrders.execute(clsGlobal.getIdEndere()+"pedido");
    }

    //Seta o retorno de pedidos
    public void setOrders(String menu) {

        try {
            JSONArray jsonArray = new JSONArray(menu);

            int nCtd = 0;
            // Percorrer o JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String status = jsonObject.getString("status").trim();

                if(status.equals("Em preparação")){
                    nCtd++;
                }
            }
            ordersArray = new String[nCtd][4];
            nCtd = 0;

            // Percorrer o JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String status = jsonObject.getString("status").trim();

                //Seta apenas pedidos finalizados
                if(status.equals("Em preparação")){

                    ordersArray[nCtd][0] = String.valueOf(jsonObject.getInt("id"));
                    ordersArray[nCtd][1] = jsonObject.getString("status");
                    ordersArray[nCtd][2] = String.valueOf(jsonObject.getInt("qtdItens"));
                    ordersArray[nCtd][3] = String.valueOf(jsonObject.getInt("valor"));
                    nCtd++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ad_orders = new Ad_Orders(this, getData(ordersArray));
        recyclerView.setAdapter(ad_orders);
    }

    //Requisição GET de pedidos
    private class SearchOrders extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String retorno = WebService.buscaWS(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            setOrders(s);
        }
    }

    //Monta o array de pedidos para visualização
    private ArrayList<Orders> getData(String[][] ordersArray) {

        ArrayList<Orders> arrayList = new ArrayList<>();

        // Percorrer o JSONArray
        for (int i = 0; i < ordersArray.length; i++) {
            arrayList.add(new Orders(ordersArray[i][0], ordersArray[i][1], Integer.parseInt(ordersArray[i][2]), Integer.parseInt(ordersArray[i][3])));
        }

        return arrayList;
    }
}
