package com.example.jamakick.mypantry;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryGridViewAdapter extends ArrayAdapter<PantryGridItem> {

    //same as pantrygridviewadapter

    private Context context;
    private int layoutResource;
    ArrayList<PantryGridItem> data = new ArrayList<>();


    GroceryGridViewAdapter(Context context, int resource, ArrayList<PantryGridItem> data) {
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
            LayoutInflater inflater = ((GroceryActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = row.findViewById(R.id.item_text);
            row.setTag(holder);

        }

        else {
            holder = (RecordHolder) row.getTag();
        }

        PantryGridItem item = data.get(position);
        holder.txtTitle.setText(item.toString());
        holder.txtTitle.setTag(item.getItemID());
        return row;
    }


    static class RecordHolder {
        TextView txtTitle;
    }

}
