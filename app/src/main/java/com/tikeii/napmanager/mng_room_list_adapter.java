package com.tikeii.napmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mng_room_list_adapter extends RecyclerView.Adapter<mng_room_list_adapter.room_list_holder> {
    private Context context;
    private ArrayList mapb_id, tenpb_id;
    private TextView tv;



    @NonNull
    @Override
    public room_list_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mng_room_list_cardview,parent,false);
        return new room_list_holder(v);
    }
    public mng_room_list_adapter(Context context, ArrayList mapb_id, ArrayList tenpb_id) {
        this.context = context;
        this.mapb_id = mapb_id;
        this.tenpb_id = tenpb_id;
    }

    @Override
    public void onBindViewHolder(@NonNull mng_room_list_adapter.room_list_holder holder, @SuppressLint("RecyclerView") int position) {
        holder.mapb.setText(String.valueOf(mapb_id.get(position)));
        holder.tenpb.setText(String.valueOf(tenpb_id.get(position)));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(String.valueOf(mapb_id.get(position)));
                clickTOGETIN4(tv);
            }
        });
    }

    public void clickTOGETIN4(TextView mapb) {
        String id = mapb.getText().toString();
        Intent i = new Intent(context, mng_staff_of_room.class);
        i.putExtra("MAPB_INPUT",id);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mapb_id.size();
    }

    public class room_list_holder extends RecyclerView.ViewHolder {
        TextView tenpb,mapb;
        CardView layout ;
        public room_list_holder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.mng_room_list_cardview);
            tv = itemView.findViewById(R.id.text_room_cardview); tv.setVisibility(View.INVISIBLE);
            tenpb = itemView.findViewById(R.id.room_list_view_tenpb);
            mapb = itemView.findViewById(R.id.room_list_view_mapb);
        }
    }
}