package com.february.edsc.repository;

import com.february.edsc.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager entityManager;

    public Long save(Post post) {
        entityManager.persist(post);
        return post.getId();
    }

    public Post findOne(Long id) {
        return entityManager.find(Post.class, id);
    }
//    public List<Post> searchAll(PostSearch postSearch) {
//
//    }
}
