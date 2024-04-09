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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class mng_staff extends AppCompatActivity {
    ArrayList<String> msnv, hoten, chucvu;
    nap_database db;
    mng_staff_list_adapter adapter;

    ImageButton btn_add,btn_back,btn_find,btn_refr;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_staff);
        RecyclerView recyclerView;
        btn_add = findViewById(R.id.mng_staff_list_add);
        btn_back = findViewById(R.id.mng_staff_list_back);
        btn_find = findViewById(R.id.mng_staff_list_search);
        btn_refr = findViewById(R.id.mng_staff_list_refresh);

        db = new nap_database(this);
        msnv = new ArrayList<>();
        hoten = new ArrayList<>();
        chucvu = new ArrayList<>();
        recyclerView = findViewById(R.id.mng_staff_list_rcv);
        adapter = new mng_staff_list_adapter(this, msnv,hoten,chucvu);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaystaff();
        Cursor cursor = db.getdata_staff_stafflist();
        if(cursor.getCount()==0) {
            Toast.makeText(getApplicationContext(), "Không có dữ liệu hiển thị", Toast.LENGTH_LONG).show();
        }

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


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mng_staff.this, mng_add_staff.class);
                startActivity(i);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void displaystaff() {
        Cursor cursor = db.getdata_staff_stafflist();
        while (cursor.moveToNext()) {
            msnv.add(cursor.getString(1));
            hoten.add(cursor.getString(0));
            chucvu.add(cursor.getString(2));
        }
    }

    private void find() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.mng_staff_finding_dialog);
        dialog.setCanceledOnTouchOutside(false);

        Spinner spinner;
        ImageButton yes = dialog.findViewById(R.id.staff_find_yes_btn), no = dialog.findViewById(R.id.staff_find_no_btn);
        TextView tv = dialog.findViewById(R.id.tv_finding); tv.setVisibility(View.INVISIBLE);
        spinner_adapter adapter;
        spinner = dialog.findViewById(R.id.mng_staff_find_cbx);
        adapter = new spinner_adapter(this,R.layout.spinner_selected_layout,getListNHANVIEN());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Intent i = new Intent(getApplicationContext(), mng_staff_in4.class);
                i.putExtra("MANV_INPUT",id); startActivity(i);
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

    private List<spinner_category> getListNHANVIEN() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_staff_stafflist();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(1)));
        }

        return list;
    }
}