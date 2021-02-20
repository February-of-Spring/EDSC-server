package com.february.edsc.repository;

import com.february.edsc.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserJpaRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
