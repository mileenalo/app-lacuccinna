package com.project.lacuccina.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.lacuccina.OrderViewActivity;

import com.project.lacuccina.R;
import com.project.lacuccina.adapter.holder.Holder_Orders;

import com.project.lacuccina.model.Orders;

import java.util.ArrayList;

public class Ad_Orders extends RecyclerView.Adapter<Holder_Orders> {

    Context mContext;

    ArrayList<Orders> data;

    public Ad_Orders(Context context, ArrayList<Orders> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Holder_Orders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_orders, viewGroup, false);
        return new Holder_Orders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_Orders holder_orders, int i) {
        final Orders orders = data.get(i);

        holder_orders.getOrderId().setText("Pedido #"+orders.getOrderId());
        holder_orders.getStatus().setText("Status: "+orders.getStatus());
        holder_orders.getPrice().setText("Valor R$" + orders.getPrice());
        holder_orders.getQtd().setText("Quantidade: "+orders.getQtd());
        Button viewOrder = holder_orders.itemView.findViewById(R.id.id_check);

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderViewActivity.class);
                intent.putExtra("orderId", orders.getOrderId());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
