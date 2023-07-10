package com.project.lacuccina.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lacuccina.R;

public class Holder_Order_View extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView Title , Body , Price, Id, Qtd, IdPedido, ObsItem;
    public Holder_Order_View(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.id_image_product);
        Title = itemView.findViewById(R.id.id_title_product);
        Body = itemView.findViewById(R.id.id_desc_product);
        Price = itemView.findViewById(R.id.id_price_product);
        Id = itemView.findViewById(R.id.id_item);
        Qtd = itemView.findViewById(R.id.id_qtd_product);
        IdPedido = itemView.findViewById(R.id.id_pedido);
        ObsItem = itemView.findViewById(R.id.id_obs_item);
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

    public TextView getQtd() {
        return Qtd;
    }

    public TextView getId() {
        return Id;
    }

    public TextView getIdPedido() {
        return IdPedido;
    }

    public TextView getObsItem() {
        return ObsItem;
    }
}

