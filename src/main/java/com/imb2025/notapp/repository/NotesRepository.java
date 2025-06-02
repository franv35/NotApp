package com.imb2025.notapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imb2025.notapp.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

}