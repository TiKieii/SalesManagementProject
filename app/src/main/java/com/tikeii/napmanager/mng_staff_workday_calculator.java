package com.tikeii.napmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mng_staff_workday_calculator extends AppCompatActivity {
    AppCompatButton check;
    ImageButton back;
    TextView name,cv,pb,tv,tv2;
    EditText OTtime;
    RadioButton on,off;
    CheckBox OT;
    RadioGroup RG;
    DigitalClock clock;
    Spinner mnv;
    String clv,np,manv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_staff_workday_calculator);
        nap_database db = new nap_database(this);
        check = findViewById(R.id.wd_cal_check);
        back = findViewById(R.id.wd_cal_back);
        name = findViewById(R.id.wd_cal_name);
        cv = findViewById(R.id.wd_cal_cv);
        pb = findViewById(R.id.wd_cal_pb);
        OTtime = findViewById(R.id.wd_cal_onwork_OT_time); OTtime.setVisibility(View.INVISIBLE);
        on = findViewById(R.id.wd_cal_onwork);
        off = findViewById(R.id.wd_cal_offwork);
        OT = findViewById(R.id.wd_cal_onwork_OT); OT.setVisibility(View.INVISIBLE);
        RG = findViewById(R.id.wd_cal_RG);
        clock = findViewById(R.id.wd_cal_time);
        mnv = findViewById(R.id.wd_cal_cbx);
        tv = findViewById(R.id.wd_tv); tv.setVisibility(View.INVISIBLE);
        tv2 = findViewById(R.id.wd_cal_tv_OTsub); tv2.setVisibility(View.INVISIBLE);

        spinner_adapter adapter = new spinner_adapter(this,R.layout.spinner_selected_layout,getListStaff());
        mnv.setAdapter(adapter);

        mnv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(adapter.getItem(i).getName());
                manv = tv.getText().toString();
                Cursor cursor1 = db.getdatastaff_with_ID(manv);
                while (cursor1.moveToNext()) {
                    name.setText(cursor1.getString(0));
                    pb.setText(cursor1.getString(2));
                    cv.setText(cursor1.getString(1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (on.isChecked()) {
                    clv = "1";
                    np = "0";
                    OT.setVisibility(View.VISIBLE);
                    OT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (OT.isChecked()) {
                                OTtime.setVisibility(View.VISIBLE);
                                tv2.setVisibility(View.VISIBLE);
                                OTtime.getText().toString();
                            }
                        }
                    });

                }
            }
        });

        off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                OT.setVisibility(View.INVISIBLE);
                np = "1";
                clv = "0";
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dinhdang_ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dinhdang_mmyy = new SimpleDateFormat("MM/yyyy");
                String giovao = clock.getText().toString();
                String giotangca = OTtime.getText().toString();
                String thang = dinhdang_mmyy.format(calendar.getTime());
                String ngay = dinhdang_ddmmyyyy.format(calendar.getTime());
                boolean kq = db.insertCHAMCONG(thang,ngay,giovao,clv,np,giotangca,manv);
                if (kq == true) {
                    Toast.makeText(getApplicationContext(),"Đã chấm công cho "+manv+" !",Toast.LENGTH_LONG).show();
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
}