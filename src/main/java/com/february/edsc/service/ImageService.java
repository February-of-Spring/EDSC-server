package com.february.edsc.service;

import com.february.edsc.domain.post.image.Image;
import com.february.edsc.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;

	public Optional<Image> findById(Long id) {
		return imageRepository.findById(id);
	}
}
