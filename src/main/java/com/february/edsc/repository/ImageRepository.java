package com.february.edsc.repository;

import com.february.edsc.domain.post.image.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
