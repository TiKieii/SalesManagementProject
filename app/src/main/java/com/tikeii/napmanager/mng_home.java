package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class mng_home extends AppCompatActivity {
    TextView tenquanly;

    ImageButton btn_staff,btn_room, btn_rank,btn_salary,btn_logout;
    Button btn_del;
    nap_database db = new nap_database(this);

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_home);
        btn_staff = findViewById(R.id.mng_home_btn_staff);
        btn_room = findViewById(R.id.mng_home_btn_room);
        btn_rank = findViewById(R.id.mng_home_btn_rank);
        btn_salary = findViewById(R.id.mng_home_btn_salary);
        btn_del = findViewById(R.id.mng_home_btn_del);
        btn_logout = findViewById(R.id.mng_home_btn_logout);

        tenquanly = findViewById(R.id.mng_home_tenquanly);
        Intent i = this.getIntent();
        String id = i.getStringExtra("MSQL");
        Cursor cursor = db.getdata_mng_withID(id);
        if (cursor.getCount()==0) {
            Toast.makeText(getApplicationContext(),"Không có dữ liệu hiển thị",Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                tenquanly.setText(cursor.getString(2));
            }
        }

        btn_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(mng_home.this,mng_staff.class);
                startActivity(i);
            }
        });

        btn_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext(), mng_room.class);
                startActivity(i);
            }
        });

        btn_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), mng_salary.class);
                startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kq = db.deleteQUANLY(id);
                Toast.makeText(getApplicationContext(),"Xóa tài khoản thành công !",Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }
}