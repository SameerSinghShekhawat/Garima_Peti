package com.example.garimapeti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.garimapeti.R;
import com.example.garimapeti.entity.ReportEntity;

import java.util.List;

public class ComplaintListAdapter extends ArrayAdapter<ReportEntity> {
    private Context mContext;
    private int mResource;
    public ComplaintListAdapter(@NonNull Context context, int resource, @NonNull List<ReportEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView textView1 = convertView.findViewById(R.id.nameTextView);
        TextView textView2 = convertView.findViewById(R.id.complaintTV);

        textView1.setText(getItem(position).getName());
        textView2.setText(getItem(position).getReportDesc());

        return convertView;
    }
}
