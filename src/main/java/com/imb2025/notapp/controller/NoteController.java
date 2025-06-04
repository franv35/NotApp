package com.imb2025.notapp.controller;


import com.imb2025.notapp.service.INotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class NoteController {
    @Autowired
    private INotesService iNotesService;
    
}
