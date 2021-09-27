package com.example.SpringBootNotes.dao;

import com.example.SpringBootNotes.models.Note;

import java.util.List;

public interface NotesDao {

    void addNote(Note note);
    List<Note> showAllNotes();
    List<Note> searchBySubstring(String substring);
    void deleteNote(Long id);
    Note showNoteById(Long id);
    void update(Long id, Note note);
}
