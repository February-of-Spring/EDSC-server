package com.february.edsc.repository;

import com.february.edsc.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void save() {
        // given
        User user = new User();
        user.setName("jeongwon");

        // when
        Long savedId = userRepository.save(user);
        User foundUser = userRepository.findOne(savedId);

        // then
        Assertions.assertEquals(user, foundUser);
        Assertions.assertEquals(foundUser.getName(),user.getName());
    }
}