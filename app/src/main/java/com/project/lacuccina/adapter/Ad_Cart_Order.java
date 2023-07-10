package com.project.lacuccina.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.project.lacuccina.CartActivity;
import com.project.lacuccina.Global;
import com.project.lacuccina.MenuActivity;
import com.project.lacuccina.ProductCard;
import com.project.lacuccina.R;
import com.project.lacuccina.WebService;
import com.project.lacuccina.adapter.holder.Holder_Cart_Order;
import com.project.lacuccina.model.CartOrder;
import com.project.lacuccina.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ad_Cart_Order extends RecyclerView.Adapter<Holder_Cart_Order> {
    String orderId;
    Context mContext;
    Global clsGlobal;
    Context cntMain;

    ArrayList<CartOrder> data;

    public Ad_Cart_Order(Context context, ArrayList<CartOrder> data, String orderId) {
        this.mContext = context;
        this.data = data;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public Holder_Cart_Order onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_order, viewGroup, false);
        return new  Holder_Cart_Order(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_Cart_Order holder_cart_order, int i) {
        // Seta contexto:
        cntMain = mContext.getApplicationContext();

        // Abre classe global:
        clsGlobal = (Global) cntMain;

        //Seta infos na visualição
        final CartOrder cart = data.get(i);
        Glide.with(mContext).load(cart.getUrlImage()).into(holder_cart_order.getImageView());
        holder_cart_order.getTitle().setText(cart.getTitle());
        holder_cart_order.getBody().setText(cart.getDesc());
        holder_cart_order.getPrice().setText("R$ " + cart.getPrice());
        holder_cart_order.getId().setText(cart.getId());
        holder_cart_order.getQtd().setText(" " + cart.getQtd());
        holder_cart_order.getIdPedido().setText(cart.getIdpedido());

        ImageView lessBtn = holder_cart_order.itemView.findViewById(R.id.id_less_btn);
        ImageView plusBtn = holder_cart_order.itemView.findViewById(R.id.id_plus_btn);
        ImageView cancelBtn = holder_cart_order.itemView.findViewById(R.id.id_image_cancel);

        //Retira um item do pedido
        lessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQtd =  cart.getQtd() - 1;
                holder_cart_order.getQtd().setText(" " + newQtd);
                if(0 == newQtd){
                    DelItem delItem = new DelItem();
                    delItem.execute(clsGlobal.getIdEndere()+"pedido", orderId, cart.getId());

                    Intent intent = new Intent(mContext, CartActivity.class);
                    intent.putExtra("orderId", orderId);
                    mContext.startActivity(intent);
                }else{
                    AlterItem alterItem = new AlterItem();
                    alterItem.execute(clsGlobal.getIdEndere()+"pedido", orderId, cart.getId(), "less");

                    Intent intent = new Intent(mContext, CartActivity.class);
                    intent.putExtra("orderId", orderId);
                    mContext.startActivity(intent);
                }

            }
        });

        //Adiciona um item no pedido
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQtd =  cart.getQtd() + 1;
                holder_cart_order.getQtd().setText(" " + newQtd);
                AlterItem alterItem = new AlterItem();
                alterItem.execute(clsGlobal.getIdEndere()+"pedido", orderId, cart.getId(), "plus");

                Intent intent = new Intent(mContext, CartActivity.class);
                intent.putExtra("orderId", orderId);
                mContext.startActivity(intent);
            }
        });

        //Retira um item do pedido
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelItem delItem = new DelItem();
                delItem.execute(clsGlobal.getIdEndere()+"pedido", orderId, cart.getId());

                Intent intent = new Intent(mContext, CartActivity.class);
                intent.putExtra("orderId", orderId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class AlterItem extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String idPedido = strings[1];
            String idProduct = strings[2];
            String requestUrl = strings[0];
            String type = strings[3];

            // Para adicionar ao pedido
            JSONObject postData = new JSONObject();

            try {
                postData.put("orderId", Integer.parseInt(idPedido));
                postData.put("menuId", Integer.parseInt(idProduct));
                if(type.equals("less")){
                    postData.put("qtd", -1);
                }else{
                    postData.put("qtd", 1);
                }
                postData.put("obsItem", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String retorno = WebService.postWS(requestUrl, String.valueOf(postData));
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class DelItem extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String idPedido = strings[1];
            String idProduct = strings[2];
            String requestUrl = strings[0];

            // Para adicionar ao pedido
            JSONObject postData = new JSONObject();

            try {
                postData.put("orderId", Integer.parseInt(idPedido));
                postData.put("menuId", Integer.parseInt(idProduct));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String retorno = WebService.delWS(requestUrl, String.valueOf(postData));
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
