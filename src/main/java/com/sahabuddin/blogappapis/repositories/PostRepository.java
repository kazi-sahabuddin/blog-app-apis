package com.sahabuddin.blogappapis.repositories;

import com.sahabuddin.blogappapis.entities.Category;
import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);


}
