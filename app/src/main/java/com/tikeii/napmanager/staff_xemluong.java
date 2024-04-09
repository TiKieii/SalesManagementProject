package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tikeii.napmanager.R;
import com.tikeii.napmanager.nap_database;

public class staff_xemluong extends AppCompatActivity {
    TextView lcb, tt, tp, tl,hoten,on,off,ot;
    ImageButton cancel,info;
    nap_database db = new nap_database(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_xemluong);

        lcb = findViewById(R.id.staff_xl_lcb);
        tt = findViewById(R.id.staff_xl_tienthuuong);
        tp = findViewById(R.id.staff_xl_tienphat);
        tl = findViewById(R.id.staff_xl_sumluong);
        hoten = findViewById(R.id.staff_xl_hoten);
        on = findViewById(R.id.salary_xl_onday);
        off = findViewById(R.id.salary_xl_off);
        ot = findViewById(R.id.salary_xl_OT);
        info = findViewById(R.id.staff_xl_info);
        cancel = findViewById(R.id.staff_xl_IB_exit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent  i = getIntent();
        String id = i.getStringExtra("MANV_INPUT");
        String month = i.getStringExtra("CHONTHANG_INPUT");
        Cursor cursor = db.getdatastaff_with_ID_salary_see(id,month);
        while (cursor.moveToNext()){
            hoten.setText(cursor.getString(0));
            lcb.setText(cursor.getString(1));
            tt.setText(cursor.getString(2));
            tp.setText(cursor.getString(3));
            tl.setText(cursor.getString(4));
            on.setText(cursor.getString(5));
            off.setText(cursor.getString(6));
            ot.setText(cursor.getString(7));;
        }

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staff_xl_detail(id);
            }
        });
    }

    private void staff_xl_detail(String id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.staff_xl_detail_dialog);
        dialog.setCanceledOnTouchOutside(false);

        TextView hoten = dialog.findViewById(R.id.staff_xemct_name_dia);
        TextView note = dialog.findViewById(R.id.staff_xemct_tv);

        ImageButton done = dialog.findViewById(R.id.staff_luong_detail_done);

        Cursor cursor = db.getdata_luong_detail(id);
        while (cursor.moveToNext()) {
            hoten.setText(cursor.getString(1));
            note.setText(cursor.getString(0));
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}