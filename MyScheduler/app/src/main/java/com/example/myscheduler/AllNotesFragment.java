package com.example.myscheduler;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;


public class AllNotesFragment extends Fragment {

    EditText editText_NoteSearch;
    ArrayList<DatabaseStructure.Note> lstAllNotes;
    CustomNotesAdapter noteArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_allnotes, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editText_NoteSearch = getView().findViewById(R.id.editText_NoteSearch);
        DatabaseHelper objDatabaseHelper = new DatabaseHelper(view.getContext());
        lstAllNotes = objDatabaseHelper.getAllNotes();
        noteArrayAdapter = new CustomNotesAdapter(lstAllNotes, view.getContext());
        ListView listView_Notes = getView().findViewById(R.id.listView_Notes);
        listView_Notes.setAdapter(noteArrayAdapter);
        listView_Notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DatabaseStructure.Note selectedNote= lstAllNotes.get(position);

                Snackbar.make(view, selectedNote.NOTE_TITLE, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        editText_NoteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lstAllNotes = objDatabaseHelper.searchNotes(s.toString());
                noteArrayAdapter = new CustomNotesAdapter(lstAllNotes, view.getContext());
                listView_Notes.setAdapter(noteArrayAdapter);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
