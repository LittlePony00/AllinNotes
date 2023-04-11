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

    EditText title, description;
    Button save;
    NoteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);
        editText();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewNote();
            }
        });
    }

    private void saveNewNote() {
        if (!title.getText().toString().isEmpty() || !description.getText().toString().isEmpty()) {
            db = NoteDB.getInstance(this.getApplicationContext());
            Note note = new Note();
            note.title = title.getText().toString();
            note.description = description.getText().toString();
            db.noteDAO().insert(note);
        }

        finish();
    }

    private void editText() {
        String edit = getIntent().getStringExtra("EditNote");
        title.setText(edit);
    }
}