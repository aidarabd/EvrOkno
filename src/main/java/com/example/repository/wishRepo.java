package com.example.repository;

import com.example.model.wishes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface wishRepo extends JpaRepository<wishes, Integer> {
    List<wishes> findFirst3ByOrderByDateDesc();
    List<wishes> findFirst3ByOrderByDateAsc();
}
