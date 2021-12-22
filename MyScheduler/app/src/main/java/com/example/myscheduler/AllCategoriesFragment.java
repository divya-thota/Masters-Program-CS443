package com.example.myscheduler;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AllCategoriesFragment extends Fragment {


    EditText editText_CategorySearch;
    ArrayList<DatabaseStructure.Category> lstAllCategories;
    CustomCategoriesAdapter categoryArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_allcategories, container, false);
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        editText_CategorySearch= getView().findViewById(R.id.editText_CategorySearch);
        DatabaseHelper objDatabaseHelper = new DatabaseHelper(view.getContext());
        lstAllCategories = objDatabaseHelper.getAllCategories();
        categoryArrayAdapter = new CustomCategoriesAdapter(lstAllCategories,view.getContext());
        ListView listView_Categories = getView().findViewById(R.id.listView_Categories);
        listView_Categories.setAdapter(categoryArrayAdapter);

        listView_Categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DatabaseStructure.Category selectedCategory= lstAllCategories.get(position);
                //getActivity().setContentView(R.layout.onclick_category);

                Snackbar.make(view, selectedCategory.CATEGORY_NAME, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
        editText_CategorySearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                lstAllCategories = objDatabaseHelper.searchCategories(s.toString());
                categoryArrayAdapter = new CustomCategoriesAdapter(lstAllCategories,view.getContext());
                listView_Categories.setAdapter(categoryArrayAdapter);
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}