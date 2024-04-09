package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class staff_home extends AppCompatActivity {

    TextView hoten, manv, gt, ns, sdt, dc, nlv, lcb, td, cv, pb;
    ImageButton xl, xcc, back;
    nap_database db = new nap_database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);
        hoten = findViewById(R.id.staff_home_name);
        manv = findViewById(R.id.staff_home_MANV);
        gt = findViewById(R.id.staff_home_sex);
        ns = findViewById(R.id.staff_home_date);
        sdt = findViewById(R.id.staff_home_phone);
        dc = findViewById(R.id.staff_home_address);
        nlv = findViewById(R.id.staff_home_workday);
        lcb = findViewById(R.id.staff_home_salary);
        td = findViewById(R.id.staff_home_level);
        cv = findViewById(R.id.staff_home_rank);
        pb = findViewById(R.id.staff_home_room);

        xl = findViewById(R.id.staff_IB_seesalary);
        xcc = findViewById(R.id.staff_IB_seecc);
        back = findViewById(R.id.staff_IB_exit);

        Intent i = getIntent();
        String id = i.getStringExtra("MANV_INPUT");

        Cursor cursor = db.getdata_staff_with_ID(id);
        while (cursor.moveToNext()) {
            hoten.setText(cursor.getString(0));
            manv.setText(cursor.getString(1));
            gt.setText(cursor.getString(2));
            ns.setText(cursor.getString(3));
            sdt.setText(cursor.getString(4));
            dc.setText(cursor.getString(5));
            nlv.setText(cursor.getString(6));
            lcb.setText(cursor.getString(7));
            td.setText(cursor.getString(10));
            cv.setText(cursor.getString(8));
            pb.setText(cursor.getString(9));
        }
        xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thangxemluong(id);
            }
        });

        xcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thangxemchamcong(id);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void thangxemluong(String id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.staff_chonthang_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = dialog.findViewById(R.id.staff_xl_tv_anmattieuroicooi_dia);
        tv.setVisibility(View.INVISIBLE);
        Spinner cbb = dialog.findViewById(R.id.staff_xl_cbb_dia);
        ImageButton yes = dialog.findViewById(R.id.staff_xl_yes_dia), no = dialog.findViewById(R.id.staff_xl_IB_cancel_dia);
        spinner_adapter adapter = new spinner_adapter(this, R.layout.spinner_selected_layout, getThangxemluong(id));
        cbb.setAdapter(adapter);
        cbb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(adapter.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), staff_xemluong.class);
                String month = tv.getText().toString();
                i.putExtra("CHONTHANG_INPUT", month);
                i.putExtra("MANV_INPUT", id);
                startActivity(i);
            }
        });
        dialog.show();
    }


    private void thangxemchamcong(String id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.staff_chonthang_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = dialog.findViewById(R.id.staff_xl_tv_anmattieuroicooi_dia);
        tv.setVisibility(View.INVISIBLE);
        Spinner cbb = dialog.findViewById(R.id.staff_xl_cbb_dia);
        ImageButton yes = dialog.findViewById(R.id.staff_xl_yes_dia), no = dialog.findViewById(R.id.staff_xl_IB_cancel_dia);
        spinner_adapter adapter = new spinner_adapter(this, R.layout.spinner_selected_layout, getThangxemluong(id));
        cbb.setAdapter(adapter);
        cbb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(adapter.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), staff_xemchamcong.class);
                String month = tv.getText().toString();
                i.putExtra("CHONTHANG_INPUT", month);
                i.putExtra("MANV_INPUT", id);
                startActivity(i);
            }
        });
        dialog.show();

    }

    private List<spinner_category> getThangxemluong(String id) {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_CHAMCONG_staff_salary_cal(id);
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));

        }
        return list;
    }
}