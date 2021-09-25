package com.cleyxds.springcustomers.domain.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

@Repository
public interface ImageServiceRepo {

	void store(MultipartFile file, Long id);
	Stream<Path> loadAll();
	Path load(String filename);
	Resource loadAsResource(String filename);
	URI loadImageById(Long id);
	Path loadPathById(Long id);
	boolean deleteById(Long id);

}
