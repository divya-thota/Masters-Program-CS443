package com.example.myscheduler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomNotesAdapter extends ArrayAdapter<DatabaseStructure.Note>{

    private ArrayList<DatabaseStructure.Note> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView textView_NoteTilte;
        TextView textView_NoteTime;
        TextView textView_NoteDescription;
    }

    public CustomNotesAdapter(ArrayList<DatabaseStructure.Note> data, Context context) {
        super(context, R.layout.listview_notes, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseStructure.Note dataModel = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_notes, parent, false);
            viewHolder.textView_NoteTilte = (TextView) convertView.findViewById(R.id.textView_NoteTilte);
            viewHolder.textView_NoteTime = (TextView) convertView.findViewById(R.id.textView_NoteTime);
            viewHolder.textView_NoteDescription = (TextView) convertView.findViewById(R.id.textView_NoteDescription);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.textView_NoteTilte.setText(dataModel.NOTE_TITLE);
        viewHolder.textView_NoteTime.setText(String.valueOf(dataModel.NOTE_DATE));
        viewHolder.textView_NoteDescription.setText(dataModel.NOTE_DESCRIPTION);
        return convertView;
    }
}
