package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class mng_add_staff extends AppCompatActivity {
    EditText msnv, matkhau, hoten, ngaysinh,sodienthoai,diachi,ngaylamviec,luongcoban;
    Spinner chucvu,trinhdo,phongban;
    TextView warning,cv,td,pb,sex;
    RadioGroup rg;
    RadioButton nam,nu;
    ImageButton add,back;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_add_staff);

        nap_database db = new nap_database(this);

        msnv = findViewById(R.id.mng_staff_cre_msnv);
        matkhau = findViewById(R.id.mng_staff_cre_password);
        hoten = findViewById(R.id.mng_staff_cre_name);
        rg = findViewById(R.id.mng_staff_cre_sex_rg);
        nam = findViewById(R.id.mng_staff_cre_sex_male);
        nu = findViewById(R.id.mng_staff_cre_sex_female);
        ngaysinh = findViewById(R.id.mng_staff_cre_bod);
        sodienthoai = findViewById(R.id.mng_staff_cre_phone);
        diachi  = findViewById(R.id.mng_staff_cre_address);
        ngaylamviec = findViewById(R.id.mng_staff_cre_startday);
        luongcoban = findViewById(R.id.mng_staff_cre_org_salary);
        chucvu = findViewById(R.id.mng_staff_cre_rank);
        trinhdo = findViewById(R.id.mng_staff_cre_level);
        phongban = findViewById(R.id.mng_staff_cre_room);
        warning = findViewById(R.id.mng_staff_cre_warning); warning.setVisibility(View.INVISIBLE);
        cv = findViewById(R.id.mng_staff_cre_tvcv);
        pb = findViewById(R.id.mng_staff_cre_tvpb);
        td = findViewById(R.id.mng_staff_cre_tvtd);
        sex = findViewById(R.id.mng_staff_cre_tvsex); sex.setVisibility(View.INVISIBLE);

        add = findViewById(R.id.mng_staff_cre_add);
        back = findViewById(R.id.mng_staff_cre_back);

        spinner_adapter adapter_chucvu, adapter_trinhdo, adapter_phongban;
        adapter_chucvu = new spinner_adapter(this,R.layout.spinner_selected_layout,getListchucvu());
        adapter_phongban = new spinner_adapter(this, R.layout.spinner_selected_layout,getListphongban());
        adapter_trinhdo = new spinner_adapter(this,R.layout.spinner_selected_layout,getListtrinhdo());
        chucvu.setAdapter(adapter_chucvu);
        trinhdo.setAdapter(adapter_trinhdo);
        phongban.setAdapter(adapter_phongban);




        chucvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cv.setText(adapter_chucvu.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        phongban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pb.setText(adapter_phongban.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        trinhdo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                td.setText(adapter_trinhdo.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (nam.isChecked()) {
                   sex.setText("Nam");
               }
           }
       });
        nu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (nam.isChecked()) {
                    sex.setText("Nữ");
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = msnv.getText().toString();
                String pass = matkhau.getText().toString();
                String ten = hoten.getText().toString();
                String gt = sex.getText().toString();
                String nsinh = ngaysinh.getText().toString();
                String sdt = sodienthoai.getText().toString();
                String dchi = diachi.getText().toString();
                String nglamviec = ngaylamviec.getText().toString();
                String luong = luongcoban.getText().toString();
                String cvu = cv.getText().toString();
                String pban = pb.getText().toString();
                String tdo = td.getText().toString();

                Cursor cursor = db.getdata_staff_withID(manv);
                while (cursor.moveToNext()) {
                    if (manv.equals(cursor.getString(0))) {
                        msnv.setError("Mã nhân viên đã tồn tại");
                    }
                }

                Boolean kq = db.insertNHANVIEN(manv,pass,ten,gt,nsinh,sdt,dchi,nglamviec,luong,cvu,tdo,pban);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Thêm nhân viên thành công",Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Thêm nhân viên không thành công",Toast.LENGTH_LONG).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });






    }

    private List<spinner_category> getListchucvu() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdatachucvu();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));
        }
        return list;
    }

    private List<spinner_category> getListphongban() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdataPHONGBAN();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));
        }
        return list;
    }


    private List<spinner_category> getListtrinhdo() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_TRINHDO();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));
        }
        return list;
    }
}