package com.tikeii.napmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mng_staff_list_adapter extends RecyclerView.Adapter<mng_staff_list_adapter.staff_list_holder> {
    private Context context;
    private ArrayList msnv_id,hoten_id,chucvu_id;

    private TextView tv1;
    @NonNull
    @Override
    public staff_list_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.staff_list_cardview,parent,false);
       return new staff_list_holder(v);
    }

    public mng_staff_list_adapter(Context context, ArrayList msnv_id, ArrayList hoten_id, ArrayList chucvu_id) {
        this.context = context;
        this.msnv_id = msnv_id;
        this.hoten_id = hoten_id;
        this.chucvu_id = chucvu_id;
    }


    @Override
    public void onBindViewHolder(@NonNull mng_staff_list_adapter.staff_list_holder holder, @SuppressLint("RecyclerView") int position) {
        holder.msnv.setText(String.valueOf(msnv_id.get(position)));
        holder.hoten.setText(String.valueOf(hoten_id.get(position)));
        holder.chucvu.setText(String.valueOf(chucvu_id.get(position)));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText(String.valueOf(msnv_id.get(position)));
                clickTOGETIN4(tv1);
            }
        });
    }

    public void clickTOGETIN4(TextView manv) {
        String tv = manv.getText().toString();
        Intent i = new Intent(context, mng_staff_in4.class);
        i.putExtra("MANV_INPUT",tv);
        context.startActivity(i);
    }



    @Override
    public int getItemCount() {
        return msnv_id.size();
    }

    public class staff_list_holder extends RecyclerView.ViewHolder{
        TextView msnv,hoten,chucvu;
        CardView layout ;
        public staff_list_holder(@NonNull View itemView) {
            super(itemView);
           layout = itemView.findViewById(R.id.parentLayout_stu_list);
           tv1 = itemView.findViewById(R.id.text_cardview); tv1.setVisibility(View.INVISIBLE);
           msnv = itemView.findViewById(R.id.staff_list_view_msnv);
           hoten = itemView.findViewById(R.id.staff_list_view_hotennv);
           chucvu = itemView.findViewById(R.id.staff_list_view_chucvunv);

        }
    }
}
