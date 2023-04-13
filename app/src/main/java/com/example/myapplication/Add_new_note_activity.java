package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.NoteDB.Note;
import com.example.myapplication.NoteDB.NoteDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Add_new_note_activity extends AppCompatActivity implements Serializable {

    EditText title, description;
    Button save, image_button;
    NoteDB db;
    Note note;
    String id = "";
    List<Note> noteList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);
        image_button = findViewById(R.id.image_button);

        editText();

        image_button.setOnClickListener(view -> createImage());
        save.setOnClickListener(view -> saveNewNote());
    }

    private void saveNewNote() {
        if (!title.getText().toString().isEmpty() || !description.getText().toString().isEmpty()) {

            db = NoteDB.getInstance(this.getApplicationContext());

            if (id != null) {
                noteList.get(Integer.parseInt(id)).title = title.getText().toString();
                noteList.get(Integer.parseInt(id)).description = description.getText().toString();
                db.noteDAO().update(noteList.get(Integer.parseInt(id)));

            } else {
                note = new Note();
                note.title = title.getText().toString();
                note.description = description.getText().toString();
                db.noteDAO().insert(note);
            }
        }

        finish();
    }

    public void editText() {
        id = getIntent().getStringExtra("EDIT");
        if (id != null) {
            db = NoteDB.getInstance(getApplicationContext());
            noteList = db.noteDAO().getAllNotes();
            title.setText(noteList.get(Integer.parseInt(id)).title);
            description.setText(noteList.get(Integer.parseInt(id)).description);
        }
    }

    private void createImage() {
        LinearLayout linearLayout = new LinearLayout(this);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(imageView);
    }

}