package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
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

    private final int SELECT_IMAGE = 200;
    Button save, new_space;
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
        new_space = findViewById(R.id.new_space);

        new_space.setOnClickListener(view -> setImage());

        editText();
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

    private void setImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Диалог")
                .setMessage("Текст в диалоге")
                .setNegativeButton("Камера", (dialog, id) -> {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, SELECT_IMAGE);
                })
                .setPositiveButton("Галерея", (dialog, id) -> {
                            builder.create().show();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(intent, "Select picture"), SELECT_IMAGE);
                        }
                );
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_IMAGE) {
            LinearLayout constraintLayout = findViewById(R.id.add_new_note_activity);
            ImageView imageView = new ImageView(this);
            Uri selectedImage = Uri.parse(data.toUri(Intent.URI_ALLOW_UNSAFE));
            if (selectedImage != null)
                imageView.setImageURI(selectedImage);

            imageView.setOnLongClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Диалог")
                        .setMessage("Текст в диалоге")
                        .setNegativeButton("Отменить", (dialog, id) -> {})
                        .setPositiveButton("УДАЛИТЬ", (dialog, id) -> {
                                    imageView.setVisibility(View.GONE);
                                }
                        );
                builder.create().show();

                return true;
            });
            int height = Resources.getSystem().getDisplayMetrics().heightPixels / 2;
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            imageView.setLayoutParams(params);


            EditText editText = new EditText(this);
            editText.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            ));

            constraintLayout.addView(imageView);
            constraintLayout.addView(editText);


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}