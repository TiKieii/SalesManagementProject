package com.tikeii.napmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class spinner_adapter extends ArrayAdapter<spinner_category> {
    public spinner_adapter(@NonNull Context context, int resource, @NonNull List<spinner_category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_selected_layout,parent,false);
        TextView tv_selected = convertView.findViewById(R.id.chucvu_selected_tv);

        spinner_category category = this.getItem(position);
        if (category != null) {
            tv_selected.setText(category.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_category_layout,parent,false);
        TextView tv_catelor = convertView.findViewById(R.id.chucvu_category_tv);

        spinner_category category = this.getItem(position);
        if (category != null) {
            tv_catelor.setText(category.getName());
        }
        return convertView;
    }
}
