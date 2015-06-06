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
    private boolean isDiet;


    public HistoryAdapter(Context context, Cursor c, boolean isDiet) {
        super(context, c);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isDiet = isDiet;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = null;
        view = inflater.inflate(R.layout.history_list_item, parent, false);

        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
        TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
        TextView tvUnit = (TextView) view.findViewById(R.id.tvUnit);

        tvCategory.setText(cursor.getString(1));
        String sep = " at ";
        if (isDiet) {
            tvUnit.setText("(pieces)");
        } else {
            tvUnit.setText("(minutes)");
            sep = ",";
        }
        tvDateTime.setText(cursor.getString(2) + sep + cursor.getString(4));
        tvAmount.setText(cursor.getString(3));


        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
        TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
        TextView tvUnit = (TextView) view.findViewById(R.id.tvUnit);

        tvCategory.setText(cursor.getString(1));
        String sep = " at ";
        if (isDiet) {
            tvUnit.setText("(pieces)");
        } else {
            tvUnit.setText("(minutes)");
            sep = ",";
        }
        tvDateTime.setText(cursor.getString(2) + sep + cursor.getString(4));
        tvAmount.setText(cursor.getString(3));

    }

}
