package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class mng_staff_of_room extends AppCompatActivity {

    ArrayList<String> msnv, hoten, chucvu;
    nap_database db = new nap_database(this);
    mng_staff_list_adapter adapter;
    TextView tenpb,mapb;

    ImageButton back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_staff_of_room);
        RecyclerView recyclerView;
        msnv = new ArrayList<>();
        hoten = new ArrayList<>();
        chucvu = new ArrayList<>();
        recyclerView = findViewById(R.id.mng_staff_of_room_list_rcv);
        adapter = new mng_staff_list_adapter(this, msnv,hoten,chucvu);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tenpb = findViewById(R.id.room_list_in4_view_tenpb);
        mapb = findViewById(R.id.room_list_in4_view_mapb);
        back = findViewById(R.id.mng_staff_of_room_list_back);

        Intent i = getIntent();
        String id = i.getStringExtra("MAPB_INPUT");

        display_staff_of_room(id);
        Cursor cursor1 = db.getdataPHONGBAN_with_ID(id);
        if(cursor1.moveToNext()) {
            tenpb.setText(cursor1.getString(1));
            mapb.setText(cursor1.getString(0));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void display_staff_of_room(String roomid) {
        Cursor cursor = db.getdata_staff_stafflist_ofRoom(roomid);
        while (cursor.moveToNext()) {
            msnv.add(cursor.getString(1));
            hoten.add(cursor.getString(0));
            chucvu.add(cursor.getString(2));
        }
    }
}