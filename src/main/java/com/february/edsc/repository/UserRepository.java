package com.february.edsc.repository;

import com.february.edsc.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public Long save(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    public User findOne(Long userId) {
        return entityManager.find(User.class, userId);
    }

    public List<User> findByName(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
