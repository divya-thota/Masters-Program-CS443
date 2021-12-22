package com.example.myscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class CustomCategoriesAdapter extends ArrayAdapter<DatabaseStructure.Category> implements View.OnClickListener{

    private ArrayList<DatabaseStructure.Category> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView textView_CategoryName;
        ImageView imageView_EditIcon;
    }

    public CustomCategoriesAdapter(ArrayList<DatabaseStructure.Category> data, Context context) {
        super(context, R.layout.listview_categories, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DatabaseStructure.Category dataModel=(DatabaseStructure.Category)object;

        switch (v.getId())
        {
            case R.id.imageView_EditIcon:
                Snackbar.make(v, ""+dataModel.CATEGORY_PRIORITY, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseStructure.Category dataModel = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_categories, parent, false);
            viewHolder.textView_CategoryName = (TextView) convertView.findViewById(R.id.textView_CategoryName);
            viewHolder.imageView_EditIcon = (ImageView) convertView.findViewById(R.id.imageView_EditIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView_CategoryName.setText(dataModel.CATEGORY_NAME);
        viewHolder.imageView_EditIcon.setOnClickListener(this);
        viewHolder.imageView_EditIcon.setTag(position);

        return convertView;
    }
}
