package com.project.lacuccina.adapter.holder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lacuccina.R;

public class Holder_Orders extends RecyclerView.ViewHolder {
    private TextView OrderId , Status , Price, Qtd;

    public Holder_Orders(@NonNull View itemView) {
        super(itemView);
        OrderId = itemView.findViewById(R.id.id_order);
        Status = itemView.findViewById(R.id.id_status);
        Price = itemView.findViewById(R.id.id_price);
        Qtd = itemView.findViewById(R.id.id_qtd);
    }


    public TextView getOrderId() {
        return OrderId;
    }

    public TextView getStatus() {
        return Status;
    }

    public TextView getPrice() {
        return Price;
    }

    public TextView getQtd() {
        return Qtd;
    }
}
