package com.project.lacuccina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends AppCompatActivity {

    Button Orders;
    Button Menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Orders = findViewById(R.id.id_Orders);
        Menu = findViewById(R.id.id_Menu);

        //Abre o menu
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MenuActivity.class);
                intent.putExtra("orderId", "");
                startActivity(intent);
            }
        });

        //Abre os pedidos finalizados
        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
