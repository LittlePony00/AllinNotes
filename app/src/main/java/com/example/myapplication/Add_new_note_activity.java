package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.NoteDB.Note;
import com.example.myapplication.NoteDB.NoteDB;

public class Add_new_note_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewNote(title.getText().toString(), description.getText().toString());
            }
        });
    }

    private void saveNewNote(String title, String descritpion) {
        NoteDB db = NoteDB.getInstance(this.getApplicationContext());
        Note note = new Note();
        note.title = title;
        note.description = descritpion;
        db.noteDAO().insert(note);

        finish();
    }
}