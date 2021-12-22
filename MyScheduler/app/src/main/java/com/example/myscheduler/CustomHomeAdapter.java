package com.example.myscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomHomeAdapter extends ArrayAdapter<DatabaseStructure.Task>{

    private ArrayList<DatabaseStructure.Task> dataSet;
    Context mContext;

    private static class ViewHolder {
        CheckBox checkBox_tasks;
        TextView textView_taskDate;
    }

    public CustomHomeAdapter(ArrayList<DatabaseStructure.Task> data, Context context) {
        super(context, R.layout.listview_home, data);
        this.dataSet = data;
        this.mContext=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseStructure.Task dataModel = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_home, parent, false);
            viewHolder.checkBox_tasks = (CheckBox) convertView.findViewById(R.id.checkBox_tasks);
            viewHolder.textView_taskDate = (TextView) convertView.findViewById(R.id.textView_taskDate);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox_tasks.setChecked(dataModel.TASK_ACTIVE);
        viewHolder.checkBox_tasks.setText(dataModel.TASK_NAME);
        viewHolder.textView_taskDate.setText(String.valueOf(dataModel.TASK_DATE));
        View finalConvertView = convertView;
        viewHolder.checkBox_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    boolean success;
                    DatabaseHelper objDatabaseHelper = new DatabaseHelper(finalConvertView.getContext());
                    success = objDatabaseHelper.updateTask(dataModel.TASK_ID, true);
                    if (success) {
                        Toast.makeText(finalConvertView.getContext(), "Task Updated successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    boolean success;
                    DatabaseHelper objDatabaseHelper = new DatabaseHelper(finalConvertView.getContext());
                    success = objDatabaseHelper.updateTask(dataModel.TASK_ID, false);
                    if (success) {
                        Toast.makeText(finalConvertView.getContext(), "Task Updated successfully",
                                Toast.LENGTH_SHORT).show();
                    }

                }
             }
         }
        );

        return convertView;
    }

}
