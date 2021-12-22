package com.example.myscheduler;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myscheduler.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    DatabaseHelper objDatabaseHelper = new DatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_allCategories, R.id.navigation_home, R.id.navigation_note)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    public void showAddMenu(View view){
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_menu, popup.getMenu());
        popup.show();
    }


    public boolean onMenuItemClick(MenuItem item) {
        LayoutInflater inflater;
        View popupView;
        int width;
        int height;
        final PopupWindow popupWindow;
        switch (item.getItemId()) {
            case R.id.add_task:
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = inflater.inflate(R.layout.popup_addtask, null);
                width = LinearLayout.LayoutParams.WRAP_CONTENT;
                height = LinearLayout.LayoutParams.WRAP_CONTENT;
                popupWindow = new PopupWindow(popupView, width, height, true);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                Spinner spinner_categoryId = popupView.findViewById(R.id.spinner_categoryId);
                ArrayList<String> lstAllCategories = objDatabaseHelper.getTaskCategories();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstAllCategories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_categoryId.setAdapter(adapter);
                EditText editText_taskName = popupView.findViewById(R.id.editText_taskName);
                EditText editText_taskDate = popupView.findViewById(R.id.editText_taskDate);
                Button button_createTask = popupView.findViewById(R.id.button_createTask);
                SimpleDateFormat formatter=new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
                button_createTask.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        boolean success = false;
                        success = objDatabaseHelper.insertTask(editText_taskName.getText().toString(), 0, false, editText_taskDate.getText().toString());

                        if(success){
                            Toast.makeText(MainActivity.this, "Task added successfully",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
                return true;
            case R.id.add_category:
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = inflater.inflate(R.layout.popup_addcategory, null);
                width = LinearLayout.LayoutParams.WRAP_CONTENT;
                height = LinearLayout.LayoutParams.WRAP_CONTENT;
                popupWindow = new PopupWindow(popupView, width, height, true);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                EditText editText_categoryName = popupView.findViewById(R.id.editText_categoryName);
                EditText editText_CategoryPriority = popupView.findViewById(R.id.editText_CategoryPriority);
                Button button_createCategory = popupView.findViewById(R.id.button_createCategory);
                button_createCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean success;
                        try (DatabaseHelper objDatabaseHelper = new DatabaseHelper(MainActivity.this)) {
                            success = objDatabaseHelper.insertCategory(editText_categoryName.getText().toString(), Integer.parseInt(editText_CategoryPriority.getText().toString()), true);
                        }
                        if(success){
                            Toast.makeText(MainActivity.this, "Category added successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return true;
            case R.id.add_note:
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = inflater.inflate(R.layout.popup_addnote, null);
                width = LinearLayout.LayoutParams.WRAP_CONTENT;
                height = LinearLayout.LayoutParams.WRAP_CONTENT;
                popupWindow = new PopupWindow(popupView, width, height, true);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                EditText editText_noteTitle = popupView.findViewById(R.id.editText_noteTitle);
                EditText editText_noteDescription = popupView.findViewById(R.id.editText_noteDescription);
                Button button_createNote = popupView.findViewById(R.id.button_createNote);
                button_createNote.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        boolean success;
                        try (DatabaseHelper objDatabaseHelper = new DatabaseHelper(MainActivity.this)) {
                            success = objDatabaseHelper.insertNote(editText_noteTitle.getText().toString(), editText_noteDescription.getText().toString(), new Date());
                        }
                        if(success){
                            Toast.makeText(MainActivity.this, "Note added successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return true;
            default:
                return false;
        }
    }

}