package com.february.edsc.service;

import com.february.edsc.domain.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void join() {
        // given
        User user = new User();
        user.setName("jeongwon");

        // when
        Long savedId = userService.join(user);

        // then
        Assertions.assertEquals(savedId, user.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void validateDuplicateMembers() {
        // given
        User user1 = new User();
        user1.setName("jeongwon");

        User user2 = new User();
        user2.setName("jeongwon");

        // when
        userService.join(user1);
        userService.join(user2);

        // then
        fail("예외가 발생해야 한다.");
    }
}