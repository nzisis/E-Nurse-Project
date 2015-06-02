package com.example.vromia.e_nurseproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vromia.e_nurseproject.Data.GridItem;
import com.example.vromia.e_nurseproject.R;

import java.util.ArrayList;

/**
 * Created by Vromia on 2/6/2015.
 */
public class GridAdapter extends ArrayAdapter<GridItem> {

    private int layoutResourceId;
    private Context context;
    private ArrayList<GridItem> data;



    public GridAdapter(Context context, int layoutResourceId, ArrayList<GridItem> data) {
    super(context, layoutResourceId, data);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.data = data;
}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new Holder();
            holder.txtTitle = (TextView) row.findViewById(R.id.tvGridItem);
            holder.imageItem = (ImageView) row.findViewById(R.id.ivGridItem);
            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        GridItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        /*
        if(item.getTitle().equals("E Substances")){
            holder.txtTitle.setTextColor(getResources().getColor(R.color.esubstancesmenu));
        }else if(item.getTitle().equals("Diseases")){
            holder.txtTitle.setTextColor(getResources().getColor(R.color.diseasemenu));
        }else if(item.getTitle().equals("Exit")){
            holder.txtTitle.setTextColor(getResources().getColor(R.color.exitmenu));
        }*/
        holder.imageItem.setImageBitmap(item.getImage());
        return row;

    }



    class Holder {
        TextView txtTitle;
        ImageView imageItem;

    }







}
