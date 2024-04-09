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

public class MainActivity extends AppCompatActivity {

    Button btn_cre_mng;
    ImageButton btn_login;
    EditText user,pass;
    RadioGroup RG;
    RadioButton rb_staff,rb_manager;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nap_database db = new nap_database(this);

        user = findViewById(R.id.nap_dn_user);
        pass = findViewById(R.id.nap_dn_pass);
        btn_cre_mng = findViewById(R.id.nap_create_mng);
        btn_login = findViewById(R.id.nap_btn_login);
        RG = findViewById(R.id.selec_G);
        rb_staff = findViewById(R.id.nap_rd_slc_worker);
        rb_manager = findViewById(R.id.nap_rd_slc_mana);
        user.setText("");
        pass.setText("");

        Cursor cursor1 = db.getdata_TRINHDO();
        if (cursor1.getCount() == 0) {
            db.insertTRINHDO("DH","Đại học");
            db.insertTRINHDO("CD","Cao đẳng");
            db.insertTRINHDO("PT","THPT");
            db.insertTRINHDO("CS","THCS");
            db.insertTRINHDO("THS","Thạc sĩ");
            db.insertTRINHDO("TSI","Tiến sĩ");
        }
        Cursor cursor2 = db.getdatachucvu();
        if (cursor2.getCount() == 0) {
            db.insertCHUCVU("TP","Trưởng Phòng");
            db.insertCHUCVU("PP","Phó Phòng");
        }

        btn_cre_mng.setVisibility(View.INVISIBLE);
        rb_staff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Bạn là Nhân Viên", Toast.LENGTH_LONG).show();
                    user.setText("");
                    pass.setText("");
                    btn_cre_mng.setVisibility(View.INVISIBLE);
                }
            }
        });

        rb_manager.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Bạn là Quản Lý", Toast.LENGTH_LONG).show();
                    user.setText("");
                    pass.setText("");
                    btn_cre_mng.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urs = user.getText().toString();
                String pss = pass.getText().toString();
                if (rb_staff.isChecked()) {

                    if (urs.equals("") || pss.equals("")) {
                        Toast.makeText(MainActivity.this,"Vui lòng nhập Tên đăng nhập và Mật khẩu",Toast.LENGTH_LONG).show();
                    } else {
                        Boolean LGcheck_stu = db.checkLG_staff(urs,pss);
                        if (LGcheck_stu == true) {
                            Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                           Intent i = new Intent(MainActivity.this,staff_home.class);
                            i.putExtra("MANV_INPUT",urs);
                            startActivity(i);

                        } else {
                            Toast.makeText(MainActivity.this,"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (rb_manager.isChecked()) {

                    if (urs.equals("") || pss.equals("")) {
                        Toast.makeText(MainActivity.this,"Vui lòng nhập Tên đăng nhập và Mật khẩu",Toast.LENGTH_LONG).show();
                    } else {
                        Boolean LGcheck_tch = db.checkLG_manager(urs,pss);
                        if (LGcheck_tch == true) {
                            Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this,mng_home.class);
                            i.putExtra("MSQL",urs);
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this,"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btn_cre_mng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MNG_create_dialog();
            }
        });


    }

    private void MNG_create_dialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.mng_create_dialog);
        dialog.setCanceledOnTouchOutside(false);
        nap_database db = new nap_database(this);




        ImageButton add = dialog.findViewById(R.id.dialog_mng_cre_confirm);
        ImageButton cancel = dialog.findViewById(R.id.dialog_mng_cre_cancel);

        EditText msgv;
        EditText mk;
        EditText hoten;
        TextView tenchucvu, tenphongban;


        msgv = dialog.findViewById(R.id.dialog_mng_cre_id);
        mk = dialog.findViewById(R.id.dialog_mng_cre_pass);
        hoten = dialog.findViewById(R.id.dialog_mng_cre_name);
        tenchucvu = dialog.findViewById(R.id.digalog_mng_cre_chucvu); tenchucvu.setVisibility(View.INVISIBLE);
        tenphongban = dialog.findViewById(R.id.digalog_mng_cre_phongban); tenphongban.setVisibility(View.INVISIBLE);


        Spinner spinner_chucvu,spinner_phongban;
        spinner_adapter adapter_chucvu,adapter_phongban;
        spinner_chucvu = dialog.findViewById(R.id.digalog_mng_cre_cb_chucvu);
        spinner_phongban = dialog.findViewById(R.id.digalog_mng_cre_cb_phongban);
        adapter_chucvu = new spinner_adapter(this,R.layout.spinner_selected_layout,getListchucvu());
        adapter_phongban = new spinner_adapter(this,R.layout.spinner_selected_layout,getListphongban());
        spinner_chucvu.setAdapter(adapter_chucvu);
        spinner_phongban.setAdapter(adapter_phongban);


        spinner_chucvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               tenchucvu.setText(adapter_chucvu.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_phongban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tenphongban.setText(adapter_phongban.getItem(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mql = msgv.getText().toString();
                String ps = mk.getText().toString();
                String ten = hoten.getText().toString();
                String chucvu = tenchucvu.getText().toString();
                String phongban = tenphongban.getText().toString();
                if (mql.equals("") || ps.equals("") || ten.equals("")) {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin !",Toast.LENGTH_LONG).show();
                }
                else {
                    Boolean insertQUANLY = db.insertQUANLY(mql,ps,ten, chucvu,phongban);
                    if (insertQUANLY == true) {
                        Toast.makeText(MainActivity.this,"Tạo tài khoảng thành công",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    } else {
                        Toast.makeText(MainActivity.this,"Tạo tài khoảng không thành công",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

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




}