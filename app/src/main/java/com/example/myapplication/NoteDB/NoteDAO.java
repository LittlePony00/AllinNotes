package com.example.myapplication.NoteDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * from noteDB")
    List<Note> getAllNotes();

    @Insert()
    void insert(Note... notes);

    @Delete()
    void delete(Note note);

}
