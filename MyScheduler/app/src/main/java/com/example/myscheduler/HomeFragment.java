package com.example.myscheduler;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    EditText editText_TaskSearch;
    ArrayList<DatabaseStructure.Task> lstTodayTasks;
    ArrayList<DatabaseStructure.Task> lstTomorrowTasks;
    ArrayList<DatabaseStructure.Task> lstOtherTasks;
    CustomHomeAdapter taskArrayAdapter;
    TextView textView_today;
    TextView textView_tomorrow;
    TextView textView_otherTasks;
    ListView listView_today;
    ListView listView_tomorrow;
    ListView listView_otherTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        editText_TaskSearch= getView().findViewById(R.id.editText_TaskSearch);
        textView_today = getView().findViewById(R.id.textView_today);
        textView_tomorrow = getView().findViewById(R.id.textView_tomorrow);
        textView_otherTasks = getView().findViewById(R.id.textView_otherTasks);

        DatabaseHelper objDatabaseHelper = new DatabaseHelper(view.getContext());
        lstTodayTasks = objDatabaseHelper.getTodayTask();
        taskArrayAdapter = new CustomHomeAdapter(lstTodayTasks,view.getContext());
        listView_today = getView().findViewById(R.id.listView_today);
        listView_today.setAdapter(taskArrayAdapter);

        lstTomorrowTasks = objDatabaseHelper.getTomorrowTask();
        taskArrayAdapter = new CustomHomeAdapter(lstTomorrowTasks,view.getContext());
        listView_tomorrow = getView().findViewById(R.id.listView_tomorrow);
        listView_tomorrow.setAdapter(taskArrayAdapter);

        lstOtherTasks = objDatabaseHelper.getOtherTask();
        taskArrayAdapter = new CustomHomeAdapter(lstOtherTasks,view.getContext());
        listView_otherTasks = getView().findViewById(R.id.listView_otherTasks);
        listView_otherTasks.setAdapter(taskArrayAdapter);


        editText_TaskSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                textView_today.setVisibility(View.GONE);
                textView_tomorrow.setVisibility(View.GONE);
                textView_otherTasks.setVisibility(View.GONE);
                listView_today.setVisibility(View.GONE);
                listView_tomorrow.setVisibility(View.GONE);
                lstOtherTasks = objDatabaseHelper.searchTasks(s.toString());
                taskArrayAdapter = new CustomHomeAdapter(lstOtherTasks,view.getContext());
                listView_otherTasks.setAdapter(taskArrayAdapter);
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
