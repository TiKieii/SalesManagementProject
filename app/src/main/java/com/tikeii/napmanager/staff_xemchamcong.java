package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class staff_xemchamcong extends AppCompatActivity {
    TextView nc, nn,tc,thang;
    ImageButton exit;
    nap_database db = new nap_database(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_xemchamcong);
        nc = findViewById(R.id.staff_xcc_ngaycong);
        nn = findViewById(R.id.staff_xcc_ngayphep);
        tc = findViewById(R.id.staff_xcc_tangca);
        thang = findViewById(R.id.staff_xcc_thang);
        exit = findViewById(R.id.staff_xl_IB_exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i = getIntent();
        String id = i.getStringExtra("MANV_INPUT");
        String month = i.getStringExtra("CHONTHANG_INPUT");
        Cursor cursor = db.getdata_xemchamcong(id,month);
        while (cursor.moveToNext()){
            thang.setText(cursor.getString(0));
            nc.setText(cursor.getString(1));
            nn.setText(cursor.getString(2));
            tc.setText(cursor.getString(3));

        }
    }


}