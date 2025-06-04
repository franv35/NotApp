package com.imb2025.notapp.repository;

import com.imb2025.notapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
Optional<Note> findByTitle(String title);
}