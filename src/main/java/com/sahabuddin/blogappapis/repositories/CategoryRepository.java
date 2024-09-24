package com.sahabuddin.blogappapis.repositories;

import com.sahabuddin.blogappapis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
