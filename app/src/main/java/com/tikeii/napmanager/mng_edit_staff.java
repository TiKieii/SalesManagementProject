package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class mng_edit_staff extends AppCompatActivity {
    EditText msnv, matkhau, hoten, ngaysinh,sodienthoai,diachi,ngaylamviec,luongcoban;
    Spinner chucvu,trinhdo,phongban;
    TextView warning,cv,td,pb,sex;
    RadioGroup rg;
    RadioButton nam,nu;
    ImageButton add,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_edit_staff);
        nap_database db = new nap_database(this);

        msnv = findViewById(R.id.mng_staff_edit_msnv);
        matkhau = findViewById(R.id.mng_staff_edit_password);
        hoten = findViewById(R.id.mng_staff_edit_name);
        rg = findViewById(R.id.mng_staff_cre_edit_rg);
        nam = findViewById(R.id.mng_staff_cre_edit_male);
        nu = findViewById(R.id.mng_staff_cre_edit_female);
        ngaysinh = findViewById(R.id.mng_staff_edit_bod);
        sodienthoai = findViewById(R.id.mng_staff_edit_phone);
        diachi  = findViewById(R.id.mng_staff_edit_address);
        ngaylamviec = findViewById(R.id.mng_staff_edit_startday);
        luongcoban = findViewById(R.id.mng_staff_edit_org_salary);
        chucvu = findViewById(R.id.mng_staff_edit_rank);
        trinhdo = findViewById(R.id.mng_staff_edit_level);
        phongban = findViewById(R.id.mng_staff_edit_room);
        cv = findViewById(R.id.mng_staff_edit_tvcv);
        pb = findViewById(R.id.mng_staff_edit_tvpb);
        td = findViewById(R.id.mng_staff_edit_tvtd);
        sex = findViewById(R.id.mng_staff_edit_tvsex); sex.setVisibility(View.INVISIBLE);

        add = findViewById(R.id.mng_staff_edit_add);
        back = findViewById(R.id.mng_staff_edit_back);


        spinner_adapter adapter_chucvu, adapter_trinhdo, adapter_phongban;
        adapter_chucvu = new spinner_adapter(this,R.layout.spinner_selected_layout,getListchucvu());
        adapter_phongban = new spinner_adapter(this, R.layout.spinner_selected_layout,getListphongban());
        adapter_trinhdo = new spinner_adapter(this,R.layout.spinner_selected_layout,getListtrinhdo());
        chucvu.setAdapter(adapter_chucvu);
        trinhdo.setAdapter(adapter_trinhdo);
        phongban.setAdapter(adapter_phongban);

        Intent i = getIntent();
        String id = i.getStringExtra("MANV_INPUT");

        Cursor cursor = db.getdata_staff_with_ID(id);
        while (cursor.moveToNext()) {
            msnv.setText(cursor.getString(1));
            matkhau.setText(cursor.getString(2)); matkhau.setInputType(InputType.TYPE_CLASS_TEXT);
            hoten.setText(cursor.getString(0));
            ngaysinh.setText(cursor.getString(3));
            sodienthoai.setText(cursor.getString(4));
            diachi.setText(cursor.getString(5));
            ngaylamviec.setText(cursor.getString(6));
            luongcoban.setText(cursor.getString(7));
        }

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
                        warning.setVisibility(View.VISIBLE);
                    }
                }
                Boolean kq = db.updateNHANVIEN(manv,pass,ten,gt,nsinh,sdt,dchi,nglamviec,luong,cvu,tdo,pban);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Sửa nhân viên thành công",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Sửa nhân viên không thành công",Toast.LENGTH_LONG).show();
                }

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