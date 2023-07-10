package com.project.lacuccina.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lacuccina.R;

public class Holder_Menu extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView Title , Body , Price, Id;

    public Holder_Menu(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.id_image);
        Title = itemView.findViewById(R.id.id_title_tx);
        Body = itemView.findViewById(R.id.id_body_tx);
        Price = itemView.findViewById(R.id.id_price);
        Id = itemView.findViewById(R.id.id_item);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitle() {
        return Title;
    }

    public TextView getBody() {
        return Body;
    }

    public TextView getPrice() {
        return Price;
    }

    public TextView getId() {
        return Id;
    }
}
