package com.example.SpringBootNotes.controllers;

import com.example.SpringBootNotes.dao.NotesDao;
import com.example.SpringBootNotes.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NotesDao notesDao;

    @Autowired
    public NotesController(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    @GetMapping()
    public String listNotes(Model model) {
        model.addAttribute("notes", notesDao.showAllNotes()
                .stream().sorted(Comparator.comparing(Note::getTitle)).collect(Collectors.toList()));
        return "list";
    }

    @GetMapping("/{id}")
    public String showNote(@PathVariable("id") Long id, Model model) {
        model.addAttribute("note", notesDao.showNoteById(id));
        return "show";
    }

    @GetMapping("/new")
    public String newNote(@ModelAttribute("note") Note note) {
        return "new";
    }

    @PostMapping
    public String createNote(@ModelAttribute("note") Note note) {
        notesDao.addNote(note);
        return "redirect:/notes";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("note", notesDao.showNoteById(id));
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("note") Note note,
                         @PathVariable("id") Long id) {
        notesDao.update(id, note);
        return "redirect:/notes";
    }

    @PostMapping("/remove/{id}")
    public String delete(@PathVariable("id") Long id) {
        notesDao.deleteNote(id);
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        notesDao.deleteNote(id);
        return "redirect:/notes";
    }

    @GetMapping("/search")
    public String searchBySubstring(@RequestParam(value = "substring", required = false) String substring,
                                    Model model) {
        if (substring == null || substring.trim().isEmpty()) {
            return "redirect:/notes";
        } else {
            model.addAttribute("notes", notesDao.searchBySubstring(substring));
        }
        return "list";
    }

    @GetMapping("/notes/search")
    public String searchAfterSearch(@RequestParam(value = "substring", required = false) String substring) {
        return "redirect:/notes/search?substring=" + substring;
    }
}
