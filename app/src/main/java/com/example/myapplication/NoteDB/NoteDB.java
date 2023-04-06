package com.example.myapplication.NoteDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDB extends RoomDatabase {

    public abstract NoteDAO noteDAO();

    private static NoteDB INSTANCE;

    public static NoteDB getInstance(Context context){

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteDB.class, "NOTE_DB")
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
