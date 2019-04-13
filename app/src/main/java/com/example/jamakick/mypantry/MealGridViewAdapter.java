package com.example.jamakick.mypantry;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MealGridViewAdapter extends ArrayAdapter<MealItem> {

    //same as pantrygridviewadapter

    private Context context;
    private int layoutResource;
    ArrayList<MealItem> data = new ArrayList<>();


    MealGridViewAdapter(Context context, int resource, ArrayList<MealItem> data) {
        super(context, resource, data);

        this.layoutResource = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((MealPlanActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = row.findViewById(R.id.item_text);
            row.setTag(holder);

        }

        else {
            holder = (RecordHolder) row.getTag();
        }

        MealItem item = data.get(position);
        holder.txtTitle.setText(item.toString());
        holder.txtTitle.setTag(item.getMealID());
        return row;
    }


    static class RecordHolder {
        TextView txtTitle;
    }

}
