package net.cleyxds.sqlite.domain.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageServiceRepository {

	void init();

	void store(MultipartFile file, Long id);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	Path loadById(Long id);

	void deleteById(Long id);

}
