package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class mng_room extends AppCompatActivity {
    ArrayList<String> mapb,tenpb;
    nap_database db;
    mng_room_list_adapter adapter;


    ImageButton btn_add,btn_back,btn_find,btn_refr;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_room);
        RecyclerView recyclerView;
        btn_add = findViewById(R.id.mng_room_list_add);
        btn_back = findViewById(R.id.mng_room_list_back);
        btn_find = findViewById(R.id.mng_room_list_search);
        btn_refr = findViewById(R.id.mng_room_list_refresh);

        db = new nap_database(this);
        mapb = new ArrayList<>();
        tenpb = new ArrayList<>();
        recyclerView = findViewById(R.id.mng_room_list_rcv);
        adapter = new mng_room_list_adapter(this,mapb,tenpb);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayROOM();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cre();
            }
        });

        btn_refr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void displayROOM() {
        Cursor cursor = db.getdataPHONGBAN();
        while (cursor.moveToNext()) {
            mapb.add(cursor.getString(0));
            tenpb.add(cursor.getString(1));
        }
    }

    private void cre() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.mng_room_cre_dialog);
        dialog.setCanceledOnTouchOutside(false);
        ImageButton yes = dialog.findViewById(R.id.dialog_room_cre_confirm), no = dialog.findViewById(R.id.dialog_room_cre_cancel);
        EditText mpb = dialog.findViewById(R.id.dialog_room_cre_id), tpb  =dialog.findViewById(R.id.dialog_roomm_cre_name);

        nap_database db = new nap_database(this);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = mpb.getText().toString();
                String ten = tpb.getText().toString();
                boolean kq = db.insertPHONGBAN(ma,ten);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Thêm không thành công",Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void find() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.mng_room_search_dialog);
        dialog.setCanceledOnTouchOutside(false);

        Spinner cbx = dialog.findViewById(R.id.room_search_spinner);
        spinner_adapter adapter = new spinner_adapter(this,R.layout.spinner_selected_layout,getListPB());
        cbx.setAdapter(adapter);

        ImageButton yes = dialog.findViewById(R.id.room_search_yes), no = dialog.findViewById(R.id.room_search_cancel);
        TextView tv = dialog.findViewById(R.id.room_search_tv);

        cbx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(adapter.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = tv.getText().toString();
               Intent i = new Intent(getApplicationContext(), mng_staff_of_room.class);
               i.putExtra("MAPB_INPUT",id);
               startActivity(i);
               finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private List<spinner_category> getListPB() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdataPHONGBAN();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));
        }
        return list;
    }


}