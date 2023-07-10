package com.project.lacuccina.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.project.lacuccina.R;
import com.project.lacuccina.adapter.holder.Holder_Order_View;
import com.project.lacuccina.model.ViewOrder;

import java.util.ArrayList;

public class Ad_Order_View extends RecyclerView.Adapter<Holder_Order_View> {
    String orderId;
    Context mContext;

    ArrayList<ViewOrder> data;

    public Ad_Order_View(Context context, ArrayList<ViewOrder> data, String orderId) {
        this.mContext = context;
        this.data = data;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public Holder_Order_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_view, viewGroup, false);
        return new  Holder_Order_View(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_Order_View holder_order_view, int i) {
        final ViewOrder cart = data.get(i);
        Glide.with(mContext).load(cart.getUrlImage()).into(holder_order_view.getImageView());
        holder_order_view.getTitle().setText(cart.getTitle());
        holder_order_view.getBody().setText(cart.getDesc());
        holder_order_view.getPrice().setText("R$ " + cart.getPrice());
        holder_order_view.getId().setText(cart.getId());
        holder_order_view.getQtd().setText(" " + cart.getQtd());
        holder_order_view.getIdPedido().setText(cart.getIdpedido());
        holder_order_view.getObsItem().setText(cart.getObsItem());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
