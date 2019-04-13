package com.example.jamakick.mypantry;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//gridview adapter for our pantry activity
public class PantryGridViewAdapter extends ArrayAdapter<PantryGridItem> {

    //create variables for our context, layout, and arraylist
    private Context context;
    private int layoutResource;
    ArrayList<PantryGridItem> data = new ArrayList<>();


    //constructor for our adapter using our superclass arrayadapter
    PantryGridViewAdapter(Context context, int resource, ArrayList<PantryGridItem> data) {
        super(context, resource, data);

        this.layoutResource = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create our row and holder
        View row = convertView;
        RecordHolder holder = null;

        //if the row doesn't exist, we create it with the layout inflater and add our holder data
        if (row == null) {
            LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = row.findViewById(R.id.item_text);
            row.setTag(holder);

        }

        //if it does exist we just set the holder to be the tag on the row
        else {
            holder = (RecordHolder) row.getTag();
        }

        //create our pantrygrid item to add to our holder

        PantryGridItem item = data.get(position);
        //set the text to display in our view
        holder.txtTitle.setText(item.toString());
        //set the tag so we can get what item is being clicked on later
        holder.txtTitle.setTag(item.getItemID());
        return row;
    }


    //holder that holds the text view
    static class RecordHolder {
        TextView txtTitle;
    }

}
