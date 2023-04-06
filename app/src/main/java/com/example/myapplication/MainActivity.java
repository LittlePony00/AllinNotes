package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.NoteDB.CustomAdapter;
import com.example.myapplication.NoteDB.Note;
import com.example.myapplication.NoteDB.NoteDB;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomAdapter noteAdapter;
    List<Note> noteList;
    NoteDB db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = NoteDB.getInstance(this.getApplicationContext());
        Button new_note = findViewById(R.id.new_note);
        Button delete = findViewById(R.id.delete);

        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, Add_new_note_activity.class), 100);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
                initRecycleView();
                loadNote();
            }
        });

        initRecycleView();
        loadNote();
    }


    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        noteAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(noteAdapter);
    }

    private void deleteNote() {

        noteList = db.noteDAO().getAllNotes();

        if (noteList.size() != 0) {
            db.noteDAO().delete(noteList.get(noteList.size() - 1));
        }
        noteAdapter.setNoteList(noteList);

    }
    private void loadNote() {
        db = NoteDB.getInstance(this.getApplicationContext());
        noteList = db.noteDAO().getAllNotes();
        noteAdapter.setNoteList(noteList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            loadNote();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}