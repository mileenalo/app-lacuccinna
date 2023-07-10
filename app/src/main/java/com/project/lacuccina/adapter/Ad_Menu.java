package com.project.lacuccina.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.project.lacuccina.ProductCard;
import com.project.lacuccina.R;
import com.project.lacuccina.adapter.holder.Holder_Menu;
import com.project.lacuccina.model.Food;

import java.util.ArrayList;

public class Ad_Menu extends RecyclerView.Adapter<Holder_Menu> {

    String orderId;
    Context mContext;

    ArrayList<Food> data;

    public Ad_Menu(Context context, ArrayList<Food> data, String orderId) {
        this.mContext = context;
        this.data = data;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public Holder_Menu onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu, viewGroup, false);
        return new Holder_Menu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_Menu holder_menu, int position) {
        final Food food = data.get(position);
        Glide.with(mContext).load(food.getUrlImage()).into(holder_menu.getImageView());
        holder_menu.getTitle().setText(food.getTitle());
        holder_menu.getBody().setText(food.getDesc());
        holder_menu.getPrice().setText("R$ " + food.getPrice());
        holder_menu.getId().setText(food.getId());
        holder_menu.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductCard.class);
                intent.putExtra("id_product", food.getId());
                intent.putExtra("orderId", orderId);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
