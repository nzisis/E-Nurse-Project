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
 * Created by Alex on 12/7/2015
 */
public class MyDrugsAdapter extends CursorAdapter {


    private LayoutInflater inflater;


    public MyDrugsAdapter(Context context, Cursor c) {
        super(context, c);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = null;
        view = inflater.inflate(R.layout.my_drugs_list_item, parent, false);

        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        TextView tvCause = (TextView) view.findViewById(R.id.tvCause);

        tvCategory.setText(cursor.getString(1));
        tvCause.setText(cursor.getString(5));


        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        TextView tvCause = (TextView) view.findViewById(R.id.tvCause);

        tvCategory.setText(cursor.getString(1));
        tvCause.setText(cursor.getString(5));

    }

}