package com.example.vromia.e_nurseproject.Utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.vromia.e_nurseproject.R;

/**
 * Created by Vromia on 17/12/2014.
 */
public class HistoryAdapter extends CursorAdapter {


    private LayoutInflater inflater;

    public HistoryAdapter(Context context, Cursor c){
        super(context,c);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view=null;
        view=inflater.inflate(R.layout.history_list_item,parent,false);

        TextView tvCategory=(TextView) view.findViewById(R.id.tvCategory);
        TextView tvDate=(TextView) view.findViewById(R.id.tvDate);
        TextView tvHour=(TextView) view.findViewById(R.id.tvHour);
        TextView tvAmount=(TextView) view.findViewById(R.id.tvAmount);


        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

}
