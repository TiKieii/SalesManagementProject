package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class mng_staff_salary_calculator extends AppCompatActivity {

    AppCompatButton save,check,update;
    ImageButton back,done;
    TextView name,cv,pb,tv,tv2,luongcb,thuclanh,on,off,ot;
    EditText thuong,phat,note;
    Spinner mnv,thang;
    String manv,thg;
    Double luongcoban;
    nap_database db = new nap_database(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_staff_salary_calculator);
        save = findViewById(R.id.salary_cal_save);
        check = findViewById(R.id.salary_cal_check);
        back = findViewById(R.id.salary_cal_back);
        done = findViewById(R.id.salary_cal_done);
        name = findViewById(R.id.salary_cal_name);
        cv = findViewById(R.id.salary_cal_cv);
        pb = findViewById(R.id.salary_cal_pb);
        tv = findViewById(R.id.salary_tv); tv.setVisibility(View.INVISIBLE);
        tv2 = findViewById(R.id.salary_tv2); tv2.setVisibility(View.INVISIBLE);
        luongcb = findViewById(R.id.salary_cal_or_salary);
        thuclanh = findViewById(R.id.salary_cal_temp_salary);
        thuong = findViewById(R.id.salary_cal_thuong);
        phat = findViewById(R.id.salary_cal_phat);
        note = findViewById(R.id.salary_cal_note);
        mnv = findViewById(R.id.salary_cal_cbx);
        thang = findViewById(R.id.salary_cal_month_cbx);
        on = findViewById(R.id.salary_cal_onday);
        off = findViewById(R.id.salary_cal_off);
        ot = findViewById(R.id.salary_cal_OT);
        update = findViewById(R.id.salary_cal_update);

        spinner_adapter adapter_manv = new spinner_adapter(this,R.layout.spinner_selected_layout,getListStaff());
        mnv.setAdapter(adapter_manv);

        mnv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(adapter_manv.getItem(i).getName());
                manv = tv.getText().toString();
                spinner_adapter adapter_thang = new spinner_adapter(getApplicationContext(),R.layout.spinner_selected_layout,getListCHAMCONG(manv));
                thang.setAdapter(adapter_thang);
                thang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        tv2.setText(adapter_thang.getItem(i).getName());
                        thg = tv2.getText().toString();
                        Cursor cursor = db.getdatastaff_with_ID_salary_cal(manv,thg);
                        while (cursor.moveToNext()) {
                            name.setText(cursor.getString(0));
                            pb.setText(cursor.getString(2));
                            cv.setText(cursor.getString(1));
                            luongcb.setText(cursor.getString(3));
                            on.setText(cursor.getString(4));
                            off.setText(cursor.getString(5));
                            ot.setText(cursor.getString(6));

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luongcoban = Double.parseDouble(String.valueOf(luongcb.getText().toString()));
                Double luongthuong = Double.parseDouble(String.valueOf(thuong.getText().toString()));
                Double luongphat = Double.parseDouble(String.valueOf(phat.getText().toString()));
                Double ngaycong =  Double.parseDouble(String.valueOf(on.getText().toString()));
                Double ngayphep =  Double.parseDouble(String.valueOf(off.getText().toString()));
                Double tangca =  Double.parseDouble(String.valueOf(ot.getText().toString()));
                String ghichu = note.getText().toString();
                Double ngaytinhluong = ngaycong+((tangca/24)*2) - ngayphep;
                Double luongthuclanh = ((luongcoban / 30)*ngaytinhluong) + luongthuong - luongphat;

                thuclanh.setText(String.valueOf(Math.round(luongthuclanh)));
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dinhdang_mmyyyy = new SimpleDateFormat("MM/yyyy");
                String thang = dinhdang_mmyyyy.format(calendar.getTime());
                Double lgthuong = Double.parseDouble(String.valueOf(thuong.getText().toString()));
                Double lgphat = Double.parseDouble(String.valueOf(phat.getText().toString()));
                Double ngaycong =  Double.parseDouble(String.valueOf(on.getText().toString()));
                Double ngayphep =  Double.parseDouble(String.valueOf(off.getText().toString()));
                Double tangca =  Double.parseDouble(String.valueOf(ot.getText().toString()));
                String ghichu = note.getText().toString();
                Double ngaytinhluong = ngaycong+((tangca/24)*2) - ngayphep;
                Double luongtamtinh = (luongcoban / 30)*ngaytinhluong;
                Toast.makeText(getApplicationContext(),"ngay"+ngaytinhluong+"luonhtt"+luongtamtinh,Toast.LENGTH_LONG).show();
                boolean kq = db.insertLUONG(thang,manv,String.valueOf(luongtamtinh),String.valueOf(lgthuong),String.valueOf(lgphat),ghichu);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Đã lưu lương cho "+manv+" !",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Đã tính lương cho tháng này",Toast.LENGTH_LONG).show();

                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dinhdang_mmyyyy = new SimpleDateFormat("MM/yyyy");
                String thang = dinhdang_mmyyyy.format(calendar.getTime());
                Double lgthuong = Double.parseDouble(String.valueOf(thuong.getText().toString()));
                Double lgphat = Double.parseDouble(String.valueOf(phat.getText().toString()));
                Double ngaycong =  Double.parseDouble(String.valueOf(on.getText().toString()));
                Double ngayphep =  Double.parseDouble(String.valueOf(off.getText().toString()));
                Double tangca =  Double.parseDouble(String.valueOf(ot.getText().toString()));
                String ghichu = note.getText().toString();
                Double ngaytinhluong = ngaycong+((tangca/24)*2) - ngayphep;
                Double luongtamtinh = (luongcoban / 30)*ngaytinhluong;
                boolean kq = db.updateLUONG(thang,manv,String.valueOf(luongtamtinh),String.valueOf(lgthuong),String.valueOf(lgphat),ghichu);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Đã cập nhật luong cho "+manv+" !",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Có lỗi xãy ra, Vui lòng thử lại",Toast.LENGTH_LONG).show();

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

    private List<spinner_category> getListStaff() {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_staff_stafflist();
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(1)));
        }
        return list;
    }

    private List<spinner_category> getListCHAMCONG(String id) {
        nap_database db = new nap_database(this);
        Cursor cursor = db.getdata_CHAMCONG_staff_salary_cal(id);
        List<spinner_category> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new spinner_category(cursor.getString(0)));
        }
        return list;
    }

}