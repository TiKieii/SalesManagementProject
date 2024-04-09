package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.circularreveal.CircularRevealHelper;

import java.util.ArrayList;
import java.util.List;

public class mng_staff_in4 extends AppCompatActivity {
    TextView hoten,manv,gioitinh,ngaysinh,sdt,diachi,ngaylamviec,luongcb, trinhdo,chucvu,phongban;

    ImageButton back,xl,cc,del,edit,info;
    nap_database db = new nap_database(this);
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_staff_in4);

        del = findViewById(R.id.staff_in4_delete_btn);
        edit = findViewById(R.id.staff_in4_edit_btn);
        xl = findViewById(R.id.staff_in4_salary_btn);
        cc = findViewById(R.id.staff_in4_workday_btn);

        hoten = findViewById(R.id.staff_in4_name);
        manv = findViewById(R.id.staff_in4_MANV);
        gioitinh = findViewById(R.id.staff_in4_sex);
        ngaysinh = findViewById(R.id.staff_in4_date);
        sdt = findViewById(R.id.staff_in4_phone);
        diachi = findViewById(R.id.staff_in4_address);
        ngaylamviec = findViewById(R.id.staff_in4_workday);
        luongcb = findViewById(R.id.staff_in4_salary);
        trinhdo = findViewById(R.id.staff_in4_level);
        chucvu = findViewById(R.id.staff_in4_rank);
        phongban = findViewById(R.id.staff_in4_room);


        Intent i = getIntent();
        String id = i.getStringExtra("MANV_INPUT");

        Cursor cursor = db.getdata_staff_with_ID(id);
        while (cursor.moveToNext()) {
            hoten.setText(cursor.getString(0));
            manv.setText(cursor.getString(1));
            gioitinh.setText(cursor.getString(2));
            ngaysinh.setText(cursor.getString(3));
            sdt.setText(cursor.getString(4));
            diachi.setText(cursor.getString(5));
            ngaylamviec.setText(cursor.getString(6));
            chucvu.setText(cursor.getString(8));
            luongcb.setText(cursor.getString(7));
            phongban.setText(cursor.getString(9));
            trinhdo.setText(cursor.getString(10));

        }

        xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thangxemluong(id);
            }
        });

        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thangxemchamcong(id);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del_dialog(id);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), mng_edit_staff.class);
                i.putExtra("MANV_INPUT",id);
                startActivity(i);
            }
        });
    }



    private void del_dialog(String id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.mng_staff_del_dialog);
        dialog.setCanceledOnTouchOutside(false);
        nap_database db = new nap_database(this);

        ImageButton yes,no;
        yes = dialog.findViewById(R.id.staff_del_yes_btn);
        no = dialog.findViewById(R.id.staff_del_no_btn);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kq = db.deleteNHANVIEN(id);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Đã xóa tài khoản",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Có lỗi xãy ra ! Vui lòng thử lại",Toast.LENGTH_LONG).show();
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


    private void thangxemluong(String id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.staff_chonthang_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = dialog.findViewById(R.id.staff_xl_tv_anmattieuroicooi_dia); tv.setVisibility(View.INVISIBLE);
        Spinner cbb = dialog.findViewById(R.id.staff_xl_cbb_dia);
        ImageButton yes = dialog.findViewById(R.id.staff_xl_yes_dia),no = dialog.findViewById(R.id.staff_xl_IB_cancel_dia);
        spinner_adapter adapter = new spinner_adapter(this, R.layout.spinner_selected_layout,getThangxemluong(id));
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
                Intent i = new Intent(getApplicationContext(),staff_xemluong.class);
                String month = tv.getText().toString();
                i.putExtra("CHONTHANG_INPUT",month);
                i.putExtra("MANV_INPUT",id);
                startActivity(i);
            }
        });
        dialog.show();
    }

    private void thangxemchamcong(String id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.staff_chonthang_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv = dialog.findViewById(R.id.staff_xl_tv_anmattieuroicooi_dia); tv.setVisibility(View.INVISIBLE);
        Spinner cbb = dialog.findViewById(R.id.staff_xl_cbb_dia);
        ImageButton yes = dialog.findViewById(R.id.staff_xl_yes_dia),no = dialog.findViewById(R.id.staff_xl_IB_cancel_dia);
        spinner_adapter adapter = new spinner_adapter(this, R.layout.spinner_selected_layout,getThangxemluong(id));
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
                Intent i = new Intent(getApplicationContext(),staff_xemchamcong.class);
                String month = tv.getText().toString();
                i.putExtra("CHONTHANG_INPUT",month);
                i.putExtra("MANV_INPUT",id);
                startActivity(i);
            }
        });
        dialog.show();
    }







    private List<spinner_category> getThangxemluong(String id) {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_CHAMCONG_staff_salary_cal(id);
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(new spinner_category(cursor.getString(0)));

        }
        return list;
    }


}