package com.cleyxds.springcustomers.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageServiceRepo {

	void store(MultipartFile file, Long id);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void loadImageById(Long id);

	boolean deleteById(Long id);

}
