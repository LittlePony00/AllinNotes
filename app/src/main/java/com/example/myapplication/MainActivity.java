package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.NoteDB.CustomAdapter;
import com.example.myapplication.NoteDB.Note;
import com.example.myapplication.NoteDB.NoteDB;
import com.example.myapplication.NoteDB.RecyclerViewIntefrace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewIntefrace {
    private final int NEW_NOTE = 100;
    private final int EDIT_NOTE = 101;
    CustomAdapter noteAdapter;
    List<Note> noteList = new ArrayList<>();
    NoteDB db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = NoteDB.getInstance(this.getApplicationContext());
        Button new_note = findViewById(R.id.new_note);
        Button delete = findViewById(R.id.delete);
        new_note.setOnClickListener(view ->
                startActivityForResult(new Intent(MainActivity.this, Add_new_note_activity.class), NEW_NOTE));

        delete.setOnClickListener(view -> {
            deleteNote(0);
        });

        initRecycleView();
        loadNote();
    }


    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new CustomAdapter(this, this);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setNoteList(noteList);
    }

    private void deleteNote(int position) {
        noteList = db.noteDAO().getAllNotes();
        if (noteList.size() != 0) {
            db.noteDAO().delete(noteList.get(position));
            noteAdapter.deleteItemFromNoteList(position);
        }

    }

    private void insertNote() {
        noteList = db.noteDAO().getAllNotes();
        noteAdapter.insertItemToNoteList(noteList.get(0));

    }
    private void loadNote() {
        db = NoteDB.getInstance(this.getApplicationContext());
        noteList = db.noteDAO().getAllNotes();
        noteAdapter.setNoteList(noteList);
    }

    private void pinNote(int position){
        Collections.swap(noteList, 0, position);
        for (int i = 1; i < noteList.size()-1; i++){
            Collections.swap(noteList, i, position);
        }

        noteAdapter.setNoteList(noteList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_NOTE) {
            insertNote();
        }

        if (requestCode == EDIT_NOTE){
            loadNote();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(MainActivity.this, Add_new_note_activity.class);

        intent.putExtra("EDIT", String.valueOf(position));

        startActivityForResult(intent, EDIT_NOTE);
    }

    @Override
    public void onLongItemClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Диалог")
                .setMessage("Текст в диалоге")
                .setNegativeButton("Закрепить", (dialog, id) -> pinNote(position))
                .setPositiveButton("УДАЛИТЬ", (dialog, id) -> {
                    deleteNote(position);
                }
                );
        builder.create().show();
    }
}